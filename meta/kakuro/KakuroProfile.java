package meta.kakuro;

import java.util.*;

public class KakuroProfile {
	protected byte[] b;
	protected byte c;
	protected byte h;
	
	public KakuroProfile(byte[] b, byte c, byte h) {
		this.b = b;
		this.c = c;
		this.h = h;
	}
	
	@Override
	protected KakuroProfile clone() {
		return new KakuroProfile(b.clone(), c, h);
	}
	
	@Override
	public String toString() {
		return h + "*" + Arrays.toString(b) + "*" + c;
	}

	public byte getColumn() {
		return c;
	}

	byte getCurrentRow() {
		return b[h];
	}
}
