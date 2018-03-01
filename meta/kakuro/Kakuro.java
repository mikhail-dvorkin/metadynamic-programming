package meta.kakuro;

import java.util.*;

import meta.*;

import tools.*;

public class Kakuro extends BeautifulProblem2D {
	private final int height, n, n2, max;
	private final int[] sum;
	private final int[] ones;
	
	public Kakuro(int n, int height) {
		this.n = n;
		this.height = height;
		
		n2 = n * n;
		max = 1 << n;
		
		sum = new int[max];
		ones = new int[max];
		for (int mask = 0; mask < max; mask++) {
			sum[mask] = Bits.bitSum(mask, n);
			ones[mask] = Bits.bitCount(mask);
		}
	}
	
	private long serialize(KakuroProfile profile) {
		LongWriter lw = new LongWriter();
		lw.writeArray(profile.b, max);
		lw.write(profile.c, max);
		lw.write(profile.h, height);
		return lw.getData();
	}
	
	private KakuroProfile deserialize(long data) {
		LongReader lr = new LongReader(data);
		byte h = lr.readByte(height);
		byte c = lr.readByte(max);
		byte[] b = lr.readByteArray(height, max);
		return new KakuroProfile(b, c, h);
	}

	@Override
	public List<NextProfile> next(long profile) {
		KakuroProfile o = deserialize(profile);
		List<NextProfile> next = new ArrayList<NextProfile>();
		{
			int hs = sum[o.getCurrentRow()];
			int hn = ones[o.getCurrentRow()];
			int vs = sum[o.getColumn()];
			int vn = ones[o.getColumn()];
			if (hn != 1 && vn != 1) {				
				KakuroProfile p = o.clone();
				p.b[p.h] = 0;
				p.c = 0;
				p.h++;
				if (p.h == height) {
					p.h = 0;
				}
				next.add(new NextProfile(serialize(p), cell(hs, hn, vs, vn)));
			}
		}
		for (int v = 0; v < n; v++) {
			if (Bits.isSet(o.getColumn(), v) || Bits.isSet(o.getCurrentRow(), v)) {
				continue;
			}
			
			KakuroProfile p = o.clone();
			p.b[p.h] = (byte) Bits.set(p.b[p.h], v);
			p.c = (byte) Bits.set(p.c, v);
			p.h++;
			if (p.h == height) {
				p.h = 0;
				int vs = sum[p.getColumn()];
				int vn = ones[p.getColumn()];
				p.c = 0;
				if (vn != 1) {
					next.add(new NextProfile(serialize(p), cell(0, 1, vs, vn)));
				}
			} else {
				next.add(new NextProfile(serialize(p), cell(0, 1, 0, 0)));
			}
		}
		return next;
	}

	private int cell(int hs, int hn, int vs, int vn) {
		return ((hs * n2 + hn) * n2 + vs) * n2 + vn;
	}

	private static int coef(int clue) {
		return clue * clue;
	}
	
	@Override
	public int getBeauty(int cell) {
		int vn = cell % n2;
		cell /= n2;
		int vs = cell % n2 + vn;
		cell /= n2;
		int hn = cell % n2;
		int hs = cell / n2 + hn;
		if (hn != 1)
			return 1000000 - coef(hn + hs) - coef(vn + vs);
		return - coef(vn + vs);
	}

	@Override
	public List<long[]> wires(int w, boolean in) {
		List<long[]> result = new ArrayList<long[]>();
		searchWires(new int[height], 0, w, 0, in, result);
		return result;
	}
	
	public void searchWires(int[] a, int y, int w, int up, boolean in, List<long[]> result) {
		if (w == 0) {
			for (; y < height; y++)
				a[y] = 0;
			foundWire(a, result);
			return;
		}
		if (y >= height)
			return;
		a[y] = 0;
		searchWires(a, y + 1, w, up, in, result);
		if (in) {
			if (up == 0) {
				if (y + 1 < height) {
					a[y] = max - 1;
					a[y + 1] = -3;
					if (y + 2 < height) {
						a[y + 2] = 0;
					}
					searchWires(a, y + 3, w - 1, 0, in, result);
				}
				if (y == 0) {
					a[y] = -3;
					a[y + 1] = 0;
					searchWires(a, y + 2, w - 1, 0, in, result);
				}
			}
			a[y] = -3;
			if (y + 1 < height) {
				a[y + 1] = max - 1;
				if (y + 2 < height) {
					a[y + 2] = 0;
				}
			}
			searchWires(a, y + 3, w - 1, 1, in, result);
		} else {
			if (y + 1 >= height)
				return;
			for (int m1 = 3; m1 < max; m1++) {
				if (Bits.bitCount(m1) < 2)
					continue;
				for (int m2 = 3; m2 < max; m2++) {
					if ((m2 & 3) != 3)
						continue;
					if (up == 0) {
						a[y] = -m2;
						a[y + 1] = m1;
						if (y + 2 < height) {
							a[y + 2] = 0;
						}
						searchWires(a, y + 3, w - 1, 0, in, result);
					}
					a[y] = m1;
					a[y + 1] = -m2;
					if (y + 2 < height) {
						a[y + 2] = 0;
					}
					searchWires(a, y + 3, w - 1, 1, in, result);
				}
			}
		}
	}
	
	private void foundWire(int[] a, List<long[]> result) {
		int w = 0;
		for (int v : a) {
			if (v < 0)
				w++;
		}
		long[] wire = new long[1 << w];
		byte[] b = new byte[height];
		for (int mask = 0; mask < (1 << w); mask++) {
			int i = 0;
			for (int y = 0; y < height; y++) {
				if (a[y] >= 0) {
					b[y] = (byte) a[y];
				} else {
					b[y] = (byte) ((-a[y]) ^ (Bits.isSet(mask, i++) ? 1 : 2));
				}
			}
			wire[mask] = serialize(new KakuroProfile(b, (byte) 0, (byte) 0));
		}
		result.add(wire);
	}

//	@Override
//	public List<long[]> inWires(int w) {
//		ArrayList<long[]> result = new ArrayList<long[]>();
//		for (int i = 0; i + 3 * (w - 1) < height; i++) {
//			result.add(wires(w, i, 1, (byte) (max - 1)));
//			result.add(wires(w, i, -1, (byte) (max - 1)));
//		}
//		return result;
//	}
//
//	@Override
//	public List<long[]> outWires(int w) {
//		ArrayList<long[]> result = new ArrayList<long[]>();
//		for (int i = 0; i + 3 * (w - 1) < height; i++) {
//			result.add(wires(w, i, 1, (byte) 3));
//			result.add(wires(w, i, -1, (byte) 3));
//		}
//		return result;
//	}
//	
//	private long[] wires(int w, int shift, int delta, byte value) {
//		long[] result = new long[1 << w];
//		for (int mask = 0; mask < (1 << w); mask++) {
//			result[mask] = wires(w, shift, delta, value, mask);
//		}
//		return result;
//	}
//
//	private long wires(int w, int shift, int delta, byte value, int mask) {
//		byte[] b = new byte[height];
//		for (int i = 0; i < w; i++) {
//			b[shift] = Bits.isSet(mask, i) ? (byte) 2 : (byte) 1;
//			if (0 <= shift + delta && shift + delta < height) {
//				b[shift + delta] = value;
//			}
//			shift += 3;
//		}
//		return serialize(new KakuroProfile(b, (byte) 0, (byte) 0));
//	}
	
	@Override
	public void printField(List<Integer> field) {
		int wid = (field.size() + height - 1) / height;
		int s = 3;
		char[][] c = new char[s * height][s * wid];
		for (int i = 0; i < s * height; i++)
			Arrays.fill(c[i], ' ');
		int lp = 0;
		for (int j = 0; j < wid; j++) {
			for (int i = 0; i < height; i++) {
				int cell = (lp < field.size()) ? field.get(lp++) : 0;
				int vn = cell % n2;
				cell /= n2;
				int vs = cell % n2 + vn;
				cell /= n2;
				int hn = cell % n2;
				int hs = cell / n2 + hn;
				if (hn != 1) {
					for (int x = 0; x < s; x++) {
						for (int y = 0; y < s; y++) {
							c[s * i + x][s * j + y] = '#';
						}
					}
					if (hn > 0 && j > 0) {
						c[s * i + s / 2][s * j - 1] = (char) ('0' + hs % 10);
						if (hs >= 10)
							c[s * i + s / 2][s * j - 2] = (char) ('0' + hs / 10);
					}
					if (vn > 0) {
						c[s * i - 1][s * j + s / 2] = (char) ('0' + vs % 10);
						if (vs >= 10)
							c[s * i - 1][s * j + s / 2 - 1] = (char) ('0' + vs / 10);
					}
				} else {
					if (vn > 0) {
						c[s * i + s - 1][s * j + s / 2] = (char) ('0' + vs % 10);
						if (vs >= 10)
							c[s * i + s - 1][s * j + s / 2 - 1] = (char) ('0' + vs / 10);
					}
				}
			}
		}
		for (int i = s * height - 1; i >= 0; i--) {
			for (int j = 0; j < s * wid; j++) {
				System.out.print(c[i][j]);
			}
			System.out.println();
		}
	}
}
