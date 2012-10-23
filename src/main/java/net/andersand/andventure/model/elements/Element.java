package net.andersand.andventure.model.elements;

import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.ElementLevelInteraction;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * @author asn
 */
public abstract class Element implements Renderable {

    protected PropertyHolder propertyHolder;
    protected ElementLevelInteraction elementLevelInteraction;

    protected Position position;
//    protected int spriteOffsetX;
//    protected int spriteOffsetY;
//    protected int spriteWidth;
//    protected int spriteHeight;

    /**
     * @return position of element
     */
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Elements may have more than one state, and require different images depending on this state.
     * This method returns the current image for rendering the next frame. That image may be a regular image
     * or a sub-image from a spritesheet.
     * @return current image of element
     */
    public abstract Image getImage();
    
    public abstract void init(char levelDataChar, Script script);

    public void render() {
        if (position == null) {
            return; // some elements may not be positioned (eg. worn)
        }
        int x = Util.getElementPixelX(position);
        int y = Util.getElementPixelY(position);
        if (getImage() == null) {
            return;
        }
        preDraw();
        getImage().draw(x, y);
    }

    protected abstract void preDraw();

    public void setElementLevelInteraction(ElementLevelInteraction elementLevelInteraction) {
        this.elementLevelInteraction = elementLevelInteraction;
    }

    protected SpriteSheet getSpriteSheet() {
        return elementLevelInteraction.getSpriteSheet();
    }
    
    public void setPropertyHolder(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
