package de.isse;

import java.util.Random;

public class Position {
	public final double x;
	public final double y;
	public final double z;
	
	public static final int MAX_HEIGHT = 200;
	public Position(Random r, int extension) {
		super();
		
		this.x = r.nextDouble()*extension;
		this.y = r.nextDouble()*extension;
		// z should be capped at maximal height
		this.z = r.nextDouble()* Math.min(extension, MAX_HEIGHT);
	}

	public double getDist(Position other) {
		return Math.sqrt( (this.x - other.x)*(this.x - other.x) + (this.y- other.y)*(this.y - other.y) + (this.z - other.z)*(this.z - other.z));
	}
	
	
}
