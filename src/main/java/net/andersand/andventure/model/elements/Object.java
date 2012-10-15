package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;

/**
 * @author asn
 */
public abstract class Object extends Element {
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
