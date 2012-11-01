package net.andersand.andventure.engine;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.elements.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asn
 */
public class Mapper {

    private static Map<String, Class<? extends Object>> objectLookupTable = new HashMap<String, Class<? extends Object>>();
    private static Map<String, Position> spriteWallOffsetMap = new HashMap<String, Position>();

    static {
        objectLookupTable.put("r", Ring.class);
        objectLookupTable.put("c", Corpse.class);
        objectLookupTable.put("a", Armor.class);
        objectLookupTable.put("h", Helmet.class);
        objectLookupTable.put("s", Sword.class);
    }
    
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
    
    public static Class<? extends Object> lookupObject(String s) {
        return objectLookupTable.get(s);
    }

    public static Position getSpriteWallOffset(String directionString) {
        return spriteWallOffsetMap.get(directionString);
    }

    private static Position pos(int x, int y) {
        return new Position(x, y);
    }
}
