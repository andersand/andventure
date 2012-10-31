package net.andersand.andventure.model.elements;

/**
 * An object is something you can pick up, put in your inventory and carry around
 * @author asn
 */
public abstract class Object extends Element implements Passable {
    @Override
    protected void preDraw() {
    }

    @Override
    protected void postDraw() {
    }
}
