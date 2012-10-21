package net.andersand.andventure.model.level;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author asn
 */
public class ScriptParser {

    protected Map<String, Class<? extends Action>> actionLookupTable = new HashMap<String, Class<? extends Action>>();

    public ScriptParser() {
        populateLookupTables();
    }

    private void populateLookupTables() {
        actionLookupTable.put("equip", EquipAction.class);
        actionLookupTable.put("dialog", DialogAction.class);
        actionLookupTable.put("give", GiveAction.class);
    }

    public Script parse(List<String> scriptLines) throws InstantiationException, IllegalAccessException {
        Script script = new Script();
        for (String line : scriptLines) {
            List<String> words = Util.getWords(line);
            Action action = handleAction(words, line);
            script.addAction(action);
        }
        return script;
    }

    protected Action handleAction(List<String> words, String scriptLine) throws IllegalAccessException, InstantiationException {
        words.remove(0); // shift off the first word (?SCRIPT descriptor)
        String actionString = words.remove(0);  // action should be the first word
        Class<? extends Action> actionClass = actionLookupTable.get(actionString);
        if (actionClass != null) {
            Action action = actionClass.newInstance();
            if (action.usingValueWords()) {
                action.setValueWords(words);
            }
            else {
                action.setValueRaw(scriptLine.split(actionString)[1]);
            }
            return action;
        }
        return null;
    }
}
