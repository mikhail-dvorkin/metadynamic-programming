package meta;

import java.util.List;

public class SingleArrayTransitionMatrix implements TransitionMatrix {
	private final int size;
	private final int transitions;
	private final int[] first;
	private final int[] p;
	private final int[] c;
	
	public SingleArrayTransitionMatrix(ProfileCollection pc) {
		size = pc.getSize();
		transitions = pc.getTransitions();
		first = new int[size + 1];
		p = new int[transitions];
		c = new int[transitions];
		
		for (int i = 0, j = 0; i < size; i++) {
			if ((short) i == 0)
				System.out.print(".");
			long profile = pc.getProfile(i);
			List<NextProfile> nextList = GadgetFinder.problem.next(profile);
			for (NextProfile next : nextList) {
				p[j] = pc.getNumber(next.getProfile());
				c[j] = next.getCell();
				j++;
			}
			first[i + 1] = j;
		}
		System.out.println();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getTransitionsFrom(int profile) {
		return first[profile + 1] - first[profile];
	}

	@Override
	public int getTransitionProfile(int profile, int index) {
		return p[first[profile] + index];
	}

	@Override
	public int getTransitionCell(int profile, int index) {
		return c[first[profile] + index];
	}
}
