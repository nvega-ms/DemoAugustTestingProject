package august.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver; //Used  for Appium drivers
import io.appium.java_client.android.StartsActivity;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Assert;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import august.helper.AppSwitcher;
import august.helper.EmailCodeExtractor;
import august.screens.registration.AgreementScreen;
import august.screens.registration.EmailAddressScreen;
import august.screens.registration.EmailCodeValidationScreen;
import august.screens.registration.LetMeInScreen;
import august.screens.registration.PhoneNumberScreen;
import august.screens.registration.SMSCodeValidationScreen;
import august.screens.registration.UploadProfilePhotoScreen;
import august.screens.registration.UserDetailsScreen;
import august.screens.registration.YouAreInvitedScreen;

public class HappyPathTest {

	static AppiumDriver _driver;

	DesiredCapabilities capabilities;

	
	/**
	 * Initialize the capabilities, and launch the Android driver
	 * 
	 * @param AugustAppPackageName
	 *            - Package name
	 * @param AugustAppActivityName
	 *            - Activity name
	 * @param version
	 *            - OS version
	 * @param deviceName
	 *            -
	 * @param apkName
	 *            - name of the apk to test
	 */
	@Parameters({ "AugustAppPackageName", "AugustAppActivityName", "version",
			"deviceName", "apkName" })
	@BeforeClass
	public void beforeTest(String AugustAppPackageName,
						   String AugustAppActivityName, String version, String deviceName,
						   String apkName) {
		
		capabilities = new DesiredCapabilities();
		
		//Setting capabilities
		settingCapabilities(AugustAppPackageName, AugustAppActivityName,
				version, deviceName, apkName);
		try {
			_driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					capabilities);
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test the registration happy past Step 1: Welcome step Step 2: Agreement
	 * step Step 3: Fill First Name, Last Name and Password field Step 4: Upload
	 * profile photo step Step 5: Enter phone number step Step 5: Validate code
	 * (SMS) step Step 6: Enter email address step Step 7: Validate code (Email)
	 * step
	 * 
	 */
	@Parameters({ "AugustAppPackageName", "AugustAppActivityName" , "email", "phoneNumber", "countryName", "photoSource", 
		         "firstName", "lastName", "password", "timeout","EmailAppPackageName", "EmailAppActivityName"})
	@Test(description = "User registration - Happy path")
	public void enterData_ValidData_SignIn(String AugustAppPackageName,
			String AugustAppActivityName, String email, String phoneNumber, String countryName, String photoSource,
			String firstName, String lastName, String password, long timeout
			, String EmailAppPackageName, String EmailAppActivityName) {

		// Initial screen
		YouAreInvitedScreen welcomeScreen = new YouAreInvitedScreen(_driver, timeout);
		Assert.assertTrue("The Welcome screen should be displayed", welcomeScreen.isExpectedScreen());
		
		welcomeScreen.clickOnCreateAccountButton();
		// Agreement screen
		AgreementScreen agreeScreen = new AgreementScreen(_driver, timeout);
		Assert.assertTrue("The Agree screen should be displayed", agreeScreen.isExpectedScreen());
		agreeScreen.Agree();

		// User details screen: First Name, Last Name and Password
		UserDetailsScreen firstStep = new UserDetailsScreen(_driver, timeout);
		Assert.assertTrue("The User details screen should be displayed", firstStep.isExpectedScreen());
		firstStep.setAllfields(firstName, lastName, password);

		// Upload picture screen
		UploadProfilePhotoScreen secondStep = new UploadProfilePhotoScreen(_driver, timeout);
		Assert.assertTrue("The Upload photo screen should be displayed", secondStep.isExpectedScreen());
		secondStep.clickOnUploadPhoto();
		secondStep.goToPhotoSource(photoSource);
		secondStep.choosePhotoByPosition(1);
		secondStep.clickOnContinue();

		// Enter country and phone number screen
		PhoneNumberScreen thirdStep = new PhoneNumberScreen(_driver, timeout);
		Assert.assertTrue("The Enter phone number screen should be displayed", thirdStep.isExpectedScreen());
		thirdStep.clickOnCountrySelector();
		thirdStep.chooseCountryByName(countryName);
		thirdStep.enterPhoneNumber(phoneNumber);
		thirdStep.clickOnContinue();

		// Validation code Screen (SMS)
		SMSCodeValidationScreen fourthStep = new SMSCodeValidationScreen(_driver, timeout);
		Assert.assertTrue("The Sms code valitation screen should be displayed", fourthStep.isExpectedScreen());
		fourthStep.waitForCode();

		// Enter email address
		EmailAddressScreen fifthStep = new EmailAddressScreen(_driver, timeout);
		Assert.assertTrue("The email address screen should be displayed", fifthStep.isExpectedScreen());
		fifthStep.enterEmailAddress(email);
		fifthStep.clickOnContinue();
		
		// Start Email app
		((StartsActivity) _driver).startActivity(EmailAppPackageName, EmailAppActivityName);
		
		//Get the code from Inbox email
		EmailCodeExtractor.loading(_driver, timeout);
		String code = EmailCodeExtractor.getCodeVerificationFromEmail(_driver, timeout);

		//Swith to August app
		AppSwitcher.SwitchApp(_driver, "August", timeout);
		
		//Enter email code
		EmailCodeValidationScreen sexthStep = new EmailCodeValidationScreen(_driver, timeout);
		Assert.assertTrue("The email code validation screen should be displayed", sexthStep.isExpectedScreen());
		sexthStep.enterCode(code);
		sexthStep.clickOnContinue();

		//Ley me in screen
		LetMeInScreen lastStep = new LetMeInScreen(_driver, timeout);
		Assert.assertTrue("The Last step of the process should be displayed",
				lastStep.isExpectedScreen());

	}
	
	/**
	 * Release the driver
	 * 
	 * @throws Exception
	 */
	@AfterTest
	public void afterClass() throws Exception {
		_driver.quit();
	}

	/**
	 * Reset the application before each test
	 */
	@BeforeMethod
	public void beforeMethod() {
		_driver.resetApp();
	}

	
	//Private

	/**
	 * 
	 * @param d
	 * @param AugustAppPackageName
	 * @param AugustAppActivityName
	 * @param version
	 * @param deviceName
	 * @param apkName
	 */
	private void settingCapabilities(String AugustAppPackageName, String AugustAppActivityName,
			                 String version, String deviceName, String apkName) {
		
		// path to Eclipse project
		File classpathRoot = new File(System.getProperty("user.dir"));

		// path to <project folder>/Resources (APKFolder)
		File appDir = new File(classpathRoot, "/src/test/resources/");

		// path to <project folder>/APKFolder/APKFile.apk
		File app = new File(appDir, apkName);

		capabilities = new DesiredCapabilities();

		// Name of mobile web browser to automate. Should be an empty string if
		// automating an app instead.
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

		// Which mobile OS to use: Android
		capabilities.setCapability("platformName", "Android");

		// Which mobile OS version to use
		capabilities.setCapability(CapabilityType.VERSION, version);

		// Device name. If the device is connected to the PC, you should execute
		// the command "adb devices" -> http://screencast.com/t/BJf5DtHxV
		capabilities.setCapability("deviceName", deviceName);

		// Java package of the tested Android app
		capabilities.setCapability("appPackage", AugustAppPackageName);// "com.august.luna");

		// Activity to lunch
		capabilities.setCapability("appActivity", AugustAppActivityName);// ".ui.startup.LandingPage");

		// The absolute local path to the APK
		capabilities.setCapability("app", app.getAbsolutePath());

	}
}
