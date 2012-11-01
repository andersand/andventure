package net.andersand.andventure.interactions;

import net.andersand.andventure.model.level.script.Statement;

import java.util.List;

/**
 * An interaction that triggers one or more other statements.
 * Eg. After talking to NPC (DialogStatement) an item is handed to the player (GiveStatement).
 * 
 * @author asn
 */
public abstract class ComplexInteraction implements Interaction {

    protected List<Statement> statements;

    public Statement getNextStatement() {
        return statements.iterator().hasNext() ? statements.remove(0) : null;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
        getNextStatement(); // shift off the first statement to prevent double execution
    }
}
