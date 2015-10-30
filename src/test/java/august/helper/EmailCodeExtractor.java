package august.helper;

import io.appium.java_client.AppiumDriver;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * This class will extracts the code from the Email Inbox
 * @author MakingSense QA Team
 *
 */
public final class EmailCodeExtractor{
	
		static By byRefresh = By.id("com.android.email:id/refresh_port");
		
		static By byDelete = By.id("com.android.email:id/delete");
		
		static By byBack = By.id("com.android.email:id/message_view_action_bar_custom");
		
		/**
		 * Wait the Email app is fully loaded
		 * @param driver
		 */
		public static void loading(AppiumDriver driver, long timeout){
			System.out.println("Loading Email inbox");
			Waiter.waitFor(driver, By.id("android:id/decor_content_parent"),timeout );
			Waiter.waitFor(driver, By.id("android:id/action_bar_container"), timeout);
			Waiter.waitFor(driver, By.id("com.android.email:id/bottom_action_bar"), timeout);
			
		}
				
		/**
		 * Extracts the code from the EmailInbox
		 * @param driver
		 * @return The code to validate
		 */
		public static String getCodeVerificationFromEmail(AppiumDriver driver, long timeout)
		{
			System.out.println("Getting validation code from email");
			String name;
			List<WebElement> emails = getEmails(driver);
			try{
				//Check if already received the email
				if (emails.size() == 0)
					waitEmail(driver, timeout);
				
				emails = getEmails(driver); 
				for(int i = 4; i < emails.size()-1;i++)
				{
					WebElement e = (WebElement)emails.get(i);
					if (e.isEnabled())
					{
						e.click();
						
						By locator = By.xpath("//android.webkit.WebView/descendant::android.view.View/descendant::android.view.View");
						Waiter.waitFor(driver,locator, timeout);
						WebElement verificationCode = driver.findElement(locator);
						name = verificationCode.getAttribute("name");
						if (name.contains("Your August email verification code is"))
						{			
							String code = name.replaceAll("\\D", "");
							getButton(driver,byDelete, timeout).click();
							return code;
						}
						else
							getButton(driver, byBack, timeout).click();
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
		 * Returns the buttons associated to the given locator
		 * @param driver
		 * @return  button
		 */
		private static WebElement getButton(AppiumDriver driver, By locator, long timeout){
			try{
			Waiter.waitFor(driver, locator, timeout);
			return (driver.findElement(locator));
			}
			catch(Exception e){
				System.out.println("The button was not found..." + e.getMessage());
				return null;
			}
		}
		
		/**
		 * Wait the email sent by August
		 * @param driver
		 */
		private static void waitEmail(AppiumDriver driver, long timeout){
			List<WebElement> options = getEmails(driver);
			WebElement refreshButton = getButton(driver, byRefresh, timeout);
			int initialCount = options.size();
			int currentCount;
			do {
				refreshButton.click();
				options = getEmails(driver);
				currentCount = options.size();
			} while (currentCount <= initialCount);
		}
		
		/**
		 * Get the emails in the Email Inbox
		 * @param driver
		 * @return list of web elements(emails)
		 */
		private static List<WebElement> getEmails(AppiumDriver driver)
		{
			try{
				return driver.findElement(By.id("list")).findElements(By.className("android.widget.FrameLayout"));
			}
			catch(Exception e){
				return null;
			}
		}
}





