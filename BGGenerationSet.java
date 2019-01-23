package Plant;

import java.util.ArrayList;

public class BGGenerationSet implements Runnable{
	protected int lengthCap;		// determines the max growth of the trunk
	protected int noOfGen;			// determines the number of generations to produce
	protected static int rule;		// holds the rule used to split
	
	public BGRule bgr;
	
	private ArrayList<BGGeneration> generations;
	// holds generations
	
	Thread thr;				// making BGGenerationSet a thread
	
	BGGenerationSet(int noOfGen, int rule,int lengthCap){
		generations=new ArrayList<>();
		this.lengthCap=lengthCap;
//		this.rule=rule;				// the rule is decided.	
		this.noOfGen=noOfGen;		// no of generations are decided.
		
		this.bgr=new BGRule(rule);
		
		
		BGGeneration firstgeneration=new BGGeneration(lengthCap);
// creating the first generation.
		firstgeneration.add(new Stem(0,0,90));
// adding the first stem to first generation.
		generations.add(firstgeneration);
// adding the first generation to list of generation.
		
		thr=new Thread(this);
		// initializing the thread
		thr.start();
		// starting the thread.
	}
	
	/*
	 * Grows Generations and adds it to ArrayList by the name generations
	 * */
	public void grow() {
		for(int i=0;i<=noOfGen;i++){
			int size=generations.size();	// size of the generations list.
			BGGeneration presentgeneration=generations.get(size-1);
			presentgeneration.grow();		// growing present generation stems

			lengthDepricating();		// Deprecates the length of stems-> length/2 as the generations pass
			
			int split=bgr.choose();					// gets the number of splits based on the rule
			
			BGGeneration previousgeneration;		// contains stems that are ready to split
			BGGeneration newgeneration=new BGGeneration(lengthCap);
			
			previousgeneration=generations.get(size-1);
			
			for(int j=0;j<previousgeneration.size();j++) {
				Stem parentstem=previousgeneration.getStem(j);
// parentstem holds the first stem in previous generation, which will be later used to split the this stem
				
				/*
				 * adds child stems to the parentstem 
				 * */
				parentstem.adding(parentstem.x, parentstem.y,parentstem.direction, split,bgr);
				
				// once the child stems are created its for each stem of the previous generation stem add it to new genration.
				for(int k=0;k<parentstem.c.length;k++) {
					newgeneration.add(parentstem.c[k]);
				}
			}
//			adding the new generation to the generations List
			generations.add(newgeneration);
		}
	}
	
	/*
	 * divides length by 2
	 * basically halfs the max-length of stems as generations pass*/
	public void lengthDepricating() {
		if(lengthCap>2) {
			lengthCap/=2;
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		grow();
	}
	
	/*
	 * gets a particular genration
	 * */
	public BGGeneration getgeneration(int index) {
		return generations.get(index);
	}
	
	public int noofGenerations() {
		return generations.size();
	}
	
	/*
	 * changes the rule of growth once the growth is paused on GUI*/
	synchronized public void changerule(int r) {
		bgr.setRule(r);
	}
}
