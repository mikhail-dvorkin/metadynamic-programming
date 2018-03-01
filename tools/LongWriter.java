package tools;

public class LongWriter {
	private long data;
	
	public LongWriter() {
	}
	
	public long getData() {
		return data;
	}
	
	public void write(int value, int max) {
		data = data * max + value;
	}
	
	public void writeArray(int[] values, int max) {
		for (int value : values) {
			write(value, max);
		}
	}
	
	public void writeArray(int[] values, int[] max) {
		if (values.length != max.length)
			throw new IllegalArgumentException("values.length != max.length");
		for (int i = 0; i < values.length; i++) {
			write(values[i], max[i]);
		}
	}
	
	public void writeArray(byte[] values, int max) {
		for (byte value : values) {
			write(value, max);
		}
	}
	
	public void writeArray(byte[] values, int[] max) {
		if (values.length != max.length)
			throw new IllegalArgumentException("values.length != max.length");
		for (int i = 0; i < values.length; i++) {
			write(values[i], max[i]);
		}
	}
}
