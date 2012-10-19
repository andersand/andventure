package net.andersand.andventure.model;

import net.andersand.andventure.model.elements.Element;
import net.andersand.andventure.model.level.Meta;

/**
 * @author asn
 */
public interface LevelCreatureInteraction {
    
    Element look(Position position);
    Meta getMeta();
}
