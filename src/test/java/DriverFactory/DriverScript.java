package DriverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
public static WebDriver driver;
String inputpath ="E:\\OJTEveningBatch\\ERP_Maven\\TestInput\\HybridData.xlsx";
String outputpath ="E:\\OJTEveningBatch\\ERP_Maven\\TestOutPut\\HybridResults.xlsx";
ExtentReports report;
ExtentTest test;
public void startTest()throws Throwable
{
	//to access excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in Mastertestcases sheet
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
	{
		String moduleStatus="";
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
		{
			//store corresponding Sheet into TCModule
			String TCModule =xl.getCellData("MasterTestCases", i, 1);
			//define path for html
			report= new ExtentReports("./Reports/"+TCModule+"  "+FunctionLibrary.generateDate()+"html");
			//iterate all in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				test=report.startTest(TCModule);
				//read all cells from TCModule
				String Description =xl.getCellData(TCModule, j, 0);
				String FunctionName =xl.getCellData(TCModule, j, 1);
				String LocatorType =xl.getCellData(TCModule, j, 2);
				String LocatorValue =xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try
				{
				 if(FunctionName.equalsIgnoreCase("startBrowser"))	
				 {
					 driver =FunctionLibrary.startBrowser();
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("openApplication"))
				 {
					 FunctionLibrary.openApplication(driver);
					 test.log(LogStatus.INFO, Description);
					 
				 }
				 else if(FunctionName.equalsIgnoreCase("waitForElement"))
				 {
					 FunctionLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);
					 test.log(LogStatus.INFO, Description);
				 }
				 
				 else if(FunctionName.equalsIgnoreCase("typeAction"))
				 {
					 FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("clickAction"))
				 {
					 FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("validateTitle"))
				 {
					 FunctionLibrary.validateTitle(driver, TestData);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("closeBrowser"))
				 {
					 FunctionLibrary.closeBrowser(driver);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("captureData"))
				 {
					 FunctionLibrary.captureData(driver, LocatorType, LocatorValue);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("suppliertable"))
				 {
					 FunctionLibrary.suppliertable(driver, TestData);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("mouseOver"))
				 {
					 FunctionLibrary.mouseOver(driver);
					 test.log(LogStatus.INFO, Description);
				 }
				 else if(FunctionName.equalsIgnoreCase("stockTable"))
				 {
					 FunctionLibrary.stockTable(driver, TestData);
					 test.log(LogStatus.INFO, Description);
				 }
				//write as pass into status cell in TCModule
				 xl.setCellData(TCModule, j, 5, "Pass", outputpath);
				 test.log(LogStatus.PASS, Description);
				 moduleStatus="True";
				}catch(Throwable t)
				{
					System.out.println(t.getMessage());
					//write as fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					test.log(LogStatus.FAIL, Description);
					moduleStatus="False";
					
				}
				if(moduleStatus.equalsIgnoreCase("True"))
				{
					xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
				}
				if(moduleStatus.equalsIgnoreCase("False"))
				{
					xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
				}
				report.endTest(test);
				report.flush();
			}
		}
		else 
		{
			//write as blocked into status cell which are flaged into N
			xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
		}
	}
}
}
