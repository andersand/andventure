package net.andersand.andventure.interactions;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.DialogStatement;
import net.andersand.andventure.view.dialogs.Dialog;

/**
 * @author asn
 */
public class DialogInteraction extends ComplexInteraction {

    protected DialogStatement statement;

    public DialogInteraction(DialogStatement statement) {
        this.statement = statement;
    }

    public Dialog getDialog() {
        return (Dialog) statement.execute();
    }
}
