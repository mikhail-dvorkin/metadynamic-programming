package meta;

import java.util.*;
import tools.IntArrays;

public class ListMetaProfile implements MetaProfile {
	int[][] p;
	
	public ListMetaProfile(int[][] numbers) {
		p = numbers;
		for (int[] a : p) {
			Arrays.sort(a);
		}
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(p);
	}
	
	@Override
	public boolean equals(Object obj) {
		ListMetaProfile that = (ListMetaProfile) obj;
		return Arrays.deepEquals(p, that.p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MetaProfile> next() {
		TreeMap<Integer, TreeSet<Integer>>[] tm = new TreeMap[p.length];
		TreeSet<Integer> cells = new TreeSet<Integer>();
		for (int i = 0; i < p.length; i++) {
			tm[i] = new TreeMap<Integer, TreeSet<Integer>>();
			for (int profile : p[i]) {
				int trans = GadgetFinder.matrix.getTransitionsFrom(profile);
				for (int j = 0; j < trans; j++) {
					int p1 = GadgetFinder.matrix.getTransitionProfile(profile, j);
					int cell = GadgetFinder.matrix.getTransitionCell(profile, j);
					if (tm[i].containsKey(cell)) {
						tm[i].get(cell).add(p1);
					} else {
						TreeSet<Integer> ts = new TreeSet<Integer>();
						ts.add(p1);
						tm[i].put(cell, ts);
					}
				}
			}
			for (int cell : tm[i].keySet())
				cells.add(cell);
		}
		List<MetaProfile> result = new ArrayList<MetaProfile>();
		for (int cell : cells) {
			int[][] q = new int[p.length][];
			for (int i = 0; i < p.length; i++) {
				TreeSet<Integer> ts = tm[i].get(cell);
				if (ts == null) {
					q[i] = IntArrays.int0;
				} else {
					q[i] = new int[ts.size()];
					int j = 0;
					for (int t : ts) {
						q[i][j] = t;
						j++;
					}
				}
			}
			result.add(new ListMetaProfile(q));
		}
		return result;
	}

	@Override
	public void print() {
		System.out.println("Desired metaprofile found");
		System.out.println("Printing the field is disabled");
	}
}
