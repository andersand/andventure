package net.andersand.andventure.model.level.script;

import net.andersand.andventure.engine.Util;

/**
 * @author asn
 */
public class GiveStatement extends Statement {

    protected String equipment;

    @Override
    public ExecutionResult execute() {
        scriptAccessor.getPlayer().equip(equipment);
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
}
