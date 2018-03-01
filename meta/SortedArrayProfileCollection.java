package meta;

import java.util.*;

public class SortedArrayProfileCollection extends ProfileCollection {
	private final long[] profiles;

	public SortedArrayProfileCollection(long[] profiles) {
		this.profiles = profiles;
		Arrays.sort(profiles);
	}

	@Override
	public int getSize() {
		return profiles.length;
	}

	@Override
	public int getNumber(long profile) {
		return Arrays.binarySearch(profiles, profile);
	}

	@Override
	public long getProfile(int number) {
		return profiles[number];
	}

	@Override
	public int add(@SuppressWarnings("unused") long profile) {
		throw new UnsupportedOperationException("SortedArrayProfileCollection is immutable and doesn't suppot add()");
	}

	@Override
	public long[] toArray() {
		return profiles;
	}
}
