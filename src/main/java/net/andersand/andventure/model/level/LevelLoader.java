package net.andersand.andventure.model.level;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.Util;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.elements.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class LevelLoader {

    protected LevelParser levelParser;
    protected List<Level> levels = new ArrayList<Level>();
    protected PropertyHolder propertyHolder;

    public LevelLoader(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
        levelParser = new LevelParser(propertyHolder);
    }

    public List<Level> getLevels() {
        return levels;
    }

    /**
     * @return The bounds of the largest map
     */
    public Bounds loadLevels() {
        File dir = new File(Const.LEVELS_DIR);
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalStateException("Failed to list files from directory: " + Const.LEVELS_DIR);
        }
        if (files.length == 0) {
            throw new IllegalStateException("No files found in " + Const.LEVELS_DIR);
        }
        for (File file : files) {
            if (file.getName().endsWith("level")) {
                try {
                    Level level = new Level(propertyHolder);
                    String levelData = Util.readFile(file);
                    levelParser.validateCoarsely(levelData, file.getName());
                    List<Element> elements = levelParser.parse(levelData, file.getName(), level);
                    level.setElements(elements);
                    levels.add(level);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load a level");
                }
            }
        }
        if (levels.isEmpty()) {
            throw new IllegalStateException("No levels have been loaded! Cannot continue without. Directory " + 
                                            Const.LEVELS_DIR + " needs to have at least one valid *.level file");
        }
        return levelParser.getLevelBounds();
    }


}
