package net.andersand.andventure.model.elements;

import net.andersand.andventure.model.interactions.Interaction;

/**
 * @author asn
 */
public interface Interactable {
    public Interaction interact(Creature actor);
}
