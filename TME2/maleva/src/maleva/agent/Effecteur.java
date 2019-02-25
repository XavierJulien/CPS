
package maleva.agent;

import java.util.ArrayList;

import maleva.framework.BasicComponent;
import maleva.framework.LifeCycleException;
import maleva.framework.ServiceBindException;

public class Effecteur extends BasicComponent 
implements /* requires */
RequireActionService,	       
RequireEnvironmentEffectService,
/* controllers */
AgentPartController,
LCStepperController {
	
	/* fields */
	protected Agent outer;
	
	/* required services */
	private ArrayList<ActionService> as;
	private EnvironmentEffectService ees;
	
	public Effecteur(Agent outer) {
		this.outer = outer;
		as = new ArrayList<ActionService>();
		ees = null;
	}
	
	public void bindActionService(ActionService as) throws ServiceBindException {
		if(!this.as.contains(as)) {
			this.as.add(as);
		}
	}
	
	public void bindEnvironmentEffectService(EnvironmentEffectService ees) throws ServiceBindException {
		this.ees = ees;
	}
	
	public boolean isBound() {
		return as!=null && ees!=null;
	}
	
	/* Stepper controller */
	
	public static int computeDeltaX(int x, int speed, int angle) {
		double rad = Math.PI/180.0 * angle;
		int nx = (int) (Math.cos(rad)*speed) + x;
		return nx;
	}
	
	public static int computeDeltaY(int y, int speed, int angle) {
		double rad = Math.PI/180.0 * angle;
		int ny = (int) (Math.sin(rad)*speed) + y;
		return ny;
	}
	
	public boolean step() throws LifeCycleException {
		if(!isBound())
			throw new LifeCycleException("Effecteur component not correctly bound");
		
		Action act = null;
		for(ActionService a : as) {
			act = a.getAction();
			if(act!=null)
				break;
		}
		String debugStr = "[Effecteur] Received action = " + act;
		if(act==null || !act.isMove()) {
			System.out.println(debugStr);
                        return false;
                }

                debugStr += " X=" + outer.getPosX();
		debugStr += "Y="+outer.getPosY();
		debugStr += "Speed= "+outer.getSpeed();
		debugStr += "Angle= "+outer.getAngle();
                outer.setAngle(act.getAngle());
		int nx = computeDeltaX(outer.getPosX(),outer.getSpeed(),act.getAngle());
		if(nx < 0) {
			nx = ees.getEnvWidth();
		} else if(nx > ees.getEnvWidth()) {
			nx = 0;
		}
		
		int ny = computeDeltaY(outer.getPosY(),outer.getSpeed(),act.getAngle());
		if(ny < 0) {
			ny = ees.getEnvHeight();
		} else if(ny > ees.getEnvHeight()) {
			ny = 0;
		}
		
		ees.moveAgent(outer.getID(),nx,ny,outer.getType());
		outer.setPosX(nx);
		outer.setPosY(ny);
                       debugStr += "; After: X'="+outer.getPosX();
		debugStr += "Y'="+outer.getPosY();
		debugStr += "Angle'="+outer.getAngle();
                System.out.println(debugStr);
		return true;
	}
}
