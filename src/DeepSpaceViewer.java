/**
 * DeepSpaceViewer.java
 * class for a weapon
 * @author carl zhang
 * @version 1.0
 */
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

	public void setLevel(int level){
		super.setUpgradeLevel(level);
		this.accuracy=10*super.getUpgradeLevel();
		this.intellectiumNeeded=10*super.getUpgradeLevel();
	}
}
