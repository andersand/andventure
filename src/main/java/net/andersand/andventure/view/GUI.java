package net.andersand.andventure.view;

import net.andersand.andventure.Const;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.view.dialogs.Dialog;

/**
 * @author asn
 */
public class GUI implements Renderable, GUIAccessor {

    protected Dialog briefingDialog; // used for briefing and debriefing
    protected Bounds windowBounds;
    protected Position briefingPosition;
    protected Dialog dialog; // npc or other dialog

    public GUI() {
    }
    
    public void init(Bounds windowBounds) {
        this.windowBounds = windowBounds;
        calculatePositionForBriefing();
    }

    public void createBriefing(Level level) {
        briefingDialog = level.getBriefing(briefingPosition);
    }

    public void createDebriefing(Level level) {
        briefingDialog = level.getDebriefing(briefingPosition);
    }

    public void render() {
    }
    
    public void renderBriefingDialog() {
        briefingDialog.render();
    }

    protected void calculatePositionForBriefing() {
        briefingPosition = new Position(0, 0);
        if (windowBounds.width > Const.PAPER_WIDTH) {
            int newX = (windowBounds.width/2)-(Const.PAPER_WIDTH /2);
            briefingPosition.setX(newX);
        }
        if (windowBounds.height > Const.PAPER_HEIGHT) {
            int newY = (windowBounds.height/2)-(Const.PAPER_HEIGHT /2);
            briefingPosition.setY(newY);
        }
    }

    @Override
    public Position getDialogPosition() {
        return briefingPosition;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void renderDialog() {
        dialog.render();
    }
}
