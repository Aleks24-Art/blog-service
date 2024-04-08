package ua.aleksenko.blogservice.selenium;

import java.io.File;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class WebDriverUtils {

  @SneakyThrows
  public void makeScreenShot(WebDriver driver, String fileName) {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(scrFile, new File("target/screenshots/" + fileName + ".png"));
  }

}
