package delta.main;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import generics.Excel;

public class BaseDriver implements AutomationConstants{
	
	public WebDriver driver;
	public ExtentReports eReport;
	public ExtentTest testReport;

	@BeforeSuite
	public void initFrameWork()
	{
		eReport =new ExtentReports(reportFilePath);
		System.out.println();
	}
	
	@DataProvider
	public String[][] getScenarios()
	{
		/*	String[][] data=new String[2][1];
			data[0][0] ="scenario1";
			data[0][1]="yes";
			data[1][0]="scenario2";
			data[1][1]="no";
			return data;*/
		
		String controllerPath = "./scripts/Controller.xlsx";
		String suiteSheet ="Suite";
		
	/*	String[][] data= new String[1][2];
		String scenarioName= Excel.getCellValue(controllerPath, suiteSheet, i, 0);
		String executionStatus= Excel.getCellValue(controllerPath, suiteSheet, i, 1);
		data[i-1][0]=scenarioName;
		data[i-1]][1]=executionStatus;*/
		
		int scenarioCount=Excel.getRowCount(controllerPath, suiteSheet);
		String[][] data= new String[scenarioCount][2];
		for(int i=1; i<=scenarioCount;i++)
		{
			String scenarioName= Excel.getCellValue(controllerPath, suiteSheet, i, 0);
			String executionStatus= Excel.getCellValue(controllerPath, suiteSheet, i, 1);
			data[i-1][0]=scenarioName;
			data[i-1][1]=executionStatus;
		}
		
				
		return data;
	}
	
	@AfterSuite()
	public void endFrameWork()
	{
		eReport.flush();
	}
	
	
	
}
