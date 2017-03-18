import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{
	private int Station;
	private int Date;
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
			String[] s1 = l1.getString().split(",");
			String[] s2 = l1.getString().split(",");
			
			if(!s1[0].equals(s2[0])){
				return s1[0].compareTo(s2[0]);
			}
			else if(!s1[1].equals(s2[1])){
				return s1[1].compareTo(s2[1]);
			}
			
			return 0;
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
    	data = new double[this.getNumFiles()];
    	Station = -1;
    	Date = -1;
    	
    	for(int i = 0; i < data.length; i++){
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
    	String[] line = li.getString().split(",");
    	int idx = li.getFileIterator().getIndex();
    	
    	if(-1 == Station || -1 == Date){
    		this.Station = Integer.parseInt(line[0]);
    		this.Date = Integer.parseInt(line[1]);
    	}
    	
    	data[idx] = Double.parseDouble(line[2]);
    	
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		// TODO
		String record = String.valueOf(Station); 
		record = record.concat("," + Date);
		
    	for(int i = 0; i < data.length; i++){
    		if(Double.MIN_VALUE == data[i]){
    			record = record.concat(",-");
    		}
    		else{
    			record = record.concat("," + data[i]);
    		}
    	}
    	
		return record;
    }
}
