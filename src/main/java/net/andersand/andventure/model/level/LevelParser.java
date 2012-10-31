package net.andersand.andventure.model.level;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.Util;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.level.objectives.*;
import net.andersand.andventure.model.level.script.Script;
import net.andersand.andventure.view.GUIAccessor;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Most of the level parsing is handled here
 * 
 * @author asn
 */
public class LevelParser {

    protected Map<String, Class<? extends Element>> elementLookupTable = new HashMap<String, Class<? extends Element>>();
    protected Map<String, Field> metaLookupTable = new HashMap<String, Field>();
    protected Map<String, Class<? extends Objective>> objectivesLookupTable = new HashMap<String, Class<? extends Objective>>();
    protected int tallestLevel;
    protected int widestLevel;
    private GUIAccessor guiAccessor;

    public LevelParser(GUIAccessor guiAccessor) {
        this.guiAccessor = guiAccessor;
        populateLookupTables();
    }

    protected void populateLookupTables() {
        elementLookupTable.put("w", Wall.class);
        elementLookupTable.put("W", Wall.class);
        elementLookupTable.put("p", Player.class);
        elementLookupTable.put("f", Foe.class);
        elementLookupTable.put("n", Neutral.class);
        elementLookupTable.put("|", Door.class);
        elementLookupTable.put("-", Door.class);
        elementLookupTable.put("r", Ring.class);
        elementLookupTable.put("c", Corpse.class);
        elementLookupTable.put("t", Tree.class);
        elementLookupTable.put("T", Table.class);
        elementLookupTable.put("d", Desk.class);
        elementLookupTable.put("c", Chair.class);
        elementLookupTable.put("C", Chair.class);
        try {
            metaLookupTable.put("name", Meta.class.getField("name"));
            metaLookupTable.put("description", Meta.class.getField("description"));
            metaLookupTable.put("objectives", Meta.class.getField("objectives"));
            metaLookupTable.put("environment", Meta.class.getField("environment"));
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        objectivesLookupTable.put("moveTo", MoveToObjective.class);
        objectivesLookupTable.put("object", RetrieveObjectObjective.class);
        objectivesLookupTable.put("rescue", RescueObjective.class);
        objectivesLookupTable.put("defend", DefendObjective.class);
        objectivesLookupTable.put("assassinate", AssassinationObjective.class);
        objectivesLookupTable.put("defeatAllFoes", DefeatAllFoesObjective.class);
    }

    /**
     * Goes through level data and creates game elements when appropriate.
     * May also perform som validation
     */
    public void parse(String levelData, String levelFileName, Level level) {
        List<String> lines = getLines(levelData);
        List<String> metaLines = getMetaLines(lines);
        List<String> scriptLines = getScriptLines(lines);
        lines = removeNonMapLines(lines);
        List<Element> elements = new ArrayList<Element>();
        try {
            level.setMeta(parseMetaData(metaLines));
            level.setScript(parseScript(scriptLines));
            int n = 0;
            for (String line : lines) {
                elements.addAll(parseLine(level, line, n++));
            }
            level.setElements(elements);
            level.handleContiguousElements();
        }
        catch (Exception e) {
            e.printStackTrace();
            throwException("level.parsing.failed", levelFileName);
        }
    }

    protected Script parseScript(List<String> scriptLines) throws IllegalAccessException, InstantiationException {
        return new ScriptParser(guiAccessor).parse(scriptLines);
    }

    public Meta parseMetaData(List<String> metaLines) throws IllegalAccessException, InstantiationException {
        Meta meta = new Meta();
        for (String metaLine : metaLines) {
            String[] parts = metaLine.split("=");
            String key = parts[0].split(" ")[1];
            String value = parts.length == 2 ? metaLine.split("=")[1] : "";
            Field field = metaLookupTable.get(key);
            if (field == null) {
                throwException("level.parsing.failed.invalid.metadata.key", key);
            }
            else {
                if (field.getType() == String.class) {
                    field.set(meta, value);
                }
                else if ("objectives".equals(key)) {
                    List<Objective> objectives = new ArrayList<Objective>();
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        for (String objectiveStr : values) {
                            objectives.add(handleObjective(objectiveStr));
                        }
                    }
                    else {
                        objectives.add(handleObjective(value));
                    }
                    meta.setObjectives(objectives);
                }
            }
        }
        return meta;
    }

    protected Objective handleObjective(String objectiveStr) throws IllegalAccessException, InstantiationException {
        Class<? extends Objective> objective;
        String objectiveValue = "";
        if (objectiveStr.contains("@")) {
            String[] parts = objectiveStr.split("@");
            objective = objectivesLookupTable.get(parts[0]);
            objectiveValue = parts[1];
        }
        else {
            objective = objectivesLookupTable.get(objectiveStr);
        }
        if (objective == null) {
            throwException("level.parsing.failed.invalid.objective.type", objectiveStr, list(objectivesLookupTable.keySet()));
        }
        Objective o = objective.newInstance();
        o.setValue(objectiveValue);
        return o;
    }

    protected String list(Set<String> strings) {
        StringBuilder ret = new StringBuilder();
        for (String string : strings) {
            ret.append(string).append(", ");
        }
        return ret.delete(ret.length()-2,ret.length()).toString();
    }
    
    protected List<String> getMetaLines(List<String> lines) {
        List<String> metaLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.startsWith(Const.LEVEL_META)) {
                metaLines.add(line);
            }
        }
        return metaLines;
    }

    protected List<String> getScriptLines(List<String> lines) {
        List<String> scriptLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.startsWith(Const.LEVEL_SCRIPT)) {
                scriptLines.add(line);
            }
        }
        return scriptLines;
    }

    protected List<Element> parseLine(Level level, String levelDataLine, int yPosition) throws IllegalAccessException, InstantiationException {
        Script script = level.getScript();
        List<Element> elements = new ArrayList<Element>();
        int xPosition = 0;
        for (char c : levelDataLine.toCharArray()) {
            Class cls = elementLookupTable.get(String.valueOf(c));
            if (c != ' ' && cls == null) {
                // floors are configurable, 
                // but any other unmapped char should be reported
                throwException("level.parsing.failed.unknown.char", String.valueOf(c));
            }
            if (cls != null) {
                Element element = (Element)cls.newInstance();
                element.setPosition(new Position(xPosition, yPosition));
                element.setElementLevelInteraction(level);
                if (element instanceof Creature) {
                    ((Creature)element).setCreatureLevelInteraction(level);
                }
                element.init(c, script);
                elements.add(element);
            }
            xPosition++;
        }
        return elements;
    }

    /**
     * Coarsely validates level data consistency without parsing
     * @return level dimension
     */
    public Bounds validateCoarsely(String levelData, String levelFileName) {
        List<String> lines = getLines(levelData);
        lines = removeNonMapLines(lines);
        int levelHeight = lines.size();
        int longestLine = 0;
        int shortestLine = 99999;
        for (String line : lines) {
            if (line.length() > longestLine) {
                longestLine = line.length();
            }
            if (line.length() < shortestLine) {
                shortestLine = line.length();
            }
        }
        // check squareness
        if (longestLine != shortestLine) {
            throwException("level.loading.failed.not.square", levelFileName);
        }
        // check if too high or wide 
        // (will level fit in a 1280x1024 screen if tiles are 15px, assuming some height is used by start menu)
        if (longestLine > 85 || levelHeight > 60) {
            throwException("level.loading.failed.too.big", levelFileName);
        }
        updateTallestWidestLevel(levelHeight, longestLine);
        return new Bounds(longestLine, levelHeight);
    }

    protected List<String> removeNonMapLines(List<String> lines) {
        List<String> mapLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.length() != 0 && !line.startsWith(Const.LEVEL_META) && !line.startsWith(Const.LEVEL_SCRIPT)) {
                mapLines.add(line);
            }
        }
        return mapLines;
    }

    protected void throwException(String propertyKey, String... parameters) {
        String text = PropertyHolder.get(propertyKey);
        for (String parameter : parameters) {
            text = text.replaceFirst("\\$", parameter);
        }
        throw new IllegalStateException(text);
    }

    protected void updateTallestWidestLevel(int levelHeight, int levelWidth) {
        if (levelHeight > tallestLevel) {
            tallestLevel = levelHeight;
        }
        if (levelWidth > widestLevel) {
            widestLevel = levelWidth;
        }
    }

    protected static List<String> getLines(String levelData) {
        return Util.toArrayList(levelData.split("\n"));
    }

    public Bounds getLevelBounds() {
        return new Bounds(widestLevel * Const.ELEMENT_SIZE_PIXELS, 
                          tallestLevel * Const.ELEMENT_SIZE_PIXELS);
    }
    
}
