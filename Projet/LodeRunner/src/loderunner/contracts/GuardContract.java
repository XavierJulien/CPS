package loderunner.contracts;

import java.util.ArrayList;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImplClone;
import loderunner.impl.GuardImpl;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class GuardContract extends CharacterContract implements GuardService {

	private final GuardService delegate;
	
	public GuardContract(GuardService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	public void checkInvariants() {
		super.checkInvariants();
		
		/** le garde est sur une échelle, 
		 * 	s'il y a un support dans la case en dessous, 
		 * 		alors la distance entre garde et target est plus courte en axe y que en axe x
		 * 	le garde est en DESSOUS du target 
		 * 
		 * inv : getEnvi().getCellNature(getWdt(),getHgt()) == LAD
		 * 			&& getHgt() < getTarget().getHgt()
		 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
		 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
		 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
		 * 			implies getBehaviour() == UP
		 *  
		 l'équivalent du implies intérieur
		 *(getEnvi().getCellNature(getWdt(),getHgt()-1) \not \in {PLT,MTL}
		 * 	and getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() == null
		 * 				or Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
		 * A VERIFIER CA A L'AIR CHELOU
		 **/
		/*if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD) {
			if (getHgt() < getTarget().getHgt()) {
				if ((getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && 
					 getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
					 getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) 
					|| 
					(Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt()))) {
					if (getBehaviour() != Command.UP)
						throw new InvariantError("Le behaviour ne renvoie pas UP alors qu'il devrait");
				}
			}
		}*/
		
		/** le garde est sur une échelle, 
		 * 	s'il y a un support dans la case en dessous, 
		 * 		alors la distance entre garde et target est plus courte en axe y que en axe x
		 * 	le garde est au DESSUS du target 
		 * 
		 * inv : getEnvi().getCellNature(getWdt(),getHgt()) == LAD
		 * 			&& getHgt() > getTarget().getHgt()
		 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
		 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
		 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
		 * 			implies getBehaviour() == DOWN 
		 **/
		/*if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD) {
			System.out.println("in ladder");
			System.out.println(getWdt());
			System.out.println(getHgt());
			if (getHgt() > getTarget().getHgt()) {
				System.out.println("hauteur plus grande");
				if ((getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && 
					 getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
					 getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) 
					|| 
					(Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt()))) {
					System.out.println(getEnvi().getCellNature(getWdt(), getHgt()-1));//souci car on as ici une PLT
					if (getBehaviour() != Command.DOWN)
						throw new InvariantError("Le behaviour ne renvoie pas DOWN alors qu'il devrait");
				}
			}
		}*/
		
		/**
		 * le garde est sur une échelle, 
		 * s'il y a un support dans la case en dessous, 
		 * 		alors la distance entre garde et target est plus courte en axe x que en axe y
		 * le garde est a DROITE du target 
		 * OU
		 * si le garde est dans un trou, sur un rail ou debout sur une case de support 
		 * le garde est a DROITe du target
		 * 
		 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
		 * 			&& getTarget().getWdt() < getWdt()
		 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
		 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
		 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
		 * 		||
		 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
		 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
		 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null)
		 * 			&& getTarget().getWdt() < getWdt())
		 * 		implies getBehaviour() == LEFT
		 * 
		 **/
		/*if ((getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD
				&& (getTarget().getWdt() < getWdt() )
					&& ((getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL
						 && getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) 
						|| Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())
						)
				)
			||
			((getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL || getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HDR
				|| getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.MTL || getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.PLT
				|| getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() != null)
			&& getTarget().getWdt() < getWdt())
			) {
				if (getBehaviour() != Command.LEFT)
					throw new InvariantError("Le behaviour ne renvoie pas LEFT alors qu'il devrait");
			}*/
		/**
		 * 
		 * le garde est sur une échelle, 
		 * s'il y a un support dans la case en dessous, 
		 * 		alors la distance entre garde et target est plus courte en axe x que en axe y
		 * le garde est a GAUCHE du target 
		 * OU
		 * si le garde est dans un trou, sur un rail ou debout sur une case de support 
		 * le garde est a GAUCHE du target
		 * 
		 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
		 * 			&& getTarget().getWdt() > getWdt()
		 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
		 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
		 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
		 * 		||
		 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
		 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
		 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
		 * 			&& getTarget().getWdt() > getWdt())
		 * 		implies getBehaviour() == RIGHT
		 * 
		 **/
		/*if ((getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD
				&& (getTarget().getWdt() > getWdt())
					&& ((getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL
						 && getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) 
						|| Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())
						)
				)
			||
			((getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL || getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HDR
				|| getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.MTL || getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.PLT
				|| getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() != null)
			&& getTarget().getWdt() > getWdt())
			) {
				if (getBehaviour() != Command.RIGHT)
					throw new InvariantError("Le behaviour ne renvoie pas RIGHT alors qu'il devrait");
			}*/
		/**
		 * inv :( (getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
		 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
		 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
		 * 		&& getTarget().getWdt() == getWdt())
		 * 		||
		 * 		(getEnvi().getCellNature(getWdt(),getHgt()) == LAD 
		 * 			&& getTarget().getHgt() == getHgt())
		 * 		
		 * 		implies getBehaviour() == NEUTRAL
		**/
		/*if (((getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL || getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HDR
				|| getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.MTL || getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.PLT
				|| getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() != null)
			& getTarget().getWdt() == getWdt())
			|| 
			(getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD 
				&&  getTarget().getHgt() == getHgt())
			) {
			if(getBehaviour() != Command.NEUTRAL) {
				throw new InvariantError("Le bahaviour ne renvoie pas NEUTRAL alors qu'il devrait");
			}
		}*/
		/**
		 * ajout de celle du character
		 */
		if(getEnvi().getCellContent(getWdt(), getHgt()).getGuard() != null) {
			if(!getEnvi().getCellContent(getWdt(), getHgt()).getGuard().equals(this))
			throw new InvariantError("le joueur dans la case de notre joueur n'est pas lui-même");
		}
	}
	
	@Override
	public EngineService getEngine() {
		//1.pre
		//none
		//2.run
		return delegate.getEngine();
	}

	@Override
	public int getId() {
		return delegate.getId();
	}

	@Override
	public PlayerService getTarget() {
		return delegate.getTarget();
	}

	@Override
	public Command getBehaviour() {
		Command behaviour =  delegate.getBehaviour();
		System.out.println("guard : "+this+", action "+behaviour);
		return behaviour;
	}

	@Override
	public int getTimeInHole() {
		return delegate.getTimeInHole();
	}
	
	@Override
	public void init(EngineService e, int x, int y, PlayerService target) {
		//pre 
		if(e.getEnvi().getCellNature(x, y) != Cell.EMP) {
			throw new PreconditionError("init de guard : la case ou on veut init le player n'est pas Cell.EMP");
		}
		if(e.getEnvi().getCellContent(x, y).getCharacter() != null)
			throw new PreconditionError("init de guard : le case contient déjà qqn"); 
		//inv
		//appel
		delegate.init(e, x, y, target);
		//inv
		//checkInvariants();
		//post
		if (!getTarget().equals(target))
			throw new PostconditionError("init guard : target");
		if (getTimeInHole()!=0)
			throw new PostconditionError("init guard : timeinhole");
	}

	@Override
	public void climbLeft() {
		//pre
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL)
			throw new PreconditionError("climbleft : n'est pas dans un trou");
		//inv
		checkInvariants();
		//captures
		int wdt_atpre = getWdt();
		int hgt_atpre = getHgt();
		//appel
		delegate.climbLeft();
		//inv
		checkInvariants();
		//post
		if (getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.PLT 
			&& getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.MTL) {
			if (getEnvi().getCellNature(wdt_atpre-1, hgt_atpre+1) != Cell.PLT 
				&& getEnvi().getCellNature(wdt_atpre-1, hgt_atpre+1) != Cell.MTL) {
				if (getEnvi().getCellContent(wdt_atpre-1, hgt_atpre+1).getGuard() == null) {
					if (getWdt() != wdt_atpre-1 || getHgt() != hgt_atpre+1)
						throw new PostconditionError("climbLeft : le guard n'a pas correctement grimpé à gauche");
				}
			}
		}
	}

	@Override
	public void climbRight() {
		//pre
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL)
			throw new PreconditionError("climbright : n'est pas dans un trou");
		//inv
		checkInvariants();
		//captures
		int wdt_atpre = getWdt();
		int hgt_atpre = getHgt();
		//appel
		delegate.climbRight();
		//inv
		checkInvariants();
		//post
		if (getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.PLT 
			&& getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.MTL) {
			if (getEnvi().getCellNature(wdt_atpre+1, hgt_atpre+1) != Cell.PLT 
				&& getEnvi().getCellNature(wdt_atpre+1, hgt_atpre+1) != Cell.MTL) {
				if (getEnvi().getCellContent(wdt_atpre+1, hgt_atpre+1).getGuard() == null) {
					if (getWdt() != wdt_atpre+1 || getHgt() != hgt_atpre+1)
						throw new PostconditionError("climbRight : le guard n'a pas correctement grimpé à droite");
				}
			}
		}

	}


	@Override
	public void step() {
		//pre : rien
		//inv
		checkInvariants();
		//captures
		EngineService engine_atpre = new EngineImplClone();
		ArrayList<Coord> guards_atpre_without_self = new ArrayList<>();
		for(int i = 0;i<getEngine().getGuards().size();i++) {
			if(getEngine().getGuards().get(i).getId() != getId()) {
				guards_atpre_without_self.add(new Coord(getEngine().getGuards().get(i).getWdt(),getEngine().getGuards().get(i).getHgt()));
			}
		}
		EditableScreenImpl edit = new EditableScreenImpl();
		edit.init(getEngine().getEnvi().getWidth(), getEngine().getEnvi().getHeight());
		for(int i  = 0;i<edit.getWidth();i++) {
			for(int j = 0;j<edit.getHeight();j++) {
				edit.setNature(i, j, getEngine().getEnvi().getCellNature(i, j));
			}
		}
		engine_atpre.init(edit, new Coord(getEngine().getPlayer().getWdt(),getEngine().getPlayer().getHgt()), guards_atpre_without_self, getEngine().getTreasures());
		PlayerService target_atpre = getTarget();
		GuardService guard_atpre = new GuardImpl(getId());
		for(int i = 0;i<engine_atpre.getGuards().size();i++) {
			if(engine_atpre.getGuards().get(i).getId() == getId()) {
				engine_atpre.getGuards().remove(i);
			}
		}
		guard_atpre.init(engine_atpre, getWdt(), getHgt(), target_atpre);
		int time_atpre = getTimeInHole();
		for(int i = 0;i<time_atpre;i++) {
			guard_atpre.waitInHole();
		}
		//appel
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(null);
		delegate.step();
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(this);
		System.out.println("apres step "+getEngine().getEnvi().getCellContent(getWdt(), getHgt()).getGuard());

		//inv
		checkInvariants();
		
		//post
		if (guard_atpre.willFall()) {
			guard_atpre.goDown();
			if (getWdt()!=guard_atpre.getWdt() || getHgt()!=guard_atpre.getHgt())
				throw new PostconditionError("step de Guard : le garde devait tombé en chute libre");
			if (getTimeInHole() != time_atpre)
				throw new PostconditionError("step de Guard : willFall pb timeInHole");
		}
		
		if (guard_atpre.willClimbLeft()) {
			guard_atpre.climbLeft();
			if (getWdt()!=guard_atpre.getWdt() || getHgt()!=guard_atpre.getHgt())
				throw new PostconditionError("step de Guard : pb position joueur apres climbleft");
			if (getTimeInHole()!= guard_atpre.getTimeInHole())
				throw new PostconditionError("step de Guard : willclimbleft pb timeInHole");
		}
		if (guard_atpre.willClimbRight()) {
			System.out.println("avant climbright clone :"+guard_atpre.getWdt()+","+guard_atpre.getHgt());
			guard_atpre.climbRight();
			System.out.println("apres climbright clone :"+guard_atpre.getWdt()+","+guard_atpre.getHgt());
			if (getWdt()!=guard_atpre.getWdt() || getHgt()!=guard_atpre.getHgt())
				throw new PostconditionError("step de Guard : pb position joueur apres climbright");
			if (getTimeInHole()!= guard_atpre.getTimeInHole())
				throw new PostconditionError("step de Guard : willclimbright pb timeInHole");
		}
		if (guard_atpre.willClimbNeutral()) {
			if (getWdt()!=guard_atpre.getWdt() || getHgt()!=guard_atpre.getHgt())
				throw new PostconditionError("step de Guard : pb position joueur climbneutral, il ne doit rien se passer");
			if (getTimeInHole()!= guard_atpre.getTimeInHole())
				throw new PostconditionError("step de Guard : willclimbneutral pb timeInHole");
		}
		if (guard_atpre.willWaitInHole()) {
			guard_atpre.waitInHole();
			if (getWdt()!=guard_atpre.getWdt() || getHgt()!=guard_atpre.getHgt())
				throw new PostconditionError("step de Guard : le garde devait rester sur place dans le trou");
			if (guard_atpre.getTimeInHole() != time_atpre+timeEpsilon) {
				throw new PostconditionError("step de Guard : willWaitInHole pb incr timeInHole");				
			}
		}
	}

	@Override
	public boolean hasItem() {
		return delegate.hasItem();
	}

	@Override
	public void setTreasure(Item treasure) {
		delegate.setTreasure(treasure);
	}

	@Override
	public void waitInHole() {
		//1.pre
		if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL) throw new PreconditionError("waitInHole : ne peut pas faire cela dans une case différent d'un HOL");
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int time_capture = getTimeInHole();
		//4.run
		delegate.waitInHole();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(time_capture+timeEpsilon != getTimeInHole()) {
			throw new PostconditionError("waitInHole : le personnage n'as pas attendu un step de plus");
		}
	}

}