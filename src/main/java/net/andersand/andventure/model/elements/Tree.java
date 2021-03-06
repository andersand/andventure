package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Tree extends Structure {
    
    protected Image image;
    
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        image = (Util.random(2) == 1) ? Util.loadElementImage("t") : Util.loadElementImage("t1");
    }
}
