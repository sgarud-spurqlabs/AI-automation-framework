package com.spurqlabs.utils;

import com.microsoft.playwright.*;

/**
 * Singleton driver manager for Playwright
 */
public class PlaywrightDriver {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    /**
     * Initialize Playwright browser instance
     */
    public static void initDriver() {
        if (playwright == null) {
            playwright = Playwright.create();
            
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                    .setHeadless(ConfigReader.isHeadless())
                    .setSlowMo(ConfigReader.getSlowMo());

            String browserType = ConfigReader.getBrowser().toLowerCase();
            browser = switch (browserType) {
                case "firefox" -> playwright.firefox().launch(launchOptions);
                case "webkit" -> playwright.webkit().launch(launchOptions);
                default -> playwright.chromium().launch(launchOptions);
            };

            context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(1920, 1080));
            
            page = context.newPage();
            page.setDefaultTimeout(ConfigReader.getTimeout());
        }
    }

    /**
     * Get the current Page instance
     */
    public static Page getPage() {
        if (page == null) {
            initDriver();
        }
        return page;
    }

    /**
     * Navigate to URL
     */
    public static void navigateTo(String url) {
        getPage().navigate(url);
    }

    /**
     * Close browser and cleanup resources
     */
    public static void quitDriver() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
