import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Fire {
    /**
     * Returns how long it takes for all vulnerable trees to be set on fire if a
     * match is lit at a given location.
     * 
     * The forest is represented via a rectangular 2d char array where t represents a tree
     * and . represents an empty space.
     * 
     * At time 0, the tree at location [matchR, matchC] is set on fire. At every subsequent
     * time step, any tree that is adjacent (up/down/left/right) to a burning tree is also
     * set on fire. 
     * 
     * 
     * EXAMPLE 
     * forest:
     * 
     * t..tttt.t
     * ..tt....t
     * ..ttttttt
     * tttt.....
     * 
     * matchR: 2
     * matchC: 6
     * 
     * Result: 8
     * 
     * Explanation:
     * At time 0, the tree at (2, 6) is set on fire. At time 1, its adjacent trees also catch on fire
     * At time 2, the trees adjacent to those catch as well. At time 8, the last tree to catch is at
     * (0,6). In this example, there is one tree that never burns, so it is not included in the time calculation.
     * 
     * 
     * @param forest a 2d array where t represents a tree and . represents the ground
     * @param matchR The row the match is lit at
     * @param matchC The column the match is lit at
     * @return the time at which the final tree to be incinerated starts burning
     */
    public static int timeToBurn(char[][] forest, int matchR, int matchC) {
        // HINT: when adding to your BFS queue, you can include more information than
        // just a location. What other information might be useful?

        // base case for if it is not a tree
        if(forest[matchR][matchC] != 't'){
            return 0;
        }

        int time = 0;
        // construct linked list of integer array
        Queue<int[]> queue = new LinkedList<>();

        // add the row column, and 0 (time) to queue
        // first tree starts burning at time 0
        queue.add(new int[] {matchR, matchC, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            int row= current[0];
            int col = current[1];
            int currentTime = current[2];

            // track the latest time any tree starts burning
            if(currentTime > time){
                time = currentTime;
            }

            // add all valid neighbor trees to queue
            for(int[] nextTrees : timetoBurnMoves(forest, row,col, currentTime)){
                queue.add(nextTrees);
            }

        }

        return time;
    }

    public static List<int[]> timetoBurnMoves(char[][] forest, int row, int col, int time){

        // create list for the next trees to burn
        List<int[]> nextTrees = new ArrayList<>();

        int[][] directions = {
                {1,0},
                {-1,0},
                {0,-1},
                {0,1}
        };

        for(int[] direction : directions){
            int newR = row + direction[0];
            int newC = col + direction[1];

            // validate if direction is within grid and is a tree
            if(newR >= 0 && newC >= 0 && newR < forest.length && newC < forest[0].length && forest[newR][newC] == 't'){
                //mark as a road to not visit again
                forest[newR][newC] = '.';
                //add the tree to list
                nextTrees.add(new int[]{newR, newC, time + 1});

            }
        }

        return nextTrees;
        
    }
}