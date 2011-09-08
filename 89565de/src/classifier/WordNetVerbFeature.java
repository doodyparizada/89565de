package classifier;

import pos.Pos;
import source.SourceFactory;

public class WordNetVerbFeature extends Feature {
	public WordNetVerbFeature() {
		this.pos=Pos.VERB;
		this.source=SourceFactory.getInstance().getSource("wordnet");

	}
}
