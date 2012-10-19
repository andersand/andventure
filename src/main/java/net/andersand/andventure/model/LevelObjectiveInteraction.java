package net.andersand.andventure.model;

import net.andersand.andventure.model.elements.Foe;

import java.util.List;

/**
 * @author asn
 */
public interface LevelObjectiveInteraction {
    
    public Position getPlayerPosition();

    public List<Foe> getFoes();
}
