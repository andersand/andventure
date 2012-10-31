package net.andersand.andventure.model.elements;

/**
 * Structures are immovable elements that are too big to be Objects, 
 * do not fit into inventory, or does not make sense to pick up.
 * @author asn
 */
public abstract class Structure extends Element {
    @Override
    protected void preDraw() {
    }

    @Override
    protected void postDraw() {
    }
}
