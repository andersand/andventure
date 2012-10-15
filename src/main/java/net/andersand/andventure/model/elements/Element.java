package net.andersand.andventure.model.elements;

import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public abstract class Element implements Renderable {

    protected PropertyHolder propertyHolder;

    protected Position position;

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
     * This method returns the current image for rendering the next frame.
     * @return current image of element
     */
    public abstract Image getImage();
    
    public abstract void init(char levelDataChar);

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

    public void setPropertyHolder(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
