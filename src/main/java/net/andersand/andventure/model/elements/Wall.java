package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Wall extends Structure {

    protected Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar) {
        String partialFileName = levelDataChar == 'w' ? "ws" : "ww";
        image = Util.loadElementImage(partialFileName);
        if (propertyHolder.getBoolean("settings.rotateWalls")) {
            int angle = Util.random(4) * 90;
            image.rotate(angle);
        }
    }
}
