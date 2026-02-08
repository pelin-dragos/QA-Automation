"""
Login Page for Cross-Platform Testing
"""
from selenium.webdriver.common.by import By
from pages.base_page import BasePage
import time
import logging

logger = logging.getLogger(__name__)


class LoginPage(BasePage):
    """Login Page Object"""
    
    # Locatori
    USERNAME_FIELD = (By.ID, "user-name")
    PASSWORD_FIELD = (By.ID, "password")
    LOGIN_BUTTON = (By.ID, "login-button")
    
    def __init__(self, driver):
        """Constructor"""
        super().__init__(driver)
        self.base_url = "https://www.saucedemo.com/"
    
    def navigate_to(self):
        """Navigate to the login page"""
        super().navigate_to(self.base_url)
    
    def login(self, username, password):
        """
        Login complet
        
        Args:
            username: Username
            password: Password
        """
        self.navigate_to()
        
        username_field = self.wait_for_element(self.USERNAME_FIELD)
        username_field.clear()
        username_field.send_keys(username)
        
        password_field = self.wait_for_element(self.PASSWORD_FIELD)
        password_field.clear()
        password_field.send_keys(password)
        
        login_button = self.wait_for_element(self.LOGIN_BUTTON)
        login_button.click()
        
        time.sleep(2)  # Wait for page transition
    
    def is_logged_in(self):
        """Check if the user is logged in"""
        current_url = self.driver.current_url
        return "inventory.html" in current_url or "inventory" in current_url

