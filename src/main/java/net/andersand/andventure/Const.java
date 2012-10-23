package net.andersand.andventure;

import net.andersand.andventure.model.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asn
 */
public class Const {
    public static String GAME_TITLE = "AndVenture";
    public static final String ELEMENT_IMG_SUFFIX = ".gif";
    public static final String IMG_DIR = "src/main/resources/img/";
    public static final String FONT_DIR = "src/main/resources/font/";
    public static final String LEVELS_DIR = "src/main/resources/levels/";
    public static final int FPS = 10;
    public static final int ELEMENT_SIZE_PIXELS = 15;
    public static final int BGTILE_SIZE_PIXELS = 100;
    public static final double AI_IDLE_CHANCE_TO_MOVE = .1;
    public static final String LEVEL_META = "?META";
    public static final String LEVEL_SCRIPT = "?SCRIPT";
    public static final boolean DEVELOPER_MODE = true;
    public static final int PAPER_WIDTH = 640;
    public static final int PAPER_HEIGHT = 480;
    public static final int WINDOW_SIDE_EXTRA_WIDTH = 140;
    public static final int BRIEFING_LINE_MAX_WIDTH = 35;
    public static final int FONT_HEIGHT = 50;
    public static final int FONT_INSET = 50;
    public static final String PREPEND_LOG = "ANDVENTURE";
    public static Map<String, Position> spriteWallOffsetMap = new HashMap<String, Position>();
    static {
        spriteWallOffsetMap.put("r", pos(0, 0));
        spriteWallOffsetMap.put("lrd", pos(1, 0));
        spriteWallOffsetMap.put("lr", pos(2, 0));
        spriteWallOffsetMap.put("ld", pos(3, 0));
        spriteWallOffsetMap.put("", pos(0, 1));
        spriteWallOffsetMap.put("ud", pos(1, 1));
        spriteWallOffsetMap.put("rd", pos(2, 1));
        spriteWallOffsetMap.put("lud", pos(3, 1));
        spriteWallOffsetMap.put("d", pos(0, 2));
        spriteWallOffsetMap.put("u", pos(1, 2));
        spriteWallOffsetMap.put("ru", pos(2, 2));
        spriteWallOffsetMap.put("lu", pos(3, 2));
        spriteWallOffsetMap.put("rud", pos(0, 3));
        spriteWallOffsetMap.put("lrud", pos(1, 3));
        spriteWallOffsetMap.put("lru", pos(2, 3));
        spriteWallOffsetMap.put("l", pos(3, 3));
    }

    private static Position pos(int x, int y) {
        return new Position(x, y);
    }
}
