package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveCarFeature extends Feature {
	public NaiveCarFeature() {
		this.pos=Pos.CAR;
		this.source=SourceFactory.getInstance().getSource("naive");

	}
}
