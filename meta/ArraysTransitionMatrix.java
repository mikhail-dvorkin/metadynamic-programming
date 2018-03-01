package meta;

import java.util.List;

public class ArraysTransitionMatrix implements TransitionMatrix {
	private final int size;
	private final int[][] p;
	private final int[][] c;
	
	public ArraysTransitionMatrix(Problem2D problem, ProfileCollection pc) {
		size = pc.getSize();
		p = new int[size][];
		c = new int[size][];
		
		for (int i = 0; i < size; i++) {
			if ((short) i == 0)
				System.out.print(".");
			long profile = pc.getProfile(i);
			List<NextProfile> nextList = problem.next(profile);
			p[i] = new int[nextList.size()];
			c[i] = new int[nextList.size()];
			int j = 0;
			for (NextProfile next : nextList) {
				p[i][j] = pc.getNumber(next.getProfile());
				c[i][j] = next.getCell();
				j++;
			}
		}
		System.out.println();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getTransitionsFrom(int profile) {
		return p[profile].length;
	}

	@Override
	public int getTransitionProfile(int profile, int index) {
		return p[profile][index];
	}

	@Override
	public int getTransitionCell(int profile, int index) {
		return c[profile][index];
	}
}
