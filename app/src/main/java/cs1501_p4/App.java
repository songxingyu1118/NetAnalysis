/**
 * A driver for CS1501 Project 4
 * @author	Dr. Farnan
 */
package cs1501_p4;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        NetAnalysis na = new NetAnalysis("build/resources/main/network_data2.txt");
        for(int z = 1; z < na.getVertexnumber(); z++)
        {
            ArrayList<Integer> path = na.lowestLatencyPath(0, z);
            for(int i = 0; i < path.size(); i++)
            {
                System.out.print(path.get(i));
                if(i != (path.size() - 1)) System.out.print(" -> ");
                else System.out.print(" ");
            }
            System.out.print("Minium bandwidth along the path is " + na.bandwidthAlongPath(path));
            System.out.println();
        }
        boolean copperOnlyConnected = na.copperOnlyConnected();
        boolean connectedTwoVertFail = na.connectedTwoVertFail();
        if(copperOnlyConnected) System.out.println("The graph can be connected only by copper.");
        else System.out.println("The graph can't be connected only by copper.");
        if(connectedTwoVertFail) System.out.println("The graph can be connected when two vertecy fail.");
        else System.out.println("The graph can't be connected when two vertecy fail.");
        ArrayList<STE> miniumSpanningTree = na.lowestAvgLatST();
        System.out.println("The minium spanning tree is:");
        for(int i = 0; i < miniumSpanningTree.size(); i++)
        {
            STE ste = miniumSpanningTree.get(i);
            System.out.println(" " + ste.u + " -> " + ste.w + " ");
        }
    }
}
