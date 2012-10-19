package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.model.level.ObjectiveType;

/**
 * Defines various mission objectives.
 * Depending on the ObjectiveType, an objective may have a value,
 * which will be handled specifically for each type. Typically a reference 
 * to a specific position in the level where a certain Element is placed.
 * 
 * @author asn
 */
public abstract class Objective {
    private boolean completed;
    private ObjectiveType type;
    private String value;

    public Objective(ObjectiveType type) {
        this.type = type;
    }

    public void setValue(String objectiveValue) {
        this.value = objectiveValue;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}
