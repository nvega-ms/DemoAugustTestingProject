package august.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import august.helper.ExcelUtils;
import august.screens.registration.AgreementScreen;
import august.screens.registration.EmailAddressScreen;
import august.screens.registration.EmailCodeValidationScreen;
import august.screens.registration.LetMeInScreen;
import august.screens.registration.PhoneNumberScreen;
import august.screens.registration.SMSCodeValidationScreen;
import august.screens.registration.UploadProfilePhotoScreen;
import august.screens.registration.UserDetailsScreen;
import august.screens.registration.YouAreInvitedScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver; //Used  for Appium drivers

public class HappyPathTest {
	
	AppiumDriver driver;
	
	DesiredCapabilities capabilities;
		
	/**
	 * Initialize the capabilities, and launch the Android driver
	 * @param AugustAppPackageName - Package name
	 * @param AugustAppActivityName - Activity name
	 * @param version - OS version
	 * @param deviceName - 
	 * @param apkName - name of the apk to test
	 */
	@Parameters({ "AugustAppPackageName", "AugustAppActivityName", "version", "deviceName", "apkName"})
	@BeforeTest
	public void beforeTest(String AugustAppPackageName, String AugustAppActivityName, String version, 
						String deviceName, String apkName)
	{
		// path to Eclipse project
		File classpathRoot = new File(System.getProperty("user.dir"));
		
		// path to <project folder>/Resources (APKFolder)
		File appDir = new File(classpathRoot, "/src/test/resources/");
		
		//path to <project folder>/APKFolder/APKFile.apk
		File app = new File(appDir, apkName);
		
		capabilities = new DesiredCapabilities();
		
		// Name of mobile web browser to automate. Should be an empty string if automating an app instead.
		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); 

		// Which mobile OS to use: Android
		capabilities.setCapability("platformName", "Android"); 

		// Which mobile OS version to use
		capabilities.setCapability(CapabilityType.VERSION, version); 

		//Device name. If the device is connected to the PC, you should execute the command "adb devices" -> http://screencast.com/t/BJf5DtHxV 
		capabilities.setCapability("deviceName", deviceName);
		
		// Java package of the tested Android app
  		capabilities.setCapability("appPackage", AugustAppPackageName);//"com.august.luna"); 

		// Activity to lunch
		capabilities.setCapability("appActivity", AugustAppActivityName);//".ui.startup.LandingPage");
		
		// The absolute local path to the APK
		capabilities.setCapability("app", app.getAbsolutePath());

		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test the registration happy past
	 * Step 1: Welcome step
	 * Step 2: Agreement step
	 * Step 3: Fill First Name, Last Name and Password field
	 * Step 4: Upload profile photo step
	 * Step 5: Enter phone number step
	 * Step 5: Validate code (SMS) step
	 * Step 6: Enter email address step
	 * Step 7: Validate code (Email) step
	 * 
	 */
	@Test(description = "User registration - Happy path")
	public void enterData_ValidData_SignIn(){
		
		//Initial screen
		YouAreInvitedScreen welcomeScreen = new YouAreInvitedScreen(driver);
		welcomeScreen.clickOnCreateAccountButton();
		
		//Agreement screen
		AgreementScreen agreeScreen= new AgreementScreen(driver);
		agreeScreen.Agree();
		
		//User details screen: First Name, Last Name and Password
		UserDetailsScreen firstStep = new UserDetailsScreen(driver);
		firstStep.setAllfields("Charles","Fox","Abcde123456!");
		
		//Upload picture screen
		UploadProfilePhotoScreen secondStep = new UploadProfilePhotoScreen(driver);
		secondStep.clickOnUploadPhoto();
		secondStep.goToPhotoSource("Photos");
		secondStep.choosePhotoByPosition(1);
		secondStep.clickOnContinue();
				
		//Enter country and phone number screen
		PhoneNumberScreen thirdStep = new PhoneNumberScreen(driver);
		thirdStep.clickOnCountrySelector();
		thirdStep.chooseCountryByName("Argentina");
		thirdStep.enterPhoneNumber("0249154376616");
		thirdStep.clickOnContinue();
		
		//Validation code Screen (SMS)
		SMSCodeValidationScreen fourthStep = new SMSCodeValidationScreen(driver);
		fourthStep.waitForCode();
		fourthStep.clickOnContinue();
		
		//Enter email address 
		EmailAddressScreen fifthStep = new EmailAddressScreen(driver);
		fifthStep.enterEmailAddress("nvega@makingsense.com");
		fifthStep.clickOnContinue();
		
		//Validation code Screen (Email)
		EmailCodeValidationScreen SexthStep = new EmailCodeValidationScreen(driver);
		SexthStep.waitForCode();
		SexthStep.clickOnContinue();
		
		LetMeInScreen lastStep = new LetMeInScreen(driver);
		Assert.assertTrue("The Last step of the process should be displayed", lastStep.isLetMeInScreen());

	}
	
	/**
	 * Release the driver
	 * @throws Exception
	 */
	@AfterTest
	public void afterTest() throws Exception {  
		driver.closeApp();
		driver.quit(); 
	}
	
	/**
	 * Reset the application before each test
	 */
	@BeforeMethod
	public void beforeMethod()
	{
		driver.resetApp();
	}
}

