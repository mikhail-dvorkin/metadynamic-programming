package meta.minesweeper;

import java.util.*;

public class MinesweeperProfile {
	protected byte[] b;
	protected byte h;
	
	public MinesweeperProfile(byte[] b, byte h) {
		this.b = b;
		this.h = h;
	}
	
	@Override
	protected MinesweeperProfile clone() {
		return new MinesweeperProfile(b.clone(), h);
	}
	
	@Override
	public String toString() {
		return h + "*" + Arrays.toString(b);
	}

//	byte getCurrentRow() {
//		return b[h];
//	}
}
