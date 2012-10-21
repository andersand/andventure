package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;

/**
 * @author asn
 */
public class Player extends Creature {

    @Override
    public void init(char levelDataChar, Script script) {
        String assignedEquipment = script.getAssignedEquipment(null);
        equipmentString = assignedEquipment == null ? Util.randomizeEquipment() : assignedEquipment;
        String partialFileName = "".equals(equipmentString) ? "pl" : "pl_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
        initAttackDefense();
    }

    @Override
    protected boolean preventMove() {
        return false; // AI method, does not apply to player...
    }

    @Override
    protected void doInteraction() {
        // todo LOW perhaps when a creature approaches the player he should be presented something
    }

    @Override
    protected void setDeadImage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
