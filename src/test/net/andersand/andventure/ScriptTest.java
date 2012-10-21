package net.andersand.andventure;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.Action;
import net.andersand.andventure.model.level.script.EquipAction;
import net.andersand.andventure.model.level.script.Script;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author asn
 */
@Test
public class ScriptTest {

    protected Script script;
    protected Action equipAction1;
    protected Action equipAction2;
    protected List<String> scriptWords1 = Util.toArrayList("player", "\"a\"");
    protected List<String> scriptWords2 = Util.toArrayList("5x10", "\"s\"");

    @BeforeClass
    protected void beforeClass() {
        script = new Script();
        equipAction1 = new EquipAction();
        equipAction1.setValueWords(scriptWords1);
        equipAction2 = new EquipAction();
        equipAction2.setValueWords(scriptWords2);
        script.addAction(equipAction1);
        script.addAction(equipAction2);
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
