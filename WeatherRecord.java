/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p3
// FILE:             WeatherRecord.java
//
// Authors: Cory Burich
// Author1: Cory Burich, cburich@wisc.edu, cburich, lecture 002
//
/////////////////////////////////////////////////////////////////////////////

import java.util.*;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{
    // TODO declare data structures required
	private int station;
	private int date;
	private double[] data;

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
    }
	
	/**
	 * This comparator should first compare the stations associated with the given FileLines. If 
	 * they are the same, then the dates should be compared. 
	 */
    private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			// TODO implement compare() functionality
			if (l2 != null) {
				String[] one = l1.getString().split(",");
				String[] two = l2.getString().split(",");
				
				//Never compares any values other than station or date
				if (one[0].equals(two[0])) {
					return one[1].compareTo(two[1]);
				}
				else {
					return one[0].compareTo(two[0]);
				}	
			}
			else {
				return 0;
			}
		}
			
		
		public boolean equals(Object o) {
			return this.equals(o);
		}
    }
    
	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 */
    public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
    }
	
	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 */
    public void clear() {
		// TODO initialize/reset data members
    	data = new double[this.getNumFiles()];
    	this.station = -1;
    	this.date = -1;
    	
    	for (int i = 0; i < data.length; i++ ) {
    		data[i] = Double.MIN_VALUE;
    	}
    }

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 */
    public void join(FileLine li) {
		// TODO implement join() functionality
    	
    	//Initialize station and date for a fileLine
    	String[] temp = li.getString().split(",");
    	int idx = li.getFileIterator().getIndex();
    	
    	//Copy rest of string and parse into doubles if data is empty
    	if (this.station == -1 || this.date == -1) {
    		this.station = Integer.parseInt(temp[0]);
        	this.date = Integer.parseInt(temp[1]);
        	this.data[idx] = Double.parseDouble(temp[2]);
    	} 
    	else {
    	
    		data[idx] = Double.parseDouble(temp[2]);
    		
    	}
    	
    	
    	
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		// TODO
		String output = String.valueOf(this.station);
		output = output.concat("," + this.date);
		
		for (int i = 0; i < data.length; i++) {
			if (data[i] == Double.MIN_VALUE) {
				output = output.concat(",-");
			}
			else {
				output = output.concat("," + data[i]);
			}
		}
		return output;
    }
}

