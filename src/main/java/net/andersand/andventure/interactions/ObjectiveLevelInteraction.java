package net.andersand.andventure.interactions;

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
