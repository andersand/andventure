package net.andersand.andventure;

import net.andersand.andventure.engine.Util;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author asn
 */
@Test
public class UtilTest {

    int lowestAsciiDigit = 48;
    int highestAsciiDigit = 57;
    int lowestAsciiUppercase = 65;
    int highestAsciiUppercase = 90;
    int lowestAsciiLowercase = 97;
    int highestAsciiLowercase = 122;    
    
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
    public void utilRandomWorksAsItShould(int upperLimit) {
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
    
    @Test
    public void randomAlphaNumericOnlyYieldsAlphaNumeric() {
        char[] valueCA;
        char c;
        for (int i = 0; i < 1000; i++) { // 1K times ought to be enough
            valueCA = Util.randomAlphaNumeric().toCharArray();
            assert valueCA != null && valueCA.length == 1;
            c = valueCA[0];
            assert (c >= lowestAsciiDigit && c <= highestAsciiDigit) ||
                   (c >= lowestAsciiLowercase && c <= highestAsciiLowercase) ||
                   (c >= lowestAsciiUppercase && c <= highestAsciiUppercase) : "Invalid char: " + c;
        }
    }
}
