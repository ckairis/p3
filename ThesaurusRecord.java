/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p3
// FILE:             ThesaurusRecord.java
//
// Authors: Cody Kairis
// Author1: Cody Kairis, kairis@wisc.edu, kairis, lecture 002
//
/////////////////////////////////////////////////////////////////////////////

import java.util.*;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 * The word field is the entry in the thesaurus, syn is the list of all associated synonyms.
 */

public class ThesaurusRecord extends Record{
    
	private ArrayList<String> syn = new ArrayList<String>();
	
	private String word = null;
	
	
	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public ThesaurusRecord(int numFiles) {
	super(numFiles);
	clear();
    }

    /**
	 * This class implements the comparator class and is used to compare two 
	 * thesaurus file lines
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		
		/**
		 * This method compares two thesaurus file lines and determines which
		 * word comes first alphabetically.
		 * 
		 * @param l1 the Fileline being compared
		 * @param l2 the Fileline being compared to
		 * @return negative if l1 comes before l2, zero if the word is the
		 * 				same, positive if l1 comes after l2
		 */
		public int compare(FileLine l1, FileLine l2) {
			
			if (l2 != null) {
				int colonIndex1 = l1.getString().indexOf(':');
				int colonIndex2 = l2.getString().indexOf(':');
				String sub1 = l1.getString().substring(0, colonIndex1);
				String sub2 = l2.getString().substring(0, colonIndex2);
			
				//Return -1 if FileLine1 comes before FileLine2	
				return sub1.compareTo(sub2);
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
	 * This method creates and returns a new instance of the ThesaurusLineComparator class.
	 * 
	 * @return a new instance of a ThesaurusLineComarator
	 */
    public Comparator<FileLine> getComparator() {
		
    	//Essentially a Constructor
    	return new ThesaurusLineComparator();
    }
	
	/**
	 * This method sets the word to null and empties the list of synonyms.
	 */
    public void clear() {
		
    	word = null;
    	
    	syn = new ArrayList<String>();
    }
	
	/**
	 * This method parses the list of synonyms contained in the given FileLine and inserts any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
    public void join(FileLine w) {
	
    	//Create key string and parse list of synonyms
    	int colonIndex = w.getString().indexOf(':');
    	String key = w.getString().substring(0, colonIndex);
    	String remaining = w.getString().substring(colonIndex+1);
		String[] temp = remaining.split(",");
    	
    	//If record is empty initialize word and syn fields
    	if (syn.isEmpty()) {
    		
    		//Create new record
    		this.word = key;
    		for (int i = 0; i < temp.length; i++) {
    			syn.add(temp[i]);
    		}
    		Collections.sort(syn);
    	} else {
    		//if file line contains the same key:
    		
    		//check for synonyms in temp that aren't apart of syn
	    	for (int count = 0; count < temp.length; count++ ){
	    		boolean identical = false;
	    		for (int count2 = 0; count2 < syn.size(); count2++ ) {
	    			if (temp[count].equals(syn.get(count2))) {
	    				identical = true;
	    				break;
	    			}
	    		}
	    		syn.add(temp[count]);
	    		identical = false;
	    	}
    	}
    	Collections.sort(syn);
    	
    }
	
	/**
	 * This method returns a string of the thesaurus record in the specified 
	 * format.
	 * 
	 * @return thesaurus record as a string
	 */
    public String toString() {
	
    	String output = this.word + ":";
    	String add;
    	for (int i = 0; i < syn.size(); i++) {
    		if (i == syn.size() - 1) {
    			add = syn.get(i);
    		} else {
    			add = syn.get(i) + ",";
    		}
    		output = output + add;
    	}
		return output;
	}
}
