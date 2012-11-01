package net.andersand.andventure.model.level;

import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.engine.Const;
import net.andersand.andventure.engine.Util;
import net.andersand.andventure.interactions.CreatureLevelInteraction;
import net.andersand.andventure.interactions.ElementLevelInteraction;
import net.andersand.andventure.interactions.ObjectiveLevelInteraction;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.Tile;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.level.objectives.Objective;
import net.andersand.andventure.model.level.script.Script;
import net.andersand.andventure.view.dialogs.Briefing;
import net.andersand.andventure.view.dialogs.Debriefing;
import net.andersand.andventure.view.dialogs.Dialog;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Level implements Renderable, CreatureLevelInteraction, ObjectiveLevelInteraction, ElementLevelInteraction {

    protected Meta meta;
    protected Script script;
    protected List<Tile> floorTiles = new ArrayList<Tile>();
    protected List<Element> elements;
    protected List<Creature> creaturesAI = new ArrayList<Creature>();
    protected List<Foe> foes = new ArrayList<Foe>();

    protected Player player;
    protected Bounds dimension;
    protected SpriteSheet spriteSheet;

    public Level() {
        loadSpriteSheet();
    }

    /**
     * Sets the elements for the level.
     * 
     * Ordering elements in drawing order. Objects and other passable stuff should be drawn first
     * so movable elements are drawn on top of them. 
     * 
     * Also picking out specific elements for quick reference from field
     * @param elements all elements, in order of y,x as they are parsed
     */
    public void setElements(List<Element> elements) {
        List<Element> elementsOrderedSpecifically = new ArrayList<Element>(elements.size());
        List<Element> interactables = new ArrayList<Element>();
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
            else if (element instanceof Interactable) {
                interactables.add(element);
            }
            else {
                elementsOrderedSpecifically.add(element);
            }
        }
        elementsOrderedSpecifically.addAll(interactables);
        elementsOrderedSpecifically.addAll(creaturesAI);
        elementsOrderedSpecifically.add(player);
        this.elements = elementsOrderedSpecifically;        
    }

    public void render() throws SlickException {
        drawFloorTiles();
        drawElements();
    }

    protected void drawFloorTiles() {
        for (Tile tile : floorTiles) {
            tile.render();
        }
    }

    protected void drawElements() throws SlickException {
        for (Element element : elements) {
            element.render();
        }
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * todo HIGH consider a double array for indexed lookup, to improve performance if necessary.
     * @return element at relative position, null if floor
     */
    public Element look(Position position) {
        if (position == null) {
            throw new IllegalStateException("Cannot look without a position");
        }
        return elementAt(position.getX(), position.getY());
    }

    protected Element elementAt(int x, int y) {
        for (Element element : elements) {
            if (element.getPosition().getX() == x
                && element.getPosition().getY() == y) {
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
    
    protected void loadSpriteSheet() {
        try {
            spriteSheet = new SpriteSheet(Const.IMG_DIR + "spritesheet.png", Const.ELEMENT_SIZE_PIXELS, Const.ELEMENT_SIZE_PIXELS);
        }
        catch (SlickException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to load the spritesheet");
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

    public Script getScript() {
        return script;
    }

    public void setDimension(Bounds dimension) {
        this.dimension = dimension;
    }

    /**
     * Some elements like Walls and Desks, that consists of several neighbouring elements,
     * need to be aligned - given specific images depending on which elements are their 
     * neighbours.
     */
    public void handleContiguousElements() {
        new ContiguityHandler();
    }

    @Override
    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    protected class ContiguityHandler {

        protected ContiguityHandler() {
            Element element;
            for (int x = 0; x < dimension.width; x++) {
                for (int y = 0; y < dimension.height; y++) {
                    element = elementAt(x, y);
                    if (element instanceof ContiguousElement) {
                        handleContiguity((ContiguousElement)element);
                    }
                }
            }
        }

        /**
         * Returs element only if it is one of the specified alignmentClasses
         */
        protected Element query(Element element, List<Class<? extends Element>> alignmentClasses) {
            if (element == null) {
                return null;
            }
            if (alignmentClasses.contains(element.getClass())) {
                return element;
            }
            else {
                return null;
            }
        }

        protected void handleContiguity(ContiguousElement element) {
            Element el = (Element) element;
            int x = el.getPosition().getX();
            int y = el.getPosition().getY();
            Element left  = query(elementAt(x-1, y), element.getAlignmentClasses());
            Element right = query(elementAt(x+1, y), element.getAlignmentClasses());
            Element up    = query(elementAt(x, y-1), element.getAlignmentClasses());
            Element down  = query(elementAt(x, y+1), element.getAlignmentClasses());
            element.align(left, right, up, down);
        }

    }
    
}
