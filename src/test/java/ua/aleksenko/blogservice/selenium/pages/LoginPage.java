package ua.aleksenko.blogservice.selenium.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage {

  @FindBy(name = "username")
  private WebElement userName;

  @FindBy(name = "password")
  private WebElement password;

  @FindBy(css = ".field:nth-child(3) > input")
  private WebElement loginButton;

  public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
