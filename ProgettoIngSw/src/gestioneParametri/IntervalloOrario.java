package gestioneParametri;

import java.time.LocalTime;

public class IntervalloOrario {
	private LocalTime orarioMin;
	private LocalTime orarioMax;
	
	public IntervalloOrario() {
	}
	
	/**
	 * @param orarioMin
	 * @param orarioMax
	 */
	public IntervalloOrario(LocalTime orarioMin, LocalTime orarioMax) {
		this.orarioMin = orarioMin;
		this.orarioMax = orarioMax;
	}

	public LocalTime getOrarioMin() {
		return orarioMin;
	}

	public void setOrarioMin(LocalTime orarioMin) {
		this.orarioMin = orarioMin;
	}

	public LocalTime getOrarioMax() {
		return orarioMax;
	}

	public void setOrarioMax(LocalTime orarioMax) {
		this.orarioMax = orarioMax;
	}
	
	public boolean parzialmenteInclude(IntervalloOrario intervallo) {
		if(this.includeMin(intervallo.getOrarioMin()) || this.includeMax(intervallo.getOrarioMax()))
			return true;
		return false;
	}
	
	private boolean includeMin(LocalTime orarioMin) {
		if((orarioMin.isAfter(this.getOrarioMin()) && orarioMin.isBefore(this.getOrarioMax())) || (orarioMin.equals(this.getOrarioMin())))
			return true;
		else
			return false;
	}
	
	private boolean includeMax(LocalTime orarioMax) {
		if((orarioMax.isAfter(this.getOrarioMin()) && orarioMax.isBefore(this.getOrarioMax())) || (orarioMax.equals(this.getOrarioMax())))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "[" + orarioMin + "-" + orarioMax + "]";
	}
}
