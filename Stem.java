package Plant;

public class Stem {
	int x;
	int y;
	int xstart;
	int ystart;
	double length;
	double direction;
	int age;
	int StemID;
	Stem[] c;
	
	static int UniqueID=0;
	
	Stem(int x,int y,double direction){
		this.x=x;
		this.y=y;
		this.xstart=x;
		this.ystart=y;
		this.length=0;
		this.direction=direction;
		this.age=0;
		this.StemID=UniqueID++;
	}
	
	
	public void grow(int lengthCap) {
		if(length<lengthCap) {
			int a;
			int b;
			double slope;
			double sum;
			double ang=(this.direction%360);
			
			aging();
			if (ang==90 || ang==-270) {
				this.y++;
				this.length+=1;
				return;
			}else if(ang==-90 || ang==270){
				this.y--;
				this.length+=1;
				return;
			}else if(ang==180 || ang==-180){
				this.x--;
				this.length+=1;
				return;
			}else if(ang==0){
				this.x++;
				this.length+=1;
				return;
			}
			
			a=this.x;		// x0
			b=this.y;		// y0
			slope=Math.tan(Math.toRadians(ang));
			
			// checks if the growth is taking place to the left of the trunk
			if (Math.abs(ang)>90 && Math.abs(ang)<270) {
				this.x--;
			}else{
				this.x++;
			}
			this.y=Math.round((float)(slope*(this.x-a)+b));
			// y1=slope*(x1-x0)+y0;
			// (y1-y0)/(x1-x0)=slope;
			
			if(BGGenerationSet.rule!=5) {
				sum=Math.pow((this.x-a), 2);
				sum+=Math.pow((this.y-b), 2);
				this.length+=Math.sqrt(sum);
			}else {
				this.length+=2;
			}
		}
	}
	
	private void aging(){
		this.age++;
	}
	
	public void create(int n) {
		c=new Stem[n];
	}
	
 	public void adding(int x,int y,double direction,int split, BGRule bgr){
 		create(split);
 		double angle[]=bgr.angle(split);
 		
 		for(int i=0;i<angle.length;i++) {
 			this.c[i]=new Stem(x,y,(angle[i]+direction));
 		}
 	}
}
