package net.andersand.andventure.engine;

import net.andersand.andventure.model.level.LevelLoader;
import net.andersand.andventure.view.GUI;
import net.andersand.andventure.view.ScriptAccessor;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

/**
 * Framework-specific class that interacts with the Controller
 * Keep to a minimum.
 * 
 * @author asn
 */
public class Game extends BasicGame {

    protected AppGameContainer appGameContainer;
    protected Controller controller;

    public Game() {
        super(Const.GAME_TITLE);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        GUI gui = new GUI();
        ScriptAccessor scriptAccessor = new ScriptAccessor();
        scriptAccessor.setGUI(gui);
        LevelLoader levelLoader = new LevelLoader(scriptAccessor);
        Bounds bounds = levelLoader.loadLevels();
        bounds = Util.getAdjustedBounds(bounds);
        gui.init(bounds);
        appGameContainer.setDisplayMode(bounds.width, bounds.height, false);
        controller = new Controller(bounds, gui, scriptAccessor);
        controller.setLevels(levelLoader.getLevels());
        controller.init();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Util.log("update : " + controller.getGameState());
        controller.handleUserInput(container);
        switch (controller.getGameState()) {
            case IN_GAME:
                controller.handlePlayerInput(container);
                controller.performAI();
                controller.checkObjectives();
                break;
            case LEVEL_COMPLETE:
                controller.endLevel();
                break;
            case INIT_COMPLETE:
                controller.startGame();
                break;
            case CHAINED_STATEMENT:
                controller.executeChainedStatement();
                break;
        }
        Display.sync(Const.FPS);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        Util.log("render : " + controller.getGameState());
        switch (controller.getGameState()) {
            case IN_GAME :
                // intentional fall through
            case CHAINED_STATEMENT:
                controller.renderLevel();
                break;
            case SHOW_DIALOG:
                controller.renderLevel();
                controller.renderDialog();
                break;
            case DEBRIEFING:
                // intentional fall through
            case BRIEFING:
                controller.renderBriefing();
                break;
        }
    }

    public void setAppContainer(AppGameContainer container) {
        this.appGameContainer = container;
    }
}
