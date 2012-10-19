package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Corpse extends Object {
    
    protected Image image;
    
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar) {
        image = Util.loadElementImage("nd");
    }

    @Override
    public boolean isPassableNow() {
        return true;
    }
}
