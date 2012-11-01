package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.view.ScriptAccessor;

import java.util.List;

/**
 * Statements are the core of scripting and drives the game 
 * behaviour.
 * Some Statements are handled upon level initing, like EquipStatement.
 * Most Statements are triggered by the player interacting with elements.
 * 
 * @author asn
 */
public abstract class Statement {

    protected Position position;
    protected String valueRaw;
    protected List<String> valueWords;
    protected ScriptAccessor scriptAccessor;

    public void setValueRaw(String value) {
        this.valueRaw = value.trim();
        parseValue();
    }

    public void setValueWords(List<String> valueWords) {
        this.valueWords = valueWords;
        parseValue();
    }

    /**
     * @return null if everything is taken care of by impl. method, 
     * otherwise return an ExecutionResult to be processed later
     */
    public abstract ExecutionResult execute();
    
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setScriptAccessor(ScriptAccessor scriptAccessor) {
        this.scriptAccessor = scriptAccessor;
    }
}
