package old.adventurespill;

import java.io.Serializable;

/**
 * <p>Title: Brett</p>
 * <p>Description: Brett for old.adventurespill</p><br>
 * 300 x 300 pixels, Sector = 15 pixels.<br>
 * 300 / 15 = 20 sectors horizontally.<br>
 * 300 / 15 = 20 sectors vertically.<br>
 * 20 * 20 = 400 sectors in total.
 *
 * @author Anders Nordbø
 * @version 1.0
 */
public class Brett implements Serializable {
    ObstacleList obstacles;
    SectorList sectors;
    ItemList items;
    private int plx, ply;
    private int mapID;
    private String endmsg;
    private int ex, ey;


    public Brett(int mapID) {
        sectors = new SectorList();
        obstacles = new ObstacleList();
        items = new ItemList();
        plx = 0;
        ply = 0;
        ex = 1;
        ey = 0;
        this.mapID = mapID;
    }

    /**
     * Returns the Sector requested if it's defined
     *
     * @param x int
     * @param y int
     * @return Sector
     */
    public Sector verifySector(int x, int y) {
        return sectors.findSector(x, y);
    }

    public SectorList getSectors() {
        return sectors;
    }

    public ObstacleList getObstacles() {
        return obstacles;
    }

    public ItemList getItems() {
        return items;
    }

    public void setPlStart(int x, int y) {
        plx = x * 15;
        ply = y * 15;
    }

    public void setExit(int x, int y) {
        ex = x;
        ey = y;
    }

    public int getPlX() {
        return plx;
    }

    public int getPlY() {
        return ply;
    }

    public int getEX() {
        return ex;
    }

    public int getEY() {
        return ey;
    }

    public int getMapID() {
        return mapID;
    }

}
