package classifier;

import pos.Pos;
import source.SourceFactory;

public class WordNetAdverbFeature extends Feature {
	public WordNetAdverbFeature() {
		this.pos=Pos.ADVERB;
		this.source=SourceFactory.getInstance().getSource("wordnet");

	}
}
