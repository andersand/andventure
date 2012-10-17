package net.andersand.andventure.model.elements;

/**
 * @author asn
 */
public interface Item {

    public void pickUp(Creature creature);
    public void drop(Creature creature);
    
}
