package meta;

import java.util.*;

import tools.IntArrays;

public class RetentiveListMetaProfile implements MetaProfile {
	int[][] p;
	RetentiveListMetaProfile prev;
	int how;
	
	public RetentiveListMetaProfile(int[][] numbers, RetentiveListMetaProfile prev, int how) {
		p = numbers;
		for (int[] a : p) {
			Arrays.sort(a);
		}
		this.prev = prev;
		this.how = how;
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(p);
	}
	
	@Override
	public boolean equals(Object obj) {
		RetentiveListMetaProfile that = (RetentiveListMetaProfile) obj;
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
			result.add(new RetentiveListMetaProfile(q, this, cell));
		}
		return result;
	}
	
	public ArrayList<Integer> history() {
		if (prev == null)
			return new ArrayList<Integer>();
		ArrayList<Integer> al = prev.history();
		al.add(how);
		return al;
	}

	@Override
	public void print() {
		System.out.println("Desired metaprofile found");
		GadgetFinder.problem.printField(history());
	}
}
