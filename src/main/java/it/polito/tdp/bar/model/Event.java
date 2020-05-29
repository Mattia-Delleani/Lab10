package it.polito.tdp.bar.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI, ABBANDONO_GRUPPO_CLIENTI
	}

	private LocalDateTime time;
	private EventType type;
	private int numeroPersone;
	private int idTavolo;
	/**
	 * @param oraArrivoGruppo
	 * @param type
	 */
	public Event(LocalDateTime oraArrivoGruppo, EventType type, int numeroPersone, int idTavolo) {
		super();
		this.time = oraArrivoGruppo;
		this.type = type;
		this.numeroPersone = numeroPersone;
		this.idTavolo = idTavolo;
	}
	
	
	public LocalDateTime getTime() {
		return time;
	}
	public EventType getType() {
		return type;
	}
	


	public int getNumeroPersone() {
		return numeroPersone;
	}
	
	

	public int getIdTavolo() {
		return idTavolo;
	}


	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
	public String toString() {
		
		return "Ora:"+ this.time.getHour() +":"+this.time.getMinute()+" - Type: "+this.type +" - idTav: "+this.idTavolo+", numPersone: "+ this.numeroPersone;
	}
	
	
}
