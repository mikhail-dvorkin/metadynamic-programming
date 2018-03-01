package meta;

import java.io.*;

public abstract class ProfileCollection {
	private int transitions;
	
	public abstract int getSize();
	
	public abstract int getNumber(long profile);
	
	public abstract long getProfile(int number);
	
	public abstract int add(long profile);
	
	public abstract long[] toArray();
	
	public void print(File file) {
		System.out.print("Printing  to " + file.getName() + "...");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file), 1000000);
//			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file), 1000000));
			writer.write(Integer.toString(getSize()));
			writer.write('\n');
			for (int i = 0; i < getSize(); i++) {
				writer.write(Long.toString(getProfile(i)));
				writer.write('\n');
//				writer.println(getProfile(i));
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException when printing ProfileCollection", e);
		}
		System.out.println(" done");
	}
	
	public int getTransitions() {
		return transitions;
	}

	public void setTransitions(int transitions) {
		this.transitions = transitions;
	}
}
