package august.screens.registration;

import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
/**
 * 
 * @author MakingSense QA Team
 * 
 * The LetMeInScreen class is which allow the user enter in the app
 */
public class LetMeInScreen extends ScreenObject{
	
	By byLetMeIn = By.name("Thanks for creating your August account!");
	
	public LetMeInScreen(AppiumDriver d, long t)
	{
		super(d, t);
	}
	
	/**
	 * Clicks on the LetMeIn button
	 */
	public void clickOnLetMeIn()
	{
		this.click(byLetMeIn, "Let me in");
	}
	/**
	 * Checks if the current screen is which contains "Let me in"
	 * @return
	 */
	@Override
	public boolean isExpectedScreen() {
		return (this.isDisplayed(byLetMeIn) );
	}	
	
}
