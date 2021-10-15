import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuplicateWords {

    public static void main(String[] args) {

       // Define the regex
       String regex = "\\b(\\w+)(\\s+\\1\\b)+";
        
       /* \b: look for word boundary 
        * (\w+): match one or more word characters and remember them as a group (group number 1)
        * 
        * \s+: match one or more space characters;
        * \1: match the word remembered before - group 1;
        * \b: word boundary – make sure it’s not a part of some longer word;
        * 
        * (\s+\1\b)+: match one or more occurrences of the word captured
        */
        
        // Create a Pattern by compiling the regex
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        Scanner in = new Scanner(System.in);
        int numSentences = Integer.parseInt(in.nextLine());
        
        while (numSentences-- > 0) {
            String input = in.nextLine();
            
            // Create a Matcher object using the pattern and the string to check
            Matcher m = p.matcher(input);
            
            // Check for subsequences of input that match the compiled pattern
            while (m.find()) {
                input = input.replaceAll(m.group(), m.group(1));
                /* sample input: the the the world world
                 *  
                 *  first loop:  m.group()  -> the the the
                 *  			 m.group(1) -> the
                 *  
                 *  second loop: m.group()  -> world world
                 *  			 m.group(1) -> world
                 * 
                 */
            }
            
            // Prints the modified sentence.
            System.out.println(input);
        }
        
        in.close();
    }
}