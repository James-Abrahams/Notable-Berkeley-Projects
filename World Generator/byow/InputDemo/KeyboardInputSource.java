package byow.InputDemo;

/**
 * Created by hug.
 */
import edu.princeton.cs.introcs.StdDraw;

import java.util.Arrays;
import java.util.List;


public class KeyboardInputSource implements InputSource {
    //private Character[] validC = {'N', 'L', 'Q', 'S', ':'};
    //List<Character> list = Arrays.asList(validC);
    char c;

    public char getNextKey() {



        return Character.toUpperCase(StdDraw.nextKeyTyped());
    }

    public boolean possibleNextInput() {
        //System.out.println("{} printing kb interface...");
        return StdDraw.hasNextKeyTyped();
    }
}
