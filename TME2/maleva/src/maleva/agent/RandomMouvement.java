
package maleva.agent;

import java.util.Random;

import maleva.framework.LifeCycleException;

public class RandomMouvement extends ComportementAgent {
	
	/* fields */
	private Random rand;
	
	/* properties */
	private int angle_step = 20;
	
	public RandomMouvement(Agent outer) {
		super(outer);
		rand = new Random();
	}
	
	/* LCStepperController */
	
	public boolean step() throws LifeCycleException {
		int hasard = rand.nextInt(angle_step + angle_step + 1) - angle_step;
		int newAngle = outer.getAngle() + hasard;
		
		action = new Action(Action.MOVE,newAngle);
		return true; // step is finished
/*
 * Pour cela, la méthode step() doit e􏰋ffectuer un déplacement élémentaire
 *  selon un angle calculé à partir de l'angle courant 
 *  (outer.getAngle()) auquel on ajoute ou on retranche une valeur prise 􏰇au hasard􏰈 entre -angle_step et +angle_step, 
 * la variable angle_step étant un paramètre du comportement.
 * 
 * */
	}
}
