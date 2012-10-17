package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * Likely this element should be removed, as floor is more nicely handled as larger tiles
 * 
 * @author asn
 */
public class Floor extends Structure {

    protected Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar) {
        image = Util.loadElementImage("fl");
    }
}
