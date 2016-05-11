package ifi.lmu.com.handmeasurementstudy.system;


public class TrialSettings {

	public static final int SETTING_INPUT_STYLE_THUMB = 0;
	public static final int SETTING_INPUT_STYLE_INDEX = 1;

    public static final int SETTING_MOBILITY_SITTING = 0;
    public static final int SETTING_MOBILITY_WALKING = 1;

	protected int grid_steps_x;
	protected int grid_steps_y;
	protected int button_width;
	protected int button_height;
	protected int input_style;
    protected int mobility;
	protected int num_targets;
	protected int trial_mode;

	public TrialSettings(int grid_steps_x, int grid_steps_y, int button_width,
			int button_height, int input_style, int mobility, int trial_mode) {
		
		this.grid_steps_x = grid_steps_x;
		this.grid_steps_y = grid_steps_y;
		this.button_width = button_width;
		this.button_height = button_height;
		this.input_style = input_style;
        this.mobility = mobility;
		this.num_targets = this.grid_steps_x * this.grid_steps_y;
		this.trial_mode = trial_mode;
	}

	public int getGridStepsX() {
		return this.grid_steps_x;
	}

	public int getGridStepsY() {
		return this.grid_steps_y;
	}

	public int getButtonWidth() {
		return this.button_width;
	}

	public int getButtonHeight() {
		return this.button_height;
	}

	public int getInputStyle() {
		return this.input_style;
	}

    public int getMobility() {
        return this.mobility;
    }

	public int getNumTargets() {
		return this.num_targets;
	}

	public void setButtonWidth(int width) {
		this.button_width = width;
	}

	public int getTrialMode() {
		return this.trial_mode;
	}
}
