import java.util.HashSet;
import java.util.Scanner;

/**
 * Denne versjonen er bygget på den originale tech-support-complete av Michael Kölling and David J. Barnes
 * 
 * InputReader reads typed text input from the standard text terminal. 
 * The text typed by a user is then chopped into words, and a set of words 
 * is provided.
 * 
 * @author     bjornar.risdalen
 * @version    2.0 (2019.01.21)
 */
public class InputReader
{
    private Scanner reader;

    /**
     * Create a new InputReader that reads text from the text terminal.
     */
    public InputReader()
    {
        reader = new Scanner(System.in);
    }

    /**
     * Read a line of text from standard input (the text terminal),
     * and return it as a set of words.
     *
     * @return  A set of Strings, where each String is one of the 
     *          words typed by the user
     */
    public HashSet<String> getInput() 
    {
        System.out.print("> ");
        String inputLine = reader.nextLine().trim().toLowerCase();

        String[] wordArray = inputLine.split(" ");

        HashSet<String> words = new HashSet<>();
        for(String word : wordArray) {
            words.add(word);
        }
        return words;
    }
}
