package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;

/**
 * @author asn
 */
public class Player extends Creature {

    @Override
    public void init(char levelDataChar, Script script) {
        String assignedEquipment = script.getAssignedEquipment(null);
        String equipmentString = assignedEquipment == null ? Util.randomizeEquipment() : assignedEquipment;
        equip(equipmentString);
    }

    @Override
    protected boolean preventMove() {
        return false; // AI method, does not apply to player...
    }

    @Override
    protected String getDeadImage() {
        return "pl_dead";
    }

    @Override
    protected String getBodyImage() {
        return "pl";
    }
}
