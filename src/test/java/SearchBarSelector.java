import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import platform.Platform;

public class SearchBarSelector {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumdriver = DriverFactory.getDriver(Platform.android);
        try{

        }catch (Exception e){e.printStackTrace();}
    }
}
