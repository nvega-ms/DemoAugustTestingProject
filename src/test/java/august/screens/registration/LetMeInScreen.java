package august.screens.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.AppiumDriver;
/**
 * 
 * @author MakingSense QA Team
 * 
 * The LetMeInScreen class is which allow the user enter in the app
 */
public class LetMeInScreen extends ScreenObject{
	
	By byLetMeIn = By.name("Thanks for creating your August account!");

	
	public LetMeInScreen(AppiumDriver d)
	{
		super(d);
	}
	
	
	public void clickOnLetMeIn()
	{
		try{
			driver.findElement(byLetMeIn).click();
		}
		catch (NoSuchElementException e){
			System.out.println("The 'Let me in' element was not found. See error: " + e.getMessage());
		}
	}
	
	public boolean isLetMeInScreen()
	{			
		return ( this.isDisplayed(byLetMeIn) );
	}	
	
}
