package firefox;

import utils.Utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static final String URL_BAR_ID = "org.mozilla.firefox_beta:id/url_bar_title";
	private static final String URL_EDIT_ID = "org.mozilla.firefox_beta:id/url_edit_text";
	private static final String[] WEBSITES = { "http://multpath-tcp.org",
		"http://www.google.com/", "http://www.facebook.com/",
		"http://www.youtube.com/", "http://www.yahoo.com/",
		"http://www.baidu.com/", "http://www.amazon.com/",
		"http://www.wikipedia.org/", "http://www.taobao.com/",
		"http://www.twitter.com/", "http://www.qq.com/",
		"https://imgur.com/", "https://www.flickr.com/",
		"http://www.nytimes.com/" };

	private void visitWebsite(String url) {
		Utils.click(URL_BAR_ID);
		Utils.setText(URL_EDIT_ID, url);
		getUiDevice().pressEnter();
	}

	public void testDemo() throws UiObjectNotFoundException {
		assertTrue("OOOOOpps",
				Utils.openApp(this, "Firefox Beta", "org.mozilla.firefox_beta"));

		sleep(3000);

		// Settings / Privacy / Last option
		System.out.println("\n\n\t## DON'T FORGET TO ENABLE OPTION TO REMOVE TRACES ##\n\n");
		// or use private mode, or remove traces manually

		Utils.launchTcpdump("firefox", 200);

		for (int i = 0; i < WEBSITES.length; i++) {
			visitWebsite(WEBSITES[i]);
			sleep(20000);
		}

		Utils.killTcpdump();
	}

}
