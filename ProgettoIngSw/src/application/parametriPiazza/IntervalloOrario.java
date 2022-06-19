package application.parametriPiazza;

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
	
	/**
	 * Precondizione: orario != null
	 * 
	 * @param orario
	 * @return
	 */
	public boolean minIsBefore(LocalTime orario) {
		return this.orarioMin.isBefore(orario);
	}
	
	/**
	 * Precondizione: intervallo != null 
	 * 
	 * @param intervallo
	 * @return
	 */
	public boolean parzialmenteInclude(IntervalloOrario intervallo) {
		return this.includeMin(intervallo.getOrarioMin()) || this.includeMax(intervallo.getOrarioMax());
	}
	
	/**
	 * Precondizione: orarioMin != null
	 * 
	 * @param orarioMin
	 * @return
	 */
	private boolean includeMin(LocalTime orarioMin) {
		return (orarioMin.isAfter(this.orarioMin) && orarioMin.isBefore(this.orarioMax)) || (orarioMin.equals(this.orarioMin));
	}
	
	/**
	 * Precondizione: orarioMax != null
	 * 
	 * @param orarioMax
	 * @return
	 */
	private boolean includeMax(LocalTime orarioMax) {
		return (orarioMax.isAfter(this.orarioMin) && orarioMax.isBefore(this.orarioMax)) || (orarioMax.equals(this.orarioMax));
	}
	
	/**
	 * Precondizione: orario != null
	 * 
	 * @param orario
	 * @return
	 */
	public boolean includeOrario(LocalTime orario) {
		return (orario.isAfter(this.orarioMin) && orario.isBefore(this.orarioMax)) || (orario.equals(this.orarioMin)) || (orario.equals(this.orarioMax));
	}
}
