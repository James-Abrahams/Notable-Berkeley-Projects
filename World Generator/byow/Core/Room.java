package byow.Core;
import java.util.ArrayList;
import java.util.List;



public class Room { //make a room of random dimension
    private Position entry;
    private Position bottomLeft;
    private int length;
    private int height;
    private String buildDirection;
    private List<Position> northSide;
    private List<Position> southSide;
    private List<Position> eastSide;
    private List<Position> westSide;



    public Room(Position entry, int height, int length, String buildDirection) {
        //put origin in center then go down and left irregular amount
        this.buildDirection = buildDirection;
        this.entry = entry;
        this.length = length;
        this.height = height;
        this.eastSide = new ArrayList<>();
        this.southSide = new ArrayList<>();
        this.westSide = new ArrayList<>();
        this.northSide = new ArrayList<>();

        //depending upon direction entering, define bottom left position in this room
        if (buildDirection.equals("South")) {
            this.bottomLeft = new Position(entry.getX() - length / 2, entry.getY() - height + 1);
        }
        if (buildDirection.equals("East")) {
            this.bottomLeft = new Position(entry.getX(), entry.getY() - height / 2);
        }
        if (buildDirection.equals("North")) {
            this.bottomLeft = new Position(entry.getX() - length / 2, entry.getY());
        }
        if (buildDirection.equals("West")) {
            this.bottomLeft = new Position(entry.getX() - length + 1, entry.getY() - height / 2);
        }
        //add non corners to relevant sideLists
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.length; i++) {
                if ((i == this.length - 1) && (j > 0) && (j < this.height - 1)) {
                    this.eastSide.add(new Position(bottomLeft.getX() + i, bottomLeft.getY() + j));
                }
                if ((i == 0) && (j > 0) && (j < this.height - 1)) {
                    this.westSide.add(new Position(bottomLeft.getX() + i, bottomLeft.getY() + j));
                }
                if ((j == 0) && (i > 0) && (i < this.length - 1)) {
                    this.southSide.add(new Position(bottomLeft.getX() + i, bottomLeft.getY() + j));
                }
                if ((j == this.height - 1) && (i > 0) && (i < this.length - 1)) {
                    this.northSide.add(new Position(bottomLeft.getX() + i, bottomLeft.getY() + j));
                }
            }
        }
    }
    //all positions
    public List<Position> getRoomPositions() {
        List<Position> allpos = new ArrayList<>();
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.length; i++) {
                allpos.add(new Position(bottomLeft.getX() + i, bottomLeft.getY() + j));
            }
        }
        return allpos;
    }
    public List<Position> getNorthSide() {
        return this.northSide;
    }
    public List<Position> getSouthSide() {
        return this.southSide;
    }
    public List<Position> getEastSide() {
        return this.eastSide;
    }
    public List<Position> getWestSide() {
        return this.westSide;
    }
    public Position getBottomLeft() {
        return this.bottomLeft;
    }
    public String getBuildDirection() {
        return this.buildDirection;
    }
    //Unable to go opposite direction of build direction, remove opposite.
    public List<List<Position>> getUsableDirections() {
        List<List<Position>> tempList = new ArrayList<>();
        tempList.add(northSide); tempList.add(southSide);
        tempList.add(westSide); tempList.add(eastSide);
        if (buildDirection.equals("West")) {
            tempList.remove(eastSide);
        }
        if (buildDirection.equals("East")) {
            tempList.remove(westSide);
        }
        if (buildDirection.equals("North")) {
            tempList.remove(southSide);
        }
        if (buildDirection.equals("South")) {
            tempList.remove(northSide);
        }

        return tempList;
    }

}
