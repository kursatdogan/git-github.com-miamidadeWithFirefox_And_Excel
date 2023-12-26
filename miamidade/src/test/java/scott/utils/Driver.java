
package scott.utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;


public class Driver {
    public Driver() {
    }
    // InheritableThreadLocal  --> this is like a container, bag, pool.
    // in this pool we can have separate objects for each thread
    // for each thread, in InheritableThreadLocal we can have separate object for that thread
    // driver class will provide separate webdriver object per thread
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    public static WebDriver get() {
        //if this thread doesn't have driver - create it and add to pool
        if (driverPool.get() == null) {
//            if we pass the driver from terminal then use that one
//           if we do not pass the driver from terminal then use the one properties file
            ChromeOptions chromeOptions = new ChromeOptions();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser");
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();

                    // chromeOptions.addArguments("incognito");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--window-size=1920,1300");
                    //chromeOptions.addArguments("start-maximized");
                    /*HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
                    chromeOptionsMap.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
                    chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
                    chromeOptionsMap.put("download.default_directory", "C:\\Users\\Downloads\\test\\");
                    //chromeOptionsMap.put("download.default_directory", "\\Users\\kursatdogan\\Downloads\\test\\");
                    chromeOptions.setExperimentalOption("prefs", chromeOptionsMap);
                    chromeOptions.addArguments("--headless");*/

                    /*HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
                    chromeOptionsMap.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
                    chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
                    chromeOptionsMap.put("download.default_directory", "C:\\Users\\Downloads\\test\\");
                    chromeOptionsMap.put("download.prompt_for_download", false); // Disable download prompts


                    chromeOptionsMap.put("download.directory_upgrade", true);
                    chromeOptionsMap.put("safebrowsing.enabled", true);

                    chromeOptions.setExperimentalOption("prefs", chromeOptionsMap);
                    chromeOptions.addArguments("--headless");*/

                    HashMap<String, Object> chromeOptionsMap = new HashMap<>();
                    chromeOptionsMap.put("plugins.plugins_disabled", new String[]{"Chrome PDF Viewer"});
                    chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
                    chromeOptionsMap.put("download.default_directory", "C:\\Users\\Downloads\\test\\");
                    chromeOptionsMap.put("download.prompt_for_download", false);
                    chromeOptionsMap.put("download.directory_upgrade", true);
                    chromeOptionsMap.put("safebrowsing.enabled", true);

                    chromeOptions.setExperimentalOption("prefs", chromeOptionsMap);
                    chromeOptions.addArguments("--headless");


//                    options.add_experimental_option("excludeSwitches", ["enable-automation"])
//                    options.add_experimental_option('useAutomationExtension', False)
//                    options.add_argument("--disable-blink-features=AutomationControlled")
//                    chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//                    chromeOptions.setExperimentalOption("useAutomationExtension",false);
//                    chromeOptions.addArguments("disable-blink-features");
//                    chromeOptions.addArguments("disable-blink-features=AutomationControlled");
                    // chromeOptions.addArguments("--auto-open-devtools-for-tabs");
//                    LoggingPreferences logPrefs = new LoggingPreferences();
//                    logPrefs.enable(LogType.BROWSER, Level.ALL);
//                    chromeOptions.setCapability(CapabilityType.LOGGING_PREFS,logPrefs);
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    // chromeOptions.addArguments("incognito");
                    //chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.setHeadless(true);
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;

            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }


}








/*
package scott.utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;


public class Driver {
    public Driver() {
    }
    // InheritableThreadLocal  --> this is like a container, bag, pool.
    // in this pool we can have separate objects for each thread
    // for each thread, in InheritableThreadLocal we can have separate object for that thread
    // driver class will provide separate webdriver object per thread
    public static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    public static WebDriver get() {
        //if this thread doesn't have driver - create it and add to pool
        if (driverPool.get() == null) {
//            if we pass the driver from terminal then use that one
//           if we do not pass the driver from terminal then use the one properties file
            ChromeOptions chromeOptions = new ChromeOptions();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser");
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--window-size=1920,1300");


                     HashMap<String, Object> chromeOptionsMap = new HashMap<>();
                    chromeOptionsMap.put("plugins.plugins_disabled", new String[]{"Chrome PDF Viewer"});
                    chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
                    chromeOptionsMap.put("download.default_directory", "C:\\Users\\Downloads\\test\\");
                    chromeOptionsMap.put("download.prompt_for_download", false);
                    chromeOptionsMap.put("download.directory_upgrade", true);
                    chromeOptionsMap.put("safebrowsing.enabled", true);

                    chromeOptions.setExperimentalOption("prefs", chromeOptionsMap);
                    chromeOptions.addArguments("--headless");

                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;

            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }

}
*/