package application;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.Node;

public class GraphAlgorithms {
	
	public GraphAlgorithms() {
		// TODO Auto-generated constructor stub
	}
	
	public  Ed[] ddijekstra(Graph g, Vertics start , Vertics dest) throws Exception {
		for (int i = 0 ; i < g.graph.size() ; i++) 
			g.graph.get(i).id = i;
		
		int[] dist = new int[g.graph.size()];
		boolean[] visited = new boolean[g.graph.size()];
		int[] road = new int[g.graph.size()];
		int[] cost = new int[g.graph.size()];
		
		for (int i = 0 ; i < g.graph.size() ; i++) {
			dist[i] = Integer.MAX_VALUE;
			visited[i] = false;
			road[i] = -1;
			cost[i] = -1;
		}
		
		dist[start.id] = 0;
		
		for (int i = 0 ; i < g.graph.size() - 1 ; i++) {
			int u = minDist(dist,visited);
			visited[u] = true;
			
			for (int j = 0 ; j < g.graph.get(u).dEdge.size() ; j++) {
				if (!visited[g.graph.get(u).dEdge.get(j).dest.id] && g.graph.get(u).dEdge.get(j).weight + dist[u] < dist[g.graph.get(u).dEdge.get(j).dest.id]) {
					dist[g.graph.get(u).dEdge.get(j).dest.id] = g.graph.get(u).dEdge.get(j).weight + dist[u];
					road[g.graph.get(u).dEdge.get(j).dest.id ] = u;
					cost[g.graph.get(u).dEdge.get(j).dest.id ] = g.graph.get(u).dEdge.get(j).weight;
				}
			}
		}
		
		for (int i = 0 ; i < road.length ; i++)
			System.out.println(road[i]);
		
		
		// track
		int d = dest.id;
		int r = -2;
		ArrayList<Ed> path = new ArrayList<>();
		if (r != -1) {
			while (r != start.id ) {
				
				r = road[d];
				if (r == -1)
					throw new Exception("A dead end");
				System.out.println(r + " " + d);
				path.add(new Ed(r,d,cost[d]));
				d = r;
				
			}
		}
		System.out.println("Reach");
		Ed[] ed = new Ed[path.size()];
		
		for (int i = path.size() - 1 ; i >= 0 ; i--) {
			ed[i] = new Ed(path.get(i).src,path.get(i).dest,path.get(i).weight);
		}
		return ed;
		
		
	}
	
public  Ed[] udijekstra(Graph g, Vertics start , Vertics dest) throws Exception {
	for (int i = 0 ; i < g.graph.size() ; i++) 
		g.graph.get(i).id = i;
	
		int[] dist = new int[g.graph.size()];
		boolean[] visited = new boolean[g.graph.size()];
		int[] road = new int[g.graph.size()];
		int[] cost = new int[g.graph.size()];
		
		for (int i = 0 ; i < g.graph.size() ; i++) {
			dist[i] = Integer.MAX_VALUE;
			visited[i] = false;
			road[i] = -1;
			cost[i] = -1;
		}
		
		dist[start.id] = 0;
		
		for (int i = 0 ; i < g.graph.size() - 1 ; i++) {
			int u = minDist(dist,visited);
			visited[u] = true;
			
			for (int j = 0 ; j < g.graph.get(u).uEdge.size() ; j++) {
				if (!visited[g.graph.get(u).uEdge.get(j).dest.id] && g.graph.get(u).uEdge.get(j).weight + dist[u] < dist[g.graph.get(u).uEdge.get(j).dest.id ]) {
					dist[g.graph.get(u).uEdge.get(j).dest.id] = g.graph.get(u).uEdge.get(j).weight + dist[u];
					road[g.graph.get(u).uEdge.get(j).dest.id] = u;
					cost[g.graph.get(u).uEdge.get(j).dest.id] = g.graph.get(u).uEdge.get(j).weight;
				}
			}
		}
		
		for (int i = 0 ; i < road.length ; i++)
			System.out.println(road[i]);
		
		
		// track
		int d = dest.id;
		int r = -2;
		ArrayList<Ed> path = new ArrayList<>();
		if (r != -1) {
			while (r != start.id) {
				
				r = road[d];
				if (r == -1)
					throw new Exception("A dead end");
				System.out.println(r + " " + d);
				path.add(new Ed(r,d,cost[d]));
				d = r;
				
			}
		}
		
		Ed[] ed = new Ed[path.size()];
		
		for (int i = path.size() - 1 ; i >= 0 ; i--) {
			ed[i] = new Ed(path.get(i).src,path.get(i).dest,path.get(i).weight);
		}
		return ed;
		
		
	}
	
	
	int minDist(int[] dist , boolean [] visited) {
		int min = Integer.MAX_VALUE;
		int min_index = -1;
		
		for (int i = 0 ; i < dist.length; i++) {
			if (visited[i] == false && dist[i] <= min) {
				min = dist[i];
				min_index = i;
			}
		}
		return min_index;
	}
	
	Ed[] primMST(Graph g)
    {
		for (int i = 0 ; i < g.graph.size() ; i++) 
			g.graph.get(i).id = i;
        // Array to store constructed MST
        int parent[] = new int[g.graph.size()];
 
        // Key values used to pick minimum weight edge in cut
        int key[] = new int [g.graph.size()];
 
        // To represent set of vertices not yet included in MST
        boolean mstSet[] = new boolean[g.graph.size()];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < g.graph.size(); i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
 
        // The MST will have V vertices
        for (int count = 0; count < g.graph.size()-1; count++)
        {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minDist(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
        
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
            	
            		for (int i = 0 ; i < g.graph.get(u).uEdge.size() ; i++) {
            			if (mstSet[g.graph.get(u).uEdge.get(i).dest.id] == false && g.graph.get(u).uEdge.get(i).weight < key[g.graph.get(u).uEdge.get(i).dest.id]) {
            				parent[g.graph.get(u).uEdge.get(i).dest.id] = u;
            				key[g.graph.get(u).uEdge.get(i).dest.id] = g.graph.get(u).uEdge.get(i).weight;
            			}
            		}

        
        }
        
        Ed[] er = new Ed[g.graph.size() - 1];
        
        for (int i = 1 ; i < g.graph.size()  ; i++) 
        		er[i-1] = new Ed(i,parent[i],key[i]);
        

        
        return er;
        
    }
	
	

		
		public Stack<Integer> topologicalSort(Graph g) throws Exception {
			
			for (int i = 0 ; i < g.graph.size() ; i++) 
				g.graph.get(i).id = i;
			
			ArrayList<Integer> queue = new ArrayList<>();
			
			int[] indegree = new int[g.graph.size()];
			
			for (int i = 0 ; i < indegree.length ; i++)
				indegree[i] = 0;
			
			for (int i = 0 ; i < g.graph.size() ; i++) {
				for (int j = 0 ; j < g.graph.get(i).dEdge.size() ; j++) {
					indegree[g.graph.get(i).dEdge.get(j).dest.id]++;
				}
			}
			
			int start = getStart(indegree);
			queue.add(start);
			Stack<Integer> st = new Stack<>();
			
			try {
			
			
			
				for (int i = 0 ; i < indegree.length ; i++) {
					start = queue.get(0);
					queue.remove(0);
					st.push(start);
					for (int j = 0 ; j < g.graph.get(start).dEdge.size() ; j++) {
						indegree[g.graph.get(start).dEdge.get(j).dest.id]--;
						if (indegree[g.graph.get(start).dEdge.get(j).dest.id] == 0) 
							queue.add(g.graph.get(start).dEdge.get(j).dest.id);
										
					}
					
				}
			
			} catch (Exception e) {

				throw new Exception("Not A DAG");
			}
			
			
			return st;
			
			
		}
		
		
		public int getStart(int[] arr) {
			for (int i = 0 ; i < arr.length ; i++)
				if (arr[i] == 0)
					return i;
			
			return -1;
		}
 		
		
}
