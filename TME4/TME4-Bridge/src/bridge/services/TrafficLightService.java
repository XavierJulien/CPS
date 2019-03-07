package bridge.services;

public interface TrafficLightService extends /* refine */ LightService {
	/** observateur: le feu est vert ? 
	 * pre: isOn() 
	 */
	public boolean isGreen();
	/** observateur: le feu est rouge ?
	 * pre: isOn()
	 */
	public boolean isRed();
	
	// inv: isGreen() xor isRed()
	
	public void init();
	
	/** passer au vert.
	 * pre: isRed()
<<<<<<< HEAD
	 * pre : isOn()
=======
>>>>>>> fc14157098d62069b3d239fe6d8f50a96a97fbbc
	 * post: isRed()==false
	 * post: isGreen()==true
	 */
	public void changeGreen();
	
	/** passer au rouge.
	 * pre: isGreen()
<<<<<<< HEAD
	 * pre: isOn()
=======
>>>>>>> fc14157098d62069b3d239fe6d8f50a96a97fbbc
	 * post: isGreen()==false
	 * post: isRed()==true
	 */	
	public void changeRed();
}
