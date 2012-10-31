package net.andersand.andventure.model.level;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.*;
import net.andersand.andventure.view.GUIAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author asn
 */
public class ScriptParser {

    protected Map<String, Class<? extends Statement>> statementLookupTable = new HashMap<String, Class<? extends Statement>>();
    private GUIAccessor guiAccessor;

    public ScriptParser(GUIAccessor guiAccessor) {
        populateLookupTables();
        this.guiAccessor = guiAccessor;
    }

    private void populateLookupTables() {
        statementLookupTable.put("equip", EquipStatement.class);
        statementLookupTable.put("dialog", DialogStatement.class);
        statementLookupTable.put("give", GiveStatement.class);
        statementLookupTable.put("exit", ExitStatement.class);
    }

    public Script parse(List<String> scriptLines) throws InstantiationException, IllegalAccessException {
        Script script = new Script();
        for (String line : scriptLines) {
            List<String> words = Util.getWords(line);
            Statement statement = handleStatement(words, line);
            script.addStatement(statement);
        }
        return script;
    }

    protected Statement handleStatement(List<String> words, String scriptLine) throws IllegalAccessException, InstantiationException {
        words.remove(0); // shift off the first word (?SCRIPT descriptor)
        String statementString = words.remove(0);  // keyword should be the first word
        Class<? extends Statement> statementClass = statementLookupTable.get(statementString);
        if (statementClass != null) {
            Statement statement = statementClass.newInstance();
            statement.setGuiAccessor(guiAccessor);
            if (statement.usingValueWords()) {
                statement.setValueWords(words);
            }
            else {
                statement.setValueRaw(scriptLine.split(statementString)[1]);
            }
            return statement;
        }
        return null;
    }
}
