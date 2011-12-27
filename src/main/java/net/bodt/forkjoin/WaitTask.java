package net.bodt.forkjoin;

import java.util.concurrent.RecursiveAction;

public class WaitTask extends RecursiveAction {


	protected void compute() {
		long startDate = System.nanoTime();
		System.out.println("compute wait");
		while(true){
			if(System.nanoTime()-startDate>10000000000L)
				break;
		}
		

	}

}
