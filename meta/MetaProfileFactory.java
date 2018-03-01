package meta;

public class MetaProfileFactory {
	public static MetaProfile make(int[][] numbers) {
		if (GadgetFinder.storeField) {
			if (GadgetFinder.beautyful) {
				return new BeautifulListMetaProfile(numbers, null, -1, 0);
			} else {
				return new RetentiveListMetaProfile(numbers, null, -1);
			}
		} else {
			return new ListMetaProfile(numbers);
		}
	}
}
