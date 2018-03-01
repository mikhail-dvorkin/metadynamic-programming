package meta;

import java.util.*;
import tools.*;

public class GadgetFinder implements Runnable {
//	public static final Problem2D problem = new meta.triminoes.Triminoes(3, 7);
	public static final Problem2D problem = new meta.kakuro.Kakuro(4, 5);
//	public static final Problem2D problem = new meta.triminoes.TriminoesVer(new int[]{2, 3}, 5);
//	public static final Problem2D problem = new meta.minesweeper.Minesweeper(1, 4);
	public static TransitionMatrix matrix;
	public static final boolean storeField = true;
	public static final boolean beautyful = (problem instanceof BeautifulProblem2D);
	public static final boolean printAllSizes = !false;
	public static final boolean printAllWays = false;
	
	public static void main(String[] args) {
		new GadgetFinder().run();
	}

	@Override
	public void run() {
//		findGadget("ff,ft,tf,tt", "ff,ft,tf,tt");
		
//		// single wire
		findGadget("f,t", "f,t");
//		
//		// double wire
//		findGadget("ft,tf", "ft,tf");
//		
//		// single wire introduction
//		findGadget("", "f t");
//		
//		// double wire introduction
//		findGadget("", "ft tf");
//		
//		// single wire enforcer
//		findGadget("t,f", ",n");
//		
//		// single wire NOT
//		findGadget("f,t", "t,f");
//		
//		// single wire OR
//		findGadget("f,t", "ff,ft tf tt");
//		
//		// single wire AND
//		findGadget("f,t", "ff ft tf,tt");
//		
//		// single wire crossing
//		findGadget("ff,ft,tf,tt", "ff,tf,ft,tt");
//		
//		// single wire splitter
//		findGadget("f,t", "tt,ff");
//		
//		// naive double wire splitter
//		findGadget("ft,tf", "ftft,tftf");
//		
//		// smart double wire splitter
//		findGadget("f,t", "ftf,tft");
//		
//		// smarter double wire splitter
//		findGadget("tf,ft,tt ff", "tf,ft,n");
//		
//		// naive 1-in-3 for single wires
//		findGadget("fft,ftf,tff,fff ftt tft ttf ttt", ",,,n");
//		
//		// smart 1-in-3 for single wires
//		findGadget("ff,ft,tf,tt", "f,t,t,n");
//		
//		// naive 2-in-3 for single wires
//		findGadget("ftt,tft,ttf,fff fft ftf tff ttt", ",,,n");
//		
//		// smart 2-in-3 for single wires
//		findGadget("ff,ft,tf,tt", "n,f,f,t");
	}
	
	private static int[][] toArray(String situations, MutableInteger wires) {
		String[] s = situations.split(",");
		int[][] a = new int[s.length][];
		int w = -1;
		for (int i = 0; i < s.length; i++) {
			if ("n".equals(s[i])) {
				a[i] = IntArrays.int0;
				continue;
			}
			String[] t = s[i].split(" ");
			a[i] = new int[t.length];
			for (int j = 0; j < t.length; j++) {
				if (w >= 0 && t[j].length() != w)
					throw new IllegalArgumentException("Different situations have different numbers of wires: " + situations);
				w = t[j].length();
				a[i][j] = toBitMask(t[j]);
			}
		}
		wires.setValue(w);
		return a;
	}
	
	private static int toBitMask(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			result <<= 1;
			if (s.charAt(i) == 't')
				result++;
		}
		return result;
	}

	private static int[] concat(int[][] arrays) {
		int size = 0;
		for (int[] a : arrays) {
			size += a.length;
		}
		int[] result = new int[size];
		int i = 0;
		for (int[] a : arrays) {
			for (int v : a) {
				result[i++] = v;
			}
		}
		return result;
	}

	private static void findGadget(String inSituations, String outSituations) {
		MutableInteger temp = new MutableInteger();
		int[][] ins = toArray(inSituations, temp);
		int inW = temp.getValue();
		int[][] outs = toArray(outSituations, temp);
		int outW = temp.getValue();
		
		List<long[]> inWires = problem.wires(inW, true);
		List<long[]> outWires = problem.wires(outW, false);
		
		int[] inputMasks = concat(ins);
		long[] initProfiles = new long[inWires.size() * inputMasks.length];
		int i = 0;
		for (long[] p: inWires) {
			for (int mask : inputMasks) {
				initProfiles[i++] = p[mask];
			}
		}
		
		ProfileCollection pc = ProfileEnumerator.enumerate(initProfiles);
		System.out.println("Profile collection calculated, " + pc.getSize() + " profiles");
		
		Set<MetaProfile> init = new HashSet<MetaProfile>();
		for (long[] p: inWires) {
			int[][] numbers = new int[ins.length][];
			for (i = 0; i < ins.length; i++) {
				numbers[i] = new int[ins[i].length];
				for (int j = 0; j < ins[i].length; j++) {
					numbers[i][j] = pc.getNumber(p[ins[i][j]]);
				}
			}
			init.add(MetaProfileFactory.make(numbers));
		}
		System.out.println("Initial metaprofiles found, " + init.size() + " metaprofiles");
		
		Set<MetaProfile> desired = new HashSet<MetaProfile>();
		for (long[] p: outWires) {
			int[][] numbers = new int[outs.length][];
			for (i = 0; i < outs.length; i++) {
				numbers[i] = new int[outs[i].length];
				for (int j = 0; j < outs[i].length; j++) {
					numbers[i][j] = pc.getNumber(p[outs[i][j]]);
				}
			}
			desired.add(MetaProfileFactory.make(numbers));
		}
		System.out.println("Desired metaprofiles found, " + desired.size() + " metaprofiles");
		
		matrix = new SingleArrayTransitionMatrix(pc);
		pc = null; System.gc();
		System.out.println("Transition matrix calculated");
		
		MetaProfileProcessor.work(init, desired);
	}
}
