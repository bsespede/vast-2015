package vast2015;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Parser {

	public static void main(String[] args) {
		try {
			parseCSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void parseCSV() throws IOException{
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader("sunday.csv"));
		String [] firstLine = reader.readNext();
		
		Set<Attendee> attendees = new HashSet<Attendee>();
		String auxHour = null;
		for (int i = 8; i < 21; i++) {
			CSVWriter writer = new CSVWriter(new FileWriter("sunday-"+ i +".csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
		    System.out.println("WRITING "+ i);
			int linesRead = 0;
		    String[] nextLine;
		    String previousHour = null;		    
		    
		    while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        //System.out.println(nextLine[0]);
		        if (linesRead == 0) {
		        	String[] entries = new String[5];
			        entries[0] = firstLine[0];
			        entries[1] = firstLine[1];
			        entries[2] = firstLine[2];
			        entries[3] = firstLine[3];
			        entries[4] = firstLine[4];
			        writer.writeNext(entries);
			        addAttendees(writer, attendees, auxHour);
			        linesRead++;
		        } else {
			        String[] entries = new String[5];
			        entries[0] = nextLine[0].substring(10);
			        entries[1] = nextLine[1];
			        entries[2] = nextLine[2].equals("check-in")? "1" : "0";
			        entries[3] = nextLine[3];
			        entries[4] = nextLine[4];

			        if (previousHour != null && !(previousHour.substring(0, 2).equals(entries[0].substring(0, 2)))){
			        	auxHour = entries[0].substring(0, 2)+ ":00:00";			        	
			        	break;
			        } else {
			        	previousHour = entries[0];			        	
			        }
			        attendees.add(new Attendee(Integer.parseInt(entries[3]), Integer.parseInt(entries[4]), Integer.parseInt(entries[1]), Integer.parseInt(entries[2])));
			        writer.writeNext(entries);			        
		        }
		    }
		    System.out.println("FINISHED "+ i);
			writer.close();
		}
	}

	private static void addAttendees(CSVWriter writer, Set<Attendee> attendees, String previousHour) {
		for(Attendee attendee: attendees){
			 String[] entries = new String[5];
		     entries[0] = previousHour;
		     entries[1] = String.valueOf(attendee.getId());
		     entries[2] = String.valueOf(attendee.getStatus());
		     entries[3] = String.valueOf(attendee.getX());
		     entries[4] = String.valueOf(attendee.getY());
		     writer.writeNext(entries);
		}		
	}
}
