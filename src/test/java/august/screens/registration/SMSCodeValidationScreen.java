package august.screens.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
/**
 * 
 * @author MakingSense QA Team
 * 
 * The SMSCodeValidationScreen class will receives code sent to the user via SMS
 */
public class SMSCodeValidationScreen extends ScreenObject{
	
	/**
	 * By locator to find the Code input
	 */
	By byCodeField = By.id("signup_flow_enter_phone_code_field");
	
	/**
	 * By locator to find the Continue button	
	 */
	By byContinue = By.name("Continue");

	/**
	 * Class constructor.
	 * @param d - AppiumDriver
	 */
	public SMSCodeValidationScreen(AppiumDriver d, long t)
	{
		super(d, t);
		this.click(byCodeField, "Enter Code");
	
	}
	
	/**
	 * Click on the Continue button
	 */
	public void clickOnContinue()
	{
		this.click(byContinue, "Continue");
	}
	
	/**
	 * 
	 */
	public void waitForCode()
	{
		try{
		WebDriverWait waiter = new WebDriverWait(driver, 300);
		waiter.until(new ExpectedCondition<Boolean>()
						{
		            		public Boolean apply(WebDriver d) {
		            				return ((driver.findElement(byCodeField).getText().length() == 6
		            						&& (driver.findElement(byCodeField).getText().compareTo("Enter Code") != 0)));
		            				 }
		            		
		            	}
					);
			}
		catch(TimeoutException e){}
	}
	

	@Override
	public boolean isExpectedScreen() {
		return (this.isDisplayed(By.name("We just sent a code to"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/signup_flow_enter_phone_code_field"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/signup_flow_didnt_recieve"))
				&&
				this.isDisplayed(By.name("tap to resend"))
				);
	}
}
