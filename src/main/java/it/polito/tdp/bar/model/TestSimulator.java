package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {


		Simulator sim = new Simulator(0.8);
		Statistiche stats = sim.getStatistiche();
		
		System.out.println(stats.toString());		
		

	}

}
