package august.screens.registration;

import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
/**
 * 
 * @author MakingSense QA Team
 * 
 * The AgreementScreen 
 */
public class AgreementScreen extends ScreenObject{
	/**
	 * By locator to find the Agree button
	 */
	By byAgreeButton = By.name("I Agree");
	
	/**
	 * Class constructor.
	 * @param d - AppiumDriver
	 */
	public AgreementScreen(AppiumDriver d, long t)
	{
		super(d, t);
	
	}
	
	public void Agree()
	{
		this.click(byAgreeButton, "Agree");
	}

	@Override
	public boolean isExpectedScreen() {
		return (this.isDisplayed(By.id("com.august.luna:id/eula_header_textview"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/eula_body_textview"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/eula_privacy_policy"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/eula_terms_of_service"))
				&&
				this.isDisplayed(By.id("com.august.luna:id/eula_license_agreement"))
				&&
				this.isDisplayed(By.name("I Agree"))
				);
	}

}
