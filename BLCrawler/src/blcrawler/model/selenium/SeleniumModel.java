package blcrawler.model.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import blcrawler.model.ConsoleOutput;

public class SeleniumModel {
	
	private WebDriver driver;
	public SeleniumModel() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/Joe/Documents/chromedriver.exe");
		driver = new ChromeDriver();

		
	}
	
	public void getURL(String url) {
		driver.get(url);
		new ConsoleOutput("Selenium: ", "Page title is: " + driver.getTitle());
	}
	
	

}
