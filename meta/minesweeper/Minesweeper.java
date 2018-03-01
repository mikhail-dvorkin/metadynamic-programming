package meta.minesweeper;

import java.util.*;

import meta.*;
import tools.*;

public class Minesweeper extends BeautifulProblem2D {
	private final int height, n;
	
	public Minesweeper(int n, int height) {
		this.n = n;
		this.height = height;
	}
	
	private long serialize(MinesweeperProfile profile) {
		LongWriter lw = new LongWriter();
		lw.writeArray(profile.b, n + 3);
		lw.write(profile.h, height);
		return lw.getData();
	}
	
	private MinesweeperProfile deserialize(long data) {
		LongReader lr = new LongReader(data);
		byte h = lr.readByte(height);
		byte[] b = lr.readByteArray(height + 1, n + 3);
		return new MinesweeperProfile(b, h);
	}

	@Override
	public List<NextProfile> next(long profile) {
		MinesweeperProfile o = deserialize(profile);
		List<NextProfile> next = new ArrayList<NextProfile>();
		for (int mine = 0; mine < 2; mine++) {
			MinesweeperProfile p = o.clone();
			if (p.h - 1 >= 0 && p.b[p.h - 1] <= n) {
				p.b[p.h - 1] -= mine;
				if (p.b[p.h - 1] < 0)
					continue;
			}
			if (p.b[p.h] <= n) {
				if (p.b[p.h] != mine)
					continue;
			}
			p.b[p.h] = (byte) (n + 1 + mine);
			if (p.b[p.h + 1] <= n) {
				p.b[p.h + 1] -= mine;
				if (p.b[p.h + 1] < 0)
					continue;
			}
			if (p.h + 2 <= height && p.b[p.h + 2] <= n) {
				p.b[p.h + 2] -= mine;
				if (p.b[p.h + 2] < 0)
					continue;
			}
			p.h++;
			if (p.h == height) {
				if (mine == 1)
					break;
				if (p.b[p.h] != 0 && p.b[p.h] <= n)
					continue;
				System.arraycopy(p.b, 0, p.b, 1, height);
				p.b[0] = (byte) 0;
				p.h = 0;
			}
			next.add(new NextProfile(serialize(p), n + 1));
		}
		if (o.b[o.h] > n || o.b[o.h] == 0) {
			for (byte i = 0; i <= n; i++) {
				MinesweeperProfile p = o.clone();
				byte j = i;
				for (int d = -1; d <= 2; d++) {
					if (p.h + d >= 0 && p.h + d <= height && p.b[p.h + d] == n + 2) {
						j--;
					}
				}
				if (j < 0)
					continue;
				p.b[p.h] = j;
				p.h++;
				if (p.h == height) {
					if (p.b[p.h] != 0 && p.b[p.h] != n + 1)
						break;
					System.arraycopy(p.b, 0, p.b, 1, height);
					p.b[0] = (byte) 0;
					p.h = 0;
				}
				next.add(new NextProfile(serialize(p), i));
			}
		}
		return next;
	}

	@Override
	public int getBeauty(int cell) {
		if (cell == n + 1)
			return 0;
		return 256 - cell * cell;
	}

	@Override
	public List<long[]> wires(int w, @SuppressWarnings("unused") boolean in) {
		List<long[]> result = new ArrayList<long[]>();
		result.add(makeWire(0, 3, w));
		return result;
	}
	
	public long[] makeWire(int y, int dist, int w) {
		long[] profiles = new long[1 << w];
		for (int mask = 0; mask < (1 << w); mask++) {
			profiles[mask] = makeWire(y, dist, w, mask);
		}
		return profiles;
	}

	private long makeWire(int y, int dist, int w, int mask) {
		byte[] b = new byte[height + 1];
		for (int i = 0; i < w; i++) {
			if (Bits.isSet(mask, i)) {
				b[y + 1] = b[y + 2] = b[y + 3] = 1;
			}
			y += dist;
		}
		return serialize(new MinesweeperProfile(b, (byte) 0));
	}

	@Override
	public void printField(List<Integer> field) {
		int wid = (field.size() + height - 1) / height;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < wid; j++) {
				int index = j * height + i;
				char c;
				if (index >= field.size())
					c = '?';
				else
					c = (field.get(index) == n + 1) ? '.' : (char) ('0' + field.get(index));
				System.out.print(c);
			}
			System.out.println();
		}
	}
}
