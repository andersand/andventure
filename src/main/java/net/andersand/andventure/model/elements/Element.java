package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.interactions.ElementLevelInteraction;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author asn
 */
public abstract class Element implements Renderable {

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
     * This method returns the current image for rendering the next frame. That image may be a regular image,
     * or a sub-image from a spritesheet.
     * Composite images (eg. armor drawn over player) should be made using postDraw()
     * 
     * @return current image of element
     */
    public abstract Image getImage() throws SlickException;
    
    public abstract void init(char levelDataChar, Script script);

    public void render() throws SlickException {
        if (position == null) {
            return; // some elements may not be positioned (eg. worn)
        }
        if (getImage() == null) {
            return;
        }
        preDraw();
        draw(getImage());
        postDraw();
    }

    protected void draw(Image image) {
        int x = Util.getElementPixelX(position);
        int y = Util.getElementPixelY(position);
        image.draw(x, y);
    }

    /**
     * Draw anything to be under the element here
     */
    protected abstract void preDraw();

    /**
     * Draw anything to be over the element here
     */
    protected abstract void postDraw();
    
    public void setElementLevelInteraction(ElementLevelInteraction elementLevelInteraction) {
        this.elementLevelInteraction = elementLevelInteraction;
    }

    protected SpriteSheet getSpriteSheet() {
        return elementLevelInteraction.getSpriteSheet();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
