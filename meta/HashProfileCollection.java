package meta;

import java.util.*;
import tools.*;

public class HashProfileCollection extends ProfileCollection {
	private final Map<Long, Integer> profile2number = new HashMap<Long, Integer>();
	private final List<Long> number2profile = new ArrayList<Long>();

	@Override
	public int getNumber(long profile) {
		return profile2number.get(profile);
	}

	@Override
	public long getProfile(int number) {
		return number2profile.get(number);
	}

	@Override
	public int getSize() {
		return number2profile.size();
	}

	@Override
	public int add(long profile) {
		Integer index = profile2number.get(profile);
		if (index == null) {
			index = number2profile.size();
			profile2number.put(profile, index);
			number2profile.add(profile);
		}
		return index;
	}

	@Override
	public long[] toArray() {
		return LongArrays.toArray(number2profile);
	}
}
