package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Ring extends Object implements Item {

    protected Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        image = Util.loadElementImage("ri");
    }

    @Override
    public boolean isPassableNow() {
        return true;
    }

    @Override
    public void pickUp(Creature creature) {
        position = null;
    }

    @Override
    public void drop(Creature creature) {
        position = creature.position.copy();
    }
}
