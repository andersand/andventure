package net.andersand.andventure.engine;

import net.andersand.andventure.Const;
import net.andersand.andventure.GameState;
import net.andersand.andventure.GameStateListener;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.menu.GUI;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import java.util.List;

/**
 * Game controller.
 * Purpose: The "brain" class, that knows what happens next,
 * exists primarily to prevent Game class from growing out of hand
 * 
 * @author asn
 */
public class Controller implements GameStateListener {
    
    private List<Level> levels;
    private Level currentLevel;
    private Player player;
    private int nextLevelIndex = 0;
    private Bounds windowBounds;
    private GUI gui;
    private GameState gameState;

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
    
    public void startGame() {
        gui = new GUI(this);
        gotoNextLevel();
        gui.setBriefingPosition(calculatePositionForBriefing());
        gameState = GameState.BRIEFING;
    }
    
    public void gotoNextLevel() {
        currentLevel = levels.get(nextLevelIndex++);
        player = currentLevel.getPlayer();
        gui.createBriefing(currentLevel);
        gameState = GameState.BRIEFING;
    }
    
    private Position calculatePositionForBriefing() {
        Position p = new Position(0, 0);
        if (windowBounds.width > Const.WINDOW_MINIMUM_WIDTH) {
            int newX = (windowBounds.width/2)-(Const.WINDOW_MINIMUM_WIDTH/2);
            p.setX(newX);
        }
        if (windowBounds.height > Const.WINDOW_MINIMUM_HEIGHT) {
            int newY = (windowBounds.height/2)-(Const.WINDOW_MINIMUM_HEIGHT/2);
            p.setY(newY);
        }
        return p;
    }
    
    public void handlePlayerInput(GameContainer container) {
        Input input = container.getInput();

        handleDeveloperMode(input);

        InteractionHandler handler = new InteractionHandler(input, currentLevel);
        if (handler.isMoveRequested()) {
            handler.perform(player);
        }
    }

    public void handleUserInput(GameContainer container) {
        Input input = container.getInput();

        if (gameState.equals(GameState.BRIEFING) && input.isKeyPressed(Input.KEY_SPACE)) {
            gameState = GameState.IN_GAME;
        }
        if (input.isKeyPressed(Input.KEY_Q)) {
            System.exit(0); // todo MID Quit game more nicely
        }
    }

    private void handleDeveloperMode(Input input) {
        if (Const.DEVELOPER_MODE) {
            
            if (input.isKeyPressed(Input.KEY_N)) {
                gotoNextLevel();
            }
        }
    }

    public void performAI(GameContainer container) {
        for (Creature c : currentLevel.getCreaturesAI()) {
           c.move();
        }
    }

    public void setWindowBounds(Bounds windowBounds) {
        this.windowBounds = windowBounds;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    public GUI getGui() {
        return gui;
    }



}
