"""
Pytest configuration and fixtures for AI-Powered Test Generation.
Proof-of-concept for AI-driven test automation.
"""
import pytest
import logging
import os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager

# Logging configuration
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)


@pytest.fixture(scope="function")
def driver():
    """Fixture for WebDriver"""
    chrome_options = Options()
    chrome_options.add_argument("--disable-blink-features=AutomationControlled")
    chrome_options.add_argument("--disable-dev-shm-usage")
    chrome_options.add_argument("--no-sandbox")
    
    try:
        driver_path = ChromeDriverManager().install()
        service = Service(driver_path)
        driver = webdriver.Chrome(service=service, options=chrome_options)
    except Exception as e:
        logger.error(f"Error setting up ChromeDriver: {e}")
        driver = webdriver.Chrome(options=chrome_options)
    
    yield driver
    
    logger.info("Closing WebDriver...")
    try:
        driver.quit()
    except Exception as e:
        logger.warning(f"Error closing WebDriver: {e}")


@pytest.fixture
def base_url():
    """Base URL for tests"""
    return os.getenv("BASE_URL", "https://www.saucedemo.com/")


@pytest.fixture
def ai_engine():
    """Fixture for AI Engine"""
    from utils.ai_element_finder import AIElementFinder
    return AIElementFinder()


@pytest.fixture
def smart_wait():
    """Fixture for Smart Wait Strategy"""
    from utils.smart_wait import SmartWaitStrategy
    return SmartWaitStrategy()


@pytest.fixture
def self_healing_locator():
    """Fixture for Self-Healing Locator"""
    from utils.self_healing import SelfHealingLocator
    return SelfHealingLocator()


def pytest_configure(config):
    """Pytest configuration for AI-powered testing"""
    logger.info("AI-Powered Test Generation Framework initialized")

