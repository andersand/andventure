package net.andersand.andventure.model.level.script;

/**
 * @author asn
 */
public class ExitStatement extends Statement {
    @Override
    public ExecutionResult execute() {
        return null;
    }

    @Override
    protected void parseValue() {
        parsePosition(valueWords.get(0));
    }

    @Override
    public boolean usingValueWords() {
        return false;
    }
}
