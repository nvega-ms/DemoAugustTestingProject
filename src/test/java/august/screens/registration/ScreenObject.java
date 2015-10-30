package august.screens.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import august.helper.Waiter;
import io.appium.java_client.AppiumDriver;

/**
 * @author MakingSense QA Team
 * @desc This class will hold functions for user interaction. 
 * Include functions related to the softkeyboar as Done, Enter, Back
 * 
*/

public abstract class ScreenObject {
	
	protected AppiumDriver driver;
	
	protected long timeout;
	
	/**
	 * Constructor
	 * @param AppiumDriver d - Android driver
	 */
	public ScreenObject(AppiumDriver d, long t)
	{
		driver = d;
		timeout = t;
	}
	
	/**
	 * Click on the Done button of the softkeyboard
	 */
	public void Done()
	{
		driver.tap(1, 995, 1819,1);
	}
	
	/**
	 * Set a specified value to a element
	 * @param selector - By element to find the element which will receives the value
	 * @param fieldName - Label of the element
	 * @param value - Value to set the elemt
	 */
	public void sendKey(By selector, String fieldName, Object value)
	{
		try{
			Waiter.waitFor(driver,selector,timeout);
			driver.findElement(selector).sendKeys((String)value);
			System.out.println("Setting the " + fieldName+ " field...");
		}
		catch (NoSuchElementException e){
			System.out.println("The " + fieldName + " element was not found. See error: " + e.getMessage());
		}
	}
	
	/**
	 * Click on the specified element (button or link)
	 * @param selector - By element to find the element which will receives the value
	 * @param buttonText - Label of the element
	 */
	public void click(By selector, String buttonText)
	{
		{
			try{
				System.out.println("Clicking on the " +  buttonText + " button...");
				Waiter.waitFor(driver,selector, timeout);
				driver.findElement(selector).click();
			}
			catch (IndexOutOfBoundsException e){
				System.out.println("The " + buttonText + " button was not found. See error: " + e.getMessage());
			
			}
		}
	}
	
	/**
	 * Check if the locator given is displayed
	 * @param byPasswordErrorLocator locator to find the element
	 * @return
	 */
	public boolean isDisplayed(By byPasswordErrorLocator) {
		boolean displayed;
		try{
			displayed = ((driver.findElement(byPasswordErrorLocator)!= null)&&(driver.findElement(byPasswordErrorLocator).isDisplayed()));
			return displayed;
		}
		catch (NoSuchElementException e){
			System.out.println("The element was not found. See error: " + e.getMessage());
			return false;
		}
		
	}
	
	
	/**
	 * Get the text of the element associated to the locator
	 * @param locator - locator
	 * @return Text of the WebElement
	 */
	public String getText(By locator)
	{
		Waiter.waitFor(driver,locator, timeout);
		return this.getElement(locator).getText();
	}
			
	/**
	 * Return the WebElemnt associated to the given locator
	 * @param elementLocator locator
	 * @return
	 */
	public WebElement getElement(By elementLocator)
	{
		Waiter.waitFor(driver,elementLocator, timeout);
		return driver.findElement(elementLocator);
	}
	
	/**
	 * Hide the soft keyboard
	 */
	public void hideKeyboard()
	{
		try{
		driver.hideKeyboard();
		}
		catch(Exception e){}
	}
	
	public abstract boolean isExpectedScreen();
	
	
	
	
	
}
