import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumLinkedInActions {
    private final WebDriver driver;

    public SeleniumLinkedInActions(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAndGoToProfile(String email, String password) {
        driver.get("https://www.linkedin.com/home");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Use CSS Selector to find the "Sign in" button and click it
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav__button-secondary.btn-md.btn-secondary-emphasis"))).click();

        // Enter login credentials
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username"))).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password")).submit();

        // Wait for the 'Me' button to be clickable using CSS selector
        try {
            WebElement meButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.global-nav__primary-link-me-menu-trigger")));
            meButton.click();
        } catch (Exception e) {
            System.out.println("Failed to find the 'Me' button: " + e.getMessage());
        }

        try {
            // Wait for the dropdown menu to be visible
            WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.global-nav__me-content")));
            // Click the 'View Profile' button within the dropdown menu
            WebElement viewProfileButton = dropdownMenu.findElement(By.xpath("//a[contains(@class, 'artdeco-button--secondary')]"));
            viewProfileButton.click();
        } catch (Exception e) {
            System.out.println("Failed to find 'View Profile' button: " + e.getMessage());
        }
    }

    public Map<String, String> getProfileInfoAndGoToConnections() {
        Map<String, String> profileInfo = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Get the user's name, workplace, city and then move on to the Connections page
        try {
            WebElement nameElement = driver.findElement(By.xpath("//h1[contains(@class, 'text-heading-xlarge inline t-24 v-align-middle break-words')]"));
            WebElement workplaceElement = driver.findElement(By.xpath("//ul[contains(@class, 'pv-text-details__right-panel')]//li//button[contains(@aria-label, 'Current company')]//div[contains(@class, 'inline-show-more-text--is-collapsed')]"));
            WebElement cityElement = driver.findElement(By.xpath("//span[contains(@class, 'text-body-small inline t-black--light break-words')]"));

            profileInfo.put("myName", nameElement.getText());
            profileInfo.put("myWorkplace", workplaceElement.getText());

            String cityText = cityElement.getText();
            if (cityText.contains(","))
                cityText = cityText.substring(0, cityText.indexOf(",")).trim();

            profileInfo.put("city", cityText);

            // Navigate to connections page
            WebElement connectionsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/mynetwork/invite-connect/connections/']")));
            connectionsLink.click();
        } catch (Exception e) {
            System.out.println("Failed to retrieve profile info or navigate to connections: " + e.getMessage());
        }

        return profileInfo;
    }
}