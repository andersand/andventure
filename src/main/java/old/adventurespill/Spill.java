package old.adventurespill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

/**
 * <p>Title: Spill <p>
 * @author Anders Nordbø
 * @version 1.1
 */

/** @todo sectors with various,multiple items */
/** @todo rewrite item/obstacle handling so that:
 * items are items only, and obstacles are only obstacles. both should be
 * addable to applet directly (rewrite of parsemap)*/
/** @todo create map victory condition field+methods
 * (exit sector/victory cond./etc)*/

/**
 * @todo load and save maps
 */


public class Spill extends JApplet implements KeyListener, FocusListener {
    private int plx, ply;
    private JLabel pl = new JLabel();
    private Dimension size = new Dimension(15, 15);
    private Brett brett;
    private Player player;

    /**
     * This is the visual representation for Obstacles
     */
    private JLabel[] obstacles;

    /**
     * This is the visual representation for Items
     */
    private JLabel[] items;

    /**
     * set DEBUG=true if you want to enable debugging output
     * (turn false before putting applet on server)
     */
    static final boolean DEBUG = true;
    /**
     * Even more debugging
     */
    static final boolean DEBUG_2 = false;
    /**
     * Gives debugging output for most interesting method
     */
    static final boolean DEBUG_3 = true;
    /**
     * Gives debugging output for the rest of the methods
     */
    static final boolean DEBUG_4 = false;
    /**
     * set LOCAL=true if applet to be run from a local machine,
     * and false if run from a webserver. If not set properly,
     * images and other files needed won't be loaded.
     * Normally this should be set to false, as applets are ment
     * to be run from servers, not from local systems.
     */
    private static final boolean LOCAL = false;

    public void init() {
        if (DEBUG_4)
            System.out.println("init");
        addKeyListener(this);
        addFocusListener(this);

        getContentPane().setLayout(null);

        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, false);
        initplayer();
        makemap();
        parsemap();
    }

    public void paint(Graphics g) {
        if (DEBUG_4)
            System.out.println("paint");
        super.paint(g);
        pl.setBounds(plx, ply, size.width, size.height);
        requestFocusInWindow();
    }

    public Brett getBrett() {
        return brett;
    }

    /**
     * Moves the player in the wanted direction provided it is verified.
     * Checking for exceeding applet boundaries is done here. So the player won't
     * walk out of the visible are of the applet.
     *
     * @param k KeyEvent
     */
    private void move(KeyEvent k) {
        if (DEBUG_3)
            System.out.println("move");
        int key = k.getKeyCode();
        Sector her = brett.getSectors().findSector(plx, ply);
        switch (key) {
            case KeyEvent.VK_UP:
                if (ply > 0 && verify(KeyEvent.VK_UP))
                    ply -= size.height;
                break;
            case KeyEvent.VK_DOWN:
                if (ply < this.getSize().height - size.height && verify(KeyEvent.VK_DOWN))
                    ply += size.width;
                break;
            case KeyEvent.VK_LEFT:
                if (plx > 0 && verify(KeyEvent.VK_LEFT))
                    plx -= size.width;
                break;
            case KeyEvent.VK_RIGHT:
                if (plx < this.getSize().width - size.width && verify(KeyEvent.VK_RIGHT))
                    plx += size.width;
                break;
            case KeyEvent.VK_HOME:
                if (ply > 0 && plx > 0 && verify(KeyEvent.VK_HOME)) {
                    ply -= size.height;
                    plx -= size.width;
                }
                break;
            case KeyEvent.VK_END:
                if (plx > 0 &&
                        ply < this.getSize().height - size.height &&
                        verify(KeyEvent.VK_END)) {
                    ply += size.height;
                    plx -= size.width;
                }
                break;
            case KeyEvent.VK_PAGE_UP:
                if (ply > 0 &&
                        plx < this.getSize().width - size.width &&
                        verify(KeyEvent.VK_PAGE_UP)) {
                    ply -= size.height;
                    plx += size.width;
                }
                break;
            case KeyEvent.VK_PAGE_DOWN:
                if (ply < this.getSize().height - size.height &&
                        plx < this.getSize().width - size.width &&
                        verify(KeyEvent.VK_PAGE_DOWN)) {
                    ply += size.height;
                    plx += size.width;
                }
                break;
            case KeyEvent.VK_SPACE: // interaction key
                interact(her);
                break;
            case KeyEvent.VK_L:
                look(her);
                break;
            case KeyEvent.VK_I:
                inventory();
                break;
        }
        repaint();
    }

    /**
     * Verifying whether there are obstacles like walls blocking the movement
     * for the player, this method is the law of movement in this game.
     * And for certain objects, check for a key, change image to open door etc.
     *
     * @param direction int, what to add/sub from plx,ply
     *                  (to get the sector player is trying to enter)
     * @return boolean, whether the movement was allowed
     */
    private boolean verify(int direction) {
        if (DEBUG_3)
            System.out.println("verify");
        showStatus("");
        int x = plx, y = ply;
        Sector ret = null;

        switch (direction) {
            case KeyEvent.VK_UP:
                ret = brett.verifySector(x, y - size.height);
                break;
            case KeyEvent.VK_DOWN:
                ret = brett.verifySector(x, y + size.height);
                break;
            case KeyEvent.VK_LEFT:
                ret = brett.verifySector(x - size.width, y);
                break;
            case KeyEvent.VK_RIGHT:
                ret = brett.verifySector(x + size.width, y);
                break;
            case KeyEvent.VK_HOME:
                ret = brett.verifySector(x - size.width, y - size.height);
                break;
            case KeyEvent.VK_END:
                ret = brett.verifySector(x - size.width, y + size.height);
                break;
            case KeyEvent.VK_PAGE_UP:
                ret = brett.verifySector(x + size.width, y - size.height);
                break;
            case KeyEvent.VK_PAGE_DOWN:
                ret = brett.verifySector(x + size.width, y + size.height);
                break;
        }

        if (ret == null) // only if the applet has been resized.. else; mapbug
        {
            return true;
        } else if (!ret.getType().passable()) {
            String text = ret.getType().getName();
            if (text.equalsIgnoreCase("ws"))
                showStatus("You walked into a stone wall");
            else if (text.equalsIgnoreCase("nw")) {
                if (!ret.getNPCMsg().equals(""))
                    showStatus("\"" + ret.getNPCMsg() + "\"");
                else
                    showStatus("The person is unconscious.");
            } else if (text.equalsIgnoreCase("ns")) {
                if (!ret.getNPCMsg().equals(""))
                    showStatus(ret.getNPCMsg());
                else
                    showStatus("\"zzzzzz...\"");
            } else if (text.equalsIgnoreCase("n") ||
                    text.equalsIgnoreCase("nf")) {
                if (!ret.getNPCMsg().equals(""))
                    showStatus("\"" + ret.getNPCMsg() + "\"");
                else
                    showStatus("The person has nothing to say to you right now.");
            }
            return false;
        } else if (ret.locked()) {
            return unlock(ret);
        } else {
            String text = ret.getType().getName();
            if (text.equalsIgnoreCase("wt"))
                text = "stepped into a pool of water";
            else if (text.equalsIgnoreCase("dvc") || text.equalsIgnoreCase("dhc")) {
                text = "opened the door";
                toggledoor(ret);
                return true;
            } else if (text.equalsIgnoreCase("dvo") || text.equalsIgnoreCase("dho")) {
                return true;
            } else
                return true; // to avoid "You nd" when walking over unspecified stuff
            showStatus("You " + text);
            return true;
        }
    }

    private boolean unlock(Sector s) {
        Item required = brett.getItems().findItemByID(s.getKeyVal());
        String type = s.getType().getName();

        if (type.equalsIgnoreCase("dvc") ||
                type.equalsIgnoreCase("dhc") ||
                type.equalsIgnoreCase("cc")) {
            if (player.inventory().findItemByID(required.getID()) != null) {
                s.setUnlocked();
                player.inventory().rob(required);
                showStatus("You have unlocked the " + s.getName() + "!");
                toggledoor(s);
                return false; // door opens but enter in seperate move
            } else
                showStatus("The " + s.getName() + " is locked and you don't have the " +
                        required.getName() + " " + required.getType() + ".");
            return false;
        }
        showStatus("It appears to be locked...");
        return false; // not a closed door or closed chest, but defined as locked anyway.
    }

    private void toggledoor(Sector s) {
        if (DEBUG_3)
            System.out.println("toggledoor");
        if (s.getType().getName().equalsIgnoreCase("dvc")) {
            s.getType().setName("dvo");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("dvo")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//dvo.gif"));
        } else if (s.getType().getName().equalsIgnoreCase("dvo")) {
            s.getType().setName("dvc");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("dvc")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//dvc.gif"));
        } else if (s.getType().getName().equalsIgnoreCase("dho")) {
            s.getType().setName("dhc");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("dhc")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//dhc.gif"));
        } else if (s.getType().getName().equalsIgnoreCase("dhc")) {
            s.getType().setName("dho");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("dho")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//dho.gif"));
        }
    }

    private void togglechest(Sector s) {
        if (DEBUG_3)
            System.out.println("togglechest");
        if (s.getType().getName().equalsIgnoreCase("cc")) {
            s.getType().setName("co");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("co")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//co.gif"));
        } else if (s.getType().getName().equalsIgnoreCase("co")) {
            s.getType().setName("cc");
            if (!LOCAL)
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon(getURL("cc")));
            else
                obstacles[s.getIndex()].setIcon(
                        new ImageIcon("img//cc.gif"));
        }
    }

    /**
     * Searches the sector the player is in.
     * As only passable sectors can be walked into, only passable obstacles are
     * defined.
     *
     * @param s Sector
     */
    private void look(Sector s) {
        if (DEBUG_3)
            System.out.println("look");
        if (s == null) {
            if (DEBUG) System.out.println("look: Null sector");
            showStatus("Nothing!");
            return;
        }
        String type = s.getType().getName();
        if (type.equalsIgnoreCase("dhc") || type.equalsIgnoreCase("dvc"))
            showStatus("A closed door");
        else if (type.equalsIgnoreCase("dho") || type.equalsIgnoreCase("dvo"))
            showStatus("An open door");
        else if (type.equalsIgnoreCase("wt"))
            showStatus("Shallow water");
        else if (type.equalsIgnoreCase("wh"))
            showStatus("Holy water");
        else if (type.equalsIgnoreCase("ss"))
            showStatus("A small stone");
        else if (type.equalsIgnoreCase("co"))
            showStatus("An open chest");
        else if (type.equalsIgnoreCase("cc"))
            showStatus("A closed chest");
        else if (type.equalsIgnoreCase("nd"))
            showStatus("A corpse");
        else if (type.equalsIgnoreCase("nw"))
            showStatus("A wounded person");
        else if (type.equalsIgnoreCase("ns"))
            showStatus("A sleeping person");
        else if (type.equalsIgnoreCase("rb")) {
            showStatus("Some rubble");
            searchSector(s, false);
        } else if (type.equalsIgnoreCase("fl")) {
            showStatus("The floor is empty");
            searchSector(s, false);
        }
    }

    private void interact(Sector s) {
        if (DEBUG_3)
            System.out.println("interact");
        if (s != null) {
            String type = s.getType().getName();
            if (type.equalsIgnoreCase("wt"))
                showStatus("You had a nice drink of water");
            else if (type.equalsIgnoreCase("dvo") ||
                    type.equalsIgnoreCase("dho")) {
                toggledoor(s);
                showStatus("You closed the door");
                return;
            } else if (type.equalsIgnoreCase("dvc") ||
                    type.equalsIgnoreCase("dhc")) {
                toggledoor(s);
                showStatus("You opened the door");
                return;
            }

            /*
                  else if( type.equalsIgnoreCase("ar"))
                 {
                   player.inventory().add( s.getItems() );
                   int r = s.getItems().countAll();
                   s.getItems().rob(s.getItems());
                   showStatus("You picked up " + r + " arrows");
                   removeSector(s);
                 }
            */
            else if (type.equalsIgnoreCase("rb")) {
                showStatus("You don't need the rubble");
            } else if (type.equalsIgnoreCase("emp")) {
                showStatus("There's nothing here now");
            }

            // for most sectors search when interacted
            searchSector(s, true);

        } else
            showStatus("There's nothing here");
    }

    private void searchSector(Sector s, boolean get) {
        if (DEBUG_3)
            System.out.println("searchSector");
        String result = "";
        if (s.getItems() != null)
            result = s.getItems().nameLine();
        if (result.equals(""))
            result = "nothing";
        if (get) // if parameter get is set
        {
            // the line under will remove items from sector and add to inventory
            player.inventory().add(s.getItems().rob(s.getItems()));
            showStatus("You searched the " + s.getName() + " and found " + result + ".");
        } else {
            if (s.getItems().countAll() > 0)
                showStatus("You can see something on the " + s.getName() + ".");
            else
                showStatus("There's nothing on the " + s.getName() + ".");
        }
        /*    if( s. )  // Floor
       removeSectorItems(s); */
    }

    /**
     * Shows the inventory
     */
    private void inventory() {
        if (DEBUG_3)
            System.out.println("inventory");
        showStatus("Inventory:" + player.inventory().nameLine());
    }

    /**
     * Creates the mapdata
     */
    private void makemap() {
        if (DEBUG_3)
            System.out.println("makemap");
        // create new brett
        brett = new Brett(1);

        // set player startingpoint
        brett.setPlStart(1, 14);

        /** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
         * Create some obstacles<br>
         * Obstacles are things that cannot be picked up.
         * Obstacles not added to a sector is not in the map.
         ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **/

        ObstacleList o = brett.getObstacles();

        o.add(new Obstacle("fl", 0, 0, true)); // floor
        o.add(new Obstacle("ws", 0, 85, false)); // wall, stone
        o.add(new Obstacle("rl", 0, 80, false)); // rock, large
        o.add(new Obstacle("ss", 0, 40, true)); // stone, small
        o.add(new Obstacle("wt", 0, 0, true)); // water
        o.add(new Obstacle("wd", 1, 0, true)); // water, deep
        o.add(new Obstacle("rb", 1, 0, true)); // rubble
        o.add(new Obstacle("dhc", 0, 15, true)); // door, horizontal, closed
        o.add(new Obstacle("dho", 0, 15, true)); // door, horizontal, open
        o.add(new Obstacle("dvc", 0, 15, true)); // door, vertical, closed
        o.add(new Obstacle("dvo", 0, 15, true)); // door, vertical, open
        o.add(new Obstacle("cc", 0, 0, true)); // chest, closed
        o.add(new Obstacle("co", 0, 0, true)); // chest, open
        o.add(new Obstacle("nd", 0, 0, true)); // npc, dead
        o.add(new Obstacle("nw", 0, 0, false)); // npc, wounded
        o.add(new Obstacle("ns", 0, 0, false)); // npc, sleeping
        o.add(new Obstacle("n", 0, 0, false)); // npc
        o.add(new Obstacle("nf", 0, 0, false)); // npc, female

        /** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
         * Create some items<br>
         * Items are things that can be picked up.
         * Items not added to a sector is not in the map.
         ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **/

        ItemList it = brett.getItems();

        it.add(new Item("k1", "key1", "silver", "key"));
        it.add(new Item("k2", "key2", "gold", "key"));
        it.add(new Item("k3", "key3", "green", "key"));
        it.add(new Item("k4", "key4", "universal", "key"));
        it.add(new Item("dg", "dagger1", "rusted", "dagger"));
        it.add(new Item("ar", "arrows1", "", "arrow"));
        it.add(new Item("a", "armor1", "armor", true, 2, 25, 0, 30));
        it.add(new Item("h", "h1", "helmet", true, 1, 100, 0, 20));
        it.add(new Item("ri", "ring1", "gold", "ring"));

        /** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
         * Create some mapsectors (plotting the map)
         ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **/

        SectorList s = brett.getSectors();

        for (int i = 0; i < 7; i++) // walls
            s.add(new Sector(3, i, "wall", o.findObstacleByName("ws")));
        // a closed unlocked door (vertical)
        s.add(new Sector(3, 7, "door", o.findObstacleByName("dvc")));
        for (int i = 8; i < 20; i++) // more walls
            s.add(new Sector(3, i, "wall", o.findObstacleByName("ws")));
        // water area
        for (int i = 9; i < 15; i++)
            for (int j = 14; j < 18; j++)
                s.add(new Sector(i, j, "water", o.findObstacleByName("wt")));
        // some more walls
        for (int i = 4; i < 12; i++)
            s.add(new Sector(i, 3, "wall", o.findObstacleByName("ws")));
        // a closed and locked door (horizontal) that require the universalkey
        Sector door1 = new Sector(12, 3, "door", o.findObstacleByName("dhc"));
        door1.setLocked("key4");
        s.add(door1);
        // walls again
        for (int i = 0; i < 4; i++)
            s.add(new Sector(13, i, "wall", o.findObstacleByName("ws")));
        // a corpse
        Sector corpse1 = new Sector(11, 11, "fighter corpse", o.findObstacleByName("nd"));
        // give the universalkey to the corpse
        corpse1.getItems().add(it.findItemByID("key4"));
        s.add(corpse1);
        // goblin corpse
        Sector corpse2 = new Sector(0, 0, "goblin corpse", o.findObstacleByName("nd"));
        // give this corpse a dagger
        corpse2.getItems().add(it.findItemByID("dagger1"));
        s.add(corpse2);
        // a wounded fighter
        Sector npc1 = new Sector(1, 0, o.findObstacleByName("nw"));
        npc1.setNPC("Fighter Thor", false,
                "We were attacked by goblins! Find the key! Save the princess!");
        s.add(npc1);
        // the princess
        Sector npc2 = new Sector(10, 1, o.findObstacleByName("nf"));
        npc2.setNPC("Princess Amalie", true,
                "Thank you!**I lost my ring out there somewhere. Will you help me find it?");
        s.add(npc2);
        // add some rubble
        for (int i = 14; i < 20; i++)
            for (int j = 0; j < 4; j++)
                s.add(new Sector(i, j, "rubble", o.findObstacleByName("rb")));
        for (int i = 4; i < 8; i++)
            s.add(new Sector(i, 4, "rubble", o.findObstacleByName("rb")));
        // some arrows laying around - ITEMS (on floor-obstacle)
        Sector flr1 = new Sector(12, 10, "floor", o.findObstacleByName("fl"));
        int r = 1 + (int) (Math.random() * 5); // numbers vary
        for (int i = 0; i < r; i++)
            flr1.getItems().add(it.findItemByID("arrows1"));
        s.add(flr1);
        // a ring can be found inside some rubble
        Sector rubbleWithRing = s.findSector2(15, 1); // get the sector i want
        rubbleWithRing.getItems().add(it.findItemByID("ring1"));
        // add armor to the dead fighter
        corpse1.getItems().add(it.findItemByID("armor1"));

        /** ** ** ** ** ** ** ** **
         * Fill blanks (null-sectors) with floor.
         * After this there are no null-sectors.
         * This must be done AFTER all actual sectors has been added or else they
         * will neither be found nor displayed!
         ** ** ** ** ** ** ** ** **/
        s.fillBlanks(o.findObstacleByName("fl"));

        if (DEBUG_2) {
            // check if obstacles/sectors are added (lots of text)
            System.out.println(o);
            System.out.println(s);
        }
    }

    private void loadmap() {
        if (DEBUG_3)
            System.out.println("loadmap");
    }

    private void savemap() {
        if (DEBUG_3)
            System.out.println("savemap");
    }

    /**
     * This method puts the map visually on the applet using
     * 400 labels (20 * 20 sectors) for obstacles  and
     * then a number of labels for items
     */
    private void parsemap() {
        if (DEBUG_3)
            System.out.println("parsemap");
        SectorList sl = brett.getSectors();
        int numO = sl.countObstacles();
        int numI = sl.countItems();

        obstacles = new JLabel[numO];
        items = new JLabel[numI];
        int j = 0; // increment for items[]

        for (int i = 0; i < numO; i++) {
            // get sector from index
            Sector s = sl.findSectorWithIndex(i);

            // set the image for this sector
            if (LOCAL) {
                // floors and shallowater are the only sectors with visible items
                if (s.getType().getName().equalsIgnoreCase("fl") ||
                        s.getType().getName().equalsIgnoreCase("wt")) {
                    obstacles[i] = new JLabel(
                            new ImageIcon("img//" + s.getType().getName() + ".gif"));

                    ItemList dist = s.getItems().getDistinctList();
                    if (dist != null) // provided there are any items here
                    {
                        int k = dist.countAll(); // how many DIFFERENT items in this sector?
                        if (DEBUG)
                            System.out.println("items in \"visual\"-sector " +
                                    s.getX2() + "x" + s.getY2() + ":" +
                                    s.getItems().nameLine());


                        for (int n = 0; n < k; n++) {
                            Item item = dist.findItemWithIndex(n);
                            if (DEBUG)
                                System.out.println("n: " + n + " item: " + item);
                            if (item != null) {
                                items[j] = new JLabel( // add one imagelabel for each itemtype
                                        new ImageIcon("img//" + item.getfilename() + ".gif"));
                                items[j].setBounds(s.getX(), s.getY(), size.width, size.height);
                                getContentPane().add(items[j]);
                                j++;
                                if (DEBUG)
                                    System.out.println(j - 1 + " " + items[j - 1]);
                            }
                        }
                    }
                } else // just add the obstacle image
                {
                    if (DEBUG_4) {
                        System.out.print(s.getType().getName() + " ");
                    }
                    obstacles[i] = new JLabel(
                            new ImageIcon("img//" + s.getType().getName() + ".gif"));
                }
            } else // not LOCAL        - NOT READY !!!
            {
                // floors are the only sectors with visible items
                if (s.getType().getName().equalsIgnoreCase("fl")) {
                    obstacles[i] = new JLabel(
                            new ImageIcon(getURL(s.getType().getName())));

                    ItemList dist = s.getItems().getDistinctList();
                    if (dist != null) // provided there are items here
                    {
                        int k = dist.countAll(); // how many DIFFERENT items here?
                        for (int n = 0; n < k; n++) {
                            items[j] = new JLabel(
                                    new ImageIcon(getURL(dist.findItemWithIndex(n).getID())));
                            items[j++].setBounds(s.getX(), s.getY(), size.width, size.height);
                        }
                    }
                } else // just add the obstacle image
                    obstacles[i] = new JLabel(
                            new ImageIcon(getURL(s.getType().getName())));
            }

            // set location for the imagelabel
            obstacles[i].setBounds(s.getX(), s.getY(), size.width, size.height);
            // add to applet
            getContentPane().add(obstacles[i]);
        }

        // retrieve startpos for player
        plx = brett.getPlX();
        ply = brett.getPlY();

    }

    private void initplayer() {
        if (DEBUG_3)
            System.out.println("initPlayer");
        // visual init
        if (!LOCAL)
            pl.setIcon(new ImageIcon(getURL("pl")));
        else
            pl.setIcon(new ImageIcon("img//pl.gif"));
        getContentPane().add(pl);

        // datainit
        if (!DEBUG_3)
            player = new Player(JOptionPane.showInputDialog(null, "Ditt navn"));
        else
            player = new Player("Player");
    }

    /**
     * Clears image of sector [ and sets sector obstacle to floor ]
     *
     * @param s Sector
     */
    private void removeSectorItems(Sector s) {
        if (DEBUG_3)
            System.out.println("removeSectorItems");
        // many things should not be removed when searched
        if (!s.getType().getName().equalsIgnoreCase("nd") && // NPC dead
                !s.getType().getName().equalsIgnoreCase("co") && // Chest, open
                !s.getType().getName().equalsIgnoreCase("cc") && // (Chest, closed)
                !s.getType().getName().equalsIgnoreCase("wt") && // Water, shallow
                !s.getType().getName().equalsIgnoreCase("rb") && // Rubble
                !s.getType().getName().equalsIgnoreCase("nw") && // NPC, wounded
                !s.getType().getName().equalsIgnoreCase("ns") && // NPC, sleeping
                !s.getType().getName().equalsIgnoreCase("dvo") && // Door vert open
                !s.getType().getName().equalsIgnoreCase("dvc") && // Door vert closed
                !s.getType().getName().equalsIgnoreCase("dhc") && // Door hori closed
                !s.getType().getName().equalsIgnoreCase("dho") && // Door hori open
                !s.getType().getName().equalsIgnoreCase("wd") && // Water, deep
                !s.getType().getName().equalsIgnoreCase("fl")) // Floor
        {
            obstacles[s.getIndex()].setIcon(null); // clear image
            brett.getSectors().change(
                    new Sector(s.getX() / 15,
                            s.getY() / 15,
                            "floor",
                            brett.getObstacles().
                                    findObstacleByName("fl")), s);
        }
    }

    public void keyPressed(KeyEvent keyevent) {
        if (DEBUG_4)
            System.out.println("keyPressed");
        move(keyevent);
    }

    public void keyReleased(KeyEvent keyevent) {
        if (DEBUG_4)
            System.out.println("keyPressed");
        //move(keyevent);
    }

    public void keyTyped(KeyEvent keyevent) {
        if (DEBUG_4)
            System.out.println("keyTyped");
        //move(keyevent);
    }

    public void focusLost(FocusEvent fe) {
        if (DEBUG_4)
            System.out.println("focusLost");
        if (!LOCAL)
            pl.setIcon(new ImageIcon(getURL("pause")));
        else
            pl.setIcon(new ImageIcon("img//pause.gif"));

        showStatus("Paused");
    }

    public void focusGained(FocusEvent fe) {
        if (DEBUG_4)
            System.out.println("focusGained");
        if (!LOCAL)
            pl.setIcon(new ImageIcon(getURL("pl")));
        else
            pl.setIcon(new ImageIcon("img//pl.gif"));
        showStatus("");
    }

    private URL getURL(String fn) {
        if (DEBUG_4)
            System.out.println("getURL");
        URL url;
        try {
            url = new URL(getCodeBase(), "img//" + fn + ".gif");
        } catch (IOException e) {
            showStatus("Problem finding file " + fn + ".gif");
            return null;
        }
        return url;
    }

}
