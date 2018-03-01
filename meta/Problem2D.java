package meta;

import java.util.*;

public abstract class Problem2D {
	public abstract List<NextProfile> next(long profile);
	
	public abstract List<long[]> wires(int w, boolean in);
	
	@SuppressWarnings("static-method")
	public void printField(@SuppressWarnings("unused") List<Integer> field) {
		System.out.println("Printing a field is not supported");
	}
}
