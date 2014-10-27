package firefox;

import utils.Utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static final String URL_BAR_ID = "org.mozilla.firefox_beta:id/url_bar_title";
	private static final String URL_EDIT_ID = "org.mozilla.firefox_beta:id/url_edit_text";

	private void visitWebsite(String url) {
		Utils.click(URL_BAR_ID);
		Utils.setText(URL_EDIT_ID, url);
		getUiDevice().pressEnter();
	}

	public void testDemo() throws UiObjectNotFoundException {
		assertTrue("OOOOOpps",
				Utils.openApp(this, "Firefox Beta", "org.mozilla.firefox_beta"));

		sleep(3000);

		Utils.launchTcpdump("firefox", 200);

		visitWebsite("http://multpath-tcp.org");
		sleep(20000);
		visitWebsite("https://www.flickr.com/");
		sleep(20000);
		visitWebsite("http://www.nytimes.com/");
		sleep(20000);
		visitWebsite("http://www.rtl.be/info");
		sleep(20000);
		visitWebsite("http://www.rtbf.be/");
		sleep(20000);
		visitWebsite("http://www.alexa.com/topsites");
		sleep(20000);

		Utils.killTcpdump();
	}

}