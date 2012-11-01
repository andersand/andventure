package net.andersand.andventure.view;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Player;

/**
 * @author asn
 */
public class ScriptAccessor {
    
    protected GUI gui;
    protected Player player;
    
    public Position getDialogPosition() {
        return gui.getDialogPosition();
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
