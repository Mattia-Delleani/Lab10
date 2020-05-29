package it.polito.tdp.bar.model;

public class Statistiche {
	
	private int totali;
	private int soddisfatti;
	private int insoddisfatti;
	/**
	 * 
	 */
	public Statistiche() {
		this.totali = 0;
		this.insoddisfatti = 0;
		this.soddisfatti = 0;
	}
	
	public void addTotali(int num) {
		this.totali+= num;
	}
	
	public void addInsofddisfatti(int num) {
		this.insoddisfatti+= num;
	}
	public void addSoddisfatti(int num) {
		this.soddisfatti+= num;
	}

	public int getTotali() {
		return totali;
	}

	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	public String toString() {
		
		return "Clienti totali: "+ this.totali +"\nSoddisfatti: "+this.soddisfatti +"\nInsoddisfatti: "+ this.insoddisfatti;
	}
	
	

}
