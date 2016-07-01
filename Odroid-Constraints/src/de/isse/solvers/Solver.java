package de.isse.solvers;

import de.isse.ProblemInstance;
import de.isse.Result;

/**
 * Represents any algorithm suited for a problem instance (task allocation)
 * @author Alexander Schiendorfer
 *
 */
public interface Solver {
	Result solve(ProblemInstance problemInstance, int timeout);
}
