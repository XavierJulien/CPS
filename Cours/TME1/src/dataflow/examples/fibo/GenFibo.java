package dataflow.examples.fibo;

import java.math.BigInteger;

import dataflow.core.Composite;
import dataflow.core.IntEvent;
import dataflow.core.IntEventReceiverService;
import dataflow.core.Relay;
import dataflow.core.RequireIntEventReceiverService;
import dataflow.operators.Add;

/*
 * 
 * Exercice 2 -- Question 4
 * 
 * A compléter  
 * 
 */


public class GenFibo implements Composite,
			/* require */
			RequireIntEventReceiverService {

	private Relay r1,r2;
	private Add add;
	
	public GenFibo() {
		this.r1 = new Relay();
		this.r2 = new Relay();
		this.add = new Add();
		add.bindIntEventReceiverService(r1);
		r2.bindIntEventReceiverService(add);
		r1.bindIntEventReceiverService(r2);
		r1.bindIntEventReceiverService(add);
		r1.onIntEvent(new IntEvent(new BigInteger("1")));
		r2.onIntEvent(new IntEvent(new BigInteger("0")));
	}	
	
	@Override
	public void bindIntEventReceiverService(IntEventReceiverService serv) {
		add.bindIntEventReceiverService(serv);
	}
		
	@Override
	public void activate() {
		r2.activate();
		r1.activate();
		add.activate();
	}

}
