package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Foe extends Creature {
    
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
    protected void doInteraction() {
        // Intentionally empty: Foes can only be attacked for now
    }

    @Override
    protected void setDeadImage() {
        image = Util.loadElementImage("en_dead");
    }
}
