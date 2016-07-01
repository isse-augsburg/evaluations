package de.isse.solvers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.isse.MiniZincLauncher;
import de.isse.MiniZincResultListener;
import de.isse.ProblemInstance;
import de.isse.Result;

public class MiniZincSolver implements Solver {
	private File modelFile;
	private ModelType modelType;
	private Integer upperBound; // should be nullable
	
	public static enum ModelType {
		DEFAULT, TWEAKED
	}
	
	public MiniZincSolver(ModelType modelType) {
		this.modelType = modelType;
		upperBound = null;
		if(modelType == ModelType.DEFAULT || modelType == null)
			modelFile = new File("mzn-model.mzn");
		else if(modelType == ModelType.TWEAKED) {
			modelFile = new File("tuned-model.mzn");
		}
			
	}
	
	@Override
	public Result solve(ProblemInstance problemInstance, int timeout) {
		// write the cost matrix to a MiniZinc data file
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append("agentTaskDistance = [|");
		int[][] costs = problemInstance.costMatrix;
		int upper = 0;
		for (int i = 0; i < costs.length; i++) {
			for (int j = 0; j < costs.length; j++) {
				upper = Math.max(upper, costs[i][j]);
				dataBuilder.append(costs[i][j]);
				if (j < costs.length - 1) {
					dataBuilder.append(", ");
				}
			}
			dataBuilder.append("|");
		}
		
		if(upperBound != null)
			upper = upperBound;
		
		dataBuilder.append("];\nn = " + costs.length + ";\n");
		dataBuilder.append("upperBound = "+upper);
		File dataFile = new File("gen.dzn");
		try {
			FileWriter fw = new FileWriter(dataFile);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(dataBuilder.toString());
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// now launch the model
		final Result result = new Result();
		MiniZincLauncher mznLauncher = new MiniZincLauncher();
		mznLauncher.addMiniZincResultListener(new MiniZincResultListener() {
			
			@Override
			public void notifyOptimality() {
				result.knownOptimally = true;				
			}
			
			@Override
			public void notifyLine(String line) {
				// update result state 
				if(line.contains("maxDist")) {
					result.maxDist = Integer.parseInt(line.split(":")[1]) ;
				} else if(line.contains("sumDist")) {
					result.sumDist = Integer.parseInt(line.split(":")[1]) ;
				}
			}

			@Override
			public void notifySolved() {
				result.solved = true;
				
			}
		});
		mznLauncher.runModel(modelFile, dataFile, timeout);
	
		return result;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

}
