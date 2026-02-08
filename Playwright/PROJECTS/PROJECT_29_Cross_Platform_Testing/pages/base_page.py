"""
Base Page for all page objects.
Optimized for Cross-Platform Testing.
"""
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import logging
import platform

logger = logging.getLogger(__name__)


class BasePage:
    """Base class for all page objects"""
    
    def __init__(self, driver):
        """
        Constructor
        
        Args:
            driver: WebDriver instance
        """
        self.driver = driver
        self.wait = WebDriverWait(driver, 10)
        self.logger = logging.getLogger(self.__class__.__name__)
        self.platform = platform.system()
        
        # Log platform info
        if hasattr(driver, 'platform'):
            self.logger.info(f"Using {driver.platform} platform")
    
    def navigate_to(self, url):
        """
        Navighează la o URL
        
        Args:
            url: URL-ul de navigat
        """
        self.logger.info(f"Navigating to: {url} (Platform: {self.platform})")
        self.driver.get(url)
    
    def wait_for_element(self, locator, timeout=10):
        """Așteaptă ca un element să fie prezent"""
        try:
            return self.wait.until(EC.presence_of_element_located(locator))
        except TimeoutException:
            self.logger.error(f"Element not found: {locator}")
            raise
    
    def is_element_present(self, locator, timeout=5):
        """Check if an element is present"""
        try:
            self.wait_for_element(locator, timeout)
            return True
        except TimeoutException:
            return False

