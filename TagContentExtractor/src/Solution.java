import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {

        // Define the regex
    	String regex = "<(.+)>([^<]+)</\\1>";
    	/*
    	 * <(.+)> - Tag name is group 1. Enclosed in < > 
    	 * 
    	 * ([^<]+) - Group 2. One or more characters EXCLUDING < character
    	 * 
    	 * </\\1> - \\1 is same name as group 1 enclosed in </ >
    	 * 
    	 */
 
        // Create a Pattern by compiling the regex
        Pattern p = Pattern.compile(regex);

        Scanner in = new Scanner(System.in);
        
		int testCases = Integer.parseInt(in.nextLine());
		while(testCases-- > 0){
			String line = in.nextLine();
			
			Matcher m = p.matcher(line);
			
			boolean isMatch = m.find();
			if (!isMatch) System.out.println("None");
			
			// May be multiple matches per line
			while (isMatch) {
				System.out.println(m.group(2));
				isMatch = m.find();
			}
		}
		in.close();
    }
}