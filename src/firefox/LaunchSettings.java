package firefox;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import common.FirefoxCommon;

public class LaunchSettings extends UiAutomatorTestCase {

	public void testDemo() throws UiObjectNotFoundException {
		new FirefoxCommon(this, "firefox", false).testDemo();
	}

}
