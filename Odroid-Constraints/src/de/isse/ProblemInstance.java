package de.isse;

import java.io.Serializable;

public class ProblemInstance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6503942216332380544L;

	public int[][] costMatrix;
	public int id;
	
	public ProblemInstance(int[][] costMatrix, int id) {
		this.costMatrix = costMatrix;
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < costMatrix.length; ++i) {
			for(int j = 0; j < costMatrix[i].length; ++j){
				sb.append(costMatrix[i][j]);
				sb.append(' ');
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
