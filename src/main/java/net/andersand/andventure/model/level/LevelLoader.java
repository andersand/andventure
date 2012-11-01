package net.andersand.andventure.model.level;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.view.ScriptAccessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IO class
 * @author asn
 */
public class LevelLoader {

    protected LevelParser levelParser;
    protected List<Level> levels = new ArrayList<Level>();

    public LevelLoader(ScriptAccessor scriptAccessor) {
        levelParser = new LevelParser(scriptAccessor);
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
        long t0 = System.currentTimeMillis();
        for (File file : files) {
            if (file.getName().endsWith("level")) {
                try {
                    long t1 = System.currentTimeMillis();
                    Level level = new Level();
                    String levelData = Util.readFile(file);
                    Bounds dimension = levelParser.validateCoarsely(levelData, file.getName());
                    level.setDimension(dimension);
                    levelParser.parse(levelData, file.getName(), level);
                    levels.add(level);
                    Util.log(file.getName() + " loaded in: " + (System.currentTimeMillis() - t1) + " ms.");
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load a level");
                }
            }
        }
        Util.log("Levels loaded in " + String.valueOf(System.currentTimeMillis() - t0) + " ms.");
        if (levels.isEmpty()) {
            throw new IllegalStateException("No levels have been loaded! Cannot continue without. Directory " + 
                                            Const.LEVELS_DIR + " needs to have at least one valid *.level file");
        }
        return levelParser.getLevelBounds();
    }


}
