package net.andersand.andventure.engine;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Input;

/**
 * @author asn
 */
public class InteractionHandler {

    protected boolean moveRequested;
    protected int changeX = 0;
    protected int changeY = 0;
    protected Level currentLevel;

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

    public InteractionResult perform(Creature creature) {
        // you gotta look before you go bro...
        // or else you might get stuck in a wall or something...
        Position lookingDestination = creature.look(changeX, changeY);
        Element elementAtDestination = currentLevel.look(lookingDestination);
        if (elementAtDestination == null) {
            creature.move(changeX, changeY);
            return InteractionResult.MOVE_PERFORMED;
        }
        if (elementAtDestination instanceof Passable) {
            Passable passable = (Passable) elementAtDestination;
            if (passable.isPassableNow()) {
                creature.move(changeX, changeY);
                return InteractionResult.MOVE_PERFORMED;
            }
        }
        // todo HIGH handle creature interaction (talk to npc), and picking up objects
        if (elementAtDestination instanceof Interactable) {
            ((Interactable) elementAtDestination).interact();
            return InteractionResult.INTERACTION_PERFORMED;
        }
        if (elementAtDestination instanceof Foe) {
            creature.attack(elementAtDestination);
        }
        if (elementAtDestination instanceof Neutral) {
            creature.interact(elementAtDestination);
        }
        return InteractionResult.MOVE_PROHIBITED;
    }

    public boolean isMoveRequested() {
        return moveRequested;
    }
}