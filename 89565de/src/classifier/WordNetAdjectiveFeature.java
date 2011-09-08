package classifier;

import pos.Pos;
import source.SourceFactory;

public class WordNetAdjectiveFeature extends Feature {
	public WordNetAdjectiveFeature() {
		this.pos=Pos.ADJECTIVE;
		this.source=SourceFactory.getInstance().getSource("wordnet");

	}
}
