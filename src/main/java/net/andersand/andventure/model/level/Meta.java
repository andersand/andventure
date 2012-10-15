package net.andersand.andventure.model.level;

import java.util.List;

/**
 * @author asn
 */
public class Meta {
    public List<Objective> objectives;
    public String name;
    public String description;
    public String equipment;

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

    public String getEquipment() {
        return equipment;
    }
}
