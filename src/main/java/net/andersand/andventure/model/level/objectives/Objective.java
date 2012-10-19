package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.model.LevelObjectiveInteraction;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Player;

/**
 * Defines various mission objectives.
 * Depending on the objective type, an objective may have a value,
 * which will be handled specifically for each type. Typically a reference 
 * to a specific position in the level where a certain Element is placed.
 * 
 * @author asn
 */
public abstract class Objective {
    
    protected boolean completed;
    protected String value;
    protected Position destination;
    protected LevelObjectiveInteraction levelObjectiveInteraction;
    
    public void setValue(String objectiveValue) {
        this.value = objectiveValue;
    }

    public boolean isCompleted(LevelObjectiveInteraction levelObjectiveInteraction) {
        return completed || isObjectiveCompleted(levelObjectiveInteraction);
    }

    protected abstract boolean isObjectiveCompleted(LevelObjectiveInteraction player);

}
