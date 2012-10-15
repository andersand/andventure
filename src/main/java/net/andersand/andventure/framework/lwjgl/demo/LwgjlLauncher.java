package net.andersand.andventure.framework.lwjgl.demo;

import net.andersand.andventure.Const;
import net.andersand.andventure.framework.lwjgl.demo.game.Game;
import net.andersand.andventure.framework.lwjgl.demo.game.LwjglDemoGameImpl;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * @author asn
 */
public class LwgjlLauncher {

    Game gameImpl = new LwjglDemoGameImpl();

    public LwgjlLauncher() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        }
        catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        initOpenGL();
        run();
        Display.destroy();
    }

    private void initOpenGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void run() {
        while (!Display.isCloseRequested()) {

            gameImpl.run();

            Display.update();
            Display.sync(Const.FPS);
        }
    }

}
