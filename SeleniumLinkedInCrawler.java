import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumLinkedInCrawler {
    private final WebDriver driver;

    public SeleniumLinkedInCrawler(WebDriver driver) {
        this.driver = driver;
    }

    public List<Map<String, String>> crawlForConnections() {
        List<Map<String, String>> connectionsData = new ArrayList<>();
        List<WebElement> connectionsElements = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'mn-connection-card__details')]")));

        // Get the name, job, time of each connection and put it in our map
        try {
            for (WebElement connectionElement : connectionsElements) {
                Map<String, String> connectionMap = new HashMap<>();
                connectionMap.put("name", connectionElement.findElement(By.xpath(".//span[@class='mn-connection-card__name t-16 t-black t-bold']")).getText());
                connectionMap.put("job", connectionElement.findElement(By.xpath(".//span[@class='mn-connection-card__occupation t-14 t-black--light t-normal']")).getText());
                connectionMap.put("time", connectionElement.findElement(By.xpath(".//time[@class='time-badge t-12 t-black--light t-normal']")).getText());
                connectionsData.add(connectionMap);
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve connections data: " + e.getMessage());
        }
        return connectionsData;
    }
}