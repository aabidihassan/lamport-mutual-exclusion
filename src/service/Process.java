package service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import message.Message;
import message.TypeMessage;

@Data @NoArgsConstructor
public class Process extends Thread{
	
	private int Hi = 0;
	private int site;
	private Map<Process, TypeMessage> requests = new HashMap<Process, TypeMessage>();
	private Map<Process, Integer> F_Hi = new HashMap<Process, Integer>();
	private List<Process> voisins;
	public static Object obj1 = new Object();
	public static Object obj2 = new Object();
	public static Object obj3 = new Object();
	public boolean b = true;
	
	public void affectV(List<Process> voisins) {
		voisins.remove(this);
		this.voisins = voisins;
		this.voisins.forEach((v)->{
			this.F_Hi.put(v, 0);
			this.requests.put(v, TypeMessage.DEFAULT);
		});
	}
	
	@Override
	public void run() {
		lancer();
	}
	
	public void lancer() {
		try {
		while(b) {
			System.out.println("Process number : " + this.site + " veut entrer en SC");
			demandesc();
		}
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
	
	public void demandesc() throws InterruptedException {
		if(myTurn()) request();
	}
	
	public void request() throws InterruptedException {
		synchronized (obj2) {
			this.Hi++;
			this.voisins.forEach(v-> v.receive(new Message(this, this.Hi, TypeMessage.REQ)));
			//this.requests.put(this, TypeMessage.REQ);
			System.out.println("Process number " + this.site + " envoie requettes aux autres");
			//if(myTurn()) 
			sc();
		}
	}
	
	private void sc() throws InterruptedException {
		System.out.println("Process number " + this.site + " entre en SC");
		Ressource r = Ressource.getInctance();
		sleep(2000);
		relache();
	}
	
	public void receive(Message message) {
		this.Hi = Math.max(this.Hi, message.getH())+1;
		this.F_Hi.put(message.getSender(), message.getH());
		this.requests.put(message.getSender(), message.getType());
		if(message.getType().equals(TypeMessage.REQ)) {
			message.getSender().receive(new Message(this, this.Hi, TypeMessage.ACK));
		}
	}
	
	public void relache() {
		synchronized (obj2) {
			this.Hi++;
			this.voisins.forEach(v->v.receive(new Message(this, this.Hi, TypeMessage.REL)));
			//this.requests.put(this, TypeMessage.REL);
			System.out.println("Process number " + this.site + " sorte de SC avec l'horloge : " + this.Hi);
			this.b = false;
		}
	}
	
	private boolean myTurn() {
		boolean s = true;
		synchronized (obj1) {
			for (Map.Entry<Process, Integer> entry : this.F_Hi.entrySet()) {
	        if(entry.getValue()<=this.Hi)
	        	if(entry.getValue()==this.Hi && entry.getKey().getSite()>this.site) s = true;
	        	else s = false;
		    }
			return s;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		return Objects.equals(F_Hi, other.F_Hi) && Hi == other.Hi && Objects.equals(requests, other.requests)
				&& site == other.site && Objects.equals(voisins, other.voisins);
	}

	@Override
	public int hashCode() {
		return Objects.hash(F_Hi, Hi, requests, site, voisins);
	}
	
	


}
