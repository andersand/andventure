package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.interactions.ComplexInteraction;
import net.andersand.andventure.interactions.DialogInteraction;
import net.andersand.andventure.interactions.Interaction;
import net.andersand.andventure.interactions.SimpleInteraction;
import net.andersand.andventure.model.level.script.DialogStatement;
import net.andersand.andventure.model.level.script.Script;
import net.andersand.andventure.model.level.script.Statement;

import java.util.List;

/**
 * Neutrals can be either passive or NPCs
 * 
 * @author asn
 */
public class Neutral extends Creature implements Interactable {

    protected List<Statement> npcStatements;
    protected DialogStatement dialogStatement;

    @Override
    public void init(char levelDataChar, Script script) {
        initNPC(script);
        bodyImage = Util.loadElementImage("n");
    }

    private void initNPC(Script script) {
        this.npcStatements = script.initNPC(this);
        for (Statement stat : npcStatements) {
            if (stat instanceof DialogStatement) {
                dialogStatement = (DialogStatement)stat;
            }
        }
    }

    @Override
    protected boolean preventMove() {
        return true; // For now, NPCs do not move
    }

    @Override
    protected String getDeadImage() {
        return "nd"; // dead npc in spritesheet looks better ;-)
    }

    @Override
    protected String getBodyImage() {
        return "n";
    }

    /**
     * @param actor the Creature that interacts with this Neutral.
     */
    @Override
    public Interaction interact(Creature actor) {
        if (actor instanceof Player) {
            if (dialogStatement != null) {
                ComplexInteraction complexInteraction = new DialogInteraction(dialogStatement);
                complexInteraction.setStatements(npcStatements);
                return complexInteraction;
            }
        }
        return SimpleInteraction.INTERACTION_PERFORMED;
    }
}
