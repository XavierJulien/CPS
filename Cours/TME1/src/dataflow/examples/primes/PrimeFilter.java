package dataflow.examples.primes;

import java.math.BigInteger;

import dataflow.core.IntEvent;
import dataflow.core.IntEventReceiverService;
import dataflow.generators.SimpleGenerator;

/*
 * 
 * Exercice 3 -- Question 5
 * 
 * A compléter
 * 
 */

public class PrimeFilter extends SimpleGenerator 
		implements /* provide */
			       IntEventReceiverService {

	private PrimeFilter next;//null
	private BigInteger value;//2
	private BigInteger receive;//2
	//private Printer print;
	
	public PrimeFilter() {
		next = null;
		value = null;
		receive = null;
		//print = new Printer();
	}
	
	@Override
	public void onIntEvent(IntEvent event) {
		this.receive = event.getValue();
	}

	@Override
	public void activate() {
		if(value == null) {
			this.value = receive;
			next = new PrimeFilter();
			this.send(new IntEvent(value));
			next.bindIntEventReceiverService(this.receivers.get(0));
			this.receivers.clear();
			this.bindIntEventReceiverService(next);
		}else {
			if(receive.intValue()%value.intValue() != 0) {
				next.onIntEvent(new IntEvent(receive));
				next.activate();
				
			}
		}
	}
}
