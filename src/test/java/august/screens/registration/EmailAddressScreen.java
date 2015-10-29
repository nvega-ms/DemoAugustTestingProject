package august.screens.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
/**
 * 
 * @author MakingSense QA Team
 * 
 * The EmailAddressScreen class will receives the email address of the user 
 */
public class EmailAddressScreen extends ScreenObject{
	
	/**
	 * By selector to find the Email address input
	 */
	By byEmailField = By.id("signup_flow_collect_email_field");
		
	/**
	 * By selector to find the Continue button
	 */
	By byContinue = By.id("signup_flow_enter_email_continue_button");

	/**
	 * Class constructor.
	 * @param d - AppiumDriver
	 */
	public EmailAddressScreen(AppiumDriver d)
	{
		super(d);
	}
	
	/**
	 * Set the email address in the email address field
	 * @param emailAddress Phone number to set in the email address field
	 */
	public void enterEmailAddress(String emailAddress)
	{
		this.sendKey(byEmailField, "Email Address", emailAddress);
		this.hideKeyboard();
	}
	
	/**
	 * Click on the Continue button
	 */
	public void clickOnContinue()
	{
		this.click(byContinue, "Continue");
		
	}
	
}
