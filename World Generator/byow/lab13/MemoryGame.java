package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String s = "";
        for (int i = 0; i < n; i++) {
            s += CHARACTERS[RandomUtils.uniform(rand, CHARACTERS.length)];
        }
        return s;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        String topLeft = "round " + round + ". It's fun!";
        String topMid = getCommandString();
        String topRight = "Go Bruin! Oops!";
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 26));
        StdDraw.text(width / 2, height / 2, s);
        if (!gameOver) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 16));
            StdDraw.text(topLeft.length() / 2 - 2, height - 1, topLeft);
            StdDraw.text(width / 2, height - 1, topMid);
            StdDraw.text(width - topRight.length() / 2 + 2, height - 1, topRight);
        }
        StdDraw.show();
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    private String getCommandString() {
        if (playerTurn) {
            return "Type:";
        }
        return "Memorize:";
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(800);
        }
    }

    private void blink(String s1, String s2) {
        drawFrame(s1);
        StdDraw.pause(450);
        drawFrame(s2);
        StdDraw.pause(450);
    }

    private void drawNPause(String s, int t) {
        drawFrame(s);
        StdDraw.pause(t);
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String s = "";
        String s1;
        String s2;
        do {
            blink("", "|");
        } while(!StdDraw.hasNextKeyTyped());
        while(s.length() < n) {
            //get the next key typed.
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                s1 = s + "|";
                s2 = s1.substring(0, s1.length() - 1) + " ";
                do {
                    blink(s1, s2);
                } while(!StdDraw.hasNextKeyTyped() && s.length() < n);
            }
            //add it to String s.
        }
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        round = 1;
        playerTurn = false;
        //TODO: Establish Engine loop
        drawNPause("GAME ON !!  ", 1);
        drawNPause("", 500);

        drawNPause("READY        ", 650);
        drawNPause("READY.       ", 650);
        drawNPause("READY..      ", 650);
        drawNPause("READY..  GO!!", 1000);
        drawNPause("", 600);

        while(!gameOver) {
            drawNPause("Round  " + round, 1000);
            drawNPause("", 500);

            String correct = generateRandomString(round);
            flashSequence(correct);
            playerTurn = true;
            String guess = solicitNCharsInput(round);
            playerTurn = false;
            if (guess.equals(correct)) {
                round += 1;
                String str = "Yo ! You Got It for \"" + correct + "\" !";
                drawNPause(str, 2000);
                drawNPause("", 300);
            } else {
                gameOver = true;
            }
        }
        String str1 = "Game Over! You made it to round " + round + " !";
        drawNPause(str1, 2000);
        drawFrame("");
        System.exit(0);
    }

}
