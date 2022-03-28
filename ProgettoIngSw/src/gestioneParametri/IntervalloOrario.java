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
		if(this.include(intervallo.getOrarioMin()) || this.include(intervallo.getOrarioMax()))
			return true;
		
		return false;
	}
	
	private boolean include(LocalTime orario) {
		if(orario.isAfter(this.getOrarioMin()) && orario.isBefore(this.getOrarioMax()))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "[" + orarioMin + "-" + orarioMax + "]";
	}
}
