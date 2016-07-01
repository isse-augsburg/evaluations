package de.isse;

/**
 * Generic listener interface to facilitate parsing MiniZinc files
 * @author Alexander Schiendorfer
 *
 */
public interface MiniZincResultListener {
	void notifyOptimality(); // called if a solution was optimal
	void notifyLine(String line); // a result line 
	void notifySolved();
}
