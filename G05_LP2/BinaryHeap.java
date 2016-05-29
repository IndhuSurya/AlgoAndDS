
//Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.*;

public class BinaryHeap<T> implements PQ<T> {
 T[] pq;
 Comparator<T> c;
 int size=0;
 
 BinaryHeap(){}
 
 /** Build a priority queue with a given array q */
 BinaryHeap(T[] q, Comparator<T> comp) {
 size= q.length-1;
	pq = q;
	c = comp;
	buildHeap();
 }
 
 /** Create an empty priority queue of given maximum size */
 BinaryHeap(int n, Comparator<T> comp) { /* to be implemented */
 	size=0;
 	pq= (T[]) new Object[n];
 	c=comp;
 	buildHeap();
 }

 public void insert(T x) {
	add(x);
 }

 public T deleteMin() {
	return remove();
 }

 public T min() { 
	return peek();
 }

 public void add(T x) { /* to be implemented */
 	if (size==pq.length-1){
 		resize();
 	}
 	size++;
 	pq[size]=x;
 	percolateUp(size);
 }

 public T remove() { /* to be implemented */
 	if(size==0)
 		return null;
 	else if (size==1){
 		T node = pq[1];
 		size--;
 		return node;
 	}
 	else{
 		T node = pq[1];
 		pq[1]=pq[size--];
 		percolateDown(1);
 		return node;
 	}
 }

 public T peek() { /* to be implemented */
 	if(size!=0){
 		T top = pq[1];
 		return top;
 	}
 	return null;
 }
 
 void resize(){
 	pq = Arrays.copyOf(pq, pq.length*2); //resize to twice the size of pq
 	
 }

 /** pq[i] may violate heap order with parent */
 void percolateUp(int i) { /* to be implemented */
 	pq[0]=pq[i];
 	while(c.compare(pq[i/2],pq[0])>0){
 		pq[i]=pq[i/2];
 		i=i/2;
 	}
 	pq[i]=pq[0];
 }

 /** pq[i] may violate heap order with children */
 void percolateDown(int i) { /* to be implemented */
 	T x = pq[i];
 	int schild=0;
 	while(2*i <=size){
 		if(2*i == size){
 			if ( c.compare(x,pq[size])<=0){
 				pq[i]=pq[size];
 				i=size;
 			}
 			else
 				break;
 		}
 		else {
 			if(c.compare(pq[2*i],pq[2*i+1])>=0){
 				schild = 2*i+1;
 			}
 			else {
 				schild = 2*i;
 			}
 			if(c.compare(x,pq[schild])>=0){
 				pq[i]=pq[schild];
 				i=schild;
 			}
 			else
 				break;
 		}
 	}
 	pq[i]=x;
 	return;
 }
 
 private int compare(T x, T t){
 	return ((Vertex) x).minEdgeDistance - ((Vertex) t).minEdgeDistance;
 }

 /** Create a heap.  Precondition: none. */
 void buildHeap() {
 	for(int i=pq.length/2;i>=1;i--){
 		percolateDown(i);
 	}
 }

 /* sort array A[1..n].  A[0] is not used. 
    Sorted order depends on comparator used to buid heap.
    min heap ==> descending order
    max heap ==> ascending order
  */
 public static<T> void heapSort(T[] A, Comparator<T> comp) { /* to be implemented */
 	BinaryHeap<T> bh = new BinaryHeap<>(A,comp);
 	int size = bh.size;
 	T temp;
 	//buildHeap();
 	for(int i=size;i>0;i--){
 		temp = bh.pq[size];
 		bh.pq[size--] = bh.pq[1];
 		bh.pq[1] = temp;
 		bh.percolateDown(1);
 	}
 }
 
}
