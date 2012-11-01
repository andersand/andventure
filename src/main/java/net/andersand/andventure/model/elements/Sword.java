package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Sword extends Object implements Weapon {

    @Override
    public Image getImage() {
        return Util.loadElementImage("s");
    }

    @Override
    public void init(char levelDataChar, Script script) {
    }

    @Override
    public boolean isPassableNow() {
        return true;
    }

    @Override
    public int getAttackValue() {
        return 1;
    }
}
