"""
Smart Wait Strategies
AI-based wait strategies care se adaptează la comportamentul paginii
"""
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.by import By
import time
import logging

logger = logging.getLogger(__name__)


class SmartWaitStrategy:
    """
    Smart Wait Strategy
    Adaptează wait time-ul bazat pe comportamentul paginii
    """
    
    def __init__(self, driver=None, base_timeout=10):
        """
        Constructor
        
        Args:
            driver: WebDriver instance
            base_timeout: Base timeout în secunde
        """
        self.driver = driver
        self.base_timeout = base_timeout
        self.page_load_history = []  # History for page load times
        self.adaptive_timeout = base_timeout
    
    def set_driver(self, driver):
        """Set WebDriver"""
        self.driver = driver
    
    def smart_wait_for_element(self, locator, description=None):
        """
        Smart wait for element with adaptive timeout
        
        Args:
            locator: Locator (tuple: By, value)
            description: Element description (for logging)
            
        Returns:
            WebElement: Elementul găsit
        """
        if not self.driver:
            raise ValueError("Driver not set")
        
        start_time = time.time()
        
        # Adaptive timeout bazat pe history
        timeout = self._calculate_adaptive_timeout()
        
        try:
            wait = WebDriverWait(self.driver, timeout)
            element = wait.until(EC.presence_of_element_located(locator))
            
            # Record success time
            elapsed = time.time() - start_time
            self._record_success(elapsed)
            
            logger.info(f"Element found in {elapsed:.2f}s (adaptive timeout: {timeout}s): {description or locator}")
            return element
            
        except TimeoutException:
            # Record failure
            elapsed = time.time() - start_time
            self._record_failure(elapsed)
            
            logger.warning(f"Element not found after {timeout}s: {description or locator}")
            raise
    
    def smart_wait_for_clickable(self, locator, description=None):
        """
        Smart wait for clickable element
        
        Args:
            locator: Locator
            description: Description
            
        Returns:
            WebElement: Elementul clickable
        """
        if not self.driver:
            raise ValueError("Driver not set")
        
        timeout = self._calculate_adaptive_timeout()
        
        try:
            wait = WebDriverWait(self.driver, timeout)
            element = wait.until(EC.element_to_be_clickable(locator))
            logger.info(f"Element clickable found: {description or locator}")
            return element
        except TimeoutException:
            logger.warning(f"Element not clickable after {timeout}s: {description or locator}")
            raise
    
    def smart_wait_for_page_load(self, timeout=None):
        """
        Smart wait for page load with adaptive timeout
        
        Args:
            timeout: Custom timeout (optional)
            
        Returns:
            bool: Success
        """
        if not self.driver:
            raise ValueError("Driver not set")
        
        if timeout is None:
            timeout = self._calculate_adaptive_timeout()
        
        start_time = time.time()
        
        try:
            # Wait for document ready
            wait = WebDriverWait(self.driver, timeout)
            wait.until(lambda d: d.execute_script('return document.readyState') == 'complete')
            
            # Wait for jQuery (if present)
            try:
                wait.until(lambda d: d.execute_script('return jQuery.active == 0'))
            except:
                pass  # jQuery might not be present
            
            elapsed = time.time() - start_time
            self.page_load_history.append(elapsed)
            
            # Keep only last 10 records
            if len(self.page_load_history) > 10:
                self.page_load_history.pop(0)
            
            logger.info(f"Page loaded in {elapsed:.2f}s")
            return True
            
        except TimeoutException:
            elapsed = time.time() - start_time
            logger.warning(f"Page load timeout after {timeout}s")
            return False
    
    def wait_for_stability(self, locator, stability_time=0.5, max_wait=10):
        """
        Așteaptă ca un element să devină stabil (nu se mișcă)
        
        Args:
            locator: Locator element
            stability_time: Time in seconds for stability
            max_wait: Max wait time
            
        Returns:
            WebElement: Elementul stabil
        """
        if not self.driver:
            raise ValueError("Driver not set")
        
        start_time = time.time()
        last_position = None
        stable_start = None
        
        while time.time() - start_time < max_wait:
            try:
                element = self.driver.find_element(*locator)
                location = element.location
                
                if last_position == location:
                    if stable_start is None:
                        stable_start = time.time()
                    elif time.time() - stable_start >= stability_time:
                        logger.info(f"Element stable after {time.time() - start_time:.2f}s")
                        return element
                else:
                    last_position = location
                    stable_start = None
                
                time.sleep(0.1)
            except:
                time.sleep(0.1)
        
        raise TimeoutException(f"Element did not stabilize within {max_wait}s")
    
    def _calculate_adaptive_timeout(self):
        """
        Calculează adaptive timeout bazat pe history
        
        Returns:
            float: Adaptive timeout în secunde
        """
        if not self.page_load_history:
            return self.base_timeout
        
        # Media + 2x standard deviation
        import statistics
        mean = statistics.mean(self.page_load_history)
        
        if len(self.page_load_history) > 1:
            stdev = statistics.stdev(self.page_load_history)
            adaptive = mean + (2 * stdev)
        else:
            adaptive = mean * 1.5
        
        # Cap la max 30 seconds
        adaptive = min(adaptive, 30)
        
        # Cap la min base_timeout
        adaptive = max(adaptive, self.base_timeout)
        
        return adaptive
    
    def _record_success(self, elapsed_time):
        """Record success time for adaptive timeout"""
        self.page_load_history.append(elapsed_time)
        if len(self.page_load_history) > 10:
            self.page_load_history.pop(0)
    
    def _record_failure(self, elapsed_time):
        """Record failure for adaptive timeout"""
        # Increase timeout after failure
        self.adaptive_timeout = min(self.adaptive_timeout * 1.2, 30)

