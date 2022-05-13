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
	
	public boolean minIsBefore(LocalTime orario) {
		return this.orarioMin.isBefore(orario);
	}
	
	public boolean parzialmenteInclude(IntervalloOrario intervallo) {
		return this.includeMin(intervallo.getOrarioMin()) || this.includeMax(intervallo.getOrarioMax());
	}
	
	private boolean includeMin(LocalTime orarioMin) {
		return (orarioMin.isAfter(this.orarioMin) && orarioMin.isBefore(this.orarioMax)) || (orarioMin.equals(this.orarioMin));
	}
	
	private boolean includeMax(LocalTime orarioMax) {
		return (orarioMax.isAfter(this.orarioMin) && orarioMax.isBefore(this.orarioMax)) || (orarioMax.equals(this.orarioMax));
	}
	
	protected boolean includeOrario(LocalTime orario) {
		return (orario.isAfter(this.orarioMin) && orario.isBefore(this.orarioMax)) || (orario.equals(this.orarioMin)) || (orario.equals(this.orarioMax));
	}
}
