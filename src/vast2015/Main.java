package vast2015;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.opencsv.CSVReader;

import processing.core.*;

@SuppressWarnings("serial")
public class Main extends PApplet {
	
	boolean finishedAnimation;
	int accumTickTime;
	int lastTickTime;
	int startTimer;
	int tickTime;
	
	Map<Integer, Attendee> attendees;
	Map<Integer, LinkedList<Attendee>> groups;
	PImage map = loadImage("map.jpg");
	CSVReader dataFile;
	GUI gui;	
		
	public void setup() {
		size(800, 600);	
		attendees = new HashMap<Integer, Attendee>();
		groups = new HashMap<Integer, LinkedList<Attendee>>();
		finishedAnimation = true;
		gui = new GUI(this);
	}
	
	public void draw() {
		background(color(0));
		tint(75);
		image(map,200,0, 600, 600);				
		
		if (!finishedAnimation) {			
			int passedTickTime = millis() - lastTickTime;
			int passedTime = millis() - startTimer;
			accumTickTime += passedTickTime;				
			lastTickTime = millis();
			
			if (accumTickTime >= tickTime){
				accumTickTime = 0;
				boolean timeChanged = false;
				int lastTimeStamp = -1;
				String [] nextLine;
				
				try {
					while (!timeChanged && (nextLine = dataFile.readNext()) != null){
						
						int timeStamp = timeStampToInt(nextLine[0]);
						if (timeStamp >= passedTime) {
							drawRow(Integer.parseInt(nextLine[1]),
									Integer.parseInt(nextLine[2]),
									Integer.parseInt(nextLine[3]),
									Integer.parseInt(nextLine[4]));
						}
						if (lastTimeStamp == -1){
							lastTimeStamp = timeStamp;
						}					
						if (timeStamp != lastTimeStamp){								
							timeChanged = true;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (Integer attendeeId: attendees.keySet()){
				Attendee attendee = attendees.get(attendeeId);
				stroke(0);
				strokeWeight(6);
				point(200 + attendee.getX() * 6, attendee.getY() * 6);
				int[] colors = attendee.getColor();
				stroke(colors[0], colors[1], colors[2]);
				strokeWeight(4);
				point(200 + attendee.getX() * 6, attendee.getY() * 6);				
			}
		}		
	}
	
	private void drawRow(int id, int status, int x, int y) {
		Attendee attendee;
		if (status == 0){
			if (attendees.containsKey(id)) {
				attendee = attendees.get(id);
				attendee.setCoords(x, y);
				attendee.setStatus(status);
			} else {
				attendee = new Attendee(x, y, id, status);
				attendees.put(id, attendee);
			}	
		}
	}
	
	int timeStampToInt(String timeStamp){
		int hours = Integer.parseInt(timeStamp.substring(0, 2));
		int minutes = Integer.parseInt(timeStamp.substring(3, 5));
		int seconds = Integer.parseInt(timeStamp.substring(6, 7));
		
		return 3600000 * hours + 60000 * minutes + seconds * 1000;
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { vast2015.Main.class.getName() });
	}

	public void startMap(String day, int hour, int speed) throws IOException {
		attendees = new HashMap<Integer, Attendee>();
		finishedAnimation = false;		
		startTimer = millis();
		tickTime = 1000 / speed;
		dataFile = new CSVReader(new FileReader("./src/data/"+day +"-"+ hour +".csv"));
		dataFile.readNext();
	}
}
