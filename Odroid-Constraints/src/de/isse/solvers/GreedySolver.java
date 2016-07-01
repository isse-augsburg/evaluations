package de.isse.solvers;

import java.util.BitSet;
import java.util.PriorityQueue;

import de.isse.ProblemInstance;
import de.isse.Result;

public class GreedySolver implements Solver {

	private static class Pair implements Comparable<Pair> {
		public Pair(int robot, int task, int dist) {
			super();
			this.robot = robot;
			this.task = task;
			this.dist = dist;
		}
		public int robot;
		public int task;
		public int dist;
		
		@Override
		public int hashCode() {
			return robot + task + dist;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null || !(obj instanceof Pair))
				return false;
			Pair other = (Pair) obj;
			return robot == other.robot && task == other.task && dist == other.dist;
		}
		
		@Override
		public int compareTo(Pair arg0) {
			return Integer.compare(dist, arg0.dist);
		}
		
		@Override
		public String toString() {
			return "Agent "+robot +" task "+task;
		}
	}
	
	@Override
	public Result solve(ProblemInstance problemInstance, int timeout) {
		// construct pairs of task-agent matchings
		int[][] costs = problemInstance.costMatrix;
		PriorityQueue<Pair> pairs = new PriorityQueue<>();
		BitSet pickedAgents = new BitSet(costs.length);
		BitSet pickedTasks = new BitSet(costs.length);
				
		for(int i = 0; i  < costs.length; ++i) {			
			for(int j = 0; j < costs.length; ++j) {
				Pair p = new Pair(i, j, costs[i][j]);
				pairs.add(p);
			}
		}
		
		int[] assignment = new int[costs.length];
		int maxDist = Integer.MIN_VALUE;
		int sumDist = 0;

		for(int pick = 0; pick < costs.length; ++pick) {
			// pick the shortest from the pool
			
			Pair nextPick = pairs.remove();
			pickedAgents.set(nextPick.robot);
			pickedTasks.set(nextPick.task);
			
			assignment[nextPick.robot] = nextPick.task;
			sumDist += nextPick.dist;
			// actually, this should always be nextPick.dist (but let's be safe here)
			maxDist = Math.max(maxDist, nextPick.dist);
		
			// remove all that belong to either a picked task or picked agent 
			Pair lookahead = pairs.peek(); 
			if(lookahead != null) { // true until the very last pick
				while(lookahead != null && (pickedAgents.get(lookahead.robot) || pickedTasks.get(lookahead.task)) ) {
					pairs.remove();
					lookahead = pairs.peek();
				}
			}			
		}
		
		Result res = new Result();
		res.maxDist = maxDist;
		res.sumDist = sumDist;
		res.solved = true;
		return res;
	}


}
