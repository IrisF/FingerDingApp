package ifi.lmu.com.handmeasurementstudy.system;

public class TrialSettingsFactory {

    //change these for debugging purposes (e.g. you don't want to touch 400 times for debugging...)
    private static final int TARGET_ROWS = 25; //for the study, use: 25
    private static final int TARGET_COLS = 16; //for the study, use: 16

	// Just a test/debug trial:
	public static TrialSettings createTrialSettingsTest() {

		TrialSettings ts = new TrialSettings(2, 2, 180, 80,
				TrialSettings.SETTING_INPUT_STYLE_THUMB, TrialSettings.SETTING_MOBILITY_SITTING, 0);
		return ts;
	}

	public static TrialSettings createTrialSettingsByTrialID(int trialID) {
		switch (trialID) {
		case 0:
			return TrialSettingsFactory.createTrialSettingsTest();

        // sitting:
		case 1:
			return TrialSettingsFactory.createTrialSettingsPointThumbSitting();
		case 2:
			return TrialSettingsFactory.createTrialSettingsPointIndexSitting();

        // walking:
        case 3:
            return TrialSettingsFactory.createTrialSettingsPointThumbWalking();
        case 4:
            return TrialSettingsFactory.createTrialSettingsPointIndexWalking();
		default:
			return null;
		}
	}


	public static TrialSettings createTrialSettingsPointThumbSitting() {
		return new TrialSettings(TARGET_COLS, TARGET_ROWS, 0, 0,
				TrialSettings.SETTING_INPUT_STYLE_THUMB, TrialSettings.SETTING_MOBILITY_SITTING, 1);
	}

	public static TrialSettings createTrialSettingsPointIndexSitting() {
		return new TrialSettings(TARGET_COLS, TARGET_ROWS, 0, 0,
				TrialSettings.SETTING_INPUT_STYLE_INDEX, TrialSettings.SETTING_MOBILITY_SITTING, 2);
	}

    public static TrialSettings createTrialSettingsPointThumbWalking() {
        return new TrialSettings(TARGET_COLS, TARGET_ROWS, 0, 0,
                TrialSettings.SETTING_INPUT_STYLE_THUMB, TrialSettings.SETTING_MOBILITY_WALKING, 3);
    }

    public static TrialSettings createTrialSettingsPointIndexWalking() {
        return new TrialSettings(TARGET_COLS, TARGET_ROWS, 0, 0,
                TrialSettings.SETTING_INPUT_STYLE_INDEX, TrialSettings.SETTING_MOBILITY_WALKING, 4);
    }


}
