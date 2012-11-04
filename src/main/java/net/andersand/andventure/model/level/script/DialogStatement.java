package net.andersand.andventure.model.level.script;

import net.andersand.andventure.view.dialogs.NPCDialog;

/**
 * @author asn
 */
public class DialogStatement extends Statement {

    protected String text;
    protected String characterName;
    protected String imageFileName;

    @Override
    public ExecutionResult execute() {
        NPCDialog dialog = new NPCDialog(scriptAccessor.getDialogPosition(), text, characterName, imageFileName);
        dialog.setup();
        return dialog;
    }

    @Override
    protected void parseValue() {
        String[] quoteParts = valueRaw.split("\"");
        text = quoteParts[1];
        String[] words = valueRaw.split(" ");
        parsePosition(words[0]);
        characterName = words[2];
        imageFileName = words[1];
    }

    @Override
    public boolean usingValueWords() {
        return false;
    }

}
