package net.andersand.andventure.model;

import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.elements.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asn
 */
public class InventoryLookup {

    public static Map<String, Class<? extends Object>> objectLookupTable = new HashMap<String, Class<? extends Object>>();

    static {
        objectLookupTable.put("r", Ring.class);
        objectLookupTable.put("c", Corpse.class);
        objectLookupTable.put("a", Armor.class);
        objectLookupTable.put("h", Helmet.class);
        objectLookupTable.put("s", Sword.class);
    }

    public static Class<? extends Object> lookup(String s) {
        return objectLookupTable.get(s);
    }
}
