package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;

import java.util.List;

/**
 * @author asn
 */
public abstract class Action {

    protected Position position;
    protected String valueRaw;
    protected List<String> valueWords;

    public void setValueRaw(String value) {
        this.valueRaw = value;
        parseValue();
    }

    public void setValueWords(List<String> valueWords) {
        this.valueWords = valueWords;
        parseValue();
    }

    /**
     * Handles action type-specific fields from value
     */
    protected abstract void parseValue();

    /**
     * Some action types need raw string, others can use words, 
     * which is more convenient.
     */
    public abstract boolean usingValueWords();

    protected void parsePosition(String positionStr) {
        if (!positionStr.contains("x")) {
            return;
        }
        String[] parts = positionStr.split("x");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }
}
