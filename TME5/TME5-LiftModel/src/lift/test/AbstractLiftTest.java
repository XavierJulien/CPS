package lift.test;

import lift.services.CommandsService;
import lift.services.LiftService;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractLiftTest {
	private LiftService lift;
	private CommandsService commands;
	
	protected AbstractLiftTest() {
		lift = null;
		commands = null;
	}
	
	protected final LiftService getLift() {
		return lift;
	}
	
	protected final CommandsService getCommands() {
		return commands;
	}
	
	protected final void setLift(LiftService lift) {
		this.lift = lift;
	}
	
	protected final void setCommands(CommandsService commands) {
		this.commands = commands;
	}
	
	@Before
	public abstract void beforeTests(); 

	@After
	public final void afterTests() {
		lift = null;
		commands = null;
	}
	
}
