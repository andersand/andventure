package net.andersand.andventure;

import net.andersand.andventure.model.elements.Foe;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author asn
 */
@Test
public class UtilTest {

    @Test(dataProvider = "equipmentStringProvider")
    public void randomizeEquipmentYieldsAllCombinations(String equipmentString) {

        //try to generate equipment string 1000 times
        for (int i = 0; i < 1000; i++) {
            String result = Util.randomizeEquipment();
            if (equipmentString.equals(result)) {
                System.out.println(i);
                return; // test OK: randomization has created the given combination
            }
        }
        assert false; // if we get here, test failed
    }

    @DataProvider(name = "equipmentStringProvider")
    private Object[][] equipmentStringProvider() {
        return new Object[][]{
                {""},
                {"a"},
                {"s"},
                {"h"},
                {"as"},
                {"ah"},
                {"sh"},
                {"ash"}
        };
    }

    @Test(dataProvider = "upperLimit")
    public void randomWorksAsItShould(int upperLimit) {
        int lowest = 999;
        int highest = 0;
        for (int i = 0; i < 100; i++) {
            int result = Util.random(upperLimit);
            if (result < lowest) {
                lowest = result;
            }
            if (result > highest) {
                highest = result;
            }
            if (lowest == 0 && highest == upperLimit-1) {
                System.out.println("tries=" + i);
                return; // test ok, exit test
            }
        }
        assert false; // test failed if we get here.
    }

    @DataProvider(name = "upperLimit")
    private Object[][] upperLimitProvider() {
        return new Object[][] {
            {1},
            {2},
            {3},
            {4},
            {5}
        };
    }
}
