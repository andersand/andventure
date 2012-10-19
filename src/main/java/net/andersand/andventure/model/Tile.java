package net.andersand.andventure.model;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Tile implements Renderable {
    protected Position position;
    protected Image image;

    public Tile(Image image) {
        this.image = image;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void render() {
        if (position == null) {
            throw new IllegalStateException("Encountered a a Tile posision");
        }
        int x = Util.getTilePixelX(position);
        int y = Util.getTilePixelY(position);
        if (image == null) {
            throw new IllegalStateException("Encountered a a Tile without image");
        }
        image.draw(x, y);
    }
}
