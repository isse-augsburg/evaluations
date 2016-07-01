package de.isse.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.isse.Result;

public class Deserializer {

	public static final String fileName = "results.ser";
	public static final String dbName = "db/results.db";
	public static String CONNECTION_PREFIX = "jdbc:sqlite:";
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// looks for a file with a result map for further postprocessing
		String resultFile = fileName;
		String dbFile = dbName;
		if(args.length > 0)
			resultFile = args[0];
		
		if(args.length > 1) {
			dbFile = args[1];
		}
			
		String connectionString = CONNECTION_PREFIX + dbFile;
		
		Map<String, List<Result>> results = null; 
		try {
			FileInputStream fileIn = new FileInputStream(resultFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			results = (Map<String, List<Result>>) in.readObject();
			
			in.close();
			fileIn.close();
			System.out.printf("Serialized data is loaded from "+resultFile);
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(results != null) { // there is something to do!
			Connection c = null;
			Statement s = null;
			
			try{
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection(connectionString);
				System.out.println("Opened DB ");
											
				
				String insertSql = "INSERT INTO JobResult (ProblemId,nTasks,SolverId,Solved,Optimally,SumDist,MaxDist,ElapsedSecs,TimeLimit,Odroid) "+
				                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";				    
				PreparedStatement ps = c.prepareStatement(insertSql);
				
				for(Entry<String, List<Result>> entry : results.entrySet()) {
					for(Result res : entry.getValue()) {
						ps.setInt(1, res.instance.id);
						ps.setInt(2, res.instance.costMatrix.length);
						ps.setInt(3, res.solverId);
						ps.setBoolean(4, res.solved);
						ps.setBoolean(5, res.knownOptimally);
						ps.setInt(6, res.sumDist);
						ps.setInt(7, res.maxDist);
						ps.setDouble(8, res.elapsedSeconds);
						ps.setInt(9, res.timeLimit);
						ps.setBoolean(10, res.odroid);
						ps.addBatch();		
					}
				}
				
				int[] batchRes = ps.executeBatch();
				System.out.println("After insert: "+Arrays.toString(batchRes));
				ps.close();
				c.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
	}

}
