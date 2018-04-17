package net.andersand.andventure.engine;

import net.andersand.andventure.model.interactions.ComplexInteraction;
import net.andersand.andventure.model.interactions.DialogInteraction;
import net.andersand.andventure.model.interactions.Interaction;
import net.andersand.andventure.model.elements.Creature;
import net.andersand.andventure.model.elements.Player;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.level.script.Statement;
import net.andersand.andventure.view.GUI;
import net.andersand.andventure.view.ScriptAccessor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.List;

/**
 * Game controller.
 * Purpose: The "brain" class, that knows what happens next,
 * exists primarily to prevent Game class from growing out of hand
 *
 * @author asn
 */
public class Controller {
    
    private List<Level> levels;
    private Level currentLevel;
    private Player playerElement; // Consider a between-levels persistent non-Element "Player" class
    private int currentLevelIndex = -1;
    private Bounds windowBounds;
    private GUI gui;
    private ScriptAccessor scriptAccessor;
    private GameState gameState;
    private ComplexInteraction complexInteraction;

    public Controller(Bounds bounds, GUI gui, ScriptAccessor scriptAccessor) {
        this.windowBounds = bounds;
        this.gui = gui;
        this.scriptAccessor = scriptAccessor;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public void init() {
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
        scriptAccessor.setPlayer(playerElement);
        gameState = GameState.IN_GAME;
    }

    protected void displayBriefing(Level level) {
        gui.createBriefing(level);
        gameState = GameState.BRIEFING;
    }
    
    protected void displayDebriefing() {
        gui.createDebriefing(currentLevel);
        gameState = GameState.DEBRIEFING;
    }

    public void handlePlayerInput(GameContainer container) {
        Input input = container.getInput();

        handleDeveloperMode(input);

        InteractionHandler handler = new InteractionHandler(input, currentLevel);
        if (handler.isMoveRequested()) {
            Interaction interaction = handler.perform(playerElement);
            if (interaction instanceof ComplexInteraction) {
                gameState = GameState.SHOW_DIALOG;
                complexInteraction = (ComplexInteraction) interaction;
                if (interaction instanceof DialogInteraction) {
                    gui.setDialog(((DialogInteraction) interaction).getDialog());
                }
            }
        }
    }

    public void handleUserInput(GameContainer container) {
        Input input = container.getInput();

        if (input.isKeyPressed(Input.KEY_SPACE)) {
            switch (gameState) {
                case BRIEFING:
                    gotoNextLevel();
                    break;
                case DEBRIEFING:
                    displayBriefing(levels.get(currentLevelIndex+1));
                    break;
                case SHOW_DIALOG:
                    gameState = GameState.CHAINED_STATEMENT;
                    break;
            }
        }
        else if (input.isKeyPressed(Input.KEY_Q)) {
            System.exit(0); // todo LOW Quit game more nicely
        }
    }

    protected void handleDeveloperMode(Input input) {
        if (PropertyHolder.getBoolean("developer.mode") && gameState.equals(GameState.IN_GAME)) {
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
        displayDebriefing();
    }

    public void renderBriefing() {
        gui.renderBriefingDialog();
    }

    public void renderLevel() throws SlickException {
        currentLevel.render();
    }

    public void renderDialog() {
        gui.renderDialog();
    }

    public void executeChainedStatement() {
        if (complexInteraction != null) {
            Statement chainedStatement = complexInteraction.getNextStatement();
            if (chainedStatement != null) {
                chainedStatement.execute();
            }
            else {
                // end of statement chain
                complexInteraction = null;
                gameState = GameState.IN_GAME;
            }
        }
        else {
            gameState = GameState.IN_GAME;
        }
    }
}
