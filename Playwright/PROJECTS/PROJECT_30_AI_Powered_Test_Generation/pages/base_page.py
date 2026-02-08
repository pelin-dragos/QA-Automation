"""
Base Page for all page objects.
With support for AI-powered element finding.
"""
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import logging

logger = logging.getLogger(__name__)


class BasePage:
    """Base class for all page objects with AI support"""
    
    def __init__(self, driver, ai_finder=None):
        """
        Constructor
        
        Args:
            driver: WebDriver instance
            ai_finder: AIElementFinder instance (optional)
        """
        self.driver = driver
        self.wait = WebDriverWait(driver, 10)
        self.logger = logging.getLogger(self.__class__.__name__)
        self.ai_finder = ai_finder
    
    def navigate_to(self, url):
        """Navighează la o URL"""
        self.logger.info(f"Navigating to: {url}")
        self.driver.get(url)
    
    def wait_for_element(self, locator, timeout=10):
        """Așteaptă ca un element să fie prezent"""
        try:
            return self.wait.until(EC.presence_of_element_located(locator))
        except TimeoutException:
            self.logger.error(f"Element not found: {locator}")
            raise
    
    def find_element_ai(self, description):
        """
        Find element using AI finder.

        Args:
            description: Natural language description of the element
            
        Returns:
            WebElement: Elementul găsit
        """
        if not self.ai_finder:
            from utils.ai_element_finder import AIElementFinder
            self.ai_finder = AIElementFinder(self.driver)
        
        element = self.ai_finder.find_best_match(description, self.driver)
        if element:
            self.logger.info(f"AI found element: {description}")
            return element
        else:
            raise ValueError(f"AI could not find element: {description}")

