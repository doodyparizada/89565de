package classifier;

import pos.Pos;
import source.DirectAdapter;
import source.SourceFactory;

public class DirectVerbFeature extends Feature {
	public DirectVerbFeature() {
		this.pos=Pos.VERB;
		this.source =  DirectAdapter.NAME;;

	}
}
