//Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
 //I index;
 IndexedHeap(){}
 /** Build a priority queue with a given array q */
 IndexedHeap(T[] q, Comparator<T> comp) {
	super(q, comp);
	for(int i = 1; i <q.length; i++)
		super.pq[i].putIndex(i);
 }

 /** Create an empty priority queue of given maximum size */
 IndexedHeap(int n, Comparator<T> comp) {
	super(n, comp);
	assign();
 }
 
 /** restore heap order property after the priority of x has decreased */
 void decreaseKey(T x) {
	percolateUp(x.getIndex());
	assign();
	}
 
 void percolateUp(int i)
 {
	 super.percolateUp(i);
	 assign();
 }
 
 void percolateDown(int i)
 {
	 super.percolateDown(i);
	 assign();
 }
 public void assign(){
	 	for(int i=1;i<=super.size;i++){
	 		Vertex v=(Vertex)super.pq[i];
	 		v.putIndex(i);
	 	}
	 }
	 


}
