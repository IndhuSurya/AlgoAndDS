/**
 * 
 * Long Project 2 Level 1 
 * 
 * Class to represent a graph
 * containing the implementation of Prim's algorithm using Java's priority queue
 * and using indexed heaps and Krukal's algorithm to find the weight of the minimum spanning tree
 * of an undirected graph 
 * 
 * @authors @karthikrk, @badhri, @indhu, @nandita
 * 
 */



import java.io.*;
import java.util.*;



class Graph implements Iterable<Vertex> {
    public List<Vertex> verts; // array of vertices
    public int numNodes; // number of verices in the graph
    public int numEdges;
    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size) {
	numNodes = size;
	verts = new ArrayList<>(size + 1);
	verts.add(0, null);
	// create an array of Vertex objects
	for (int i = 1; i <= size; i++)
	    verts.add(i, new Vertex(i));
    }

    /**
     * Method to add an edge to the graph
     * 
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(int a, int b, int weight) {
	Vertex u = verts.get(a);
	Vertex v = verts.get(b);
	Edge e = new Edge(u, v, weight);
	u.Adj.add(e);
	v.Adj.add(e);
    }

    /**
     * Method to add an arc (directed edge) to the graph
     * 
     * @param a
     *            : int - the head of the arc
     * @param b
     *            : int - the tail of the arc
     * @param weight
     *            : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
	Vertex head = verts.get(a);
	Vertex tail = verts.get(b);
	Edge e = new Edge(head, tail, weight);
	head.Adj.add(e);
	tail.revAdj.add(e);
    }


    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<Vertex> iterator() {
	return new VertexIterator();
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     * 
     *
     * @param <Vertex>
     */
    private class VertexIterator implements Iterator<Vertex> {
	private Iterator<Vertex> it;
	/**
	 * Constructor for VertexIterator
	 * 
	 * @param v
	 *            : Array of vertices
	 * @param n
	 *            : int - Size of the graph
	 */
	private VertexIterator() {
	    it = verts.iterator();
	    it.next();  // Index 0 is not used.  Skip it.
	}

	/**
	 * Method to check if there is any vertex left in the iteration
	 * Overrides the default hasNext() method of Iterator Class
	 */
	public boolean hasNext() {
	    return it.hasNext();
	}

	/**
	 * Method to return the next Vertex object in the iteration
	 * Overrides the default next() method of Iterator Class
	 */
	public Vertex next() {
	    return it.next();
	}

	/**
	 * Throws an error if a vertex is attempted to be removed
	 */
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    public static Graph readGraph(Scanner in, boolean directed) {
	// read the graph related parameters
	System.out.println("Enter number of Nodes:");
    int n = in.nextInt(); // number of vertices in the graph
    System.out.println("Enter number of Edges:");
	int m = in.nextInt(); // number of edges in the graph
	// create a graph instance
	Graph g = new Graph(n);
	g.numEdges=m;
	g.numNodes=n;
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    if(directed) {
		g.addDirectedEdge(u, v, w);
	    } else {
		g.addEdge(u, v, w);
	    }
	}
	in.close();
	return g;
    }
    
    /*
     * Method to find the weight of the MST using Prim's One Algorithm
     * */
    public static int primOne(Graph graph)
    {
    	for(Vertex u: graph)
    	{
    		u.seen=false;
    		u.parent=null;
    	}
    	Vertex src=graph.verts.get(1);
    	src.seen=true;
    	int wmst=0;
    	// Using Java's PriorityQueue
    	PriorityQueue<Edge> pq=new PriorityQueue<Edge>(graph.numEdges);
    	for(Edge e: src.Adj)
    	{
    		pq.add(e);
    	}
    	Edge edge;
    	Vertex u,v,w;
    	while(!pq.isEmpty())
    	{
    		edge=pq.remove();
    		if(!edge.From.seen||!edge.To.seen)
    		{
    			if(edge.From.seen)
    			{
    				u=edge.From;
    				v=edge.To;
    			}
    			else
    			{
    				u=edge.To;
    				v=edge.From;
    			}
    			v.parent=u;
    			wmst=wmst+edge.Weight;
    			v.seen=true;
    			for(Edge f: v.Adj)
    			{
    				if(!f.From.seen||!f.To.seen)
    				{
    					pq.add(f);
    				}
    				w=f.otherEnd(v);
    				if(!w.seen)
    				{
    					pq.add(f);
    				}
    			}
    		}
    	}
    	return wmst;
    }
    
    /*
     * Method to find the weight of the MST using Prim's Two Algorithm
     * */
    
    public static int primTwo(Graph graph)
    {
    	// reInitializing the parameters of Vertices
    	for(Vertex u: graph)
    	{
    		u.seen=false;
    		u.parent=null;
    		u.minEdgeDistance=Integer.MAX_VALUE;
    	}
    	int wmst=0;
    	
    	Vertex[] vArray = new Vertex[graph.numNodes];
    	// Using Indexed Priority Queue
    	IndexedHeap<Vertex> indexPQ=new IndexedHeap<Vertex>(graph.verts.toArray(vArray), new Comparator<Vertex>() {
    		@Override
    		public int compare(Vertex u, Vertex v) {
    		return u.minEdgeDistance - v.minEdgeDistance;
    		}

			
		} );
    	
    	int count=0;
    	while(indexPQ.size>0)
    	{
    		Vertex u=(Vertex)indexPQ.remove();
    		u.seen=true;
    		if(count==0)
    		{
    			u.minEdgeDistance=0;
    			count++;
    		}
    		wmst+=u.minEdgeDistance;
    		for(Edge e: u.Adj)
    		{
    			Vertex v=e.otherEnd(u);
    			if(!v.seen && e.Weight<v.minEdgeDistance)
    			{
    				v.minEdgeDistance=e.Weight;
    				v.parent=u;
    				indexPQ.decreaseKey(v);
    			}
    		}
    		
    	}
    	return wmst;
    }
    /*
     * Method for disjoint data structure
     * */
    public static void makeSet(Vertex x)
    {
    	x.parent=x;
    	x.depth=0;
    }
    
    /*
     * Method to find the parent node until it reaches the root
     * */
    public static Vertex find(Vertex x)
    {
    	if(x!=x.parent)
    	{
    		x.parent=find(x.parent);
    	}
    	return x.parent;
    }
    /*
     * Method to attach smaller tree to the root of the larger tree
     * */
    public static void union(Vertex x, Vertex y)
    {
    	if(x.depth>y.depth)
    	{
    		x.parent=y;
    	}
    	else if(y.depth>x.depth)
    	{
    		y.parent=x;
    	}
    	else
    	{
    		y.parent=x;
    		x.depth++;
    	}
    }
    /*
     * Method to find the weight of the MST using Kruskal's Algorithm
     * */
    
    public static int kruskal(Graph graph)
    {
    	for(Vertex u: graph)
    	{
    		makeSet(u);
    		for(Edge e:u.Adj)
    		{
    			e.visited=false;
    		}
    	}
    	int wmst=0;
    	PriorityQueue<Edge> pq=new PriorityQueue<Edge>(graph.numEdges);
    	for(Vertex v: graph)
    	{
    		for(Edge e: v.Adj)
    		{
    			if(!e.visited)
    			{
    				pq.add(e);
    				e.visited=true;
    			}
    		}
    		
    	}
    	Vertex ru, rv;
    	Edge e;
    	while(!pq.isEmpty())
    	{
    		e=pq.remove();
    		ru=find(e.From);
    		rv=find(e.To);
    		if(ru!=rv)
    		{
    			wmst=wmst+e.Weight;
    			union(ru,rv);
    		}
    	}
    	return wmst;
    }
   
}