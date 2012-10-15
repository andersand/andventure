package net.andersand.andventure.framework.slick.demo;

import net.andersand.andventure.Const;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

/**
 * @author asn
 */
public class SlickPlaneDemoGame extends BasicGame {

    Image land = null;
    Image plane = null;
    float x = 400;
    float y = 300;
    float scale = 1.0f;

    public SlickPlaneDemoGame() {
        super(Const.GAME_TITLE);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        land = new Image(Const.IMG_DIR + "demo/land.jpg");
        plane = new Image(Const.IMG_DIR + "demo/plane.png");
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();

        if(input.isKeyDown(Input.KEY_A))
        {
            plane.rotate(-0.2f * delta);
        }

        if(input.isKeyDown(Input.KEY_D))
        {
            plane.rotate(0.2f * delta);
        }

        if(input.isKeyDown(Input.KEY_W))
        {
            float hip = 0.4f * delta;

            float rotation = plane.getRotation();

            x+= hip * Math.sin(Math.toRadians(rotation));
            y-= hip * Math.cos(Math.toRadians(rotation));
        }

        if(input.isKeyDown(Input.KEY_2))
        {
            scale += (scale >= 5.0f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
        if(input.isKeyDown(Input.KEY_1))
        {
            scale -= (scale <= 0.5f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
        Display.sync(Const.FPS);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        land.draw(0, 0);
        graphics.drawString("x = " + x + "   y = " + y , 0, 100);
        plane.draw(x, y, scale);
    }
}