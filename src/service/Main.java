package service;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		Process p1 = new Process();
		Process p2 = new Process();
		Process p3 = new Process();
		
		p1.setSite(1); p2.setSite(2); p3.setSite(3);
		
		List<Process> proccess = new ArrayList<Process>();
		proccess.add(p1); proccess.add(p2); proccess.add(p3);
		
		p1.affectV(proccess); p2.affectV(proccess); p3.affectV(proccess);
		
		p1.start(); p3.start(); Thread.sleep(1000); p2.start();
		
//		p1.lancer(); p2.lancer();
//		//Thread.sleep(2000);
//		p1.setB(true); p1.lancer(); p3.lancer();
//		//Thread.sleep(1000);
//		p2.setB(true); p1.setB(true); p2.lancer(); p1.lancer();
	}

}
