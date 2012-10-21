package net.andersand.andventure.model.elements;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Aligning wall using SpriteSheet.
 * 
 * @author asn
 */
public class Wall extends Structure implements ContiguousElement, SpriteSheetElement {

    protected SpriteSheet spriteSheet;
    private int spriteOffsetX;
    private int spriteOffsetY;

    @Override
    public Image getImage() {
        return spriteSheet.getSubImage(spriteOffsetX, spriteOffsetY);
    }

    @Override
    public void init(char levelDataChar, Script script) {
        String fileName = levelDataChar == 'w' ? "walls_stone.png" : "walls_wood.png";
        spriteSheet = new SpriteSheet(Util.loadImage(fileName), 15, 15);
        
    }

    @Override
    public void align(Element left, Element right, Element up, Element down) {
        // determine sprite offset
    }

    @Override
    public List<Class<? extends Element>> getAlignmentClasses() {
        List<Class<? extends Element>> alignmentClasses = new ArrayList<Class<? extends Element>>();
        alignmentClasses.add(Wall.class);
        return alignmentClasses;
    }

    @Override
    public void render() {
    }

    @Override
    public void renderSprite() {
        getImage().drawEmbedded(position.getX(), position.getY(), Const.ELEMENT_SIZE_PIXELS, Const.ELEMENT_SIZE_PIXELS);
    }
}
