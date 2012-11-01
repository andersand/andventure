package net.andersand.andventure;

import net.andersand.andventure.model.level.LevelParser;
import net.andersand.andventure.model.level.Meta;
import net.andersand.andventure.view.GUI;
import net.andersand.andventure.view.ScriptAccessor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author asn
 */
@Test
public class LevelParserTest {

    @Test(dataProvider = "metaDataProvider")
    public void parseMetaDataSetsMetadataFields(List<String> metadataLines) throws IllegalAccessException, InstantiationException {
        LevelParser parser = new LevelParser(new ScriptAccessor());
        Meta meta = parser.parseMetaData(metadataLines);
        assert meta.getName() != null;
        assert meta.getDescription() != null;
        assert meta.getObjectives() != null;
        assert !meta.getObjectives().isEmpty();
    }
    
    @DataProvider(name = "metaDataProvider")
    private Object[][] metaDataProvider() {
        return new Object[][] {
                {Arrays.asList(new String[] {"?META name=foobar", "?META description=yarr foo bar!", "?META objectives=object@60x58"})},
                {Arrays.asList(new String[] {"?META name=Foo zonk", "?META description=YARR Foo Zonk!", "?META objectives=object@60x58,defeatAllFoes"})}
        };
    }
    
    
}
