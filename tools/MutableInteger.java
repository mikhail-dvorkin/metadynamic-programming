package tools;

public class MutableInteger {
	int value;

	public MutableInteger(int value) {
		this.value = value;
	}
	
	public MutableInteger() {
		this(0);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
