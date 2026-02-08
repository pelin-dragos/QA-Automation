"""
Test Suite: AI-Powered Test Generation
Demonstrează capabilitățile AI-powered framework-ului
"""
import pytest
import sys
import os

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../../')))
from pages.login_page import LoginPage


@pytest.mark.ai_generated
class TestAIElementFinding:
    """Tests for AI element finding"""
    
    def test_find_element_by_description(self, driver, base_url, ai_engine):
        """Test: Găsire element folosind descriere natural language"""
        ai_engine.set_driver(driver)
        driver.get(base_url)
        
        # Find element using natural language description
        login_button = ai_engine.find_best_match("login button")
        
        assert login_button is not None, "AI should find login button"
        assert login_button.tag_name.lower() == 'input' or login_button.tag_name.lower() == 'button'
    
    def test_find_element_semantic(self, driver, base_url, ai_engine):
        """Test: Găsire element folosind semantic meaning"""
        ai_engine.set_driver(driver)
        driver.get(base_url)
        
        # Find elements using semantic description
        username_input = ai_engine.find_by_semantic_meaning("email input field", driver)
        
        assert len(username_input) > 0, "AI should find username input using semantic"
    
    def test_find_element_text_pattern(self, driver, base_url, ai_engine):
        """Test: Găsire element folosind text pattern"""
        ai_engine.set_driver(driver)
        driver.get(base_url)
        
        # Find elements using text pattern
        elements = ai_engine.find_by_text_pattern("login", "button")
        
        assert len(elements) > 0, "AI should find elements by text pattern"


@pytest.mark.self_healing
class TestSelfHealing:
    """Tests for self-healing locators"""
    
    def test_self_healing_locator_success(self, driver, base_url, self_healing_locator):
        """Test: Self-healing locator când primary locator funcționează"""
        self_healing_locator.set_driver(driver)
        driver.get(base_url)
        
        # Primary locator should work
        element = self_healing_locator.find_element_healing(
            (driver.find_element, ("id", "user-name")),  # Simulate locator
            description="username field"
        )
        
        # Convert to proper locator
        from selenium.webdriver.common.by import By
        element = self_healing_locator.find_element_healing(
            (By.ID, "user-name"),
            description="username field"
        )
        
        assert element is not None, "Self-healing should find element"
    
    def test_self_healing_click(self, driver, base_url, self_healing_locator):
        """Test: Self-healing click"""
        self_healing_locator.set_driver(driver)
        driver.get(base_url)
        
        from selenium.webdriver.common.by import By
        
        # Fill form first
        username_elem = driver.find_element(By.ID, "user-name")
        username_elem.send_keys("standard_user")
        
        password_elem = driver.find_element(By.ID, "password")
        password_elem.send_keys("secret_sauce")
        
        # Click with self-healing
        success = self_healing_locator.click_healing(
            (By.ID, "login-button"),
            description="login button"
        )
        
        assert success, "Self-healing click should succeed"
        
        # Verify login
        import time
        time.sleep(2)
        assert "inventory" in driver.current_url, "Login should succeed"


@pytest.mark.smart_wait
class TestSmartWait:
    """Tests for smart wait strategies"""
    
    def test_smart_wait_for_element(self, driver, base_url, smart_wait):
        """Test: Smart wait for element"""
        smart_wait.set_driver(driver)
        driver.get(base_url)
        
        from selenium.webdriver.common.by import By
        
        # Smart wait with adaptive timeout
        element = smart_wait.smart_wait_for_element(
            (By.ID, "user-name"),
            description="username field"
        )
        
        assert element is not None, "Smart wait should find element"
    
    def test_smart_wait_for_page_load(self, driver, base_url, smart_wait):
        """Test: Smart wait for page load"""
        smart_wait.set_driver(driver)
        
        # Navigate and wait
        driver.get(base_url)
        success = smart_wait.smart_wait_for_page_load()
        
        assert success, "Smart wait should detect page load"
    
    def test_smart_wait_for_clickable(self, driver, base_url, smart_wait):
        """Test: Smart wait for clickable element"""
        smart_wait.set_driver(driver)
        driver.get(base_url)
        
        from selenium.webdriver.common.by import By
        
        element = smart_wait.smart_wait_for_clickable(
            (By.ID, "login-button"),
            description="login button"
        )
        
        assert element is not None, "Smart wait should find clickable element"


@pytest.mark.ai_generated
class TestAILogin:
    """Tests for login using AI"""
    
    def test_login_with_ai_finding(self, driver, base_url, ai_engine):
        """Test: Login folosind AI element finding"""
        ai_engine.set_driver(driver)
        login_page = LoginPage(driver, ai_engine)
        
        # Login using AI-powered element finding
        login_page.login_with_ai("standard_user", "secret_sauce")
        
        assert login_page.is_logged_in(), "Login with AI should succeed"
    
    def test_login_traditional_vs_ai(self, driver, base_url, ai_engine):
        """Test: Comparare traditional vs AI login"""
        # Traditional
        login_page1 = LoginPage(driver)
        driver.get(base_url)
        login_page1.login("standard_user", "secret_sauce")
        assert login_page1.is_logged_in(), "Traditional login should work"
        
        # Reset
        driver.quit()
        from conftest import driver as new_driver
        driver = new_driver()
        
        # AI-powered
        ai_engine.set_driver(driver)
        login_page2 = LoginPage(driver, ai_engine)
        login_page2.login_with_ai("standard_user", "secret_sauce")
        assert login_page2.is_logged_in(), "AI login should work"


@pytest.mark.ai_generated
class TestPatternRecognition:
    """Tests for pattern recognition"""
    
    def test_recognize_form_pattern(self, driver, base_url, ai_engine):
        """Test: Recunoaștere pattern de form"""
        ai_engine.set_driver(driver)
        driver.get(base_url)
        
        # Recognize form fields
        username_field = ai_engine.find_by_semantic_meaning("username input", driver)
        password_field = ai_engine.find_by_semantic_meaning("password input", driver)
        
        assert len(username_field) > 0, "Should recognize username field"
        assert len(password_field) > 0, "Should recognize password field"
    
    def test_recognize_button_pattern(self, driver, base_url, ai_engine):
        """Test: Recunoaștere pattern de button"""
        ai_engine.set_driver(driver)
        driver.get(base_url)
        
        # Recognize buttons
        buttons = ai_engine.find_by_text_pattern("login", "button")
        buttons.extend(ai_engine.find_by_text_pattern("submit", "button"))
        
        assert len(buttons) > 0, "Should recognize button patterns"

