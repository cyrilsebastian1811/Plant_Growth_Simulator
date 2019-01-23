package Plant;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/*
 * making BGGeneration as an Observable, as i want the App to be notified to draw the generation
 * every time each stem in this generation grows by a unit length.
 * */
public class BGGeneration extends Observable{
	protected ArrayList<Stem> gStems;
	// holds a list of all the stems in a generation
	int lengthCap=0;
	// max length to which the generation grows.
	
	@SuppressWarnings("deprecation")
	BGGeneration(int lengthCap){
		gStems=new ArrayList<>();
		this.lengthCap=lengthCap;
		
		// adding The App as an observer 
		
		this.addObserver((BGPanel)GApp.getinstance().getCanvas());
	}
	
	@SuppressWarnings("deprecation")
	public void grow() {
		int length=0;
		while(length<lengthCap) {
			for(Stem s: gStems) {
				// grows all the stems of this generation by one unit length
				s.grow(lengthCap);
			}
			length++;
			
			// sleeping the Thread for 5 millisecs after growing 
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			setChanged();
			notifyObservers();
		}
	}
	
	/*
	 * used to add stems this generation */
	public void add(Stem s) {
		try {
			if(s==null) {
				throw new NullPointerException();
			}
			gStems.add(s);
		}catch(NullPointerException exc) {
			exc.printStackTrace();
			System.out.println("one of the Stem in generation: ");
		}
		
	}
	
	/*
	 * gets the number of stems in this generation*/
	public int size() {
		return gStems.size();
	}
	
	/*
	 * gets stem at a particular index in gstems ArrayList of this generation*/
	public Stem getStem(int index) {
		return gStems.get(index);
	}

	public ArrayList<Stem> getGenerationStems(){
		return gStems;
	}
}
