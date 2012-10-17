package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Player extends Creature {

    @Override
    public void init(char levelDataChar) {
        String equipment = levelListener.getMeta().getEquipment();
        equipmentString = equipment == null ? Util.randomizeEquipment() : equipment;
        String partialFileName = "".equals(equipmentString) ? "pl" : "pl_" + equipmentString;
        image = Util.loadElementImage(partialFileName);
        initAttackDefense();
    }

    @Override
    protected void doInteraction() {
        // todo: perhaps when a creature approaches the player he should be presented something
    }

    @Override
    protected void setDeadImage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
