package net.andersand.andventure.model.elements;

/**
 * Inventory items
 * Consider the need for this interface (arent all objects items?)
 * @author asn
 */
public interface Item {

    public void pickUp(Creature creature);
    public void drop(Creature creature);
    
}
