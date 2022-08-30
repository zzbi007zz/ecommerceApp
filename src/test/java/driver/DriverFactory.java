package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import platform.Platform;

import java.net.URL;
import java.util.concurrent.TimeUnit;



public class DriverFactory implements MobileCapabilityTypeEx {

    private AppiumDriver<MobileElement> appiumDriver;

    public static AppiumDriver<MobileElement> getDriver(Platform platform) {
        AppiumDriver<MobileElement> appiumDriver = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(UDID, "LMV409N0831cfe7");
        desiredCapabilities.setCapability(APP_PACKAGE, "com.tuhuynh.sdetproecommerce");
        desiredCapabilities.setCapability(APP_ACTIVITY, "host.exp.exponent.MainActivity");
        URL appiumServer = null;
        try {
            appiumServer = new URL("http://localhost:4723/wd/hub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

        if (appiumServer == null)
            throw new RuntimeException("Cannot connect to selenium grid");


        switch (platform) {
            case android:
                appiumDriver = new AndroidDriver<>(appiumServer, desiredCapabilities);
                break;
            case ios:
                appiumDriver = new IOSDriver<>(appiumServer, desiredCapabilities);
                break;

        }
        appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return appiumDriver;
    }


    public AppiumDriver<MobileElement> getDriver(Platform platform, String udid, String systemPort, String platformVersion) {

        String remoteInfoViaEnv = System.getenv("remote");
        String remoteInfoViaCommandEnv = System.getProperty("remote");
        String isRemote = remoteInfoViaEnv == null ? remoteInfoViaCommandEnv :remoteInfoViaEnv;

        if(isRemote == null) {
            throw new IllegalArgumentException("Please provide env variable");
        }

        String targerServer = "https://localhost:4723/wd/hub";
        if(isRemote.equals(true)) {
            String hubIPAddr = System.getenv("hub");
            if(hubIPAddr==null) hubIPAddr = System.getProperty("hub");
            if(hubIPAddr == null) {
                throw new IllegalArgumentException("Please provide hub ip address via env [hub]");
            }
            targerServer= hubIPAddr +":4444/wd/hub";
        }

        if (appiumDriver == null) {
            URL appiumServer = null;
            try {
                appiumServer = new URL(targerServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ;

            if (appiumServer == null)
                throw new RuntimeException("Cannot connect to selenium grid");

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(PLATFORM_NAME,platform);

            switch (platform) {

                case android:
                    desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
                    desiredCapabilities.setCapability(UDID, udid);
                    desiredCapabilities.setCapability(APP_PACKAGE, "com.tuhuynh.sdetproecommerce");
                    desiredCapabilities.setCapability(APP_ACTIVITY, "host.exp.exponent.MainActivity");
                    desiredCapabilities.setCapability(SYSTEM_PORT, systemPort);
                    appiumDriver = new AndroidDriver<>(appiumServer, desiredCapabilities);

                    break;
                case ios:
                    desiredCapabilities.setCapability(AUTOMATION_NAME, "XCUITest");
                    desiredCapabilities.setCapability(DEVICE_NAME, udid);
                    desiredCapabilities.setCapability(PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(BUNDLE_ID, "org.wdioNativeDemoApp");
                    desiredCapabilities.setCapability(WDA_LOCAL_PORT, systemPort);
                    appiumDriver = new IOSDriver<>(appiumServer, desiredCapabilities);
                    break;

            }
            appiumDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        return appiumDriver;
    }

    public void quitAppiumDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;
        }
    }
}


