package cs1501_p4;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class NetAnalysis implements NetAnalysis_Inter
{
    private ArrayList<Edge>[] edges;

    public NetAnalysis(String filename)
    {
        try
        {
            Scanner sca = new Scanner(new File(filename));
            this.edges = new ArrayList[Integer.parseInt(sca.nextLine())];
            for(int i = 0; i < edges.length; i++) edges[i] = new ArrayList<Edge>();
            while(sca.hasNextLine())
            {
                String[] info = sca.nextLine().split(" ");
                if(info.length == 5)
                {
                    int u = Integer.parseInt(info[0]);
                    int w = Integer.parseInt(info[1]);
                    String type = info[2];
                    int width = Integer.parseInt(info[3]);
                    int length = Integer.parseInt(info[4]);
                    Edge newedge = new Edge(u, w, type, width, length);
                    edges[u].add(newedge);
                    edges[w].add(newedge);
                }
            }
        }
        catch(Exception e){}
    }

    public int getVertexnumber() {return edges.length;}

    private Edge getEdge(int u, int w)
    {
        if(u >= edges.length) return null;
        ArrayList<Edge> list = edges[u];
        for(int i = 0; i < list.size(); i++)
        {
            Edge getEdge = list.get(i);
            if(getEdge.u == w || getEdge.w == w) return getEdge;
        }
        return null;
    }

    public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException
    {
        if(p.size() < 2) throw new IllegalArgumentException();
        int bandwidthAlongPath = Integer.MAX_VALUE;
        for(int i = 0; i < (p.size() - 1); i++)
        {
            Edge currEdge = getEdge(p.get(i), p.get(i + 1));
            if(currEdge == null) throw new IllegalArgumentException();
            if(currEdge.band < bandwidthAlongPath) bandwidthAlongPath = currEdge.band;
        }
        return bandwidthAlongPath;
    }

    private int getnext(int u, Edge edge)
    {
        if(edge.u == u) return edge.w;
        else return edge.u;
    }

    public ArrayList<Integer> lowestLatencyPath(int u, int w)
    {
        HashMap<Integer[], Double> seenpaths = new HashMap<Integer[], Double>();
        ArrayList<Integer[]> usedpaths = new ArrayList<Integer[]>();
        TreeMap<Integer, Double> visited = new TreeMap<Integer, Double>();
        int curr = u;
        visited.put(curr, (double)0.0);
        while(visited.size() < edges.length && curr != w)
        {
            double distancetocurr = visited.get(curr);
            ArrayList<Edge> neighbors = edges[curr];
            for(int i = 0; i < neighbors.size(); i++)
            {
                Edge neighbor = neighbors.get(i);
                int next = getnext(curr, neighbor);
                if(visited.containsKey(next)) continue;
                double distancetonext = distancetocurr + neighbor.time;
                boolean addnewpath = true;
                boolean updatepath = false;
                Integer[] updateoldpath = new Integer[2];
                Integer[] updatenewpath = {curr, next};
                for(Integer[] key : seenpaths.keySet())
                {
                    if(key[1] == next)
                    {
                        addnewpath = false;
                        double latency = seenpaths.get(key);
                        if(distancetonext < latency)
                        {
                            updatepath = true;
                            updateoldpath = key;
                        }
                    }
                }
                if(addnewpath)
                {
                    Integer[] path = {curr, next};
                    seenpaths.put(path, distancetonext);
                }
                if(updatepath)
                {
                    seenpaths.remove(updateoldpath);
                    seenpaths.put(updatenewpath, distancetonext);
                }
            }
            Integer[] choosetopath = {0,0};
            double choosetodistance = Double.MAX_VALUE;
            for(Integer[] key : seenpaths.keySet())
            {
                Integer from = key[0];
                Integer to = key[1];
                if(visited.containsKey(to)) continue;
                double distance = seenpaths.get(key);
                if(distance < choosetodistance)
                {
                    choosetopath[0] = from;
                    choosetopath[1] = to;
                    choosetodistance = distance;
                }
            }
            usedpaths.add(choosetopath);
            visited.put(choosetopath[1], choosetodistance);
            curr = choosetopath[1];
        }
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(usedpaths.get(usedpaths.size() - 1)[1]);
        for(int i = (usedpaths.size() - 1); i >= 0; i--)
        {
            if(usedpaths.get(i)[1] == temp.get(temp.size() - 1)) temp.add(usedpaths.get(i)[0]);
        }
        ArrayList<Integer> lowestLatencyPath = new ArrayList<Integer>();
        for(int i = (temp.size() - 1); i >= 0; i--)
        {
            lowestLatencyPath.add(temp.get(i));
        }
        return lowestLatencyPath;
    }

    private boolean allvisited(boolean[] seen, boolean[] visited)
    {
        if(seen.length != visited.length) return false;
        for(int i = 0; i < seen.length; i++) if(seen[i] && !visited[i]) return false;
        return true;
    }

    public boolean copperOnlyConnected()
    {
        if(edges.length < 1) return false;
        boolean[] seenvertex = new boolean[edges.length];
        for(int i = 0; i < seenvertex.length; i++) seenvertex[i] = false;
        boolean[] visitedvertex = new boolean[edges.length];
        for(int i = 0; i < visitedvertex.length; i++) visitedvertex[i] = false;
        seenvertex[0] = true;
        while(!allvisited(seenvertex, visitedvertex))
        {
            for(int i = 0; i < seenvertex.length; i++) visitedvertex[i] = seenvertex[i];
            for(int i = 0; i < visitedvertex.length; i++) if(visitedvertex[i])
            {
                ArrayList<Edge> neighbors = edges[i];
                for(int n = 0; n < neighbors.size(); n++)
                {
                    Edge neighbor = neighbors.get(n);
                    if(!neighbor.isCopper()) continue;
                    int next = getnext(i, neighbor);
                    seenvertex[next] = true;
                }
            }
        }
        for(int i = 0; i < visitedvertex.length; i++) if(!visitedvertex[i]) return false;
        return true;
    }

    private boolean connected(int[] failedVertex)
    {
        if(edges.length < 1) return false;
        boolean[] seenvertex = new boolean[edges.length];
        for(int i = 0; i < seenvertex.length; i++) seenvertex[i] = false;
        for(int i = 0; i < seenvertex.length; i++)
        {
            boolean isFailed = false;
            for(int f = 0; f < failedVertex.length; f++) if(failedVertex[f] == i) isFailed = true;
            if(!isFailed)
            {
                seenvertex[i] = true;
                break;
            }
        }
        boolean[] visitedvertex = new boolean[edges.length];
        for(int i = 0; i < visitedvertex.length; i++) visitedvertex[i] = false;
        while(!allvisited(seenvertex, visitedvertex))
        {
            for(int i = 0; i < seenvertex.length; i++) visitedvertex[i] = seenvertex[i];
            for(int i = 0; i < visitedvertex.length; i++) if(visitedvertex[i])
            {
                ArrayList<Edge> neighbors = edges[i];
                for(int n = 0; n < neighbors.size(); n++)
                {
                    Edge neighbor = neighbors.get(n);
                    int next = getnext(i, neighbor);
                    boolean cango = true;
                    for(int f = 0; f < failedVertex.length; f++) if(failedVertex[f] == next) cango = false;
                    if(cango) seenvertex[next] = true;
                }
            }
        }
        for(int i = 0; i < visitedvertex.length; i++) if(!visitedvertex[i])
        {
            boolean isFailed = false;
            for(int f = 0; f < failedVertex.length; f++)
            {
                if(failedVertex[f] == i) isFailed = true;
            }
            if(!isFailed) return false;
        }
        return true;
    }

    public boolean connectedTwoVertFail()
    {
        for(int i = 0; i < edges.length; i++) for(int o = (i + 1); o < edges.length; o++)
        {
            int[] failedVertex = {i, o};
            if(!connected(failedVertex)) return false;
        }
        return true;
    }

    private boolean hasfalse(boolean[] bl)
    {
        for(int i = 0; i < bl.length; i++) if(!bl[i]) return true;
        return false;
    }

    public ArrayList<STE> lowestAvgLatST()
    {
        ArrayList<STE> lowestAvgLatST = new ArrayList<STE>();
        boolean[] visited = new boolean[edges.length];
        for(int i = 0; i < visited.length; i++) visited[i] = false;
        visited[0] = true;
        while(hasfalse(visited))
        {
            STE ste = null;
            int next = 0;
            double distance = Double.MAX_VALUE;
            for(int i = 0; i < visited.length; i++) if(visited[i])
            {
                ArrayList<Edge> neighbors = edges[i];
                for(int n = 0; n < neighbors.size(); n++)
                {
                    Edge neighbor = neighbors.get(n);
                    int maynext = getnext(i, neighbor);
                    double maydistance = neighbor.time;
                    STE maySTE = neighbor.ste;
                    if(!visited[maynext] && maydistance < distance)
                    {
                        distance = maydistance;
                        next = maynext;
                        ste = maySTE;
                    }
                }
            }
            if(ste != null)
            {
                lowestAvgLatST.add(ste);
                visited[next] = true;
            }
        }
        return lowestAvgLatST;
    }
}