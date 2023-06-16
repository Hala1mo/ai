package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class graph {


	  HashMap<String,List<Edge>> adjList=new HashMap<>();
	 // Map<String,String> InnerMap=new HashMap<>();
	  HashMap<String, Map<String, Double>> heuristicValues=new HashMap<>();
	  //static HashMap<String, double[]> COUNTRIES = new HashMap<String, double[]>();
	  List<String> vertexNames=new ArrayList<>();

	  public void print() {
	    	for (Entry<String, List<Edge>> entry : adjList.entrySet()) {
	    	
	    	      System.out.println("Key: " + entry.getKey() +" is ");
	    	      List<Edge> edgeList = entry.getValue();
	    	      for (Edge edge : edgeList) {
	    	          System.out.println("Destination: " + edge.getDestination() + ", Weight: " + edge.getWeight());
	    	      }
	    	      }
	    	      System.out.println();   
	    	    }
	  
	  public void print1() {
		  for (Map.Entry<String, Map<String, Double>> entry : heuristicValues.entrySet()) {
			    String key = entry.getKey();
			    Map<String, Double> innerMap = entry.getValue();

			    System.out.println("Key: " + key);
			    System.out.println("Values: " + innerMap);
			}  
	    	    }
	  
	  
	  
	  
	    	
	  public void addheur(String Source,String Destination,double AirDistance) {
		  if (heuristicValues.containsKey(Source)) {
	            // Get the distances map for the source city
	            Map<String, Double> distances = heuristicValues.get(Source);
	            // Insert the distance for the destination city
	            distances.put(Destination,AirDistance);
	        } else {
	            // Create a new distances map for the source city and insert the distance
	            Map<String, Double> distances = new HashMap<>();
	            distances.put(Destination, AirDistance);
	            heuristicValues.put(Source, distances);
	        }
	    }
		 
	  
	  
	  
	  /*public void AddAirDistance(String Source,String Destination,double AirDistance) {
		  
	    	for (Entry<String, List<Edge>> entry : adjList.entrySet()) {
	    		if(entry.getKey().equals(Source)) {
	    			
	    		
	    	      System.out.println("Key: " + entry.getKey() +" is ");
	    	      
	    	      List<Edge> edgeList = entry.getValue();
	    	      for (int i=0;i<edgeList.size();i++) {
	    	    	  if(edgeList.get(i).destination.compareTo(Destination)==0) {
	    	          //System.out.println("Destination: " + edge.getDestination() + ", Weight: " + edge.getWeight());
	    	          edgeList.get(i).setAirDistance(AirDistance);
	    	    	  }
	    	      }
	    	      }
	    	      
	    	     	    	      System.out.println();   
	    	    }
	    	}
	  */
	   
	    public void addVertex(String vertex) {
	    	//System.out.println("hi"+vertex);
	        //adjList.put(vertex, new LinkedList<>());
	    	 adjList.putIfAbsent(vertex,new LinkedList<>());
	       
	    }
	    /*public List<String> findShortestPath(String source, String goal) {
	        PriorityQueue<Node> openQueue = new PriorityQueue<>(Comparator.comparingDouble(node ->
	                node.fScore));

	        Map<String, String> visitedFrom = new HashMap<>();

	        openQueue.add(new Node(source, 0, getHeuristicValue(source, goal)));

	        while (!openQueue.isEmpty()) {
	            Node current = openQueue.poll();
	            String currentCity = current.city;

	            if (currentCity.equals(goal)) {
	                // Goal city reached, build and return the path
	                return buildPath(visitedFrom, currentCity);
	            }

	            List<Edge> successors = adjList.get(currentCity);
	            for (int i=0;i<successors.size();++i) {
	                double distance = successors.get(i).weight;
	                double newGScore = current.gScore + distance;

	                double hScore = getHeuristicValue(successors.get(i).destination, goal);
	                double fScore = newGScore + hScore;

	                openQueue.add(new Node(successors.get(i).destination, newGScore, hScore));
	                visitedFrom.put(successors.get(i).destination, currentCity);
	            }
	        }

	        return new ArrayList<>();
	    }
	    

	    
	    
	    private double getHeuristicValue(String city, String goal) {
	        Map<String, Double> heuristicMap = heuristicValues.get(city);
	        return heuristicMap != null ? heuristicMap.getOrDefault(goal, Double.POSITIVE_INFINITY) : Double.POSITIVE_INFINITY;
	    }
	    private List<String> buildPath(Map<String, String> visitedFrom, String current) {
	        List<String> path = new ArrayList<>();
	        while (current != null) {
	            path.add(0, current);
	            current = visitedFrom.get(current);
	        }
	        return path;
	    }
*/
	    public double findShortestDistance(String source, String goal) {
	        PriorityQueue<Node> openQueue = new PriorityQueue<>(Comparator.comparingDouble(node ->
	                node.fScore));

	        Map<String, String> visitedFrom = new HashMap<>();

	        openQueue.add(new Node(source, 0, getHeuristicValue(source, goal)));

	        while (!openQueue.isEmpty()) {
	            Node current = openQueue.poll();
	            String currentCity = current.city;

	            if (currentCity.equals(goal)) {
	                return current.gScore;
	            }

	            List<Edge> successors = adjList.get(currentCity);
	            for (int i = 0; i < successors.size(); ++i) {
	                double distance = successors.get(i).weight;
	                double newGScore = current.gScore + distance;
	                
	                double hScore = getHeuristicValue(successors.get(i).destination, goal);
	                System.out.println(hScore);
	                double fScore = newGScore + hScore;
	                openQueue.add(new Node(successors.get(i).destination, newGScore, hScore));
	                visitedFrom.put(successors.get(i).destination, currentCity);
	            }
	        }

	        // No path found, return a value indicating failure (e.g., negative value)
	        return -1.0;
	    }  
	    
	    private double getHeuristicValue(String city, String goal) {
	        Map<String, Double> sourceMap = heuristicValues.get(city);
	       
	        if (sourceMap != null) {
	        	 System.out.println("City found in heuristicValues.");
	            Double heuristicValue = sourceMap.get(goal);
	            if (heuristicValue != null) {
	                return heuristicValue;
	            }
	        }
	        // Return a default value indicating failure (e.g., negative value)
	        return -1.0;
	    }
	  
	    
	    
	    
	    
	    
	   
	        	
	    public void addEdge(String source, String destination, double weight) {
	       /* Edge edge = new Edge(destination, weight);
	        System.out.println(adjList.get(source));
	        adjList.get(source).add(edge);*/
	    	 List<Edge> edges = adjList.get(source);
	    	    
	    	    // If the adjacency list is null, create a new ArrayList and associate it with the source vertex
	    	    if (edges == null) {
	    	        edges = new ArrayList<>();
	    	        adjList.put(source, edges);
	    	    }

	    	    // Create a new edge and add it to the adjacency list of the source vertex
	    	    Edge edge = new Edge(destination, weight);
	    	    edges.add(edge);
	        
	    }
	    
	    private static class Node {
	        private String city;
	        private double gScore;
	        private double hScore;
	        private double fScore;

	        public Node(String city, double gScore, double hScore) {
	            this.city = city;
	            this.gScore = gScore;
	            this.hScore = hScore;
	            this.fScore = gScore + hScore;
	        }
	    }
	    
	    
	    
	    
     
	   
	    

	    class Edge {
	        String destination;
	       double weight;
	       double AirDistance;
	       
	        public Edge(String destination, double weight) {
	            this.destination = destination;
	            this.weight = weight;
	        }
	      

			public String getDestination() {
				return destination;
			}

			public void setDestination(String destination) {
				this.destination = destination;
			}

			public double getWeight() {
				return weight;
			}

			public void setAirDistance(double AirDistance) {
				this.AirDistance = AirDistance;
			}
			
			public double getAirDistance() {
				return AirDistance;
			}

			public void setWeight(double weight) {
				this.weight = weight;
			}
	        
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}