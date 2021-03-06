package delta.main;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import generics.Excel;
import generics.Property;
import generics.Utility;


public class DeltaDriver extends BaseDriver{
	
	public String browser;
	public String hubURL;
	@BeforeMethod
	public void launchApp(XmlTest xmltest) throws IOException
	{
		
		browser=xmltest.getParameter("browser");
		hubURL=xmltest.getParameter("hubURL");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);;
		dc.setPlatform(Platform.ANY);
		driver = new RemoteWebDriver(new URL(hubURL),dc);
	
		
	//	without hub node, we can still run the framework with different browsers
/*		if(browser.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver=new ChromeDriver();
			
		}
		else
		{
			driver=new FirefoxDriver();
		}*/
		
		String appURL=Property.getPropertyValue(configPptPath, "URL");
		String timeOut = Property.getPropertyValue(configPptPath,  "TimeOut");	
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(timeOut),TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		
		
	}
	
	
	
	@Test(dataProvider="getScenarios")
	public void testScenarios(String scenarioSheet, String executionStatus)throws InterruptedException
	{
				
		testReport = eReport.startTest(browser+"_"+scenarioSheet);
	if(executionStatus.equalsIgnoreCase("yes"))
	{
		System.out.println("I'm in yes");
		int stepcount = Excel.getRowCount(scenariosPath, scenarioSheet);
		System.out.println(stepcount);
		
		for(int i=1; i<=stepcount;i++)
		{
			String description=Excel.getCellValue(scenariosPath, scenarioSheet, i, 0);
			String action=Excel.getCellValue(scenariosPath, scenarioSheet, i, 1);
			String input1  =Excel.getCellValue(scenariosPath, scenarioSheet, i, 2);
			String input2  =Excel.getCellValue(scenariosPath, scenarioSheet, i, 3);
			String msg = "description:"+ description+" action:"+ " input1:"+input1+"input2:"+input2;
			testReport.log(LogStatus.INFO, msg);
			KeyWord.executeKeyWord(driver, action, input1, input2);
		//	Assert.fail();	
		}
	}else
	{
		testReport.log(LogStatus.SKIP, "Execution status is 'No'");
		throw new SkipException("Skipping this scenario"); //to skip any testmethod by yourself, then, throw the exception SkipException
	}
				
}
	
		
	@AfterMethod
	public void quitApp(ITestResult test)
	{
		if(test.getStatus()==ITestResult.FAILURE)
		{
			String pImage=Utility.getPageScreenShot(driver, imageFolderPath);
			String p=testReport.addScreenCapture("."+pImage);
			testReport.log(LogStatus.FAIL,"Page Screen shot:"+p);
						
		}
		eReport.endTest(testReport);
	//	driver.close();
	}
	
	
}

