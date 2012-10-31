package net.andersand.andventure.interactions;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Element;

/**
 * @author asn
 */
public interface CreatureLevelInteraction {
    
    Element look(Position position);
}
