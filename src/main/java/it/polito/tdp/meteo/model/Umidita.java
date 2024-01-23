package it.polito.tdp.meteo.model;

import java.util.Objects;

public class Umidita {
	
	private String localita;
	private double umiditaMedia;
	
	public Umidita(String localita, double umiditaMedia) {
		this.localita = localita;
		this.umiditaMedia = umiditaMedia;
	}
	
	public String getLocalita() {
		return localita;
	}
	
	public double getUmiditaMedia() {
		return umiditaMedia;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(localita, umiditaMedia);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Umidita other = (Umidita) obj;
		return Objects.equals(localita, other.localita)
				&& Double.doubleToLongBits(umiditaMedia) == Double.doubleToLongBits(other.umiditaMedia);
	}
	
	@Override
	public String toString() {
		return "UmiditaMedia [localita=" + localita + ", umiditaMedia=" + umiditaMedia + "]";
	}
	
	

}
