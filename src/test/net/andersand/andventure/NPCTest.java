package net.andersand.andventure;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.Statement;
import net.andersand.andventure.model.level.script.EquipStatement;
import net.andersand.andventure.model.level.script.Script;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author asn
 */
@Test
public class NPCTest {

    protected Script script;
    protected Statement statementWithoutPosition;
    protected Statement statementWithPosition1;
    protected Statement statementWithPosition2;

    @BeforeClass
    protected void beforeClass() {
        script = new Script();
        statementWithoutPosition = new EquipStatement();
        statementWithPosition1 = new EquipStatement();
        statementWithPosition1.setPosition(new Position(5, 5));
        statementWithPosition2 = new EquipStatement();
        statementWithPosition2.setPosition(new Position(10, 10));
        
        script.addStatement(statementWithoutPosition);
        script.addStatement(statementWithPosition1);
        script.addStatement(statementWithPosition2);
    }

    @Test(dataProvider = "positonsAndStatementsProvider")
    public void statementsForPosition(Position position, int expectedNumberOfHits) {
        List<Statement> results = script.statementsForPosition(position);
        assert results.size() == expectedNumberOfHits;
    }

    @DataProvider(name = "positonsAndStatementsProvider")
    protected Object[][] positonsAndStatementsProvider() {
        return new Object[][] {
                {null, 1},
                {new Position(0,0), 0},
                {new Position(5,0), 0},
                {new Position(5,5), 1},
                {new Position(10,10), 1},
                {new Position(5,10), 0},
                {new Position(10,5), 0},
        };
    }

}