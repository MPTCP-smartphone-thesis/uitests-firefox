package common;

import utils.Utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class FirefoxCommon {
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
	private static final int LIMIT = 14;

	private final UiAutomatorTestCase testCase;
	private final String tcpdump;
	private final boolean withProxy;

	public FirefoxCommon(UiAutomatorTestCase testCase, String tcpdump,
			boolean withProxy) {
		this.testCase = testCase;
		this.tcpdump = tcpdump;
		this.withProxy = withProxy;
	}

	private void visitWebsite(String url) {
		Utils.click(URL_BAR_ID);
		Utils.setText(URL_EDIT_ID, url);
		testCase.getUiDevice().pressEnter();
	}

	private void proxy() {
		// There are two different buttons to indicate if proxy is up or down
		UiObject enableProxy = Utils.getObjectWithDescription("Enable Proxy");
		UiObject disableProxy = Utils.getObjectWithDescription("Disable Proxy");

		while (!enableProxy.exists() && !disableProxy.exists()) {
			testCase.sleep(500);
			enableProxy = Utils.getObjectWithDescription("Enable Proxy");
			disableProxy = Utils.getObjectWithDescription("Disable Proxy");
		}

		if (withProxy) {
			// If down, set up proxy
			if (enableProxy.exists()) {
				Utils.click(enableProxy);
			}
		} else {
			// If up, set down proxy
			if (disableProxy.exists()) {
				Utils.click(disableProxy);
			}
		}
	}

	public void testDemo() throws UiObjectNotFoundException {
		testCase.assertTrue("OOOOOpps", Utils.openApp(testCase, "Firefox Beta",
				"org.mozilla.firefox_beta"));

		testCase.sleep(3000);

		// First enable or disable the proxy
		proxy();

		for (int i = 0; i < WEBSITES.length && (LIMIT < 0 || i < LIMIT); i++) {
			visitWebsite(WEBSITES[i]);
			testCase.sleep(2000);
		}
	}
}
