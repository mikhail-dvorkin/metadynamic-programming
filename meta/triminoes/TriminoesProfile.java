package meta.triminoes;

public class TriminoesProfile {
	protected int b;
	protected byte h;
	
	public TriminoesProfile(int b, byte h) {
		this.b = b;
		this.h = h;
	}
	
	@Override
	protected TriminoesProfile clone() {
		return new TriminoesProfile(b, h);
	}
	
	@Override
	public String toString() {
		return Integer.toBinaryString(b) + ":" + h;
	}
}
