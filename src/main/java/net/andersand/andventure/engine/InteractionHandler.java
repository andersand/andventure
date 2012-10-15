package net.andersand.andventure.engine;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Creature;
import net.andersand.andventure.model.elements.Element;
import net.andersand.andventure.model.elements.Interactable;
import net.andersand.andventure.model.elements.Passable;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Input;

/**
 * @author asn
 */
public class InteractionHandler {

    protected boolean moveRequested;
    protected int changeX = 0;
    protected int changeY = 0;
    private Level currentLevel;

    public InteractionHandler(Input input, Level level) {
        currentLevel = level;
        queryInput(input);
    }

    protected void queryInput(Input input) {
        if (input.isKeyDown(Input.KEY_A)) {
            changeX = -1;
        }
        if (input.isKeyDown(Input.KEY_S)) {
            changeY = 1;
        }
        if (input.isKeyDown(Input.KEY_D)) {
            changeX = 1;
        }
        if (input.isKeyDown(Input.KEY_W)) {
            changeY = -1;
        }
        moveRequested = !(changeX == 0 && changeY == 0);
    }

    public InteractionResult perform(Creature performer) {
        // you gotta look before you go bro...
        // or else you might get stuck in a wall or something...
        Position lookingDestination = performer.look(changeX, changeY);
        Element elementAtDestination = currentLevel.look(lookingDestination);
        if (elementAtDestination == null) {
            performer.move(changeX, changeY);
            return InteractionResult.MOVE_PERFORMED;
        }
        if (elementAtDestination instanceof Passable) {
            Passable passable = (Passable) elementAtDestination;
            if (passable.isPassableNow()) {
                performer.move(changeX, changeY);
                return InteractionResult.MOVE_PERFORMED;
            }
            else {
                return InteractionResult.MOVE_PROHIBITED; // eg. door closed/locked
            }
        }
        // todo HIGH handle door opening, creature interaction (talk to npc or attack foe), etc.
        if (elementAtDestination instanceof Interactable) {
            ((Interactable) elementAtDestination).interact();
        }
        return InteractionResult.MOVE_PROHIBITED;
    }

    public boolean isMoveRequested() {
        return moveRequested;
    }
}