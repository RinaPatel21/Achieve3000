package TestCases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC_001 {

	WebDriver driver=null;
	@Parameters({"url","browser"})
	@BeforeTest
	public void setUp(String url,String browser) throws Exception {
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "Drivers//chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "Drivers//IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}else if(browser.equalsIgnoreCase("FF")) {
			System.setProperty("webdriver.gecko.driver", "Drivers//geckodriver.exe");
			driver=new FirefoxDriver();
		}
		
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		driver.findElement(By.xpath("//nav[@id='main-navbar']/div/div/div[6]/button")).click();
		driver.findElement(By.linkText("Partners")).click();
		List<WebElement> imgElements=driver.findElements(By.xpath("//div[@class='col-12 col-md-3 ft-img']/a/img"));
		for(WebElement ele:imgElements){
            System.out.println(ele.getAttribute("src"));
        }
		int count=0;
		for(int i=1;i<(imgElements.size()*2);i+=2) {
			WebElement imgPatner=driver.findElement(By.xpath("//*[@id='fullpage']/div[2]/div[2]/div["+i+"]/div[1]/a/img"));
			WebElement headerPatner=driver.findElement(By.xpath("//*[@id='fullpage']/div[2]/div[2]/div["+i+"]/div[2]/a/h3"));
			WebElement descriptionPatner=driver.findElement(By.xpath("//*[@id='fullpage']/div[2]/div[2]/div["+i+"]/div[2]/div/p"));
			if(imgPatner.getAttribute("src")!=null) {
				if(headerPatner.getText()!=null) {
					if(descriptionPatner.getText()!=null) {
						count+=1;
					}
				}
			}
		}
		if(count==imgElements.size()) {
			System.out.println("Logo,header and description are present for all patners");
		}else {
			throw new Exception("Logo,header and description are not present for all patners");
		}
	}
}
