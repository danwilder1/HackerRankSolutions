import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        scan.close();
        
        String delimiter = "[ !,?._'@]+";
        
        // Remove any delimiters that are before any alphanumeric
        String leadingRemoved = s.replaceFirst("^" + delimiter, "");
        
        String[] tokens = (leadingRemoved.equals("")) 
        		? new String[0] : leadingRemoved.split(delimiter);
        
        System.out.println(tokens.length);
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}

