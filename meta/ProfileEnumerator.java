package meta;

import java.util.*;

public class ProfileEnumerator {
	public static ProfileCollection enumerate(long[] initProfiles) {
		ProfileCollection pc = new MyHashProfileCollection(17700000);
		for (Long profile : initProfiles) {
			pc.add(profile);
		}
		
		int transitions = 0;
		for (int cur = 0; cur < pc.getSize(); cur++) {
			if ((short) cur == 0)
				System.out.print(".");
			long profile = pc.getProfile(cur);
			List<NextProfile> nextList = GadgetFinder.problem.next(profile);
			transitions += nextList.size();
			for (NextProfile next : nextList) {
				pc.add(next.getProfile());
			}
		}
		System.out.println();
		
		System.out.println("Primary profile collection calculated, " + transitions + " transitions");
		System.gc();
		
		long[] profiles = pc.toArray();
		pc = null;
		System.gc();
		pc = new SortedArrayProfileCollection(profiles);
		pc.setTransitions(transitions);
		return pc;
		
//		File file = new File("profiles.txt");
//		pc.print(file);
//		
//		pc = null;
//		System.gc();
//		
//		long[] profiles = LongArrays.readLongArray(file);
//		return new SortedArrayProfileCollection(profiles);
	}
}
