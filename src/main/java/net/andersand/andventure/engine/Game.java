package net.andersand.andventure.engine;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.LevelLoader;
import net.andersand.andventure.view.GUI;
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
        LevelLoader levelLoader = new LevelLoader(gui);
        Bounds bounds = levelLoader.loadLevels();
        bounds = Util.getAdjustedBounds(bounds);
        gui.init(bounds);
        appGameContainer.setDisplayMode(bounds.width, bounds.height, false);
        controller = new Controller(bounds, gui);
        controller.setLevels(levelLoader.getLevels());
        controller.init();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
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
            case BRIEFING:
                controller.renderBriefing();
                break;
        }
    }

    public void setAppContainer(AppGameContainer container) {
        this.appGameContainer = container;
    }
}
