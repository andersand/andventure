package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Mapper;
import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Aligning wall using SpriteSheet.
 * 
 * @author asn
 */
public class Wall extends Structure implements ContiguousElement {

    protected Image subImage;
    protected int wallTypeOffsetX;

    @Override
    public Image getImage() {
        return subImage;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        wallTypeOffsetX = levelDataChar == 'w' ? 4 : 0;
    }

    @Override
    public void align(Element left, Element right, Element up, Element down) {
        String directionString = Util.directionString(
                left != null, right != null, up != null, down != null);
        Position p = getSpriteOffset(directionString);
        subImage = getSpriteSheet().getSubImage(p.getX() + wallTypeOffsetX, p.getY());
    }

    protected Position getSpriteOffset(String directionString) {
        return Mapper.getSpriteWallOffset(directionString);
    }

    @Override
    public List<Class<? extends Element>> getAlignmentClasses() {
        List<Class<? extends Element>> alignmentClasses = new ArrayList<Class<? extends Element>>();
        alignmentClasses.add(Wall.class);
        return alignmentClasses;
    }

}
