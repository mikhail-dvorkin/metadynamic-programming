package meta.triminoes;

import java.util.*;

import meta.*;

import tools.*;

public class Triminoes extends BeautifulProblem2D {
	private final int height, t;
	
	public Triminoes(int t, int height) {
		this.t = t;
		this.height = height;
	}
	
	private long serialize(TriminoesProfile profile) {
		LongWriter lw = new LongWriter();
		lw.write(profile.b, 1 << height);
		lw.write(profile.h, height);
		return lw.getData();
	}
	
	private TriminoesProfile deserialize(long data) {
		LongReader lr = new LongReader(data);
		byte h = lr.readByte(height);
		int b = lr.readInt(1 << height);
		return new TriminoesProfile(b, h);
	}

	@Override
	public List<NextProfile> next(long profile) {
		TriminoesProfile o = deserialize(profile);
		if (Bits.isSet(o.b, o.h)) {
			TriminoesProfile p = o.clone();
			p.b = Bits.clear(p.b, p.h);
			p.h++;
			if (p.h == height) {
				p.h = 0;
			}
			return Collections.singletonList(new NextProfile(serialize(p), 0));
		}
		List<NextProfile> next = new ArrayList<NextProfile>();
		TriminoesProfile p = o.clone();
		p.h++;
		if (p.h == height) {
			p.h = 0;
		}
		next.add(new NextProfile(serialize(p), 1));
		p.b = Bits.set(p.b, o.h);
		next.add(new NextProfile(serialize(p), 0));
		if (o.h + t <= height && Bits.isClear(o.b, o.h, o.h + t)) {
			p.b = Bits.set(o.b, o.h + 1, o.h + t);
			next.add(new NextProfile(serialize(p), 0));
		}
		return next;
	}

	@Override
	public List<long[]> wires(int w, @SuppressWarnings("unused") boolean in) {
		List<long[]> result = new ArrayList<long[]>();
		int d = 5;//t;
		int f = (height - d * (w - 1)) / 2;
//		int f = 0;
//		int f = in ? 0 : 3;
//		for (int d = 1; d < height; d++) {
//			for (int f = 0; f + d * (w - 1) < height; f++) {
				long[] profiles = new long[1 << w];
				for (int mask = 0; mask < (1 << w); mask++) {
					int b = 0;
					for (int i = 0, p = f; i < w; i++, p += d) {
						if (Bits.isSet(mask, i)) {
							b = Bits.set(b, p);
						}
					}
					profiles[mask] = serialize(new TriminoesProfile(b, (byte) 0));
				}
				result.add(profiles);
//			}
//		}
		return result;
	}

	@Override
	public int getBeauty(int cell) {
		return cell;
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
					c = (field.get(index) == 1) ? '#' : '.';
				System.out.print(c);
			}
			System.out.println();
		}
	}
}
