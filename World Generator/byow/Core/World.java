package byow.Core;

//import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class World implements Serializable {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    //Positions at room ends before iteration
    private HashMap<Position, String> currDisjoints = new HashMap<>();
    //Positions at room ends after iteration
    private HashMap<Position, String> newDisjoints = new HashMap<>();
    //positions in world that are occupied
    private HashSet<Position> occupiedPositions = new HashSet<>();
    //array of world tiles
    private TETile[][] world;
    private Position startPoint;
    private TETile filling;



    public World(Random randomVal) {
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);

        //gets a predicted random num based on input seed
        //Random randomVal = new Random(seed);

        //initializes world
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if ((x == 0 && y != HEIGHT - 1) || (x == WIDTH - 1 && y != HEIGHT - 1)
                        || (y == 0) || (y == HEIGHT - 2)) {
                    world[x][y] = TilesetAlt.WALL; //fill edge of world with Mountains
                } else {
                    world[x][y] = TilesetAlt.TREE;
                    // ### world[x][y] = Tileset.NOTHING;
                }
            }
        }
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
            if (x < WIDTH && y == HEIGHT - 1) {
                world[x][y] = Tileset.NOTHING;
        }}}

        //draw world
        Position startingPoint = new Position(randomVal.nextInt(10) + 10, 8);
        Room startingRoom = generateRoom(startingPoint, randomVal.nextInt(3)
                + 3, randomVal.nextInt(3) + 3, randomDirection("South", randomVal));

        world = displayRoom(startingRoom, world);
        world = displayRoomHallways(startingRoom, world, randomVal);
        world = worldMaker(world, randomVal);

        //world[startingPoint.getX()][startingPoint.getY()] = Tileset.AVATAR; //starting point is @
        this.startPoint = startingPoint;

        Position nextPosition;
        for (int i = 0; i < WIDTH; i++) { //make wall tiles
            for (int j = 0; j < HEIGHT; j++) {
                nextPosition = new Position((i), (j));
                if (!occupiedPositions.contains(nextPosition)) {
                    for (Position p : nextPosition.adjacentPositions()) {
                        if (occupiedPositions.contains(p)) {
                            world[i][j] = TilesetAlt.WALL; //...
                        }
                    }
                }
            }
        }
//        ter.renderFrame(world);
    }


    private TETile[][] worldMaker(TETile[][] tempWorld, Random rand) {
        int iterations = 0;
        while ((newDisjoints.size() > 0) && iterations < 100) {

            currDisjoints = newDisjoints;
            newDisjoints = new HashMap<>();

            for (Position p : currDisjoints.keySet()) {
                //generate hallway or room
                int roomOrHallway = rand.nextInt(3);

                if (roomOrHallway > 0) { //create Room 2/3 of the time
                    if (((HEIGHT - p.getY()) > 6) && ((WIDTH - p.getX()) > 6)
                            && (p.getX() > 6) && (p.getY() > 6)) { //check bounds

                        if (!interSectionPrevention(p, currDisjoints.get(p))) {
                            Room tempRoom = generateRoom(p, rand.nextInt(3) + 3, rand.nextInt(3)
                                    + 3, currDisjoints.get(p));
                            tempWorld = displayRoomHallways(tempRoom, tempWorld, rand);
                            tempWorld = displayRoom(tempRoom, tempWorld);
                        }
                    } else {
                        System.out.println("Room at point" + " X: " + p.getX() + " Y: " + p.getY()
                                + " not created, too close to edge.");
                    }
                } else { //create hallway
                    Hallway tempHallway = generateHallWay(p, rand.nextInt(3)
                            + 3, randomDirection(currDisjoints.get(p), rand));


                    tempWorld = displayHallway(tempHallway, tempWorld);
                }
            }
            currDisjoints = new HashMap<>();
            iterations += 1;
        }
        return tempWorld;
    }


    private TETile[][] displayRoomHallways(Room room, TETile[][] tempWorld, Random rand) {
        List<Position> halls = randomHallwayPositions(room, rand);

        for (Position p : halls) {

            if (((HEIGHT - p.getY()) > 6) && ((WIDTH - p.getX()) > 6)
                    && (p.getX() > 6) && (p.getY() > 6)) { //check bounds
                if (room.getEastSide().contains(p)) {
                    Hallway tempHallway = generateHallWay(p, rand.nextInt(3) + 3, "East");
                    tempWorld = displayHallway(tempHallway, tempWorld);
                }
                if (room.getSouthSide().contains(p)) {
                    Hallway tempHallway = generateHallWay(p, rand.nextInt(3) + 3, "South");
                    tempWorld = displayHallway(tempHallway, tempWorld);
                }
                if (room.getNorthSide().contains(p)) {
                    Hallway tempHallway = generateHallWay(p, rand.nextInt(3) + 3, "North");
                    tempWorld = displayHallway(tempHallway, tempWorld);
                }
                if (room.getWestSide().contains(p)) {
                    Hallway tempHallway = generateHallWay(p, rand.nextInt(3) + 3, "West");
                    tempWorld = displayHallway(tempHallway, tempWorld);
                }
            }
        }
        return tempWorld; //return an updated world
    }

    //return a list of positions for hallways to spawn from given room
    private List<Position> randomHallwayPositions(Room room, Random rand) {

        List<Position> newHallPositions = new ArrayList<>();
        List<List<Position>> allDirections = room.getUsableDirections();

        for (int i = 0; i < 3; i++) {
            newHallPositions.add(allDirections.get(i)
                    .get(rand.nextInt(allDirections.get(i).size())));
        }
        return newHallPositions;
    }

    private TETile[][] displayRoom(Room room, TETile[][] tempWorld) {
        for (Position p : room.getRoomPositions()) {
            tempWorld[p.getX()][p.getY()] = TilesetAlt.FLOOR;
            occupiedPositions.add(p);
        }
        return tempWorld;
    }

    private TETile[][] displayHallway(Hallway hallway, TETile[][] tempWorld) {
        for (Position p : hallway.getHallwayPositions()) {
            tempWorld[p.getX()][p.getY()] = TilesetAlt.FLOOR;
            occupiedPositions.add(p);
        }
        return tempWorld;
    }

    private Room generateRoom(Position pos, int height, int length, String entranceDirection) {
        Room newRoom = new Room(pos, height, length, entranceDirection);
        return newRoom;
    }


    private Hallway generateHallWay(Position origin, int length, String direction) {
        Boolean intersectionCheck = false;
        Hallway newHallWay = new Hallway(origin, length, direction);
        int maxLength = 6;
        if (((newHallWay.getExitPosition().getX() > maxLength)
                && (newHallWay.getExitPosition().getY() > maxLength))
                && ((WIDTH - newHallWay.getExitPosition().getX() > maxLength)
                && (HEIGHT - newHallWay.getExitPosition().getY() > maxLength))) { //checks bounds

            //iterate through hall positions to check intersections
            for (Position p : newHallWay.getMiddlePositions()) {
                for (Position b : occupiedPositions) {
                    if (b.equals(p)) {
                        intersectionCheck = true;
                    }
                }
            }
            if (!intersectionCheck) {
                newDisjoints.put(newHallWay.getExitPosition(), direction);
            }
        }
        return newHallWay;
    }

    //prevents going from direction hallway came from
    private String randomDirection(String direction, Random rand) {
        ArrayList<String> directions = new ArrayList<>();
        if (!direction.equals("North")) {
            directions.add("South");
        }
        if (!direction.equals("South")) {
            directions.add("North");
        }
        if (!direction.equals("West")) {
            directions.add("East");
        }
        if (!direction.equals("East")) {
            directions.add("West");
        }
        return directions.get(rand.nextInt(2));
    }

    //prevent rooms spawning in rooms
    private Boolean interSectionPrevention(Position p, String direction) {
        Boolean otherRoomCheck = false;
        Position tempPosition;
        int xLowerBound; int xUpperBound; int yLowerBound; int yUpperBound;
        if (direction.equals("South")) {
            xLowerBound = -3; xUpperBound = 3; yLowerBound = -4; yUpperBound = 0;
        } else if (direction.equals("North")) {
            xLowerBound = -3; xUpperBound = 3; yLowerBound = 0; yUpperBound = 4;
        } else if (direction.equals("East")) {
            xLowerBound = 0; xUpperBound = 4; yLowerBound = -3; yUpperBound = 3;
        } else {
            xLowerBound = -4; xUpperBound = 0; yLowerBound = -3; yUpperBound = 3;
        }

        for (int i = xLowerBound; i < xUpperBound; i++) {
            for (int j = yLowerBound; j < yUpperBound; j++) {
                tempPosition = new Position((p.getX() + i), (p.getY() + j));
                for (Position b : occupiedPositions) {
                    if (b.equals(tempPosition)) {
                        otherRoomCheck = true;
                    }
                }
            }
        }
        return otherRoomCheck;
    }

    public World getWorldObj() {
        return this;
    }

    public TETile[][] getWorld() {
        return this.world;
    }

    public TETile getPosition(Position position) {
        return world[position.getX()][position.getY()];
    }

    public Position getStartPoint() {
        return this.startPoint;
    }

    public void putChar(Position position, TETile tile) {
        world[position.getX()][position.getY()] = tile;
    }

    public boolean walkable(Position position) {
        return !world[position.getX()][position.getY()].equals(TilesetAlt.WALL);
    }

    public boolean placeable(Position position) {
        return world[position.getX()][position.getY()].equals(TilesetAlt.FLOOR);
    }

    public boolean inBound(Position position) {
        return position.getX() < WIDTH && position.getY() < HEIGHT;
    }

    public Position getRandomFloor(Random rand) {
        Position newRand;
        do {
            newRand = new Position(RandomUtils.uniform(rand, WIDTH),
                    RandomUtils.uniform(rand, HEIGHT));
        } while (!placeable(newRand));
        return newRand;
    }
}





