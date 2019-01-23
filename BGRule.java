package Plant;

public class BGRule {
	private int splits[]= {5,3,2};
	private int indexcount=3;
	private int rule;
	private double angle;
	
	BGRule(int rule){
		this.rule=rule;
	}

	public int choose() {
		return option(rule);
	}
	
	/*
	 * sets the angle between extreme stems;
	 * */
	
	public int option(int choice){
		if(choice==1) {
			angle=90;
			return splits[2];
		}
		if(choice==2) {
			angle=90;
			return splits[1];
		}
		if(choice==3) {
// if choice is 3 then the rule is rule3, followed by rule2,
// followed by rule1 and then rule 1 stays
			return option3();
		}
		if(choice==4) {
			angle=180;
			return splits[1];
		}
		if(choice==5) {
			angle=240;
			return splits[1];
		}
		angle=120;
		return splits[1];
	}
	
	
	/*
	 * sets the angle between extreme stems a
	 * */
	
	public int option3() {
		if(indexcount<1) {
			return option(1);
		}else if (indexcount==3) {
			indexcount--;
			angle=180;
			return splits[0];
		}else {
			return option(indexcount--);
		}
	}
	
	/*
	 * if the a stem splits into 4 stems 45 degrees apart and the base stem happens to be
	 * growing upwards then the left stems grows in 135 and 180 degree direction,
	 * and the stems to the right will be growing to 45 and 0 degree direction.
	 * 
	 * 
	 * for example:  parent stem direction 90, split=5, then angle[]={180,135,90,45,0}
	 * parent stem direction 90, split=3, then angle[]={135,90,45}
	 * */
	
	public double[] angle(int split) {
		double[] anglelist = new double[split];
        int n=split-1;
        double splitangle=angle/n;
        int i;
        if(split%2==0){
        	i=n/2;
			anglelist[i]=splitangle/2;
			anglelist[i+1]=-anglelist[i];
        }else {
        	i=n/2;
        	anglelist[i]=0;
        }
    	i--;
    	int k;
    	for(;i>=0;i--) {
    		anglelist[i]=anglelist[i+1]+splitangle;
    		k=(n-i)%split;
    		anglelist[k]=anglelist[k-1]-splitangle;
    	}
    	
		return anglelist;
	}
	
	public void setRule(int rule) {
		this.rule=rule;
	}
	
}