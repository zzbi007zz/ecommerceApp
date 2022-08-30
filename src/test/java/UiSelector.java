import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import platform.Platform;

public class UiSelector {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumdriver = DriverFactory.getDriver(Platform.android);
        try {
            MobileElement searchBar = appiumdriver.findElement(MobileBy.AccessibilityId("searchInput"));
            searchBar.click();
            MobileElement navHomeBtn = appiumdriver.findElement(MobileBy.AccessibilityId("homeIcon"));
            navHomeBtn.click();
            MobileElement navCartBtn = appiumdriver.findElement(MobileBy.AccessibilityId("cartIcon"));
            navCartBtn.click();
            MobileElement navUserBtn = appiumdriver.findElement(MobileBy.AccessibilityId("userIcon"));
            navUserBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appiumdriver.quit();
    }
}
