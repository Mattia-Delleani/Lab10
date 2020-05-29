package it.polito.tdp.bar.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	// Modello del mondo
	private List<Tavolo> tavoli;

	// Parametri di simulazione
	private int NUM_EVENTI = 2000;
	private int T_MIN_ARRIVO_MAX = 10;
	private int NUM_PERSONE_MAX = 10;
	private int DURATA_MIN = 60;
	private int DURATA_MAX = 120;
	private double TOLLERANZA_MAX = 0.9;
	private double OCCUPAZIONE_MIN = 0.5; // tavolo pieno almeno al 50%

	// Valori calcolati
	private Statistiche stat ;
	
	// Coda degli eventi

	private PriorityQueue<Event> queue;

	private void caricaTavoli() {

		this.tavoli = new ArrayList<>();

		aggiungiTavolo(2, 10);
		aggiungiTavolo(4, 8);
		aggiungiTavolo(4, 6);
		aggiungiTavolo(5, 4);

		// ordina i tavoli dal più piccolo al più grande, così facilito le ricerche
		Collections.sort(this.tavoli, new Comparator<Tavolo>() {
			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				return -(o1.getnPosti() - o1.getnPosti());
			}
		});

	}

	private void caricaEventi() {
		Duration arrivo = Duration.ofMinutes(0); // non ci sono orari dichiarati, parto a contare da 0

		for (int i = 0; i < this.NUM_EVENTI; i++) {
			int numPersone = (int) (Math.random() * this.NUM_PERSONE_MAX + 1);
			Duration durata = Duration
					.ofMinutes(this.DURATA_MIN + (int) (Math.random() * (this.DURATA_MAX - this.DURATA_MIN)));
			double tolleranza = Math.random() * this.TOLLERANZA_MAX;
			Event e = new Event(arrivo, EventType.ARRIVO_GRUPPO_CLIENTI, numPersone, durata, tolleranza, null);
			this.queue.add(e);
			arrivo = arrivo.plusMinutes(1 + (int) (Math.random() * this.T_MIN_ARRIVO_MAX));
		}
	}

	public void init() {
		caricaTavoli();

		this.queue = new PriorityQueue<>();
		caricaEventi();

		this.stat = new Statistiche() ;
	}

	public void run() {

		while (!queue.isEmpty()) {
			Event e = queue.poll();
			System.out.println(e);
			processEvent(e);
		}

	}

	private void processEvent(Event e) {

		switch (e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:

			// conta i clienti
			stat.addNumClientiTot(e.getNumPersone());

			// cerca un tavolo
			Tavolo trovato = null;
			for (Tavolo t : this.tavoli) {
				if (!t.isOccupato() && t.getnPosti() >= e.getNumPersone()
						&& t.getnPosti() * this.OCCUPAZIONE_MIN <= e.getNumPersone()) {
					trovato = t;
					break; // il primo che trovo sarà il più piccolo che soddisfa il requisito
				}
			}

			if (trovato != null) {
				System.out.format("Sedute %d persone a tavolo da %d\n", e.getNumPersone(), trovato.getnPosti());
				// tavolo disponibile, li faccio sedere
				stat.addNumClientiSoddisfatti(e.getNumPersone());
				trovato.setOccupato(true);

				// si alzeranno
				queue.add(new Event(e.getTime().plus(e.getDurata()), EventType.TAVOLO_LIBERATO, e.getNumPersone(),
						e.getDurata(), e.getTolleranza(), trovato));
			} else {
				// nessun tavolo disponibile. Accettano il bancone?
				double bancone = Math.random() ;
				if(bancone<=e.getTolleranza()) {
					// sì, vado al bancone
					stat.addNumClientiSoddisfatti(e.getNumPersone());
				} else {
					// no, vado a casa
					stat.addNumClientiInsoddisfatti(e.getNumPersone());
				}
			}

			break;
		case TAVOLO_LIBERATO:
			e.getTavolo().setOccupato(false);
			break;
		}

	}

	private void aggiungiTavolo(int num, int nPosti) {
		for (int i = 0; i < num; i++) {
			Tavolo t = new Tavolo(nPosti, false);
			this.tavoli.add(t);
		}
	}
	
	public Statistiche getStat() {
		return stat;
	}



}
