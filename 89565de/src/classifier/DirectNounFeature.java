package classifier;

import pos.Pos;
import source.SourceFactory;

public class DirectNounFeature extends Feature {
	public DirectNounFeature() {
		this.pos=Pos.NOUN;
		this.source=SourceFactory.getInstance().getSource("direct");
	}
}
