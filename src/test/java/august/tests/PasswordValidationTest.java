package august.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import august.helper.ExcelUtils;
import august.screens.registration.AgreementScreen;
import august.screens.registration.UploadProfilePhotoScreen;
import august.screens.registration.UserDetailsScreen;
import august.screens.registration.YouAreInvitedScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver; //Used  for Appium drivers

public class PasswordValidationTest {
	
	AppiumDriver driver;
	
	DesiredCapabilities capabilities;
	
	/**
	 * Get all the invalid password, from the Excel file that contains
	 * invalid password
	 * @param context - ITestContext: used to get the parameters defined
	 * in the current test context
	 * @return Return an array that contains the password to test
	 **/ 
	@DataProvider
 	public Object[][] InvalidPasswords(ITestContext context) throws Exception{
		String folderPath = context.getCurrentXmlTest().getParameter("folderPath");
		String sheetName = context.getCurrentXmlTest().getParameter("sheetNameInvalidPasswords");
		Object[][] testObjArray = ExcelUtils.getTableArray(System.getProperty("user.dir") + folderPath, sheetName);//ExcelUtils.getTableArray(System.getProperty("user.dir") + "\\Resources\\testData\\TestData.xlsx", "InvalidPassword");//folderPath ,sheetName);
		return (testObjArray);
	 
	}
	
	/**
	 * Get all the valid password, from the Excel file that contains
	 * invalid password
	 * @param context - ITestContext: used to get the parameters defined
	 * in the current test context
	 * @return Return an array that contains the password to test
	 **/ 
	@DataProvider
 	public Object[][] ValidPasswords(ITestContext context) throws Exception{
		String folderPath = context.getCurrentXmlTest().getParameter("folderPath");
		String sheetName = context.getCurrentXmlTest().getParameter("sheetNameValidPasswords");
		Object[][] testObjArray = ExcelUtils.getTableArray(System.getProperty("user.dir") + folderPath, sheetName);//ExcelUtils.getTableArray(System.getProperty("user.dir") + "\\Resources\\testData\\TestData.xlsx", "InvalidPassword");//folderPath ,sheetName);
		return (testObjArray);
	 
	}
	
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
	 * Release the driver
	 * @throws Exception
	 */
	@AfterTest
	public void tearDown() throws Exception {  
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

	/**
	 * Test if an error is displayed when the user enters an invalid password.
	 * Error to display:  "Password must use a combination of upper and lower case letters, numbers and symbols."
	 * @param password - Given password
	 * 
	 */
	@Parameters({"timeout"})
	@Test(description = "The user enters a invalid password", dataProvider="InvalidPasswords")
	public void enterPassword_InvalidPassword_ThrowError(String password, long timeout){
		System.out.println("Scenario: The user enters a invalid password. Password: " + password);
		System.out.println("--------------------------------------------------------------------");
		//Initial screen
		YouAreInvitedScreen welcomeScreen = new YouAreInvitedScreen(driver, timeout);
		welcomeScreen.clickOnCreateAccountButton();
		
		//Agreement screen
		AgreementScreen agreeScreen= new AgreementScreen(driver, timeout);
		agreeScreen.Agree();
		
		//User details screen: First Name, Last Name and Password 
		UserDetailsScreen firstStep = new UserDetailsScreen(driver, timeout);
		firstStep.setAllfields("Charles","Fox",password);
		Assert.assertTrue("A error message should be displayed", firstStep.isErrorDisplayed());
	}
	
	/**
	 * Test if an error is displayed when the user enters an invalid password.
	 * Error to display:  "Password must use a combination of upper and lower case letters, numbers and symbols."
	 * @param password - Given password
	 * 
	 */
	@Parameters({"timeout"})
	@Test(description = "The user enters a valid password", dataProvider="ValidPasswords")
	public void enterPassword_validPassword_NoError(String password, long timeout){
		System.out.println("Scenario: The user enters a valid password. Password: " + password);
		System.out.println("--------------------------------------------------------------------");
		//Initial screen
		YouAreInvitedScreen welcomeScreen = new YouAreInvitedScreen(driver, timeout);
		welcomeScreen.clickOnCreateAccountButton();
		
		//Agreement screen
		AgreementScreen agreeScreen= new AgreementScreen(driver, timeout);
		agreeScreen.Agree();
		
		//User details screen: First Name, Last Name and Password 
		UserDetailsScreen firstStep = new UserDetailsScreen(driver, timeout);
		firstStep.setAllfields("Charles","Fox",password);
		UploadProfilePhotoScreen secondStep = new UploadProfilePhotoScreen(driver, timeout);
		Assert.assertTrue("No error should be displayed",secondStep.isUploadProfilePhotoScreen());
	}
	
}

