package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Player extends Creature {

    private String equipmentString;
    private Image image;

    @Override
    public Image getImage() {
        return image;
    }

    public String getEquipmentString() {
        return equipmentString;
    }

    @Override
    public void init(char levelDataChar) {
        String equipment = levelListener.getMeta().getEquipment();
        equipmentString = equipment == null ? Util.randomizeEquipment() : equipment;
        String partialFileName = "".equals(equipmentString) ? "pl" : "pl_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
    }

    public void setEquipmentString(String equipmentString) {
        this.equipmentString = equipmentString;
    }
}
