/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p3
// FILE:             Reducer.java
//
// Authors: Cody Kairis, Cory Burich
// Author1: Cory Burich, cburich@wisc.edu, cburich, lecture 002
// Author2: Cody Kairis, kairis@wisc.edu, kairis, lecture 002
//
/////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file. 
 *
 */
public class Reducer {
    // list of files for stocking the PQ
    private List<FileIterator> fileList;
    private String type,dirName,outFile;

    public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		r.run();
	
    }

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
    public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
    }

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 */
    public void run() {
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);

		Record r = null;

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();

		for(int i = 0; i < files.length; i++) {
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				//fileList.add(fif.makeFileIterator(f.getAbsolutePath()));
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}

		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}
		
		//Create a new FileLinePriorityQueue
		MinPriorityQueueADT<FileLine> queue = 
				new FileLinePriorityQueue(fileList.size() + 1,
						r.getComparator());
		
		//Add one line from each file by going through fileList
		for (int i = 0; i < fileList.size(); i++ ) {
			FileLine temp = fileList.get(i).next();
			try {
				queue.insert(temp);
			} catch (PriorityQueueFullException e) {
				System.out.println("Priority Queue Full.");
			}
		}
		//Reference to the FileLine removed from the queue
		FileLine one = null;
		FileLine two = null;
		

		//Remove first FileLine from the queue
		try {
			one = queue.removeMin();
			
			//Replace the fileLine in the queue with another from the same file 
			if (one.getFileIterator().hasNext()) {
				queue.insert(one.getFileIterator().next());
			}
		} catch (PriorityQueueEmptyException e) {
			System.out.println("Priority queue empty.");
		} catch (PriorityQueueFullException e) {
			System.out.println("Priority queue full.");
		}
		
		//Add this fileLine to the empty record
		r.join(one);
		
		//Declare output string
		String output = null;
		
		//Record has one entry in it, key has been established
		while (!queue.isEmpty()) {
			
			try {
				
				//Remove another fileLine from the queue
				two = queue.removeMin();
				
				//Replace two in the queue
				if (two.getFileIterator().hasNext()) {
					queue.insert(two.getFileIterator().next());
				}
				
				//Compare the second fileLine with the one in the record
				int compare = r.getComparator().compare(one, two);
				
				
				//Compare key values
				if (compare == 0) {
					
					//Join if keys match
					r.join(two);
					
				} else {
					//Create/append to output string if key words don't match
					output = output + r.toString() + "\n";
				
					//Clear record
					r.clear();
					
					//Add two to record after it's been cleared
					r.join(two);
					
					
				}
				//Change the references of two to one, for next comparison
				one = two;
				
			} catch (PriorityQueueEmptyException e) {
				System.out.println("Priority queue empty.");
			} catch (PriorityQueueFullException e) {
				System.out.println("Priority queue full.");
			} 
			
			
		}
		
		//Write record to output file at the end
		output = output + r.toString();
		output = output.substring(4);
		try (PrintWriter out = new PrintWriter(this.outFile)) {
			out.println(output);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
		
		
	}	
		
}

