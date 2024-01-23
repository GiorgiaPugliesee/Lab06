package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private MeteoDAO meteoDao;
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private double costoMigliore = Integer.MAX_VALUE;
	private List<Citta> sequenzaMigliore;
	
	List<Citta> citta;
	List<Rilevamento> rilevamenti; 
	
	public Model() {
		this.meteoDao = new MeteoDAO();
		citta = this.meteoDao.getAllCitta();

	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		String s = "L'umidità media per il mese " + mese + " è:\n";
		List<Umidita> umiditaMedia = this.meteoDao.getMediaUmiditaMese(mese);
		for(Umidita u : umiditaMedia) {
			s += u.getLocalita() + ":  " + u.getUmiditaMedia() + "\n"; 
		}
		return s;
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		rilevamenti = this.meteoDao.getAllRilevamentiLocalitaMese(mese);
		List<Citta> parziale = new ArrayList<>();
		
		cerca(parziale, 0);
		
		String s = "";
		for(Citta c : sequenzaMigliore) {
			s += c.getNome() + "\n";
		}
		
		return s;
	}
	
	public void cerca(List<Citta> parziale, int livello) {
		
		if(!controllo(parziale)) {
			return;
		}
		
		if (parziale.size() > 15) {
			  return;
		 }
		
		if(parziale.size() == NUMERO_GIORNI_TOTALI) {
			double costo = calcolaCosto(parziale);
			if(costo < costoMigliore) {
				costoMigliore = costo;
				sequenzaMigliore = new ArrayList<>(parziale);
				return;
			}
		}
		
		for(Citta c : citta) {
			
			if(parziale.size() == 0 || c.getNome().compareTo(parziale.get(parziale.size()-1).getNome()) != 0) {
				parziale.add(c);	
				parziale.add(c);	
				parziale.add(c);
				
				c.increaseCounter(3);

				cerca(parziale, livello+1);
				
				for(int i=0; i<3; i++) {
					parziale.remove(parziale.size()-1);
				}

				c.decreaseCounter(3);

				
			} else {
				parziale.add(c);
				
				c.increaseCounter(1);
				
				cerca(parziale, livello+1); 
				
				parziale.remove(parziale.size()-1);
				
				c.decreaseCounter(1);
			}
			
			
		}
		
		
//		//E -- sequenza di istruzioni che vengono eseguite sempre
//		//Da usare solo in casi rari (es. Ruzzle)
//		doAlways;
//		
//		//A
//		if(condizioneTerminazione) {
//			doSomething;
//			return;
//		}
//		
//		//Potrebbe essere anche un while()
//		for() {
//
//			//B
//			generaNuovaSoluzioneParziale;
//
//			//C
//			if(filtro) {
//				recursive(..., level + 1);
//			}
//
//			//D
//			backtracking;
//		}
		
	}

	private boolean controllo(List<Citta> parziale) {
		boolean flag = true;
		  
		  for(Citta c : citta) {
			  if(c.getCounter()>6) {
				  flag = false;
			  }
		  }
		  
		  if(parziale.size()==Model.NUMERO_GIORNI_TOTALI) {
			  for(Citta c : citta) {
				  if (!parziale.contains(c)) {
					  flag = false;
				  }
			  }
		  }
		  
		  return flag;
	}

	private double calcolaCosto(List<Citta> parziale) {
		
		String citta = parziale.get(0).getNome();
		double costo = 0;
		
		for(Citta c : parziale) {
			if(c.getNome().compareTo(citta) != 0) {
				costo += COST;
				citta = c.getNome();
			} else {
				for(Rilevamento r : rilevamenti) {
					if(r.getLocalita().compareTo(citta)==0 && r.getData().getDayOfMonth() == parziale.indexOf(c)) {
						costo += r.getUmidita();
					}
				}
			}
		}
		
		return costo;
	}
	

}
