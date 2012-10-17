package net.andersand.andventure.model.level;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author asn
 */
public class LevelParser {

    private PropertyHolder propertyHolder;
    
    Map<String, Class<? extends Element>> elementLookupTable = new HashMap<String, Class<? extends Element>>();
    private Map<String, Field> metaLookupTable = new HashMap<String, Field>();
    private Map<String, ObjectiveType> objectivesLookupTable = new HashMap<String, ObjectiveType>();
    int tallestLevel;
    int widestLevel;

    public LevelParser(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
        populateLookupTables();
    }

    private void populateLookupTables() {
        if (propertyHolder.getBoolean("settings.floorAsElements")) {
            elementLookupTable.put(" ", Floor.class);
        }
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
        try {
            metaLookupTable.put("name", Meta.class.getField("name"));
            metaLookupTable.put("description", Meta.class.getField("description"));
            metaLookupTable.put("objectives", Meta.class.getField("objectives"));
            metaLookupTable.put("equipment", Meta.class.getField("equipment"));
            metaLookupTable.put("environment", Meta.class.getField("environment"));
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        objectivesLookupTable.put("object", ObjectiveType.RETRIEVE_OBJECT);
        objectivesLookupTable.put("rescue", ObjectiveType.RESCUE_NPC);
        objectivesLookupTable.put("defend", ObjectiveType.DEFEND_AREA_TIMELIMIT);
        objectivesLookupTable.put("assassinate", ObjectiveType.ASSASINATION);
        objectivesLookupTable.put("defeatAllFoes", ObjectiveType.DEFEAT_ALL_FOES);
    }

    /**
     * Goes through level data and creates game elements when appropriate.
     * May also perform som validation
     */
    public List<Element> parse(String levelData, String levelFileName, Level level) {
        List<String> lines = getLines(levelData);
        List<String> metaLines = getMetaLines(lines);
        lines = removeEmptyLinesAndMeta(lines);
        List<Element> elements = new ArrayList<Element>();
        try {
            level.setMeta(parseMetaData(metaLines));
            int n = 0;
            for (String line : lines) {
                elements.addAll(parseLine(level, line, n++));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throwException("level.parsing.failed", levelFileName);
        }
        return elements;
    }

    public Meta parseMetaData(List<String> metaLines) throws IllegalAccessException {
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

    private Objective handleObjective(String objectiveStr) {
        ObjectiveType objectiveType;
        String objectiveValue = "";
        if (objectiveStr.contains("@")) {
            String[] parts = objectiveStr.split("@");
            objectiveType = objectivesLookupTable.get(parts[0]);
            objectiveValue = parts[1];
        }
        else {
            objectiveType = objectivesLookupTable.get(objectiveStr);
        }
        if (objectiveType == null) {
            throwException("level.parsing.failed.invalid.objective.type", objectiveStr);
        }
        Objective o = new Objective(objectiveType);
        o.setValue(objectiveValue);
        return o;
    }

    private List<String> getMetaLines(List<String> lines) {
        List<String> metaLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.startsWith(Const.LEVEL_META)) {
                metaLines.add(line);
            }
        }
        return metaLines;
    }

    private List<Element> parseLine(Level level, String levelDataLine, int yPosition) throws IllegalAccessException, InstantiationException {
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
                element.setPropertyHolder(propertyHolder);
                if (element instanceof Creature) {
                    ((Creature)element).setLevelListener(level);
                }
                element.init(c);
                elements.add(element);
            }
            xPosition++;
        }
        return elements;
    }

    /**
     * Coarsely validates level data consistency without parsing
     */
    public void validateCoarsely(String levelData, String levelFileName) {
        List<String> lines = getLines(levelData);
        lines = removeEmptyLinesAndMeta(lines);
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
    }

    private List<String> removeEmptyLinesAndMeta(List<String> lines) {
        List<String> mapLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.length() != 0 && !line.startsWith(Const.LEVEL_META)) {
                mapLines.add(line);
            }
        }
        return mapLines;
    }

    private void throwException(String propertyKey, String parameter) {
        throw new IllegalStateException(propertyHolder.get(propertyKey).replaceFirst("\\$", parameter));
    }

    private void updateTallestWidestLevel(int levelHeight, int levelWidth) {
        if (levelHeight > tallestLevel) {
            tallestLevel = levelHeight;
        }
        if (levelWidth > widestLevel) {
            widestLevel = levelWidth;
        }
    }

    private static List<String> getLines(String levelData) {
        return Arrays.asList(levelData.split("\n"));
    }

    public Bounds getLevelBounds() {
        Bounds b = new Bounds();
        b.width = widestLevel * Const.ELEMENT_SIZE_PIXELS;
        b.height = tallestLevel * Const.ELEMENT_SIZE_PIXELS;
        return b;
    }
    
}
