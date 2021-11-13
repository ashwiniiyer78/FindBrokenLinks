import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FindBrokenLinks {
    private WebDriver driver = null;
    //public static final String homePage = "https://www.google.com";
    public static final String filepath = "./src/test/resources/BrokenLinksReport.html";

    public FindBrokenLinks(WebDriver driver) {
        this.driver = driver;
    }

    public void printBrokenLinks(String homePage) {
        HttpURLConnection connection = null;
        int respCode = 0;
        driver.get(homePage);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<WebElement> links = driver.findElements(By.tagName("a"));

        int validCount = 0;
        int invalidCount = 0;

        ExtentReports rep = new ExtentReports(filepath);
        ExtentTest test = rep.startTest("BrokenLinks");
        for (WebElement link : links) {
            try {
                String url = link.getAttribute("href");
                if (url == null || url.isEmpty()) {
                    test.log(LogStatus.ERROR, "URL is either not configured for anchor tag or it is empty");
                    System.out.println("url null or empty ");
                    invalidCount++;
                    continue;
                }
                URL urlPassed = new URL(url);
                connection = (HttpURLConnection) (urlPassed.openConnection());
                connection.setRequestMethod("HEAD");
                connection.connect();
                respCode = connection.getResponseCode();
                if (respCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                    test.log(LogStatus.FAIL, url + " is a broken link");
                    System.out.println("Invalid" + url);
                    invalidCount++;
                } else {
                    test.log(LogStatus.PASS, url + " is a valid link");
                    System.out.println("valid " + url);
                    validCount++;
                }

            } catch (IOException | StaleElementReferenceException e) {
                test.log(LogStatus.ERROR, e.getMessage());
            }

        }
        test.log(LogStatus.PASS, "Valid count is " + validCount + "invalid link count " + invalidCount);
        test.log(LogStatus.PASS, "The total url count is ::" + links.size());
        driver.quit();
        rep.endTest(test);
        rep.flush();


    }

}