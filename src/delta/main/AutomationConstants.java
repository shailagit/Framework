package delta.main;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import generics.Property;

public interface AutomationConstants {
	
	//Always in iterface, convention is variables should be in Capital letters
	public static final String imageFolderPath="./screeshots";
	public static final  String reportFilePath="./report/results.html";
	public static final String configPptPath="./config/config.properties";
	public static final String scenariosPath="./scripts/Scenarios.xlsx";
	public static final  String controllerPath="./scripts/Controller.xlsx";
	public static final String suiteSheet ="Suite";
	public static final  String chromeDriverPath ="./drivers/chromedriver.exe";
		
	
	
	
}
