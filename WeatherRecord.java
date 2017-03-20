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
	 * This class implements the comparator class and is used to compare two 
	 * weather file lines
	 */
    private class WeatherLineComparator implements Comparator<FileLine> {
    	
    	/**
    	 * This method compares two weather file lines by first comparing the 
    	 * stations and then comparing the dates.
    	 * 
    	 * @param l1 the Fileline being compared
		 * @param l2 the Fileline being compared to
		 * @return negative if l1 comes before l2, zero if the word is the
		 * 				same, positive if l1 comes after l2
    	 */
		public int compare(FileLine l1, FileLine l2) {
			
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
	 * This method creates and returns a new instance of the WeatherLineComparator
	 * class.
	 * 
	 * @return a new instance of a WeatherLineComarator
	 */
    public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
    }
	
	/**
	 * This method fills each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 */
    public void clear() {
		
    	data = new double[this.getNumFiles()];
    	this.station = -1;
    	this.date = -1;
    	
    	for (int i = 0; i < data.length; i++ ) {
    		data[i] = Double.MIN_VALUE;
    	}
    }

	/**
	 * This method parses the information contained in the given FileLine and inserts the
	 * data into the record based on which file it came from.
	 */
    public void join(FileLine li) {
		
    	
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
	 * Returns a string of the weather record in the specified format.
	 * 
	 * @return weather record as a string
	 */
    public String toString() {
	
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

