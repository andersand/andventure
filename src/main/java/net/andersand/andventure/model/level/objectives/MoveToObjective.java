package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.model.LevelObjectiveInteraction;
import net.andersand.andventure.model.Position;

/**
 * @author asn
 */
public class MoveToObjective extends Objective {

    protected Position target;
    
    @Override
    protected boolean isObjectiveCompleted(LevelObjectiveInteraction levelObjectiveInteraction) {
        return false;  // this objective type needs access to player.position
    }
}
