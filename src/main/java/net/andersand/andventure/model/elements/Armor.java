package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Armor extends Object implements Wearable {

    @Override
    public Image getImage() {
        return Util.loadElementImage("a");
    }

    @Override
    public void init(char levelDataChar, Script script) {
    }

    @Override
    public boolean isPassableNow() {
        return true;
    }

    @Override
    public int getDefenseValue() {
        return 1;
    }
}
