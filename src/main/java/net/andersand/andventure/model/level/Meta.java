package net.andersand.andventure.model.level;

import net.andersand.andventure.model.level.objectives.Objective;

import java.util.List;

/**
 * Due to the nature of java reflection, the fields must be public...
 * 
 * @author asn
 */
public class Meta {
    public List<Objective> objectives;
    public String name;
    public String description;
    public String environment;
    public String debrief;

    public List<Objective> getObjectives() {
        return objectives;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getDebrief() {
        return debrief;
    }
}
