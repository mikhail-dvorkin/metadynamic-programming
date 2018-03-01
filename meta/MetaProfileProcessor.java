package meta;

import java.util.*;

public class MetaProfileProcessor {
	public static void work(Set<MetaProfile> init, Set<MetaProfile> desired) {
		int total = 0;
		while (true) {
			Set<MetaProfile> nextLayer;
			if (GadgetFinder.storeField && GadgetFinder.beautyful) {
				Map<MetaProfile, Integer> nextLayerMap = new HashMap<MetaProfile, Integer>();
				for (MetaProfile mp : init) {
					Collection<MetaProfile> nexts = mp.next();
					for (MetaProfile next : nexts) {
						Integer prevBeauty = nextLayerMap.get(next);
						int newBeauty = ((BeautifulMetaProfile) next).getBeauty();
						if (prevBeauty == null || prevBeauty < newBeauty) {
							nextLayerMap.remove(next);
							nextLayerMap.put(next, newBeauty);
						}
					}
				}
				nextLayer = nextLayerMap.keySet();
			} else {
				nextLayer = new HashSet<MetaProfile>();
				for (MetaProfile mp : init) {
					Collection<MetaProfile> nexts = mp.next();
					for (MetaProfile next : nexts) {
						nextLayer.add(next);
					}
				}
			}
			System.out.println("Layer size: " + nextLayer.size());
			total += nextLayer.size();
			init = nextLayer;
			
			MetaProfile found = null;
			boolean stop = false;
			good:
			for (MetaProfile good : desired) {
				if (nextLayer.contains(good)) {
					for (MetaProfile tried : nextLayer) {
						if (tried.equals(good)) {
							if (GadgetFinder.printAllWays) {
								tried.print();
								stop = true;
								continue good;
							}
							if (GadgetFinder.storeField && GadgetFinder.beautyful) {
								if (found == null || (((BeautifulMetaProfile) found).getBeauty() < (((BeautifulMetaProfile) tried).getBeauty()))) {
									found = tried;
								}
								continue good;
							}
							found = tried;
							continue good;
						}
					}
				}
			}
			if (found != null) {
				found.print();
				System.out.println("Total: " + total + " metaprofiles");
				stop = true;
			}
			boolean done = stop && !GadgetFinder.printAllSizes;
			if (done) {
				return;
			}
		}
	}
}
