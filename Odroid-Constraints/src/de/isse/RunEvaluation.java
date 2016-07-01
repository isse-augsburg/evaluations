package de.isse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import de.isse.solvers.GreedyMinizincHybrid;
import de.isse.solvers.GreedySolver;
import de.isse.solvers.MiniZincSolver;
import de.isse.solvers.MiniZincSolver.ModelType;
import de.isse.solvers.Solver;

public class RunEvaluation {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		EvalParameters params;
		if(args.length > 0) {
			String propFile = args[0];
			params = EvalParameters.loadFromProperties(propFile);
		} else {
			// for now, a fixed set
			int fixed = 100;
			params = new EvalParameters(1000, fixed, fixed, 1, 1, 5);
		}
		
		String deviceName = "default";
		File nameFile = new File("name.properties");
		if(nameFile.exists()) {
			Properties p = new Properties();
			p.load(new FileInputStream(nameFile));
			deviceName = p.getProperty("name");
		}
		ProblemCreator creator = new ProblemCreator();
		List<ProblemInstance> problems = creator.getProblems(params);

		// now the solvers
		Map<String, Solver> solvers = new HashMap<>();
		solvers.put("Greedy", new GreedySolver());
		solvers.put("MiniZinc-Gecode", new MiniZincSolver(ModelType.DEFAULT));
		//solvers.put("MiniZinc-Gecode-Tweaked", new MiniZincSolver(ModelType.TWEAKED));
		//solvers.put("Greedy-MZN", new GreedyMinizincHybrid());
		
		// save the results
		Map<String, List<Result>> results = new HashMap<>();

		int solverId = 1;
		for (Entry<String, Solver> entry : solvers.entrySet()) {
			System.out.println("Solver ... " + entry.getKey());
			System.out.println("----------- ");
	
			List<Result> resultsForSolver = new ArrayList<>(problems.size());
			results.put(entry.getKey(), resultsForSolver);
			for (ProblemInstance pi : problems) {
				// measure the time
				long start = System.nanoTime();
				Solver s = entry.getValue();
				Result r = s.solve(pi, params.timeLimit);
				
				long end = System.nanoTime();
				
				r.elapsedNanoSeconds = end - start;
				r.solvedBy = entry.getKey();
				r.solverId = solverId;
				r.instance = pi;
				r.timeLimit = params.timeLimit;
				r.elapsedSeconds = toSeconds(r.elapsedNanoSeconds);
				
				if(deviceName.contains("odroid")) 
					r.odroid = true;
				
				System.out.println("Problem #" + pi.id + ": ");
				System.out.println("Needed time: " + toSeconds(r.elapsedNanoSeconds));
				System.out.println("Result: " + r);
				resultsForSolver.add(r);
			}
			++solverId;
		}

		// now save the results map

		try {
			FileOutputStream fileOut = new FileOutputStream("results.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(results);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in results.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}

	}


	private static double toSeconds(long elapsedNanoSeconds) {
		return elapsedNanoSeconds / 1E9;
	}

}
