package net.andersand.andventure.view;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.engine.GameState;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.view.dialogs.Dialog;

/**
 * @author asn
 */
public class GUI implements Renderable {

    protected Dialog briefingDialog;
    protected PropertyHolder propertyHolder;
    protected Bounds windowBounds;
    protected Position briefingPosition;

    public GUI(PropertyHolder propertyHolder, Bounds windowBounds) {
        this.propertyHolder = propertyHolder;
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
    
    public void renderDialog() {
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

}
