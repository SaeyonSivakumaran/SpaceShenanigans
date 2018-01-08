
public class DeepSpaceViewer extends Module {

	int accuracy, intellectiumNeeded;

	public DeepSpaceViewer() {
		super();
		this.accuracy = 20;
		this.intellectiumNeeded = 20;
	}

	public void upgrade() {
		super.upgrade();
		this.accuracy += 20;
		this.intellectiumNeeded *= 2;
	}

	public int getAccuracy() {
		return this.accuracy;
	}

	public int getIntellectium() {
		return this.intellectiumNeeded;
	}
}