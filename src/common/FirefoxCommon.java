package common;

import utils.Utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class FirefoxCommon {
	private static final String URL_BAR_ID = "org.mozilla.firefox_beta:id/url_bar_title";
	private static final String URL_EDIT_ID = "org.mozilla.firefox_beta:id/url_edit_text";
	private static final String PACKAGE_NAME = "org.mozilla.firefox_beta";
	private static final String[] WEBSITES = {
			"http://www.google.com/", "http://www.facebook.com/",
			"http://www.youtube.com/", "http://www.yahoo.com/",
			"http://www.baidu.com/", "http://www.amazon.com/",
			"http://www.wikipedia.org/", "http://www.twitter.com/",
			"http://www.qq.com/", "https://imgur.com/",
			"https://www.flickr.com/", "http://www.nytimes.com/" };
	private static final int LIMIT = 13;
	private static int NB_TIMES = 1;

	private final UiAutomatorTestCase testCase;
	private final boolean withProxy;
	private final boolean proxySupported;

	public FirefoxCommon(UiAutomatorTestCase testCase, boolean proxySupported,
			boolean withProxy) {
		this.testCase = testCase;
		this.withProxy = withProxy;
		this.proxySupported = proxySupported;
	}

	private void visitWebsite(String url) {
		Utils.click(URL_BAR_ID);
		Utils.setText(URL_EDIT_ID, url, false);
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

		UiAutomatorTestCase.assertTrue("OOOOOpps",
				Utils.openApp(testCase, "Firefox Beta",
						PACKAGE_NAME,
						".App"));
		NB_TIMES = Math.max(1, (int) (NB_TIMES * Utils.getMultTime(testCase)));
		testCase.sleep(1000);

		// First enable or disable the proxy
		if (proxySupported)
			proxy();

		for (int j = 0; j < NB_TIMES; j++) {
			for (int i = 0; i < WEBSITES.length && (LIMIT < 0 || i < LIMIT); i++) {
				visitWebsite(WEBSITES[i]);
				// 1s: No need more, it takes times to write a new address...
				testCase.sleep(1000);
			}
			testCase.sleep(2000); // wait for loading the last webpage
		}
		Utils.runAsUser("am kill " + PACKAGE_NAME); // kill to be sure to erase data
	}
}
