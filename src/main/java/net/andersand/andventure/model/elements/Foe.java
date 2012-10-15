package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Foe extends Creature {

    private String equipmentString;
    Image image;
    
    @Override
    public Image getImage() {
        return image;
    }

    public String getEquipmentString() {
        return equipmentString;
    }

    public void setEquipmentString(String equipmentString) {
        this.equipmentString = equipmentString;
    }

    @Override
    public void init(char levelDataChar) {
        equipmentString = Util.randomizeEquipment();
        String partialFileName = "".equals(equipmentString) ? "en" : "en_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
    }

}
