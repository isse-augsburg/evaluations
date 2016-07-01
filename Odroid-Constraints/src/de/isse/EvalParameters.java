package de.isse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents the factors that affect the creation of random problems that are solved by bespoke algorithms or minizinc 
 * @author Alexander Schiendorfer
 *
 */
public class EvalParameters {
	public final int extension; // the length/width/height in meters (where to choose positions and tasks from)
	public final int randomSeed = 1337;
	public final int nMin; // number of tasks (identical to robots)
	public final int nMax;  
	public final int nStep;
	public final int numberIterations; // how many repeated draws for each n
	public final int timeLimit; // in seconds
	public String name; // to better identify the experiment
	
	public EvalParameters(int extension, int nMin, int nMax, int nStep, int numberIterations, int timeLimit) {
		super();
		this.extension = extension;
		this.nMin = nMin;
		this.nMax = nMax;
		this.nStep = nStep;
		this.numberIterations = numberIterations;
		this.timeLimit = timeLimit;
	}

	public static EvalParameters loadFromProperties(String propFile) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(propFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int extension = Integer.parseInt(p.getProperty("extension", "1000"));
		int nMin = Integer.parseInt(p.getProperty("nMin", "5"));
		int nMax = Integer.parseInt(p.getProperty("nMax", "10"));
		int nStep = Integer.parseInt(p.getProperty("nStep", "2"));
		int numberIterations = Integer.parseInt(p.getProperty("numberIterations", "2"));
		int timeLimit = Integer.parseInt(p.getProperty("timeLimit", "5"));
		String name = p.getProperty("name", "default");
		
		EvalParameters params = new EvalParameters(extension, nMin, nMax, nStep, numberIterations, timeLimit);
		params.name = name;
		
		return params;
	}	
}
