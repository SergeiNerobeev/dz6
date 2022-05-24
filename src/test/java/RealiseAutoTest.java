import org.openqa.selenium.firefox.FirefoxDriver;
import personalinfo.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;


import java.time.Duration;

public class RealiseAutoTest {
  private final Logger logger = (Logger) LogManager.getLogger(RealiseAutoTest.class);
  private WebDriver driver;
  private final IServerConfiguration serverConfiguration = ConfigFactory.create(IServerConfiguration.class);

  @Before
  public void startUp() {
    WebDriverManager.firefoxdriver().setup();
    driver = new FirefoxDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    logger.info("Logs on");
  }

  @After
  public void end() {
    if (driver != null)
    driver.quit();
  }

  @Test
  public void openWeb() {

    //Открыть https://otus.ru
    String currentUrl = "https://otus.ru/";
    driver.get(serverConfiguration.url());
    Assert.assertEquals("Current Url is wrong", serverConfiguration.url(), currentUrl);

    //Авторизоваться на сайте
    String loginExpected = serverConfiguration.login();
    driver.findElement(By.xpath("//button[contains(@data-modal-id, 'new-log-reg')]")).click();
    WebElement loginButton = driver.findElement(By.xpath("//input[@type='text'][contains(@class,'email')]"));
    loginButton.sendKeys(serverConfiguration.login());
    Assert.assertEquals("Login is doesn't correct", loginExpected,loginButton.getAttribute("value"));

    String emailExpected = serverConfiguration.password();
    WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password'][contains(@class,'new-input')]"));
    passwordInput.sendKeys(serverConfiguration.password());
    Assert.assertEquals("Password doesn't correct", emailExpected,passwordInput.getAttribute("value"));

    driver.findElement(By.xpath("//button[contains(text(),'Войти')]")).click();

    //Войти в личный кабинет
    By itemTextUsername = By.xpath("//*[contains(@class, 'header2-menu__item-text__username')]");
    WebElement clickItemTextUsername = driver.findElement(itemTextUsername);
    clickItemTextUsername.click();
    Assert.assertTrue("Text User name is not displayed",clickItemTextUsername.isDisplayed());

    By dropDownTextName = By.xpath("//*[contains(@class, 'header2-menu__dropdown-text_name')]");
    WebElement clickDropDownTextName = driver.findElement(dropDownTextName);
    clickDropDownTextName.click();
    WebElement checkTitleText = driver.findElement(By.xpath("//*[contains(@class,'title__text')]"));
    Assert.assertTrue("Dropdown text name is not displayed",checkTitleText.isDisplayed());

    //В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
    String fnameExpected = serverConfiguration.fname();
    WebElement fnameInput = driver.findElement(By.xpath("//*[contains(@name,'fname_latin')]"));
    fnameInput.sendKeys(serverConfiguration.fname());
    Assert.assertEquals("First name is wrong", fnameExpected, fnameInput.getAttribute("value"));

    String lnameExpected = serverConfiguration.lname();
    WebElement lnameInput = driver.findElement(By.xpath("//*[contains(@name,'lname_latin')]"));
    lnameInput.sendKeys(serverConfiguration.lname());
    Assert.assertEquals("Last name is wrong", lnameExpected, lnameInput.getAttribute("value"));

    String bloggerExpected = serverConfiguration.bloggername();
    WebElement bloggerNameInput = driver.findElement(By.xpath("//*[contains(@name,'blog_name')]"));
    bloggerNameInput.sendKeys(serverConfiguration.bloggername());
    Assert.assertEquals("Blogger name is wrong", bloggerExpected, bloggerNameInput.getAttribute("value"));

    String dateBirthdayExpected = serverConfiguration.dateofbirthday();
    WebElement dateBirthInput = driver.findElement(By.xpath("//*[contains(@name,'date_of_birth')]"));
    dateBirthInput.sendKeys(serverConfiguration.dateofbirthday());
    Assert.assertEquals("Date is wrong", dateBirthdayExpected, dateBirthInput.getAttribute("value"));

    WebElement dateOfBirth = driver.findElement(By.xpath("//input[@title='День рождения']"));
    dateOfBirth.sendKeys(Keys.ENTER);

    //Прокрутка страницы вниз
    WebElement scrollElement = driver.findElement(By.xpath("//label[contains(text(),'Дата рождения')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement);

    driver.findElement(By.xpath(" //*[contains(@class, 'lk-cv-block__line')][.//input[@name='country']]//*[@data-selected-option-class]")).click();
    WebElement countryActual = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-scroll_country')]//button[contains(@title,'Россия')]"));
    countryActual.click();
    String countryExpected = Country.RUSSIA.getCountry();
    Assert.assertEquals("Country is wrong",countryExpected, countryActual.getAttribute("title") );

    driver.findElement(By.xpath("//*[contains(@class, 'lk-cv-block__line')][.//input[@name='city']]")).click();
    WebElement cityActual = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-options')]//*[contains(@class,'js-custom-select-options')]//button[contains(@title,'Москва')]"));
    cityActual.click();
    String cityExpected = City.MOSCOW.getCity();
    Assert.assertEquals("City is wrong",cityExpected, cityActual.getAttribute("title"));

    driver.findElement(By.xpath("//*[contains(@class, 'lk-cv-block__line')][.//input[@name='english_level']]")).click();
    WebElement englishActual = driver.findElement(By.xpath("//button[contains(@title,'Начальный уровень (Beginner)')]"));
    englishActual.click();
    String englishExpected = Englishlevel.BEGINNER.getEnglishLevel();
    Assert.assertEquals("English level is wrong", englishExpected, englishActual.getAttribute("title"));

    driver.findElement(By.xpath("//span[contains(text(),'Да')]")).click();
    WebElement checkYeas = driver.findElement(By.xpath("//span[contains(text(),'Да')]"));
    driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]")).click();
    WebElement checkRemote = driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]"));
    Assert.assertTrue(checkYeas.isDisplayed());
    Assert.assertTrue(checkRemote.isDisplayed());

    driver.findElement(By.xpath("//button[contains(@class,'js-change-phone')]")).click();
    WebElement clearField = driver.findElement(By.xpath("//input[@placeholder='Номер телефона']"));
    clearField.clear();
    clearField.click();
    clearField.sendKeys(serverConfiguration.phonenumber());
    Assert.assertTrue(clearField.isDisplayed());

    driver.findElement(By.xpath("//button[contains(text(),'Отправить')]")).click();
    driver.findElement(By.xpath("//*[contains(@class,'ic-close ')]")).click();

    //Прокрутка страницы вниз
    WebElement scrollElementT = driver.findElement(By.xpath("//p[contains(text(),'Почта *')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElementT);

    driver.findElement(By.xpath("//*[contains(@class,'js-custom-select-presentation')]//span[contains(text(),'Способ связи')]")).click();
    WebElement vkActual = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-scroll')]//button[@title='VK']"));
    vkActual.click();
    String vkExpected = Communication.VK.getCommunication();
    Assert.assertEquals("Communication is wrong",vkExpected,vkActual.getAttribute("title"));

    WebElement checkJohn = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-0-value']")).sendKeys(serverConfiguration.fname());
    Assert.assertTrue(checkJohn.isDisplayed());

    driver.findElement(By.xpath("//button[contains(text(),'Добавить')]")).click();

    driver.findElement(By.xpath("//*[contains(@class,'js-custom-select-presentation')]//span[contains(text(),'Способ связи')]")).click();
    WebElement okActual = driver.findElement(By.xpath("//*[contains(@data-num,'1')]//*[contains(@class,'js-custom-select-options')]//button[contains(@title,'OK')][text()]"));
    okActual.click();
    String okExcepted = Communication.OK.getCommunication();
    Assert.assertEquals("Communication 2 is wrong", okExcepted, okActual.getAttribute("title"));

    WebElement checkContact1 = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-1-value']")).sendKeys(serverConfiguration.bloggername());
    Assert.assertTrue(checkContact1.isDisplayed());

    WebElement scrollElement1 = driver.findElement(By.xpath("//p[contains(text(),'Телефон *')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement1);

    driver.findElement(By.xpath("//select[@id='id_gender']")).click();
    WebElement genderActual = driver.findElement(By.xpath("//select[@id='id_gender']//option[contains(@value,'m')]"));
    genderActual.click();
    String genderExpected = Gender.MALE.getGender();
    Assert.assertEquals("Gender is wrong", genderExpected, genderActual.getAttribute("value"));

    WebElement checkCompany = driver.findElement(By.xpath("//input[@id='id_company']"));
    driver.findElement(By.xpath("//input[@id='id_company']")).sendKeys("EMI Records");
    Assert.assertTrue(checkCompany.isDisplayed());

    WebElement checkWork = driver.findElement(By.xpath("//input[@id='id_work']"));
    driver.findElement(By.xpath("//input[@id='id_work']")).sendKeys("FOH");
    Assert.assertTrue(checkWork.isDisplayed());

    WebElement checkAdding = driver.findElement(By.xpath("//a[@title='Добавить']"));
    driver.findElement(By.xpath("//a[@title='Добавить']")).click();
    Assert.assertTrue(checkAdding.isDisplayed());

    //Нажать сохранить
    driver.findElement(By.xpath("//*[contains(@title,'Сохранить и заполнить позже')]")).click();
    driver.quit();

    //Открыть https://otus.ru в "чистом браузере"
    WebDriverManager.firefoxdriver().setup();
    driver = new FirefoxDriver();
    driver.get(serverConfiguration.url());
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    Assert.assertEquals("Current Url is wrong", serverConfiguration.url(), currentUrl);

    //Авторизоваться на сайте
    String loginExpected2 = serverConfiguration.login();
    driver.findElement(By.xpath("//button[contains(@data-modal-id, 'new-log-reg')]")).click();
    WebElement loginButton2 = driver.findElement(By.xpath("//input[@type='text'][contains(@class,'email')]"));
    loginButton2.sendKeys(serverConfiguration.login());
    Assert.assertEquals("Login is doesn't correct", loginExpected2,loginButton2.getAttribute("value"));

    String emailExpected2 = serverConfiguration.password();
    WebElement passwordInput2 = driver.findElement(By.xpath("//input[@type='password'][contains(@class,'new-input')]"));
    passwordInput2.sendKeys(serverConfiguration.password());
    Assert.assertEquals("Password doesn't correct", emailExpected2,passwordInput2.getAttribute("value"));

    driver.findElement(By.xpath("//button[contains(text(),'Войти')]")).click();

    //Войти в личный кабинет
    By itemTextUsername2 = By.xpath("//*[contains(@class, 'header2-menu__item-text__username')]");
    WebElement clickItemTextUsername2 = driver.findElement(itemTextUsername2);
    clickItemTextUsername2.click();
    Assert.assertTrue(clickItemTextUsername2.isDisplayed());

    By dropDownTextName2 = By.xpath("//*[contains(@class, 'header2-menu__dropdown-text_name')]");
    WebElement clickDropDownTextName2 = driver.findElement(dropDownTextName2);
    clickDropDownTextName2.click();
    WebElement checkTitleText2 = driver.findElement(By.xpath("//*[contains(@class,'title__text')]"));
    Assert.assertTrue(checkTitleText2.isDisplayed());

         ////////////////////////////////// проверка ////////////////////////////////////////////
    WebElement actualFirstName = driver.findElement(By.xpath("//*[contains(@id,'id_fname_latin')]"));
    String expectedName = FirstName.JOHN.getFirstName();
    Assert.assertEquals("First Name of user doesn't correct", expectedName, actualFirstName.getAttribute("value"));

    WebElement actualLastName = driver.findElement(By.xpath("//*[contains(@value,'Smith')]"));
    String expectedLastName = LastName.SMITH.getLastName();
    Assert.assertEquals("Last Name of user doesn't correct", expectedLastName, actualLastName.getAttribute("value"));

    WebElement actualBloggerName = driver.findElement(By.xpath("//*[contains(@id,'id_blog_name')]"));
    String expectedBloggerName = BloggerName.AMERICANO.getBloggerName();
    Assert.assertEquals("Blogger Name is not correct", expectedBloggerName, actualBloggerName.getAttribute("value"));

    WebElement actualDateOfBirth = driver.findElement(By.xpath("//*[contains(@value,'01.01.2001')]"));
    String expectedDateOfBirth = DateOfBirthday.DATEONE.getDateOfBirthday();
    Assert.assertEquals("Date of Birth is not doesn't correct", expectedDateOfBirth, actualDateOfBirth.getAttribute("value"));

    WebElement scrollElement3 = driver.findElement(By.xpath("//p[contains(text(),'Контактная информация')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement3);

    WebElement countryActual1 = driver.findElement(By.xpath("//*[contains(@class, 'lk-cv-block__select-scroll_country')]//button[contains(@title,'Россия')]"));
    String countryExpected1 = Country.RUSSIA.getCountry();
    Assert.assertEquals("Country is wrong",countryExpected1, countryActual1.getAttribute("title") );

    WebElement cityActual1 = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-option')][contains(@title,'Москва')]"));
    String cityExpected1 = City.MOSCOW.getCity();
    Assert.assertEquals("City is wrong",cityExpected1, cityActual1.getAttribute("title"));

    WebElement englishActual1 = driver.findElement(By.xpath("//button[contains(@title,'Начальный уровень (Beginner)')]"));
    String englishExpected1 = Englishlevel.BEGINNER.getEnglishLevel();
    Assert.assertEquals("English level is wrong", englishExpected1, englishActual1.getAttribute("title"));

    WebElement checkYeas1 = driver.findElement(By.xpath("//span[contains(text(),'Да')]"));
    Assert.assertTrue(checkYeas1.isDisplayed());

    WebElement checkRemote1 = driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]"));
    Assert.assertTrue(checkRemote1.isDisplayed());

    WebElement checkFieldEmail = driver.findElement(By.xpath("//input[@id='id_email']"));
    Assert.assertTrue(checkFieldEmail.isDisplayed());

    WebElement clearFieldPhone = driver.findElement(By.xpath("//input[@id='id_phone']"));
    Assert.assertTrue(clearFieldPhone.isDisplayed());

    WebElement scrollElement4 = driver.findElement(By.xpath("//p[contains(text(),'Телефон *')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement4);

    WebElement okActual1 = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-scroll')]//button[@title='OK']"));
    String okExcepted1 = Communication.OK.getCommunication();
    Assert.assertEquals("Communication 2 is wrong", okExcepted1, okActual1.getAttribute("title"));

    WebElement americanoActual = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
    String americanoExpected = BloggerName.AMERICANO.getBloggerName();
    Assert.assertEquals("Name is wrong or not Americano", americanoExpected, americanoActual.getAttribute("value") );

    WebElement vkActual1 = driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-scroll')]//button[@title='VK']"));
    String vkExpected1 = Communication.VK.getCommunication();
    Assert.assertEquals("Communication is wrong",vkExpected1,vkActual1.getAttribute("title"));

    WebElement JohnActual = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
    String JohnExpected = FirstName.JOHN.getFirstName();
    Assert.assertEquals("First name is wrong",JohnExpected, JohnActual.getAttribute("value"));

    WebElement scrollElement2 = driver.findElement(By.xpath("//h3[contains(text(),'Другое')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement2);

    WebElement genderActual1 = driver.findElement(By.xpath("//select[@id='id_gender']//option[contains(@value,'m')]"));
    String genderExpected1 = Gender.MALE.getGender();
    Assert.assertEquals("Gender is wrong", genderExpected1, genderActual1.getAttribute("value"));

    WebElement companyActual = driver.findElement(By.xpath("//input[@id='id_company']"));
    String companyExpected = Company.EMI.getCompany();
    Assert.assertEquals("Company is wrong",companyExpected, companyActual.getAttribute("value"));

    WebElement positionActual = driver.findElement(By.xpath("//input[@id='id_work']"));
    String positionExpected = Position.POSITIONONE.getPosition();
    Assert.assertEquals("Position is wrong", positionExpected, positionActual.getAttribute("value"));

    WebElement checkAdding1 = driver.findElement(By.xpath("//select[@id='id_experience-0-experience']"));
    Assert.assertTrue(checkAdding1.isDisplayed());

    WebElement checkAdding2 = driver.findElement(By.xpath("//select[@id='id_experience-0-level']"));
    Assert.assertTrue(checkAdding2.isDisplayed());
  }
}




