package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.model.interactions.ObjectiveLevelInteraction;

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
    
    public void setValue(String objectiveValue) {
        this.value = objectiveValue;
    }

    public boolean isCompleted(ObjectiveLevelInteraction objectiveLevelInteraction) {
        return completed || isObjectiveCompleted(objectiveLevelInteraction);
    }

    protected abstract boolean isObjectiveCompleted(ObjectiveLevelInteraction player);

}
