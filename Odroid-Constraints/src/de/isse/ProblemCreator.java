package de.isse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * Expands a parameter spec into a list of random problem instances
 * @author Alexander Schiendorfer
 *
 */
public class ProblemCreator {
	public List<ProblemInstance> getProblems(EvalParameters params) {
		Random r = new Random(params.randomSeed);
		int ns = (params.nMax-params.nMin)/params.nStep;
		List<ProblemInstance> problems = new ArrayList<>(ns*params.numberIterations);
		int problemId = 1;
		
		for(int n = params.nMin; n <= params.nMax; n+=params.nStep) {
			System.out.println("WORKING ON N ... "+n);
			for(int i = 0; i < params.numberIterations; ++i) {
				// draw n positions for robots
				List<Position> robots = drawPositions(n,r,params.extension);
				
				// draw n positions for tasks 
				List<Position> tasks = drawPositions(n,r,params.extension);
				
				// cost matrix: A[i,j] = costs for agent i performing task j
				int[][] costMatrix = getCostMatrix(robots, tasks);
				
				ProblemInstance pi = new ProblemInstance(costMatrix,problemId);
				
				problems.add(pi);
				++problemId;
			}
		}
		
		return problems;
	}

	private int[][] getCostMatrix(List<Position> robots, List<Position> tasks) {
		int[][] costs = new int[robots.size()][robots.size()];
		
		for(int i = 0; i < robots.size(); ++i) {
			Position robot = robots.get(i);
			
			for(int j = 0; j < tasks.size(); ++j) {
				Position task = tasks.get(j);
				int dist = getDist(robot,task);
				costs[i][j] = dist;
			}
		}
		return costs;
	}

	private int getDist(Position robot, Position task) {
		double euclideanDist = robot.getDist(task);
		
		return (int) Math.round(euclideanDist);
	}

	private List<Position> drawPositions(int n, Random r, int extension) {
		List<Position> positions = new ArrayList<>(n);
		for(int i = 0; i < n; ++i) {
			positions.add(new Position(r, extension));
		}
		return positions;
	}
}
