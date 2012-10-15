package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;

/**
 * @author asn
 */
public abstract class Structure extends Element {
    @Override
    protected void preDraw() {
        if (position == null) {
            return;
        }
        if (propertyHolder.getBoolean("settings.floorAsElements")) {
            Util.drawFloor(position);
        }
    }
}
