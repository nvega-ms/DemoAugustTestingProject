package august.helper;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class will wait for element taking into account 
 * differents strategies
 * @author MakingSense QA team
 *
 */
public final class Waiter {

	/**
	 * Wait until the specified element is present
	 * @param elementSelector - By element to find the element which will receives the value
	 */
	public static void waitFor(final AppiumDriver driver, final By elementSelector,final long miliseconds)
	{
		WebDriverWait waiter = new WebDriverWait(driver, miliseconds);
		waiter.until(new ExpectedCondition<Boolean>(){
		            public Boolean apply(WebDriver d) {
		                return (d.findElement(elementSelector)!=null);
		            }});
	}
		
}
