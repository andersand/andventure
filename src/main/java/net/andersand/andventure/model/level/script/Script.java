package net.andersand.andventure.model.level.script;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Neutral;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Script {
    protected List<Statement> statements;

    public Script() {
        this.statements = new ArrayList<Statement>();
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    /**
     * @param position specified position or null (meaning player)
     * @return assigned equipmentString for position or player
     */
    public String getAssignedEquipment(Position position) {
        for (Statement statement : statements) {
            if (statement instanceof EquipStatement && positionCompares(position, statement)) {
                return ((EquipStatement) statement).getEquipment();
            }
        }
        return null;
    }

    public boolean positionCompares(Position position, Statement statement) {
        return (position == null && statement.getPosition() == null) ||
               (position != null && position.equals(statement.getPosition()));
    }

    public List<Statement> initNPC(Neutral neutral) {
        return statementsForPosition(neutral.getPosition());
    }

    public List<Statement> statementsForPosition(Position position) {
        List<Statement> statementsForPosition = new ArrayList<Statement>();
        for (Statement statement : statements) {
            if (positionCompares(position, statement)) {
                statementsForPosition.add(statement);
            }
        }
        return statementsForPosition;
    }
}
