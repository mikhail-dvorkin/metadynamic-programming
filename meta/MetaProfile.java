package meta;

import java.util.*;

public interface MetaProfile {
	public Collection<MetaProfile> next();

	public void print();
}
