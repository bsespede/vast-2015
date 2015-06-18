package vast2015;

import java.util.Random;

public class Attendee {

	int r, g, b;
	int x;
	int y;
	int id;
	int status;
	
	public Attendee(int x, int y, int id, int status){
		Random rand = new Random();
		r = rand.nextInt(256);
		g = rand.nextInt(256);
		b = rand.nextInt(256);
		this.x = x;
		this.y = y;
		this.id = id;
		this.status = status;
	}
	
	public int[] getColor(){
		int[] colors = {r, g, b};
		return colors;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getId(){
		return id;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Attendee other = (Attendee) obj;
	    if (this.id != other.getId()) {
	        return false;
	    }
	    return true;
	}
	
	public int hashCode() {
	    return this.id;
	}
}
