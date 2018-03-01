package meta;

public class NextProfile {
	private final long profile;
	private final int cell;
		
	public NextProfile(Long profile, int cell) {
		this.profile = profile;
		this.cell = cell;
	}

	public long getProfile() {
		return profile;
	}

	public int getCell() {
		return cell;
	}
}
