package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveAdverbFeature extends Feature {
		public NaiveAdverbFeature() {
			this.pos=Pos.ADVERB;
			this.source=NaiveSource.NAME;
		}
}
