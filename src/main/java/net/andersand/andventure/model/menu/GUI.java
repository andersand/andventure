package net.andersand.andventure.model.menu;

import net.andersand.andventure.GameState;
import net.andersand.andventure.GameStateListener;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;

/**
 * @author asn
 */
public class GUI implements Renderable {

    Briefing briefing;
    GameStateListener gameStateListener;

    public GUI(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public void createBriefing(Level level) {
        Position p = null;
        if (briefing != null) {
            p = briefing.getPosition();
        }
        briefing = new Briefing(level);
        briefing.setPosition(p);
    }
    
    public void render() {
        if (gameStateListener.getGameState().equals(GameState.BRIEFING)) {
            briefing.render();
        }
    }

    public void setBriefingPosition(Position position) {
        briefing.setPosition(position);
    }
}
