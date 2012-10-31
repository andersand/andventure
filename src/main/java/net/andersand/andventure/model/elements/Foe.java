package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;

/**
 * @author asn
 */
public class Foe extends Creature implements Passable {
    
    public void setEquipmentString(String equipmentString) {
        this.equipmentString = equipmentString;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        String assignedEquipment = script.getAssignedEquipment(position);
        equipmentString = assignedEquipment != null ? assignedEquipment : Util.randomizeEquipment();
        String partialFileName = "".equals(equipmentString) ? "en" : "en_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
        initAttackDefense();
    }

    @Override
    protected boolean preventMove() {
        return false; // todo LOW foes acting as guards should be able to stand still
    }

    @Override
    protected void setDeadImage() {
        image = Util.loadElementImage("en_dead");
    }

    @Override
    public boolean isPassableNow() {
        return dead; // allow walking over dead foes
    }
}
