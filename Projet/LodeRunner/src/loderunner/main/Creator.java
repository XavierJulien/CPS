package loderunner.main;

import loderunner.contracts.CharacterContract;
import loderunner.contracts.CharacterContractClone;
import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.contracts.EngineContractClone;
import loderunner.contracts.EnvironnementContract;
import loderunner.contracts.GuardContract;
import loderunner.contracts.PlayerContract;
import loderunner.contracts.PlayerContractClone;
import loderunner.contracts.ScreenContract;
import loderunner.services.CharacterService;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;
import loderunner.services.ScreenService;

public class Creator {
	
	public static ScreenContract createScreenContract(ScreenService s) {
		return new ScreenContract(s);
	}
	public static EditableScreenContract createEditableScreenContract(EditableScreenService s) {
		return new EditableScreenContract(s);
	}
	public static EnvironnementContract createEnvironnementContract(EnvironnementService s) {
		return new EnvironnementContract(s);
	}
	public static CharacterContract createCharacterContract(CharacterService s) {
		return new CharacterContract(s);
	}
	public static CharacterContractClone createCharacterContractClone(CharacterService s) {
		return new CharacterContractClone(s);
	}
	public static PlayerContractClone createPlayerContractClone(PlayerService s) {
		return new PlayerContractClone(s);
	}
	public static PlayerContract createPlayerContract(PlayerService s) {
		return new PlayerContract(s);
	}
	public static GuardContract createGuardContract(GuardService s) {
		return new GuardContract(s);
	}
	public static EngineContract createEngineContract(EngineService s) {
		return new EngineContract(s);
	}
	public static EngineContractClone createEngineContract2(EngineService s) {
		return new EngineContractClone(s);
	}
	
}
