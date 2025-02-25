package august.helper;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * This class will swith between the current application, and a
 * @author MakingSense QA Team
 *
 */
public final class AppSwitcher {
	
	/**
	 * This method will switches between the current app
	 * and the given app
	 * @param _d - AppiumDriver
	 * @param appName - Name of the app to re-lauch
	 */
	public static void SwitchApp(final AppiumDriver _d, String appName, long timeout)
	{	
		
		// Press Home Key
		_d.sendKeyEvent(AndroidKeyCode.HOME);
		
		// KEYCODE_APP_SWITCH
		_d.sendKeyEvent(0x000000bb);
		
		Waiter.waitFor(_d, By.id("com.android.systemui:id/recents_view"), timeout);
		
		// Tap our first app
		List<WebElement> elem = _d.findElements(By.className("android.widget.TextView"));//"android.widget.FrameLayout"));
		for (int i = 0; i < elem.size() - 1; i++) {
			if (elem.get(i).getAttribute("name").equals(appName)) {
				WebDriverWait wait4 = new WebDriverWait(_d, 10000);
				_d.tap(1, elem.get(i), 1000);
				break;
			}

		}
		
	
	}
}
