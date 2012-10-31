package net.andersand.andventure.model.level.objectives;

import net.andersand.andventure.interactions.ObjectiveLevelInteraction;
import net.andersand.andventure.model.elements.Foe;

import java.util.List;

/**
 * @author asn
 */
public class DefeatAllFoesObjective extends Objective {

    @Override
    protected boolean isObjectiveCompleted(ObjectiveLevelInteraction objectiveLevelInteraction) {
        List<Foe> foes = objectiveLevelInteraction.getFoes();
        for (Foe foe : foes) { // Consider doing this iteration only when a Foe dies to save resources
            if (!foe.isDead()) {
                return false;
            }
        }
        return true;
    }
}
