package de.isse;

import java.io.Serializable;

/**
 * A result for a single problem instance; these are the dependent variables to be measured
 * @author Alexander Schiendorfer
 *
 */
public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3200391989844877322L;
	public int maxDist;
	public int sumDist;
	public long elapsedNanoSeconds;
	public boolean knownOptimally;
	public ProblemInstance instance;
	public String solvedBy;
	public boolean solved;
	public int solverId;
	public boolean odroid;
	public int timeLimit;
	public double elapsedSeconds;
	
	@Override
	public String toString() {
		return "Max dist: "+maxDist + " sum dist: "+sumDist + (knownOptimally?" (optimal) " : "");
	}
}
