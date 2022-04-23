import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class RealiseAutoTest {
  private final Logger logger = (Logger) LogManager.getLogger(RealiseAutoTest.class);
  private WebDriver driver;
  private final IServerConfiguration serverConfiguration = ConfigFactory.create(IServerConfiguration.class);

  @Before
  public void startUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
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
    Assert.assertEquals("Current Url wrong", serverConfiguration.url(), currentUrl);
    //Авторизоваться на сайте
    String loginExpected = serverConfiguration.login();
    Assert.assertEquals("Login is doesn't correct", serverConfiguration.login(), loginExpected);
    driver.findElement(By.xpath("//button[contains(@data-modal-id, 'new-log-reg')]")).click();
    driver.findElement(By.xpath("//input[@type='text'][contains(@class,'email')]")).sendKeys(loginExpected);

    String emailExpected = serverConfiguration.password();
    Assert.assertEquals("Password is doesn't correct", serverConfiguration.password(), emailExpected);
    driver.findElement(By.xpath("//input[@type='password'][contains(@class,'new-input')]")).sendKeys(serverConfiguration.password());
    driver.findElement(By.xpath("//button[contains(@class,'new-button new-button_full new-button_blue new-button_md')][contains(text(),'Войти')]")).click();
    //Войти в личный кабинет
    By itemTextUsername = By.xpath("//*[contains(@class, 'header2-menu__item-text__username')]");
    WebElement clickItemTextUsername = driver.findElement(itemTextUsername);
    clickItemTextUsername.click();
    Assert.assertTrue(clickItemTextUsername.isDisplayed());

    By dropDownTextName = By.xpath("//*[contains(@class, 'header2-menu__dropdown-text_name')]");
    WebElement clickDropDownTextName = driver.findElement(dropDownTextName);
    clickDropDownTextName.click();
    WebElement checkTitleText = driver.findElement(By.xpath("//*[contains(@class,'title__text')]"));
    Assert.assertTrue(checkTitleText.isDisplayed());

    //В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
    String fnameExpected = serverConfiguration.fname();
    String lnameExpected = serverConfiguration.lname();
    String bloggernameExpected = serverConfiguration.bloggername();
    String dateOfbirthdayExpected = serverConfiguration.dateofbirthday();
    driver.findElement(By.xpath("//*[contains(@name,'fname_latin')]")).sendKeys(fnameExpected);
    driver.findElement(By.xpath("//*[contains(@name,'lname_latin')]")).sendKeys(lnameExpected);
    driver.findElement(By.xpath("//*[contains(@name,'blog_name')]")).sendKeys(bloggernameExpected);
    driver.findElement(By.xpath("//*[contains(@name,'date_of_birth')]")).sendKeys(dateOfbirthdayExpected);
    WebElement dateOfBirth = driver.findElement(By.xpath("//input[@title='День рождения']"));
    dateOfBirth.sendKeys(Keys.ENTER);
    Assert.assertEquals("Field with First Name is not correct", serverConfiguration.fname(), fnameExpected);
    boolean checkJohn = driver.findElement(By.xpath("//*[contains(@name,'fname_latin')]")).isDisplayed();
    Assert.assertEquals("Field with Last Name is not correct", serverConfiguration.lname(), lnameExpected);
    boolean checkSmith = driver.findElement(By.xpath("//*[contains(@name,'lname_latin')]")).isDisplayed();
    Assert.assertEquals("Field with Blogger Name is not correct", serverConfiguration.bloggername(), bloggernameExpected);
    boolean checkBloggerName = driver.findElement(By.xpath("//*[contains(@name,'blog_name')]")).isDisplayed();
    Assert.assertEquals("Field with Date of Birthday is not correct", serverConfiguration.dateofbirthday(), dateOfbirthdayExpected);
    boolean checkDateOfBirthday = driver.findElement(By.xpath("//*[contains(@name,'date_of_birth')]")).isDisplayed();

    JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("window.scrollBy(0,620)");

    driver.findElement(By.xpath("//*[contains(@class,'select lk-cv-block__input lk-cv-block__input_full js-lk-cv-dependent-master js-lk-cv-custom-select')]//span[@class='placeholder'][contains(text(),'Не указано')]")).click();
    driver.findElement(By.xpath("//button[contains(@title,'Россия')]")).click();
    WebElement checkFieldRussia = driver.findElement(By.cssSelector("[name='country']~.js-custom-select-presentation"));
    Assert.assertTrue(checkFieldRussia.isDisplayed());

    driver.findElement(By.xpath("//*[contains(@class,'input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation')]//span[contains(text(),'Город')]")).click();
    driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-option')][contains(@title,'Москва')]")).click();
    WebElement checkFieldMoscow = driver.findElement(By.cssSelector("[name='city']~.js-custom-select-presentation"));
    Assert.assertTrue(checkFieldMoscow.isDisplayed());

    driver.findElement(By.xpath("//*[@class='select lk-cv-block__input lk-cv-block__input_full js-lk-cv-custom-select']//label//div[1]")).click();
    driver.findElement(By.xpath("//button[contains(@title,'Начальный уровень (Beginner)')]")).click();
    WebElement checkEnglishLevel = driver.findElement(By.xpath("//*[contains(@class,'input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation')][text()='Начальный уровень (Beginner)']"));
    Assert.assertTrue(checkEnglishLevel.isDisplayed());

    driver.findElement(By.xpath("//span[contains(text(),'Да')]")).click();
    WebElement checkYeas = driver.findElement(By.xpath("//span[contains(text(),'Да')]"));
    driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]")).click();
    WebElement checkRemote = driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]"));
    Assert.assertTrue(checkYeas.isDisplayed());
    Assert.assertTrue(checkRemote.isDisplayed());

    JavascriptExecutor jse1 = (JavascriptExecutor) driver;
    jse1.executeScript("window.scrollBy(0,690)");

    driver.findElement(By.xpath("//button[contains(@class,'js-change-phone input-group__addon button')]")).click();
    WebElement clearField = driver.findElement(By.xpath("//input[@placeholder='Номер телефона']"));
    clearField.clear();
    clearField.click();
    clearField.sendKeys("+7 912 300 60 89");
    Assert.assertTrue(clearField.isDisplayed());

    driver.findElement(By.xpath("//button[@class='js-send button button_blue button_md']")).click();
    WebElement checkClose = driver.findElement(By.xpath("//*[contains(@class,'modal__close ic-close js-close-modal')]"));
    driver.findElement(By.xpath("//*[contains(@class,'modal__close ic-close js-close-modal')]")).click();

    driver.findElement(By.xpath("//*[@class='container__row js-formset-row']//div[@class='input input_full lk-cv-block__input input_straight-bottom-right input_straight-top-right input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']")).click();
    driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-options lk-cv-block__select-options_left js-custom-select-options-container')]//button[contains(@title,'VK')][normalize-space()='VK']")).click();
    WebElement checkContact = driver.findElement(By.xpath("//*[@class='container__row js-formset-row']//div[@class='input input_full lk-cv-block__input input_straight-bottom-right input_straight-top-right input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']"));
    Assert.assertTrue(checkContact.isDisplayed());

    WebElement checkJohnSmith = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-0-value']")).sendKeys("John Smith");
    Assert.assertTrue(checkJohnSmith.isDisplayed());

    driver.findElement(By.xpath("//button[contains(text(),'Добавить')]")).click();
    driver.findElement(By.xpath("//*[@class='container__row js-formset-row']//span[@class='placeholder'][contains(text(),'Способ связи')]")).click();
    driver.findElement(By.xpath("//*[@class='lk-cv-block__select-options lk-cv-block__select-options_left js-custom-select-options-container']//button[@title='OK'][normalize-space()='OK']")).click();
    WebElement checkContact1 = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-1-value']")).sendKeys("Americano");
    Assert.assertTrue(checkContact1.isDisplayed());

    WebElement checkGender = driver.findElement(By.xpath("//select[@id='id_gender']"));
    Select se = new Select(driver.findElement(By.xpath("//select[@id='id_gender']")));
    se.selectByValue("m");
    Assert.assertTrue(checkGender.isDisplayed());

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
    WebElement checkSaveAndFilllater = driver.findElement(By.xpath("//*[contains(@title,'Сохранить и заполнить позже')]"));
    driver.findElement(By.xpath("//*[contains(@title,'Сохранить и заполнить позже')]")).click();
    driver.manage().deleteAllCookies();
    //Открыть https://otus.ru в "чистом браузере"
    driver.get(serverConfiguration.url());
    String currentUrl2 = "https://otus.ru/";
    Assert.assertEquals("Current Url wrong", serverConfiguration.url(), currentUrl2);
    //Авторизоваться на сайте
    String loginExpected2 = serverConfiguration.login();
    Assert.assertEquals("Login is doesn't correct", serverConfiguration.login(), loginExpected2);
    driver.findElement(By.xpath("//button[contains(@data-modal-id, 'new-log-reg')]")).click();
    driver.findElement(By.xpath("//input[@type='text'][contains(@class,'email')]")).sendKeys(loginExpected2);

    String emailExpected2 = serverConfiguration.password();
    Assert.assertEquals("Password is doesn't correct", serverConfiguration.password(), emailExpected2);
    driver.findElement(By.xpath("//input[@type='password'][contains(@class,'new-input')]")).sendKeys(serverConfiguration.password());
    driver.findElement(By.xpath("//button[contains(@class,'new-button new-button_full new-button_blue new-button_md')][contains(text(),'Войти')]")).click();
    //Войти в личный кабинет
    By itemTextUsername2 = By.xpath("//*[contains(@class, 'header2-menu__item-text__username')]");
    WebElement clickItemTextUsername2 = driver.findElement(itemTextUsername2);
    clickItemTextUsername2.click();
    Assert.assertTrue(clickItemTextUsername2.isDisplayed());

    By dropDownTextName2 = By.xpath("//*[contains(@class, 'header2-menu__dropdown-text_name')]");
    WebElement clickDropDownTextName2 = driver.findElement(dropDownTextName2);
    clickDropDownTextName2.click();
    WebElement checkTitleText2 = driver.findElement(By.xpath("//*[contains(@class,'title__text')]"));
    Assert.assertEquals(true, checkTitleText2.isDisplayed());

    /////////////////////////////////////////////////////////////////////////////////////////////////
    WebElement actualWeblogName = driver.findElement(By.xpath("//*[contains(@id,'id_fname_latin')]"));
    Assert.assertEquals("First Name of user doesn't correct", serverConfiguration.fname(), actualWeblogName.getAttribute("value"));

    WebElement actualWebloglastName = driver.findElement(By.xpath("//*[contains(@value,'Smith')]"));
    Assert.assertEquals("Last Name of user doesn't correct", serverConfiguration.lname(), actualWebloglastName.getAttribute("value"));

    WebElement actualBloggerName = driver.findElement(By.xpath("//*[contains(@id,'id_blog_name')]"));
    Assert.assertEquals("Blogger Name is not correct", serverConfiguration.bloggername(), actualBloggerName.getAttribute("value"));

    WebElement actualDateOfBirth = driver.findElement(By.xpath("//*[contains(@value,'01.01.2001')]"));
    Assert.assertEquals("Date of Birth is not doesn't correct", serverConfiguration.dateofbirthday(), actualDateOfBirth.getAttribute("value"));

    JavascriptExecutor jse2 = (JavascriptExecutor) driver;
    jse2.executeScript("window.scrollBy(0,690)");

    driver.findElement(By.cssSelector("[name='country']~.js-custom-select-presentation"));
    WebElement actualFieldRussia = driver.findElement(By.cssSelector("[name='country']~.js-custom-select-presentation"));
    By fieldRussiaLocator = By.cssSelector("[name='country']~.js-custom-select-presentation");
    WebElement exceptedFieldRussia = driver.findElement(fieldRussiaLocator);
    Assert.assertNotEquals("Country is not correct", exceptedFieldRussia, actualFieldRussia.getAttribute("value"));

    driver.findElement(By.cssSelector("[name='city']~.js-custom-select-presentation"));
    WebElement actualFieldMoscow = driver.findElement(By.cssSelector("[name='city']~.js-custom-select-presentation"));
    By fieldMoscowLocator = By.cssSelector("[name='city']~.js-custom-select-presentation");
    WebElement exceptedFieldMoscow = driver.findElement(fieldMoscowLocator);
    Assert.assertNotEquals("City is not correct", exceptedFieldMoscow, actualFieldMoscow.getAttribute("value"));

    driver.findElement(By.cssSelector("[name='english_level']~.js-custom-select-presentation"));
    WebElement actualFieldLevel = driver.findElement(By.cssSelector("[name='english_level']~.js-custom-select-presentation"));
    Assert.assertTrue(actualFieldLevel.isDisplayed());

    driver.findElement(By.xpath("//span[contains(text(),'Да')]"));
    WebElement checkYeas1 = driver.findElement(By.xpath("//span[contains(text(),'Да')]"));
    Assert.assertTrue(checkYeas1.isDisplayed());

    driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]"));
    WebElement checkRemote1 = driver.findElement(By.xpath("//span[contains(text(),'Удаленно')]"));
    Assert.assertTrue(checkRemote1.isDisplayed());

    JavascriptExecutor jse3 = (JavascriptExecutor) driver;
    jse3.executeScript("window.scrollBy(0,690)");

    driver.findElement(By.xpath("//input[@id='id_email']"));
    WebElement checkFieldemail = driver.findElement(By.xpath("//input[@id='id_email']"));
    Assert.assertTrue(checkFieldemail.isDisplayed());

    driver.findElement(By.xpath("//input[@id='id_phone']"));
    WebElement clearField1 = driver.findElement(By.xpath("//input[@id='id_phone']"));
    Assert.assertTrue(clearField1.isDisplayed());

    driver.findElement(By.xpath("//*[@class='container__row js-formset-row']//div[@class='input input_full lk-cv-block__input input_straight-bottom-right input_straight-top-right input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']")).click();
    driver.findElement(By.xpath("//*[contains(@class,'lk-cv-block__select-options lk-cv-block__select-options_left js-custom-select-options-container')]//button[contains(@title,'VK')][normalize-space()='VK']")).click();
    WebElement checkContactVK = driver.findElement(By.xpath("//*[@class='container__row js-formset-row']//div[@class='input input_full lk-cv-block__input input_straight-bottom-right input_straight-top-right input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']"));
    Assert.assertTrue(checkContactVK.isDisplayed());

    WebElement checkJohnSmith2 = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
    Assert.assertTrue(checkJohnSmith2.isDisplayed());

    WebElement checkContactAmericano = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
    driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
    Assert.assertTrue(checkContactAmericano.isDisplayed());

    WebElement checkGender1 = driver.findElement(By.xpath("//select[@id='id_gender']"));
    Select select = new Select(driver.findElement(By.xpath("//select[@id='id_gender']")));
    select.selectByValue("m");
    Assert.assertTrue(checkGender1.isDisplayed());

    WebElement checkEmi = driver.findElement(By.xpath("//input[@id='id_company']"));
    driver.findElement(By.xpath("//input[@id='id_company']"));
    Assert.assertTrue(checkEmi.isDisplayed());

    By fieldFohLocator = By.xpath("//input[@id='id_work']");
    WebElement webElement = driver.findElement(fieldFohLocator);
    WebElement checkFoh = driver.findElement(By.xpath("//input[@id='id_work']"));
    driver.findElement(By.xpath("//input[@id='id_work']"));
    Assert.assertEquals(checkFoh.getAttribute("FOH"), webElement.getAttribute("FOH"));

    WebElement webExperience = driver.findElement(By.xpath("//select[@id='id_experience-0-experience']"));
    Select select1 = new Select(driver.findElement(By.xpath("//select[@id='id_experience-0-experience']")));
    select1.selectByValue("1");
    Assert.assertTrue(webExperience.isDisplayed());

  }
}




