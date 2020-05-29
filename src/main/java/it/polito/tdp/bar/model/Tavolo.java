package it.polito.tdp.bar.model;

public class Tavolo implements Comparable<Tavolo>{
	
	private int id;
	private int numeroPosti;
	private boolean isDisponibile;
	/**
	 * @param id
	 * @param numeroPosti
	 */
	public Tavolo(int id, int numeroPosti) {
		super();
		this.id = id;
		this.numeroPosti = numeroPosti;
		this.isDisponibile= true;
	}
	@Override
	public int compareTo(Tavolo o) {
		return this.numeroPosti - o.getNumeroPosti();
	}
	
	public int getNumeroPosti() {
		
		return this.numeroPosti;
	}
	public boolean isDisponibile() {
		// TODO Auto-generated method stub
		return isDisponibile;
	}
	public void setDisponibile(boolean isDisponibile) {
		this.isDisponibile = isDisponibile;
	}
	public int getId() {
		return id;
	}
	
	

}
