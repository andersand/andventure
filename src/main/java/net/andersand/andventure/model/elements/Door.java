package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Door extends Structure implements Interactable, Passable {

    protected Image open;
    protected Image closed;
    
    protected State state;

    @Override
    public Image getImage() {
        return state.equals(State.OPEN) ? open : closed;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        if ("-".equals(String.valueOf(levelDataChar))) {
            open = Util.loadElementImage("dho");
            closed = Util.loadElementImage("dhc");
        }
        else if ("|".equals(String.valueOf(levelDataChar))) {
            open = Util.loadElementImage("dvo");
            closed = Util.loadElementImage("dvc");
        }
        state = State.CLOSED;// all doors are initially closed
    }

    @Override
    public void interact() {
        if (state.equals(State.OPEN)) {
            state = State.CLOSED;
            position.change(-1, 0);
        }
        else {
            state = State.OPEN;
            position.change(1, 0); // meh - this makes the wall next to the door passable :/
        }
        Util.log("Using door " + state);
    }

    @Override
    public boolean isPassableNow() {
        return state == State.OPEN;
    }

    enum State {
        OPEN,
        CLOSED
    }
}
