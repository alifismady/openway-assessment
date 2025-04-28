import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class OpenwayAssessmentTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final Dotenv dotenv = Dotenv.load();
    private static final String EMAIL = dotenv.get("EMAIL_PERIPLUS");
    private static final String PASSWORD = dotenv.get("PASSWORD_PERIPLUS");

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testPeriplusWebsite() {
        driver.get("https://www.periplus.com/");

        loginToPeriplus();

        findProduct();

        addProductToCart();

        verifyProductInCart();

        System.out.println("Test Finished");
    }

    private void loginToPeriplus() {
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/header/div[2]/div/div[1]/div[3]/div/div[3]/a/i")));
        loginLink.click();
        System.out.println("Navigated to Login Page");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"login\"]/div/table/tbody/tr[2]/td/input")));
        emailField.sendKeys(EMAIL);
        System.out.println("Entered email");

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"ps\"]")));
        passwordField.sendKeys(PASSWORD);
        System.out.println("Entered password");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"button-login\"]")));
        loginButton.click();
        System.out.println("Clicked Login button");

        wait.until(ExpectedConditions.urlContains("account"));
        System.out.println("Login Success");
    }

    private void findProduct() {
        try {
            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"filter_name\"]")));
            searchField.sendKeys("Diary of a Wimpy Kid");
            System.out.println("Entered Product");

            Thread.sleep(2000);

            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/header/div[2]/div/div[1]/div[2]/div/form/div/button")));
            searchButton.click();
            System.out.println("Clicked Search Button");

            Thread.sleep(2000);

            WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/div/div[2]/h3/a")));
            firstProduct.click();
            System.out.println("Clicked First Product");

            wait.until(ExpectedConditions.urlContains("diary-of-a-wimpy-kid"));
            System.out.println("Entered Product Page Success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addProductToCart() {
        try {
            Thread.sleep(2000);

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[1]/div/div/div[3]/div[1]/div[3]/div[1]/button")));
            addToCartButton.click();
            System.out.println("Clicked Add to Cart");

            WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[1]/div/div/div/div/div[3]")));

            Assert.assertTrue(successModal.isDisplayed(), "Modal Should Pop Up");
            System.out.println("Success Add to Cart");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void verifyProductInCart() {
        try {
            Thread.sleep(2000);

            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"show-your-cart\"]/a/i")));
            cartButton.click();
            System.out.println("Clicked Cart Button");

            wait.until(ExpectedConditions.urlContains("cart"));

            Thread.sleep(2000);

            WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"basket\"]/div/div/div/div/div/div/div[1]")));

            Assert.assertTrue(cartItem.isDisplayed(), "Product should be displayed in the cart");
            System.out.println("Product Displayed Verified");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @AfterTest
    public void cleanUp() {
        try {
            System.out.println("Cleaning Up");
            driver.get("https://www.periplus.com/checkout/cart");

            Thread.sleep(2000);

            WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"basket\"]/div/div/div/div/div/div/div[1]/div[2]/div[5]/div/a[1]")));
            removeBtn.click();
            System.out.println("Product Removed from Cart");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}