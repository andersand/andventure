package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;

/**
 * @author asn
 */
public class EquipAction extends Action {

    String equipment;
    
    @Override
    protected void parseValue() {
        parsePosition(valueWords.get(0));
        equipment = valueWords.get(1);
    }

    @Override
    public boolean usingValueWords() {
        return true;
    }

    public String getEquipment() {
        return equipment;
    }
}
