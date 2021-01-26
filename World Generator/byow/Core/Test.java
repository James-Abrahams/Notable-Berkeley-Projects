package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);

        String str1  = "n7193300625454684331saaawasdaawdwsd";

        String str21 = "n7193300625454684331saaawasdaawd:q";
        String str22 = "lwsd";

        Engine eg = new Engine();
        Engine eg2 = new Engine();

        TETile[][] world1 = eg.interactWithInputString(str1);

        TETile[][] world2 = eg2.interactWithInputString(str21 + str22);
        //world2 = eg2.interactWithInputString(str22);

        System.out.println("Deep equals: world1 and world2: " + Arrays.deepEquals(world1, world2));

        ter.renderFrame(world1);

        System.out.println("");
        System.out.println(TETile.toString(world1));
        System.out.println("");
        System.out.println(TETile.toString(world2));
        System.out.println("");
    }
}
