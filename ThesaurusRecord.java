import java.util.*;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 * The word field is the entry in the thesaurus, syn is the list of all associated synonyms.
 */

public class ThesaurusRecord extends Record{
    // TODO declare data structures required
	ArrayList<String> syn = new ArrayList<String>();
	
	String word = null;
	
	
	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public ThesaurusRecord(int numFiles) {
	super(numFiles);
	clear();
    }

    /**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		
		
		public int compare(FileLine l1, FileLine l2) {
			// TODO implement compare() functionality
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
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 */
    public Comparator<FileLine> getComparator() {
		
    	//Essentially a Constructor
    	return new ThesaurusLineComparator();
    }
	
	/**
	 * This method should (1) set the word to null and (2) empty the list of synonyms.
	 */
    public void clear() {
		// TODO initialize/reset data members
    	word = null;
    	
    	syn = new ArrayList<String>();
    }
	
	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
    public void join(FileLine w) {
		// TODO implement join() functionality
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
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		// TODO
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
