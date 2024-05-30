import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;

public class SeleniumLinkedInMain {
    private static final String EMAIL = "yarinbenmoshe@gmail.com";
    private static final String PASSWORD = "x";
    private static final String CHROMEDRIVER_PATH = "C:\\Program Files\\chromedriver-win64\\Chromedriver.exe";

    public static void main(String[] args) {

        // Set up the Chrome Web Driver
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
        WebDriver driver = getChromeWebDriver();

        // The main flow of the home task (Not a test as nothing is being tested)
        try {
            SeleniumLinkedInActions actions = new SeleniumLinkedInActions(driver);
            actions.loginAndGoToProfile(EMAIL, PASSWORD);
            Map<String, String> profileInfo = actions.getProfileInfoAndGoToConnections();

            SeleniumLinkedInCrawler crawler = new SeleniumLinkedInCrawler(driver);
            List<Map<String, String>> connectionsData = crawler.crawlForConnections();

            JsonActions.exportAsJson(profileInfo, connectionsData);
        } catch (Exception e) {
            System.out.println("Failed to execute the test: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    // Create and return the Chrome Web Driver
    private static WebDriver getChromeWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-web-security"); // Disable web security for WebSocket testing
        options.addArguments("--remote-allow-origins=*"); // https://stackoverflow.com/questions/75678572/java-io-ioexception-invalid-status-code-403-text-forbidden

        return new ChromeDriver(options);
    }
}
