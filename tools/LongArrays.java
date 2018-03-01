package tools;

import java.io.*;
import java.util.*;

public class LongArrays {
	public static long[] toArray(List<Long> list) {
		int length = list.size();
		long[] array = new long[length];
		int i = 0;
		for (Iterator<Long> iterator = list.iterator(); iterator.hasNext(); i++) {
			array[i] = iterator.next();
		}
		return array;
	}
	
	public static long[] readLongArray(File file) {
		try {
			System.out.print("Reading from " + file.getName() + "...");
			BufferedReader in = new BufferedReader(new FileReader(file));
			int length = Integer.parseInt(in.readLine());
			long[] array = new long[length];
			for (int i = 0; i < length; i++) {
				array[i] = Integer.parseInt(in.readLine());
			}
			System.out.println(" done");
			in.close();
			return array;
		} catch (IOException e) {
			throw new RuntimeException("IOException when reading long[]", e);
		}
	}
}
