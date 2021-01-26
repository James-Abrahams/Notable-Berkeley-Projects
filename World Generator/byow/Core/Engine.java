package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private enum STATUS {
        WELCOME,
        SEED,
        CHARACTER,
        PLACE,
        PLAY,
        QUIT,
        SAVE,
        WIN,
        LOSE,
        LEAVE
    }
    private STATUS status = STATUS.WELCOME;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    private boolean gameOver = false;
    private long seed;
    private World world;
    private User user;
    private Devil devil1;
    private Devil devil2;
    private Devil devil3;
    private Devil devil4;
    private Deer deer1;
    private Deer deer2;
    private Snowman snowman;
    private Ring ring;
    private File file;


    private Random rand;
    private String records = "";


    /****************************  interactWithKeyboard  ****************************/

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        showMenu();
        KeyboardInputSource kb = new KeyboardInputSource();


        while (!this.gameOver) {
            if (kb.possibleNextInput()) {
                char c = kb.getNextKey();
                goKeyboard(c);
            }
            goMouse((int)StdDraw.mouseX(), (int)StdDraw.mouseY());
        }
        if (status == STATUS.WIN) {
            gameEnd("YOU WIN :)");
            saveGame("");
        } else if (status == STATUS.SAVE || status == STATUS.LEAVE){
            gameEnd("BYE :)");
        } else {
            gameEnd("YOU LOSE :(");
            saveGame("");
        }
        System.exit(0);
    }

    /****************************  interactWithInputString  ****************************/

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        /**
        ter.initialize(WIDTH, HEIGHT);
        String seedStr = input.substring(1, input.length() - 1);
        long seed = Long.parseLong(seedStr);
        world = new World(rand).getWorldObj();
        ter.renderFrame(world.getWorld());
        return world.getWorld();
         */

        StringInputDevice st = new StringInputDevice(input);
        while (st.possibleNextInput()) {
            char c = st.getNextKey();

            goKeyboard(c);

        }
        return world.getWorld();
    }

    /** The welcome menu */
    private void showMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 26);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.enableDoubleBuffering();

        String s1 = "WELCOME TO";
        String s2 = "CS61B";
        String s3 = "THE 2D MAP ADVENTURE <3";
        String mN = "N - NEW  GAME";
        String mL = "L - LOAD GAME";
        String mQ = "Q - QUIT GAME";

        //Draw the welcome(menu) page
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.15, s1);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.3, s2);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, s3);
        showAndPause(500);
        StdDraw.clear(StdDraw.BLACK); //blink
        showAndPause(500);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.15, s1);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.3, s2);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, s3);
        showAndPause(500);
        StdDraw.clear(StdDraw.BLACK); //blink
        showAndPause(500);

        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.15, s1);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.3, s2);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, s3);
        showAndPause(600);

        Font menu = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(menu);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.5, mN);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 3.0, mL);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 3.8, mQ);

        showAndPause(5);
    }

    private void showAndPause(int timePeriod) {
        StdDraw.show();
        StdDraw.pause(timePeriod);
    }

    private void goMouse(int mX, int mY) {
        Position p = new Position(mX, mY);
        Font font = new Font("Monaco", Font.BOLD, 13);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        if (status == STATUS.PLAY && p.getX() < WIDTH && p.getY() < HEIGHT) {
            TETile tile = world.getPosition(p);
            // show HUD String
            StdDraw.text(WIDTH / 2, HEIGHT - 0.5, tile.description());
        }
        if (status == STATUS.PLAY) {
            StdDraw.text(3, HEIGHT - 0.5, "Health: " + Integer.toString(user.getHealth()));
            StdDraw.show();
            ter.renderFrame(world.getWorld());
        }

    }

    private void goKeyboard(char command) {

        switch(status) {
            case WELCOME:
                if (command == 'N') {
                    promptSeed();
                    records += command;
                    status = STATUS.SEED;
                    break;
                } else if (command == 'L') {
                    loadGame();
                    //status = STATUS.WELCOME;
                    break;
                } else if (command == 'Q') {
                    status = STATUS.LEAVE;
                    gameOver = true;
                    break;
                }
                break;
            case SEED:
                records += command;
                if (command == 'S') {
                    status = STATUS.PLAY;
                    startGame();
                    break;
                } else {
                    seed = Character.getNumericValue(command) + seed * 10;
                    promptSeed();
                    break;
                }
            case PLAY:
                if (command == ':') {
                    status = STATUS.QUIT;
                    break;
                } else {
                    records += command;
                    playGame(command);
                    break;
                }
            case QUIT:
                if (command == 'Q') {
                    saveGame(records);
                    status = STATUS.LEAVE;
                    gameOver = true;
                    break;
                } else {
                    records += command;
                    playGame(command);
                    break;
                }
        }
    }

    public void gameEnd(String str) {
        Font font = new Font("Monaco", Font.BOLD, 26);
        StdDraw.setFont(font);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.enableDoubleBuffering();

        StdDraw.text(WIDTH / 2.0, HEIGHT / 2, str);
        StdDraw.show();
        StdDraw.pause(1800);
    }

    private void playGame(char command) {
        devil1.move(user.getPosition(), command);
        devil2.move(user.getPosition(), command);
        devil3.move(user.getPosition(), command);
        devil4.move(user.getPosition(), command);
        user.move(command);
        ter.renderFrame(world.getWorld());
        if (user.getHealth() <= 0) {
            status = STATUS.LOSE;
            gameOver = true;
        } else if (user.wins()) {
            status = STATUS.WIN;
            gameOver = true;
        }
    }

    private void startGame() {
        ter.initialize(WIDTH, HEIGHT);
        rand = new Random(seed);
        world = new World(rand).getWorldObj();

        user = new User(world, world.getStartPoint());
        ring = new Ring(world, world.getRandomFloor(rand));
        devil1 = new Devil(world, world.getRandomFloor(rand), user);
        devil2 = new Devil(world, world.getRandomFloor(rand), user);
        devil3 = new Devil(world, world.getRandomFloor(rand), user);
        devil4 = new Devil(world, world.getRandomFloor(rand), user);
        deer1 = new Deer(world, world.getRandomFloor(rand));
        deer2 = new Deer(world, world.getRandomFloor(rand));
        snowman = new Snowman(world, world.getRandomFloor(rand));
        ter.renderFrame(world.getWorld());
    }

    private void loadGame() {
        file = new File("./saved.txt");
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream os = new ObjectInputStream(fs);
                records = (String) os.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String temp = records;
            records = "";
            seed = 0;
            status = STATUS.WELCOME;
            StringInputDevice load = new StringInputDevice(temp);
            while (load.possibleNextInput()) {
                char c = load.getNextKey();
                goKeyboard(c);
                showAndPause(111);
            }
        }
    }

    private void saveGame(String s) {
        file = new File("./saved.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void promptSeed() {
        Font font = new Font("Monaco", Font.BOLD, 21);
        StdDraw.setFont(font);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.enableDoubleBuffering();
        String s1, s2, s3;
        s3 = "Press 'S' to play";
        if (seed != 0) {
            s1 = "Enter a random magic seed: " + Long.toString(seed) + "|";
            s2 = "Enter a random magic seed: " + Long.toString(seed) + " ";
        } else {
            s1 = "Enter a random magic seed: " + "|";
            s2 = "Enter a random magic seed: " + " ";
        }
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.text(WIDTH / 2.0, 6, s1);
            StdDraw.text(WIDTH / 2.0, 4, s3);
            showAndPause(300);
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.text(WIDTH / 2.0, 6, s2);
            StdDraw.text(WIDTH / 2.0, 4, s3);
            showAndPause(250);
        }
    }
}
