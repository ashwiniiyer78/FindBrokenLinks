
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class GenerateBrokenLinks {
    private static WebDriver driver = null;
    public static void main(String args[]){
        String url = args[0];
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setAcceptInsecureCerts(true);
        ChromeOptions opt = new ChromeOptions();
        opt.merge(cap);
        driver = new ChromeDriver(opt);
        driver.manage().window().maximize();
        FindBrokenLinks fbl = new FindBrokenLinks(driver);
        fbl.printBrokenLinks(url);
    }
}
