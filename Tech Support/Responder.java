import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Denne versjonen er bygget på den originale tech-support-complete av Michael Kölling and David J. Barnes
 * 
 * The responder class represents a response generator object.
 * It is used to generate an automatic response, based on specified input.
 * Input is presented to the responder as a set of words, and based on those
 * words the responder will generate a String that represents the response.
 *
 * Internally, the reponder uses a HashMap to associate words with response
 * strings and a list of default responses. If any of the input words is found
 * in the HashMap, the corresponding response is returned. If none of the input
 * words is recognized, one of the default responses is randomly chosen.
 * 
 * @author     bjornar.risdalen
 * @version    2.0 (2019.01.21)
 */

/*
 * Dette dukker ikke opp i dokumentasjonen
 * 
 */

public class Responder
{
    // 1 HashMap to map key words to responses and 1 HashMap to count 
    // every response used.
    private HashMap<String, String> responseMap;
    private HashMap<String, Integer> responseCounter;
    // Default responses to use if we don't recognise a word.
    private ArrayList<String> defaultResponses;
    private int[] defaultResponseCounter;
    private Random randomGenerator;

    /**
     * Construct a Responder
     */
    public Responder()
    {
        responseMap = new HashMap<>();
        responseCounter = new HashMap<>();
        defaultResponses = new ArrayList<>();
        fillResponseMap();
        fillDefaultResponses();
        defaultResponseCounter = new int[defaultResponses.size()];
        randomGenerator = new Random();
        // Seed foråsaker samme rekkefølge om det brukes
        randomGenerator.setSeed(30000);
    }

    public HashMap getResponseCounter() {
        return this.responseCounter;
    }
    
    /**
     * @param a HashSet of Strings with words from inputReader
     * 
     * @return either a response from the responseMap or a random response from the defaultResponses
     */
    public String generateResponse(HashSet<String> words)
    {
        for (String word : words) {
            String response = responseMap.get(word);
            if(response != null) {
                // .put overskriver verdien til en nøkkel siden den ikke godtar duplikat-nøkler
                responseCounter.put(word, responseCounter.get(word) +1);
                
                return response;
            }
        }
        
        return pickDefaultResponse();
    }

    /**
     * Enter all the known keywords and their associated responses
     * into our response map.
     */
    private void fillResponseMap()
    {
        responseMap.put("crash", 
                        "Well, it never crashes on our system. It must have something\n" +
                        "to do with your system. Tell me more about your configuration.");
        responseCounter.put("crash", 0);                
        responseMap.put("crashes", 
                        "Well, it never crashes on our system. It must have something\n" +
                        "to do with your system. Tell me more about your configuration.");
        responseCounter.put("crashes", 0);
        responseMap.put("slow", 
                        "I think this has to do with your hardware. Upgrading your processor\n" +
                        "should solve all performance problems. Have you got a problem with\n" +
                        "our software?");
        responseCounter.put("slow",0);                
        responseMap.put("performance", 
                        "Performance was quite adequate in all our tests. Are you running\n" +
                        "any other processes in the background?");
        responseCounter.put("performance",0);                                
        responseMap.put("bug", 
                        "Well, you know, all software has some bugs. But our software engineers\n" +
                        "are working very hard to fix them. Can you describe the problem a bit\n" +
                        "further?");
        responseCounter.put("bug",0);                                
        responseMap.put("buggy", 
                        "Well, you know, all software has some bugs. But our software engineers\n" +
                        "are working very hard to fix them. Can you describe the problem a bit\n" +
                        "further?");
        responseCounter.put("buggy",0);                
        responseMap.put("windows", 
                        "This is a known bug to do with the Windows operating system. Please\n" +
                        "report it to Microsoft. There is nothing we can do about this.");
        responseCounter.put("windows",0);                
        responseMap.put("mac", 
                        "This is a known bug to do with the Mac operating system. Please\n" +
                        "report it to Apple. There is nothing we can do about this.");
        responseCounter.put("mac",0);                
        responseMap.put("expensive", 
                        "The cost of our product is quite competitive. Have you looked around\n" +
                        "and really compared our features?");
        responseCounter.put("expensive",0);                
        responseMap.put("installation", 
                        "The installation is really quite straight forward. We have tons of\n" +
                        "wizards that do all the work for you. Have you read the installation\n" +
                        "instructions?");
        responseCounter.put("installation",0);                
        responseMap.put("memory", 
                        "If you read the system requirements carefully, you will see that the\n" +
                        "specified memory requirements are 1.5 giga byte. You really should\n" +
                        "upgrade your memory. Anything else you want to know?");
        responseCounter.put("memory",0);                
        responseMap.put("linux", 
                        "We take Linux support very seriously. But there are some problems.\n" +
                        "Most have to do with incompatible glibc versions. Can you be a bit\n" +
                        "more precise?");
        responseCounter.put("linux",0);                
        responseMap.put("bluej", 
                        "Ahhh, BlueJ, yes. We tried to buy out those guys long ago, but\n" +
                        "they simply won't sell... Stubborn people they are. Nothing we can\n" +
                        "do about it, I'm afraid.");
        responseCounter.put("bluej",0);                
    }

    /**
     * Build up a list of default responses from which we can pick one
     * if we don't know what else to say.
     */
    private void fillDefaultResponses()
    {
        defaultResponses.add("That sounds odd. Could you describe that problem in more detail?");
        defaultResponses.add("No other customer has ever complained about this before. \n" +
                             "What is your system configuration?");
        defaultResponses.add("That sounds interesting. Tell me more...");
        defaultResponses.add("I need a bit more information on that.");
        defaultResponses.add("Have you checked that you do not have a dll conflict?");
        defaultResponses.add("That is explained in the manual. Have you read the manual?");
        defaultResponses.add("Your description is a bit wishy-washy. Have you got an expert\n" +
                             "there with you who could describe this more precisely?");
        defaultResponses.add("That's not a bug, it's a feature!");
        defaultResponses.add("Could you elaborate on that?");
    }

    /**
     * Randomly select and return one of the default responses.
     * @return     A random default response
     */
    private String pickDefaultResponse() {
        // Pick a random number for the index in the default response list.
        // The number will be between 0 (inclusive) and the size of the list (exclusive).
        int index = randomGenerator.nextInt(defaultResponses.size());
        defaultResponseCounter[index]++;
        return defaultResponses.get(index);
    }
    
    public void printStats() {
        System.out.println("\n\n\n" + "Underneath follows some statistics:");
        // Print ut alle HashMap-svar og hvor mange ganger de er brukt
        System.out.println("\n Mapped responses:");
        for(String key : responseMap.keySet()) {
            System.out.println("\"" + responseMap.get(key) + "\" was used "+ responseCounter.get(key) + " numbers of times.");
            System.out.println("/---------------------------------------------" +
                                    "--------------------------------------------/");
        }
        // Her brukes en index for å sammenkjøre hvilke svar fra ArrayListen som er blitt brukt, 
        // og hvor mange ganger
        System.out.println("\n And default responses:");
        for(int i = 0; i < defaultResponses.size(); i++) {
            System.out.println("\"" + defaultResponses.get(i) + "\" was used " + 
                                    defaultResponseCounter[i] + " times.");
            System.out.println("/---------------------------------------------" +
                                    "--------------------------------------------/");                        
        }
    }
}
