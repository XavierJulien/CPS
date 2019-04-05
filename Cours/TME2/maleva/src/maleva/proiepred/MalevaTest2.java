package maleva.proiepred;

import java.util.Random;
import maleva.agent.Agent;
import maleva.agent.Environnement;
import maleva.view.EnvironmentView;

public class MalevaTest2 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final int numSteps = Integer.parseInt(args[0]);
		final int numMechants = Integer.parseInt(args[1]);
		final int numTrouillards = Integer.parseInt(args[2]);
        final String mechantNames[] = {"Serge", "Mariok", "Knievel", "Guy",
                                 "Demon", "PasCool", "Coyote"};
        final String trouillardNames[] = {"Peureux", "BipBip", "Scoobidoo",
                                    "Bart", "Omer", "Marge", "Lisa"};
        final int sizeX = 640;
        final int sizeY = 400;
        
		EnvironmentView env = new EnvironmentView(sizeX, sizeY);

        Random rand = new Random();

		Agent mechants[] = new Agent[numMechants];
        Agent trouillards[] = new Agent[numTrouillards];
        
        for (int i = 0; i < numMechants; i++) {
          mechants[i] = new Mechant("Evil" + mechantNames[i]);
        }
        for (int i = 0; i < numTrouillards; ++i) {
          trouillards[i] = new Trouillard(trouillardNames[i]);
        }

		try {
			for (Agent m : mechants) 
				m.initialize();
            for (Agent t : trouillards) 
            		t.initialize();
            
            for (Agent m : mechants) {
            		m.configure(env, rand.nextInt(sizeX), rand.nextInt(sizeY));
            		m.setSpeed(4);
            		m.setAngle(rand.nextInt(360));
            		
            	}
            for (Agent t : trouillards) {
            		t.configure(env, rand.nextInt(sizeX), rand.nextInt(sizeY));
            		t.setSpeed(3);  // Slower than the Mechant.
            		t.setAngle(rand.nextInt(360));
            	}

			for(int i = 0; i < numSteps; i++) {
				System.out.println("  =====       STEP: " + i + "       ======");
				for (Agent m : mechants) {
					m.step();
					env.moveAgent(m.getID(), m.getPosX(), m.getPosY(), m.getType());;
				}
				for (Agent t : trouillards) {
					t.step();
					env.moveAgent(t.getID(), t.getPosX(), t.getPosY(), t.getType());
				}
				
				Thread.sleep(50);
			}
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}

		env = null;
		
	}

}
