package net.andersand.andventure.model.elements;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author asn
 */
public class Ring extends Object {

    private Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar) {
        image = Util.loadElementImage("ri");
    }
}
