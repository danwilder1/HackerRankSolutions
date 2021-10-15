    import java.util.*;
    public class Solution {
        
        // maps a number to the number of the times it appears in the current subarray
        private static HashMap<Integer, Integer> map = new HashMap<>();
        
        private static void addToMap(int num) {
            Integer frequency = map.get(num); 
            
            if (frequency == null) {
                map.put(num , 1);
            }
            else {
                map.put(num, ++frequency);
            }
        }
        
        private static void subtractFromMap(int num) {
            Integer frequency = map.get(num);
            
            if (frequency == 1) {
                map.remove(num);
            }
            else {
                map.put(num, --frequency);
            }  
        }
        
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            Deque<Integer> deque = new ArrayDeque<>();
            
            int n = in.nextInt();
            int m = in.nextInt();
            
            // first subarray
            for (int i = 0; i < m; i++) {
                int num = in.nextInt();
                deque.add(num);
                addToMap(num);
            }
            int maxUnique = map.size();
            
            // subsequent subarrays
            for (int i = m; i < n; i++) {
                int num = in.nextInt();
                
                subtractFromMap((Integer)deque.removeFirst());
                deque.add(num);
                addToMap(num);
            
                int size = map.size();
                maxUnique = (size > maxUnique) ? size : maxUnique;                
            }
            
            in.close();
            System.out.println(maxUnique);
        }
    }



