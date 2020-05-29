package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	//CODA DEGLI EVENTI
		PriorityQueue<Event> queue;
		
		//PARAMETRI SIMULAZIONE DI INGRESSO
		private int numEventi;
		private Duration t_in;
		private Map<Integer, Tavolo> tavoli;
		
		private final LocalDateTime oraApertura;
		private double tolleranza;
		//MODELLO NEL MONDO
		
		
		//VARIABILI DA CALCOLARE
		private Statistiche statistiche;
	
	public Simulator(double tolleranza) {
		queue = new PriorityQueue<Event>();
		numEventi  = 2000;
		oraApertura  = LocalDateTime.of(2020, 05, 15, 6, 00);
		this.tolleranza = tolleranza;
		this.run();
	}
	
	
	//SIMULAZIONE
	
	public void run() {
		//inizializzo le variabili;
		this.tavoli =  this.generaTavoli();
		this.statistiche = new Statistiche();
		LocalDateTime oraArrivoGruppo = this.oraApertura;
		//pulisco la coda
		this.queue.clear();
		
		//CARICO LA CODA
		int contatore = 0;
		do {
			Random r = new Random();
			int min = 1;
			int max = 10;
			int result = r.nextInt(max -min+1) +min; //Da il casuale tra min e massimo inclusi, se non metto il + 1 mi include solo il minimo
			//aggiungo il tempo di arrivo
			t_in = Duration.of(result, ChronoUnit.MINUTES);
			
			//genero il numero di persone del gruppo
			Random rPersone = new Random();
			int minPersone = 1;
			int maxPersone = 10;
			int nPersone = rPersone.nextInt(maxPersone -minPersone +1) +min;
			
			oraArrivoGruppo=oraArrivoGruppo.plus(t_in);
			queue.add(new Event(oraArrivoGruppo, EventType.ARRIVO_GRUPPO_CLIENTI, nPersone, 0));
			contatore++;
		}while(contatore< this.numEventi);
		
		
		
		//ESEGUO LA CODA; QUINDI LA SIMULAZIONE
		while(!queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
			
		}
	}

	private void processEvent(Event e) {

		switch(e.getType()){
			
		case ARRIVO_GRUPPO_CLIENTI:
			int idTavolo = 0;
			boolean assegnato = false;
			for(Tavolo t: tavoli.values()) {
				if(t.isDisponibile() && t.getNumeroPosti()>=e.getNumeroPersone() && e.getNumeroPersone()>= t.getNumeroPosti()/2) {
					t.setDisponibile(false);
					idTavolo = t.getId();
					assegnato = true;
					break;
					
				}
				
			}
			Random randomTolleranza = new Random();
			
			
			float tolleranzaGruppo = (float) ((double)randomTolleranza.nextInt(91)/100);
			//System.out.println("Tolleranza gruppo: "+tolleranzaGruppo);
			//System.out.println("Tolleranza: "+tolleranza);

			if(assegnato || tolleranzaGruppo>=tolleranza) {
				statistiche.addSoddisfatti(e.getNumeroPersone());
				statistiche.addTotali(e.getNumeroPersone());
				//tempo di lasccio del tavolo
				Random r = new Random();
				int tmin =60;
				int tmax= 121;
				int tempo = r.nextInt(tmax-tmin) + tmin;
				if(assegnato) {
					//System.out.println("AL TAVOLO: num: "+e.getNumeroPersone()+"; tav: "+e.getIdTavolo());
					this.queue.add(new Event(e.getTime().plus(Duration.of(tempo, ChronoUnit.MINUTES)), EventType.ABBANDONO_GRUPPO_CLIENTI, e.getNumeroPersone(), idTavolo));
				}else {
					//VANNO AL BANCONE
					//System.out.println("AL BANCONE: "+ e.getIdTavolo()+"; numP: "+e.getNumeroPersone());
					this.queue.add(new Event(e.getTime().plus(Duration.of(tempo, ChronoUnit.MINUTES)), EventType.ABBANDONO_GRUPPO_CLIENTI, e.getNumeroPersone(), 0));

				}
			}else {
				statistiche.addInsofddisfatti(e.getNumeroPersone());
				statistiche.addTotali(e.getNumeroPersone());
			}
			
			
			
		case ABBANDONO_GRUPPO_CLIENTI:
			//controlla il caso in cui erano al tavolo ossia idTavolo !=0 e nel caso tavoli.get(idTavol).setDisponibile ==true
			if(tavoli.get(e.getIdTavolo())!=null) {
				tavoli.get(e.getIdTavolo()).setDisponibile(true);
				//System.out.println("ABBANDONO: num: "+e.getNumeroPersone()+"; tav: "+e.getIdTavolo());

			}
			//in entrambi i casi --> esce 
			
		}
		
	}

	private Map<Integer, Tavolo> generaTavoli() {
		
		int array[] = {4,4,4,4,4,6,6,6,6,8,8,8,8,10,10};
		Map<Integer, Tavolo> tavoli = new LinkedHashMap<>();
		for(int i=1; i<=array.length; i++) {
			tavoli.put(i,new Tavolo(i,array[i-1]));
			
		}
		return tavoli;
	}



	
	//GETTER RESULTS
	public Statistiche getStatistiche() {
		return statistiche;
	}


	public int getNumEventi() {
		return numEventi;
	}
	
	
	
}
