package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;

/**
 * @author asn
 */
public class Foe extends Creature {
    
    @Override
    public void init(char levelDataChar, Script script) {
        String assignedEquipment = script.getAssignedEquipment(position);
        String equipmentString = assignedEquipment == null ? Util.randomizeEquipment() : assignedEquipment;
        equip(equipmentString);
    }

    @Override
    protected boolean preventMove() {
        return false; // todo LOW foes acting as guards should be able to stand still
    }

    @Override
    protected String getDeadImage() {
        return "en_dead";
    }

    @Override
    protected String getBodyImage() {
        return "en";
    }

}
