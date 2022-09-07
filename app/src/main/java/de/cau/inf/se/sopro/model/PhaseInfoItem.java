package de.cau.inf.se.sopro.model;

public class PhaseInfoItem {

	private String[] phases;
	private int activePhase;
	
	public PhaseInfoItem(String[] phases, int activePhase) {
		this.phases = phases;
		this.activePhase = activePhase;
	}

	public String[] getPhases() {
		return phases;
	}

	public void setPhases(String[] phases) {
		this.phases = phases;
	}

	public int getActivePhase() {
		return activePhase;
	}

	public void setActivePhase(int activePhase) {
		this.activePhase = activePhase;
	}
	
}
