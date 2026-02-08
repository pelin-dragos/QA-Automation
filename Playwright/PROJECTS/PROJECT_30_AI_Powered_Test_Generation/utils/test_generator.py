"""
AI-Powered Test Generator
Generate tests automatically from natural language descriptions.
Proof-of-concept for test generation.
"""
import re
import logging

logger = logging.getLogger(__name__)


class TestGenerator:
    """
    AI-Powered Test Generator
    Generate test code from natural language descriptions
    """
    
    def __init__(self):
        """Constructor"""
        self.templates = self._load_templates()
    
    def generate_test_from_description(self, description, test_name=None):
        """
        Generate test code from description
        
        Args:
            description: Natural language description (e.g. "test login with valid credentials")
            test_name: Test name (optional)
            
        Returns:
            str: Generated test code
        """
        description_lower = description.lower()
        
        # Extract test type
        test_type = self._extract_test_type(description_lower)
        
        # Generate test name
        if not test_name:
            test_name = self._generate_test_name(description_lower)
        
        # Generate test code based on type
        if test_type == 'login':
            return self._generate_login_test(description_lower, test_name)
        elif test_type == 'navigation':
            return self._generate_navigation_test(description_lower, test_name)
        elif test_type == 'form':
            return self._generate_form_test(description_lower, test_name)
        elif test_type == 'click':
            return self._generate_click_test(description_lower, test_name)
        else:
            return self._generate_generic_test(description_lower, test_name)
    
    def _extract_test_type(self, description):
        """Extrage tipul testului din descriere"""
        if 'login' in description or 'sign in' in description:
            return 'login'
        elif 'navigate' in description or 'go to' in description:
            return 'navigation'
        elif 'form' in description or 'fill' in description or 'submit' in description:
            return 'form'
        elif 'click' in description or 'button' in description:
            return 'click'
        return 'generic'
    
    def _generate_test_name(self, description):
        """Generate test name from description"""
        # Remove common words
        words = re.findall(r'\b\w+\b', description)
        filtered = [w for w in words if w not in ['test', 'a', 'an', 'the', 'with', 'and', 'or']]
        test_name = '_'.join(filtered[:5])  # Max 5 words
        return f"test_{test_name}"
    
    def _generate_login_test(self, description, test_name):
        """Generate test for login"""
        template = """
def {test_name}(driver, base_url):
    \"\"\"Test: {description}\"\"\"
    from pages.login_page import LoginPage
    
    login_page = LoginPage(driver)
    login_page.navigate_to()
    
    # AI-generated: login with credentials
    login_page.login("standard_user", "secret_sauce")
    
    assert login_page.is_logged_in(), "Login should succeed"
"""
        return template.format(test_name=test_name, description=description)
    
    def _generate_navigation_test(self, description, test_name):
        """Generate test for navigation"""
        template = """
def {test_name}(driver, base_url):
    \"\"\"Test: {description}\"\"\"
    from pages.login_page import LoginPage
    
    login_page = LoginPage(driver)
    login_page.navigate_to()
    
    # AI-generated: verify page loads
    assert login_page.is_element_present(login_page.USERNAME_FIELD), "Page should load"
"""
        return template.format(test_name=test_name, description=description)
    
    def _generate_form_test(self, description, test_name):
        """Generate test for form"""
        template = """
def {test_name}(driver, base_url):
    \"\"\"Test: {description}\"\"\"
    from pages.login_page import LoginPage
    
    login_page = LoginPage(driver)
    login_page.navigate_to()
    
    # AI-generated: fill form fields
    username_field = login_page.wait_for_element(login_page.USERNAME_FIELD)
    username_field.send_keys("standard_user")
    
    password_field = login_page.wait_for_element(login_page.PASSWORD_FIELD)
    password_field.send_keys("secret_sauce")
    
    # AI-generated: submit form
    login_button = login_page.wait_for_element(login_page.LOGIN_BUTTON)
    login_button.click()
"""
        return template.format(test_name=test_name, description=description)
    
    def _generate_click_test(self, description, test_name):
        """Generate test for click"""
        template = """
def {test_name}(driver, base_url):
    \"\"\"Test: {description}\"\"\"
    from pages.login_page import LoginPage
    
    login_page = LoginPage(driver)
    login_page.navigate_to()
    
    # AI-generated: find and click button
    login_button = login_page.wait_for_element(login_page.LOGIN_BUTTON)
    login_button.click()
"""
        return template.format(test_name=test_name, description=description)
    
    def _generate_generic_test(self, description, test_name):
        """Generate generic test"""
        template = """
def {test_name}(driver, base_url):
    \"\"\"Test: {description}\"\"\"
    from pages.login_page import LoginPage
    
    login_page = LoginPage(driver)
    login_page.navigate_to()
    
    # AI-generated: basic test
    assert login_page.driver.current_url == base_url or base_url in login_page.driver.current_url
"""
        return template.format(test_name=test_name, description=description)
    
    def _load_templates(self):
        """Load templates for test generation"""
        return {
            'login': self._generate_login_test,
            'navigation': self._generate_navigation_test,
            'form': self._generate_form_test,
            'click': self._generate_click_test,
            'generic': self._generate_generic_test
        }

