package net.andersand.andventure.engine;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.model.elements.Creature;
import net.andersand.andventure.model.elements.Player;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.level.LevelLoader;
import net.andersand.andventure.view.GUI;
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
public class Controller {
    
    protected List<Level> levels;
    protected Level currentLevel;
    protected Player playerElement; // Consider a between-levels persistent non-Element "Player" class
    protected int currentLevelIndex = 0;
    protected Bounds windowBounds;
    protected GUI gui;
    protected GameState gameState;
    protected PropertyHolder propertyHolder;

    public Controller(PropertyHolder propertyHolder, Bounds bounds) {
        this.propertyHolder = propertyHolder;
        this.windowBounds = bounds;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
    
    public void init() {
        gui = new GUI(propertyHolder, windowBounds);
        gameState = GameState.INIT_COMPLETE;
    }
    
    public void startGame() {
        displayBriefing(levels.get(0));
    }

    protected void gotoNextLevel() {
        gotoLevel(+1);
    }

    protected void gotoPreviousLevel() {
        gotoLevel(-1);
    }

    protected void gotoLevel(int relativeIndex) {
        currentLevelIndex += relativeIndex;
        startLevel();
    }

    protected void startLevel() {
        currentLevel = levels.get(currentLevelIndex);
        currentLevel.init(windowBounds);
        playerElement = currentLevel.getPlayer();
    }

    protected void displayBriefing(Level level) {
        gui.createBriefing(level);
        gameState = GameState.BRIEFING;
    }
    
    protected void displayDebriefing(Level level) {
        gui.createDebriefing(level);
        gameState = GameState.BRIEFING;
    }

    public void handlePlayerInput(GameContainer container) {
        Input input = container.getInput();

        handleDeveloperMode(input);

        InteractionHandler handler = new InteractionHandler(input, currentLevel);
        if (handler.isMoveRequested()) {
            handler.perform(playerElement);
        }
    }

    public void handleUserInput(GameContainer container) {
        Input input = container.getInput();

        if (gameState.equals(GameState.BRIEFING) && input.isKeyDown(Input.KEY_SPACE)) {
            gameState = GameState.IN_GAME;
            startLevel();
        }
        if (input.isKeyPressed(Input.KEY_Q)) {
            System.exit(0); // todo LOW Quit game more nicely
        }
    }

    protected void handleDeveloperMode(Input input) {
        if (Const.DEVELOPER_MODE && gameState.equals(GameState.IN_GAME)) {
            
            if (input.isKeyPressed(Input.KEY_ADD)) {
                gotoNextLevel();
            }
            if (input.isKeyPressed(Input.KEY_SUBTRACT)) {
                gotoPreviousLevel();
            }
        }
    }

    public void performAI() {
        for (Creature c : currentLevel.getCreaturesAI()) {
           c.move();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void checkObjectives() {
        if (currentLevel.isCompleted()) {
            gameState = GameState.LEVEL_COMPLETE;
        }
    }

    public void endLevel() {
        displayDebriefing(currentLevel);
        currentLevelIndex++;
    }

    public void renderBriefing() {
        gui.renderDialog();
    }

    public void renderLevel() {
        currentLevel.render();
    }

}
