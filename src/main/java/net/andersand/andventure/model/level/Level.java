package net.andersand.andventure.model.level;

import net.andersand.andventure.Const;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.Util;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.*;
import net.andersand.andventure.model.elements.Creature;
import net.andersand.andventure.model.elements.Element;
import net.andersand.andventure.model.elements.Foe;
import net.andersand.andventure.model.elements.Player;
import net.andersand.andventure.model.level.objectives.Objective;
import net.andersand.andventure.model.level.script.Script;
import net.andersand.andventure.view.dialogs.Briefing;
import net.andersand.andventure.view.dialogs.Debriefing;
import net.andersand.andventure.view.dialogs.Dialog;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Level implements Renderable, LevelCreatureInteraction, LevelObjectiveInteraction  {

    protected List<Tile> floorTiles = new ArrayList<Tile>();
    protected List<Element> elements;
    protected Meta meta;
    protected List<Creature> creaturesAI = new ArrayList<Creature>();
    protected List<Foe> foes = new ArrayList<Foe>();
    protected Player player;
    protected Script script;
    protected PropertyHolder propertyHolder;

    public Level(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
    }

    public void setElements(List<Element> elements) {
        // ordering elements in drawing order. Objects and other passable stuff should be drawn first
        // so movable elements are drawn on top of them
        List<Element> elementsOrderedSpecifically = new ArrayList<Element>(elements.size());
        for (Element element : elements) {
            if (element instanceof Player) {
                player = (Player)element;
            }
            else if (element instanceof Creature) {
                creaturesAI.add((Creature)element);
                if (element instanceof Foe) {
                    foes.add((Foe)element);
                }
            }
            else {
                elementsOrderedSpecifically.add(element);
            }
        }
        elementsOrderedSpecifically.addAll(creaturesAI);
        elementsOrderedSpecifically.add(player);
        this.elements = elementsOrderedSpecifically;        
    }

    public void render() {
        drawFloorTiles();
        drawElements();
    }

    protected void drawFloorTiles() {
        for (Tile tile : floorTiles) {
            tile.render();
        }
    }

    protected void drawElements() {
        for (Element element : elements) {
            element.render();
        }
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * todo LOW consider a double array for indexed lookup, to improve performance if necessary.
     * @return element at relative position, null if floor
     */
    public Element look(Position position) {
        if (position == null) {
            throw new IllegalStateException("Cannot look without a position");
        }
        for (Element element : elements) {
            if (element.getPosition().equals(position)) {
                return element;
            }
        }
        return null;
    }

    public List<Creature> getCreaturesAI() {
        return creaturesAI;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }
    
    public boolean isCompleted() {
        for (Objective objective : meta.getObjectives()) {
            if (!objective.isCompleted(this)) {
                return false;
            }
        }
        return true;
    }
    
    public void setScript(Script script) {
        this.script = script;
    }

    public void init(Bounds bounds) {
        String environmentFileName = getMeta().getEnvironment();
        if (environmentFileName == null) {
            environmentFileName = "interior_stone";
        }
        int floorTilesHoriz = bounds.width / Const.BGTILE_SIZE_PIXELS;
        int floorTilesVert = bounds.height / Const.BGTILE_SIZE_PIXELS;
        for (int i = 0; i <= floorTilesHoriz; i++) {
            for (int j = 0; j <= floorTilesVert; j++) {
                Image image = Util.loadImage("backgrounds/" + environmentFileName + ".png");
                image.rotate(Util.random(4) * 90);
                Tile tile = new Tile(image);
                tile.setPosition(new Position(i, j));
                floorTiles.add(tile);
            }
        }
    }

    @Override
    public Position getPlayerPosition() {
        return player.getPosition();
    }

    @Override
    public List<Foe> getFoes() {
        return foes;
    }

    public Dialog getBriefing(Position dialogPosition) {
        return new Briefing(dialogPosition, this);
    }

    public Dialog getDebriefing(Position dialogPositon) {
        return new Debriefing(dialogPositon, this);
    }
}
