package meta;

import java.math.BigInteger;
import java.util.Arrays;

public class MyHashProfileCollection extends ProfileCollection {
	private int mod;
	private int[] head;
	private long[] profiles;
	private int[] next;
	private int size;
	
	public MyHashProfileCollection(int capacity) {
		mod = BigInteger.valueOf(capacity).nextProbablePrime().intValue();
		head = new int[mod];
		Arrays.fill(head, -1);
		profiles = new long[capacity];
		next = new int[capacity];
		size = 0;
	}

	@Override
	public int add(long profile) {
		int number = getNumber(profile);
		if (number == -1) {
			int m = (int) (profile % mod);
			number = size++;
			profiles[number] = profile;
			next[number] = head[m];
			head[m] = number;
		}
		return number;
	}

	@Override
	public int getNumber(long profile) {
		int cur = head[(int) (profile % mod)];
		while (cur >= 0) {
			if (profiles[cur] == profile)
				return cur;
			cur = next[cur];
		}
		return -1;
	}

	@Override
	public long getProfile(int number) {
		return profiles[number];
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public long[] toArray() {
		return Arrays.copyOf(profiles, size);
	}

}
