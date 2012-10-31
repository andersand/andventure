package net.andersand.andventure;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.EquipStatement;
import net.andersand.andventure.model.level.script.Statement;
import net.andersand.andventure.model.level.script.Script;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author asn
 */
@Test
public class EquipActionTest {

    protected Script script;
    protected Statement equipStatement1;
    protected Statement equipStatement2;
    protected List<String> scriptWords1 = Util.toArrayList("player", "\"a\"");
    protected List<String> scriptWords2 = Util.toArrayList("5x10", "\"s\"");

    @BeforeClass
    protected void beforeClass() {
        script = new Script();
        equipStatement1 = new EquipStatement();
        equipStatement1.setValueWords(scriptWords1);
        equipStatement2 = new EquipStatement();
        equipStatement2.setValueWords(scriptWords2);
        script.addStatement(equipStatement1);
        script.addStatement(equipStatement2);
    }
    
    @Test(dataProvider = "positionProvider")
    public void getAssignedEquipment(Position position, String expectedResult) {
        String actualResult = script.getAssignedEquipment(position);
        if (expectedResult == null) {
            assert actualResult == null : actualResult;
        }
        else {
            assert expectedResult.equals(actualResult) : actualResult;
        }
    }

    @DataProvider(name = "positionProvider")
    protected Object[][] positionProvider() {
        return new Object[][] {
            {null, "a"},
            {new Position(5, 10), "s"}
        };
    }

}