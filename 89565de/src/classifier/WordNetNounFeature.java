package classifier;

import pos.Pos;
import source.SourceFactory;

public class WordNetNounFeature extends Feature {
	public WordNetNounFeature() {
		this.pos=Pos.NOUN;
		this.source=SourceFactory.getInstance().getSource("wordnet");

	}
}
