package meta;

public interface TransitionMatrix {
	public int getSize();
	
	public int getTransitionsFrom(int profile);
	
	public int getTransitionProfile(int profile, int index);
	
	public int getTransitionCell(int profile, int index);
}
