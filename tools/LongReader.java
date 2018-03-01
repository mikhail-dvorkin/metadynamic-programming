package tools;

public class LongReader {
	private long data;

	public LongReader(long data) {
		this.data = data;
	}
	
	public int readInt(int max) {
		int value = (int) (data % max);
		data /= max;
		return value;
	}
	
	public int[] readIntArray(int length, int max) {
		int[] values = new int[length];
		for (int i = length - 1; i >= 0; i--) {
			values[i] = readInt(max);
		}
		return values;
	}
	
	public int[] readIntArray(int[] max) {
		int length = max.length;
		int[] values = new int[length];
		for (int i = length - 1; i >= 0; i--) {
			values[i] = readInt(max[i]);
		}
		return values;
	}
	
	public byte readByte(int max) {
		byte value = (byte) (data % max);
		data /= max;
		return value;
	}
	
	public byte[] readByteArray(int length, int max) {
		byte[] values = new byte[length];
		for (int i = length - 1; i >= 0; i--) {
			values[i] = readByte(max);
		}
		return values;
	}
	
	public byte[] readByteArray(int[] max) {
		int length = max.length;
		byte[] values = new byte[length];
		for (int i = length - 1; i >= 0; i--) {
			values[i] = readByte(max[i]);
		}
		return values;
	}
}
