package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;

/**
 * @author asn
 */
public class Foe extends Creature implements Passable {
    
    public void setEquipmentString(String equipmentString) {
        this.equipmentString = equipmentString;
    }

    @Override
    public void init(char levelDataChar) {
        equipmentString = Util.randomizeEquipment();
        String partialFileName = "".equals(equipmentString) ? "en" : "en_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
        initAttackDefense();
    }

    @Override
    protected boolean preventMove() {
        return false; // todo LOW foes acting as guards should be able to stand still
    }

    @Override
    protected void doInteraction() {
        // Intentionally empty: Foes can only be attacked for now
    }

    @Override
    protected void setDeadImage() {
        image = Util.loadElementImage("en_dead");
    }

    @Override
    public boolean isPassableNow() {
        return dead;
    }
}
