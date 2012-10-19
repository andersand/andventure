package net.andersand.andventure.view;

import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.engine.GameState;
import net.andersand.andventure.engine.GameStateListener;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.menu.Briefing;

/**
 * @author asn
 */
public class GUI implements Renderable {

    protected Briefing briefing;
    protected GameStateListener gameStateListener;
    protected PropertyHolder propertyHolder;
    protected Position briefingPosition;

    public GUI(GameStateListener gameStateListener, PropertyHolder propertyHolder) {
        this.gameStateListener = gameStateListener;
        this.propertyHolder = propertyHolder;
    }

    public void createBriefing(Level level) {
        briefing = level.getBriefing(briefingPosition);
    }

    public void createDebriefing(Level level) {
        briefing = new Debriefing(briefingPosition, level);
    }

    public void render() {
        if (gameStateListener.getGameState().equals(GameState.BRIEFING)) {
            briefing.render();
        }
    }

    public void setBriefingPosition(Position position) {
        briefingPosition = position;
    }

}
