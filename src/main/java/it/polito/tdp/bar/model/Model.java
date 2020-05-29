package it.polito.tdp.bar.model;

public class Model {
	Simulator sim;
	public void simula(double tolleranza) {
		sim = new Simulator(tolleranza);
	}
	
	
	public String getResults() {
		if(sim==null) {
			
			throw new RuntimeException("DEVI PRIMA SIMULARE!");
		}
		return sim.getStatistiche().toString();
	}
	
	public int getNumeroEventi() {
		
		return sim.getNumEventi();
	}
}
