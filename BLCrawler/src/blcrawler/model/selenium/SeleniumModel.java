package blcrawler.model.selenium;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.Timer;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;

public class SeleniumModel {
	private ProfilesIni profile;
	private FirefoxProfile myprofile;
	private WebDriver driver;
	
	public SeleniumModel() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/Joe/Documents/chromedriver.exe");

		profile = new ProfilesIni();
		myprofile = profile.getProfile("selenium");
		driver = new FirefoxDriver(myprofile);


 
 
		GUIModel.getGuiView().setIndeterminiteOff();
		

		
	}
	
	public void relaunchTor() throws InterruptedException, IOException {
		GUIModel.getConsoleController().killFirefox();
		Thread.sleep(1000);
		Runtime.getRuntime().exec("C:/Users/Joe/Desktop/Tor Browser/Browser/firefox.exe");
		Thread.sleep(5000);
		driver = new FirefoxDriver(myprofile);
	}
	
	
	
	public void gotoURL(String url) {
		driver.get(url);
		new ConsoleOutput("Selenium: ", "Page title is: " + driver.getTitle());
	}
	
	public String getHTML() {
		return driver.getPageSource();
	}
	
	

}
