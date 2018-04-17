package net.andersand.andventure.model.interactions;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Foe;

import java.util.List;

/**
 * @author asn
 */
public interface ObjectiveLevelInteraction {
    
    public Position getPlayerPosition();
    public List<Foe> getFoes();
    
}
