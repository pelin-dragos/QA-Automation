"""
Self-Healing Locator System
Proof-of-concept for self-healing tests when locators change
"""
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException
from utils.ai_element_finder import AIElementFinder
import logging

logger = logging.getLogger(__name__)


class SelfHealingLocator:
    """
    Self-Healing Locator System
    Dacă un locator eșuează, încearcă să găsească elementul folosind alternative strategies
    """
    
    def __init__(self, driver=None):
        """
        Constructor
        
        Args:
            driver: WebDriver instance
        """
        self.driver = driver
        self.ai_finder = AIElementFinder(driver)
        self.locator_cache = {}  # Cache for successful locators
    
    def set_driver(self, driver):
        """Set WebDriver"""
        self.driver = driver
        self.ai_finder.set_driver(driver)
    
    def find_element_healing(self, primary_locator, description=None, timeout=10):
        """
        Find element with self-healing capabilities
        
        Args:
            primary_locator: Locator principal (tuple: By, value)
            description: Element description (for healing)
            timeout: Timeout for wait
            
        Returns:
            WebElement: Elementul găsit
        """
        if not self.driver:
            raise ValueError("Driver not set")
        
        # Strategy 1: Try primary locator
        try:
            wait = WebDriverWait(self.driver, timeout)
            element = wait.until(EC.presence_of_element_located(primary_locator))
            
            # Cache successful locator
            if description:
                self.locator_cache[description] = primary_locator
            
            logger.info(f"Element found using primary locator: {primary_locator}")
            return element
        except (TimeoutException, NoSuchElementException) as e:
            logger.warning(f"Primary locator failed: {primary_locator}. Attempting healing...")
        
        # Strategy 2: Try cached locator
        if description and description in self.locator_cache:
            cached_locator = self.locator_cache[description]
            if cached_locator != primary_locator:
                try:
                    wait = WebDriverWait(self.driver, timeout)
                    element = wait.until(EC.presence_of_element_located(cached_locator))
                    logger.info(f"Element found using cached locator: {cached_locator}")
                    return element
                except:
                    pass
        
        # Strategy 3: Try alternative locators
        alternatives = self._generate_alternative_locators(primary_locator)
        for alt_locator in alternatives:
            try:
                wait = WebDriverWait(self.driver, timeout)
                element = wait.until(EC.presence_of_element_located(alt_locator))
                
                # Update cache
                if description:
                    self.locator_cache[description] = alt_locator
                
                logger.info(f"Element found using alternative locator: {alt_locator} (healing successful)")
                return element
            except:
                continue
        
        # Strategy 4: AI-based search
        if description:
            try:
                element = self.ai_finder.find_best_match(description, self.driver)
                if element:
                    # Generate locator from found element
                    new_locator = self._element_to_locator(element)
                    if description:
                        self.locator_cache[description] = new_locator
                    
                    logger.info(f"Element found using AI search: {description} (healing successful)")
                    return element
            except Exception as e:
                logger.error(f"AI search failed: {e}")
        
        # All strategies failed
        raise NoSuchElementException(
            f"Could not find element with primary locator {primary_locator} "
            f"and healing strategies failed"
        )
    
    def click_healing(self, primary_locator, description=None, timeout=10):
        """
        Click cu self-healing
        
        Args:
            primary_locator: Locator principal
            description: Element description
            timeout: Timeout
            
        Returns:
            bool: Success
        """
        element = self.find_element_healing(primary_locator, description, timeout)
        
        # Try click
        try:
            wait = WebDriverWait(self.driver, timeout)
            wait.until(EC.element_to_be_clickable((primary_locator[0], primary_locator[1])))
            element.click()
            logger.info(f"Successfully clicked element: {description or primary_locator}")
            return True
        except:
            # Try with found element
            try:
                element.click()
                logger.info(f"Successfully clicked element (alternative): {description or primary_locator}")
                return True
            except Exception as e:
                logger.error(f"Click failed: {e}")
                raise
    
    def send_keys_healing(self, primary_locator, text, description=None, timeout=10):
        """
        Send keys cu self-healing
        
        Args:
            primary_locator: Locator principal
            text: Text de trimis
            description: Element description
            timeout: Timeout
            
        Returns:
            bool: Success
        """
        element = self.find_element_healing(primary_locator, description, timeout)
        
        try:
            element.clear()
            element.send_keys(text)
            logger.info(f"Successfully sent keys to element: {description or primary_locator}")
            return True
        except Exception as e:
            logger.error(f"Send keys failed: {e}")
            raise
    
    def _generate_alternative_locators(self, locator):
        """
        Generate alternative locators from main locator
        
        Args:
            locator: Locator principal (tuple: By, value)
            
        Returns:
            List[tuple]: Lista de alternative locators
        """
        alternatives = []
        by_type, value = locator
        
        if by_type == By.ID:
            # Try name
            alternatives.append((By.NAME, value))
            # Try class name
            alternatives.append((By.CLASS_NAME, value))
            # Try CSS selector
            alternatives.append((By.CSS_SELECTOR, f"#{value}"))
        
        elif by_type == By.NAME:
            # Try ID
            alternatives.append((By.ID, value))
            # Try CSS selector
            alternatives.append((By.CSS_SELECTOR, f"[name='{value}']"))
        
        elif by_type == By.CLASS_NAME:
            # Try CSS selector
            alternatives.append((By.CSS_SELECTOR, f".{value}"))
        
        elif by_type == By.XPATH:
            # Try simplified XPath
            if '//' in value and '@id=' in value:
                # Extract ID
                import re
                match = re.search(r"@id='([^']+)'", value)
                if match:
                    id_value = match.group(1)
                    alternatives.append((By.ID, id_value))
                    alternatives.append((By.CSS_SELECTOR, f"#{id_value}"))
        
        return alternatives
    
    def _element_to_locator(self, element):
        """
        Convertește element la locator
        
        Args:
            element: WebElement
            
        Returns:
            tuple: Locator (By, value)
        """
        try:
            # Try ID
            elem_id = element.get_attribute('id')
            if elem_id:
                return (By.ID, elem_id)
        except:
            pass
        
        try:
            # Try name
            elem_name = element.get_attribute('name')
            if elem_name:
                return (By.NAME, elem_name)
        except:
            pass
        
        # Fallback to XPath
        try:
            xpath = self.driver.execute_script(
                "function getXPath(element) {"
                "  if (element.id !== '') return \"//*[@id='\" + element.id + \"']\";"
                "  if (element === document.body) return '/html/body';"
                "  var ix = 0;"
                "  var siblings = element.parentNode.childNodes;"
                "  for (var i = 0; i < siblings.length; i++) {"
                "    var sibling = siblings[i];"
                "    if (sibling === element) {"
                "      return getXPath(element.parentNode) + '/' + element.tagName.toLowerCase() + '[' + (ix + 1) + ']';"
                "    }"
                "    if (sibling.nodeType === 1 && sibling.tagName === element.tagName) {"
                "      ix++;"
                "    }"
                "  }"
                "  return null;"
                "}"
                "return getXPath(arguments[0]);",
                element
            )
            if xpath:
                return (By.XPATH, xpath)
        except:
            pass
        
        return None

