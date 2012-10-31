package net.andersand.andventure.model.level.script;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;

/**
 * @author asn
 */
public class EquipStatement extends Statement {

    String equipment;

    @Override
    public ExecutionResult execute() {
        return null;
    }

    @Override
    protected void parseValue() {
        parsePosition(valueWords.get(0));
        equipment = Util.removeQuotes(valueWords.get(1));
    }

    @Override
    public boolean usingValueWords() {
        return true;
    }

    public String getEquipment() {
        return equipment;
    }
}
