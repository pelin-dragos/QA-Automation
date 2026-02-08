"""
Login Page cu AI-Powered element finding
"""
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
import time
import logging

logger = logging.getLogger(__name__)


class LoginPage(BasePage):
    """Login Page Object cu AI support"""
    
    # Traditional locators
    USERNAME_FIELD = (By.ID, "user-name")
    PASSWORD_FIELD = (By.ID, "password")
    LOGIN_BUTTON = (By.ID, "login-button")
    
    def __init__(self, driver, ai_finder=None):
        """Constructor"""
        super().__init__(driver, ai_finder)
        self.base_url = "https://www.saucedemo.com/"
    
    def navigate_to(self):
        """Navigate to the login page"""
        super().navigate_to(self.base_url)
    
    def login(self, username, password):
        """Login complet"""
        self.navigate_to()
        
        username_field = self.wait_for_element(self.USERNAME_FIELD)
        username_field.clear()
        username_field.send_keys(username)
        
        password_field = self.wait_for_element(self.PASSWORD_FIELD)
        password_field.clear()
        password_field.send_keys(password)
        
        login_button = self.wait_for_element(self.LOGIN_BUTTON)
        login_button.click()
        
        time.sleep(2)
    
    def login_with_ai(self, username, password):
        """
        Login folosind AI element finding
        
        Args:
            username: Username
            password: Password
        """
        self.navigate_to()
        
        # Find elements using AI
        username_field = self.find_element_ai("username input field")
        username_field.clear()
        username_field.send_keys(username)
        
        password_field = self.find_element_ai("password input field")
        password_field.clear()
        password_field.send_keys(password)
        
        login_button = self.find_element_ai("login button")
        login_button.click()
        
        time.sleep(2)
    
    def is_logged_in(self):
        """Check if the user is logged in"""
        current_url = self.driver.current_url
        return "inventory.html" in current_url or "inventory" in current_url

