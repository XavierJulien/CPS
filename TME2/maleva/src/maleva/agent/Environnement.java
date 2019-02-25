
package maleva.agent;

import java.util.ArrayList;
import java.util.HashMap;

import maleva.framework.BasicComponent;

public class Environnement extends BasicComponent 
implements /* provides */
EnvironmentFetchService,
EnvironmentRegisterService,
EnvironmentEffectService {
	
	/* fields */
	protected HashMap<String,AgentSight> agents;
	
	/* properties */
	private int width;
	private int height;
	
	public Environnement(int width, int height) {
		agents = new HashMap<String,AgentSight>();
		this.width = width;
		this.height = height;
	}
	
	public Environnement() {
		this(65536,65536);
	}
	
	public int getEnvWidth() {
		return width;
	}
	
	public int getEnvHeight() {
		return height;
	}
	
	public void registerAgent(String id, int posx, int posy, int type) {
		agents.put(id, new AgentSight(posx,posy,type));
	}
	
	public void unregisterAgent(String id) {
		agents.remove(id);
	}
	
	public void moveAgent(String id, int posx, int posy, int type) {
		agents.put(id, new AgentSight(posx,posy,type));
	}
	
	protected static int computeDist(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	/* EnvironmentFetchService functionalities */
	
	protected AgentRef[] fetchAgentsByType(int type,String from_id, int fromx, int fromy, int vangle, int fov, int vdist) {
		ArrayList<AgentRef> vagents = new ArrayList<AgentRef>();
		for(String key : agents.keySet()) {
			if(key.equals(from_id)) continue;  // Don't fetch 'myself'.
			AgentSight sight = agents.get(key);
                        if(type!=Agent.TYPE_AGENT && type!=sight.getType()) continue;  // Not the right agent type!
			// Compute the distance.
                        int dist = computeDist(sight.getX(),sight.getY(),fromx,fromy);
			if(dist > vdist) continue;  // Too far!

                        // Compute the angle of view.
                        int viewAngle = 0;
                        if (sight.getX() == fromx) {  // Corner case, in which the formula based on Math.atan doesn't work.
                                viewAngle = sight.getY() > fromy ? 90 : -90;
                        } else {
                                viewAngle = (int)((180.0/Math.PI) * Math.atan((double)(sight.getY() - fromy) / (sight.getX() - fromx)));
                                // Atan only gives value in ]-90, 90[, we have to maybe add 180°.
                                if (sight.getX() < fromx) viewAngle += 180;
                        }
                        // Compute the angle difference with the reference vision angle,
                        // And normalize this difference in [-180, 180[.
                        double angleDelta = viewAngle - vangle;
                        angleDelta = -180 + (angleDelta + 180) % 360;
                        if (Math.abs(angleDelta) > fov) continue;  // Outside field of vision.

			vagents.add(new AgentRef(key,sight.getX(),sight.getY(),viewAngle,dist));
		}
		
		if(vagents.size()==0) return null;
		
		AgentRef[] arefs = new AgentRef[vagents.size()];
		for(int i=0;i<vagents.size();i++) arefs[i] = vagents.get(i);
		return arefs;
	}
	
	public AgentRef[] fetchAgents(String from_id, int fromx, int fromy, int angle, int fov, int vdist) {
		return fetchAgentsByType(Agent.TYPE_AGENT,from_id,fromx,fromy,angle,fov,vdist);
	}
	
	public AgentRef[] fetchProies(String from_id, int fromx, int fromy, int angle, int fov, int vdist) {
		return fetchAgentsByType(Agent.TYPE_PROIE,from_id,fromx,fromy,angle,fov,vdist);
	}
	
	public AgentRef[] fetchPreds(String from_id, int fromx, int fromy, int angle, int fov, int vdist) {
		return fetchAgentsByType(Agent.TYPE_PRED,from_id,fromx,fromy,angle,fov,vdist);
	}
	
	protected class AgentSight {
		private int x, y, type;
		
		public AgentSight(int x, int y, int type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getType() {
			return type;
		}
		
	}
}
