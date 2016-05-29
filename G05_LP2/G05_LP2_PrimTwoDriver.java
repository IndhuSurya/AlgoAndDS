
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**Long Project 2 Level 1 
 * 
 * Driver Program for Prim's Algorithm implementation using Indexed PriorityQueue* 
 * 
 * @authors @karthikrk, @badhri, @indhu, @nandita
 *
 */

public class G05_LP2_PrimTwoDriver {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File file = new File(args[0]);
			in = new Scanner(file);
		} else {
			in = new Scanner(System.in);
		}
		// Always false as we are dealing with Minimum spanning tree for
		// undirected graph
		Graph g = Graph.readGraph(in, false);
		System.out.println(" Weight of the MST using Prim's Algorithm implemented using indexed Priority Queue is :");
		System.out.println(Graph.primTwo(g));
	}
}

/*
 * OUTPUT:
 * 
 * 
Enter number of Nodes:
7
Enter number of Edges:
11

1
2
7
1
4
5
2
3
8
2
4
9
2
5
7
3
5
5
4
5
15
4
6
6
6
5
8
5
7
9
6
7
11
 Weight of the MST using Prim's Algorithm implemented using indexed Priority Queue is :
39

*/