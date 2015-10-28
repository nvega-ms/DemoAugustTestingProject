package august.helper;

import io.appium.java_client.AppiumDriver;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class will extracts the code from the Email Inbox
 * @author MakingSense QA Team
 *
 */
public final class EmailCodeExtractor{
				
		/**
		 * Extracts the code from the EmailInbox
		 * @param driver
		 * @return The code to validate
		 */
		public static String getCodeVerificationFromEmail(AppiumDriver driver)
		{
			String name;
			try{
				List<WebElement> options = driver.findElement(By.id("list")).findElements(By.className("android.widget.FrameLayout"));
				int initialCount = options.size();
				int currentCount;
				do {
					options = driver.findElement(By.id("list")).findElements(By.className("android.widget.FrameLayout"));
					currentCount = options.size();
				} while (initialCount <= currentCount);
				
				for(int i = 4; i < options.size()-1;i++)
				{
					WebElement e = (WebElement)options.get(i);
					if (e.isEnabled())
					{
						e.click();
						By locator = By.xpath("//android.webkit.WebView/descendant::android.view.View/descendant::android.view.View");
						waitElement(driver,locator);
						WebElement verificationCode = driver.findElement(locator);
						name = verificationCode.getAttribute("name");
						if (name.contains("Your August email verification code is"))
						{			
							System.out.println("Extracting code...");
							String code = name.replaceAll("\\D", "");
							System.out.println("Code: " + code);
							driver.findElement(By.className("android.widget.Button")).click();
							return code;
						}
						else
						{
							By backLocator = By.xpath("//android.widget.LinearLayout");
							waitElement(driver, backLocator);
							driver.findElement(backLocator).click();;
						}
								
						
					}
				}
			}
			catch (NoSuchElementException e){
				System.out.println(e.getMessage());
				return null;
			}
			return null;
		}
		
		/**
		 * Wait a given WebElement
		 * @param driver
		 * @param elementLocator
		 */
		private static void waitElement(AppiumDriver driver, final By elementLocator)
		{
			WebDriverWait waiter = new WebDriverWait(driver, 900, 1000);
			waiter.until(new ExpectedCondition<Boolean>(){
			            public Boolean apply(WebDriver d) {
			                return (d.findElement(elementLocator)!=null);
			            }});
		}

}





