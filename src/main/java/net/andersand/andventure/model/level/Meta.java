package net.andersand.andventure.model.level;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Script;

import java.util.List;

/**
 * @author asn
 */
public class Meta {
    public List<Objective> objectives;
    public String name;
    public String description;
    public String equipment;
    public String environment;

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

    public Script getScriptForNPC(Position position) {
        return null;
    }

    public String getEnvironment() {
        return environment;
    }
}
