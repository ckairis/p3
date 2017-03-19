import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
    // TODO
    private Comparator<FileLine> cmp;
    private int maxSize;
    private FileLine[] queue;
    private int numLines;
    

    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		numLines = 0;
		
		// TODO
		queue = new FileLine[maxSize + 1];
    }

    /**
     * Removes the minimum element from the Priority Queue, and returns it.
     *
     * @return the minimum element in the queue, according to the compareTo()
     * method of FileLine.
     * @throws PriorityQueueEmptyException if the priority queue has no elements
     * in it
     */
    public FileLine removeMin() throws PriorityQueueEmptyException {
		// TODO
    	if (isEmpty()) {
    		throw new PriorityQueueEmptyException();
    	}
    	FileLine temp = queue[1];
    	//Retain shape
    	queue[1] = queue[numLines];
    	boolean done = false;
    	
    	int parent = 1;
    	
    	while (!done) {
    		int child1 = parent * 2;
        	int child2 = (parent * 2) + 1;
        	
    		FileLine maxChild = null;
        	//Find child with max priority
        	if (child1 < queue.length && child2 < queue.length) {
	    		
	    		maxChild = maxChild(queue[child1], queue[child2]);
        	} 
        	//Both greater than the length, set max as parent
        	else if (child1 >= queue.length && child2 >= queue.length) {
        		maxChild = queue[parent];
        	}
        	else if (child1 >= queue.length) {
        		maxChild = queue[child2];
        	}
        	else if (child2 >= queue.length) {
        		maxChild = queue[child1];
        	}
        	
    		
    		int parentComp = cmp.compare(queue[parent], maxChild);
    		
    			
    		//Compare parent to child
    		if (parentComp <= 0) {
    			done = true;
    		} 
    		//Swap parent and child if child higher priority
    		else {
    			FileLine hold = queue[parent];
    			
    			//child1 was the max, swap parent with it
    			if (cmp.compare(maxChild, queue[child1]) == 0) {
    				queue[parent] = queue[child1];
        			queue[child1] = hold;
        			parent = child1;
    			}
    			//child 2 was the max, swap parent with it
    			else {
    				queue[parent] = queue[child2];
    				queue[child2] = hold;
    				parent = child2;
    			}
    			
    		}
    	}
    	
    	numLines--;
		return temp;
    }
    
    private FileLine maxChild(FileLine a, FileLine b) {
    	int compare = cmp.compare(a, b);
    	
    	if (compare < 0) {
    		return a;
    	}
    	else if (compare == 0) {
    		return a;
    	}
    	else {
    		return b;
    	}
    }

    /**
     * Inserts a FileLine into the queue, making sure to keep the shape and
     * order properties intact.
     *
     * @param fl the FileLine to insert
     * @throws PriorityQueueFullException if the priority queue is full.
     */
    public void insert(FileLine fl) throws PriorityQueueFullException {
		// TODO
    	boolean done = false;
    	
    	if (numLines >= (maxSize - 1)) {
    		throw new PriorityQueueFullException();
    	}
    	//If empty insert FileLine into index one, skip comparisons
    	if (numLines == 0) {
    		queue[1] = fl;
    		
    	}
    	//Otherwise, run comparisons
    	else {
    		//Local variable to keep track of the parent index
        	int parent;
        	int child = numLines + 1;
        	//Insert the FileLine in the next available space in the queue
        	queue[child] = fl;
        	
        	//Reheapify
        	while (!done) {
        		parent = child / 2;
        		if (parent == 0) {
        			break;
        		}
        		//Compare the strings of the parent and child to decide priority
        		int compare = cmp.compare(queue[parent], queue[child]);
        		
        		//If parent and child's Strings are the same then exit loop
        		if (compare == 0) {
        			done = true;
        		}
        		//If parent's String comes before child's string, exit loop
        		else if (compare < 0) {
        			done = true;
        		} 
        		//Swap parent and child if priority of child is higher
        		else {
        			
        			FileLine temp = queue[parent];
        			queue[parent] = queue[child];
        			queue[child] = temp;
        			
        			//Swap index of child with parent index
        			child = parent;
        		}
        	}
    	}
  
    	numLines++;
    	
    }

    /**
     * Checks if the queue is empty.
     * e.g. 
     * 
     * <pre>
     * {@code
     * m = new MinPriorityQueue(); 
     * // m.isEmpty(): true
     * m.insert(FileLine fl);
     * // m.isEmpty(): false
     * m.remove();
     * // m.isEmpty(): true
     * }
     * </pre>
     *
     * @return true, if it is empty; false otherwise
     */
    public boolean isEmpty() {
		// TODO
    	if (numLines == 0) {
    		return true;
    	} else {
    		return false;
    	}	
    }
}
