package service;

public class Ressource {
	
	private static Ressource ressource;
	
	private Ressource() {}
	
	public static Ressource getInctance() {
		if(ressource==null) ressource = new Ressource();
		return ressource;
	}

}
