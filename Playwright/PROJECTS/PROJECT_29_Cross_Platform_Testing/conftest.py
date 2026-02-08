"""
Pytest configuration and fixtures for Cross-Platform Testing.
Optimized for running on Windows, Linux, macOS.
"""
import pytest
import logging
import os
import platform
import sys
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

# Platform detection
SYSTEM = platform.system().lower()
IS_WINDOWS = SYSTEM == 'windows'
IS_LINUX = SYSTEM == 'linux'
IS_MACOS = SYSTEM == 'darwin'
PYTHON_VERSION = f"{sys.version_info.major}.{sys.version_info.minor}"


@pytest.fixture(scope="function")
def driver():
    """
    Fixture for WebDriver - optimized for cross-platform
    Adaptează configurarea în funcție de platformă
    """
    platform_name = platform.system()
    logger.info(f"Setting up WebDriver on {platform_name} (Python {PYTHON_VERSION})...")
    
    chrome_options = Options()
    chrome_options.add_argument("--disable-blink-features=AutomationControlled")
    chrome_options.add_argument("--disable-dev-shm-usage")
    chrome_options.add_argument("--no-sandbox")
    
    # Platform-specific options
    if IS_WINDOWS:
        chrome_options.add_argument("--start-maximized")
    elif IS_LINUX:
        # Linux specific options
        chrome_options.add_argument("--headless")  # Optional for CI
    elif IS_MACOS:
        chrome_options.add_argument("--start-maximized")
    
    try:
        driver_path = ChromeDriverManager().install()
        service = Service(driver_path)
        driver = webdriver.Chrome(service=service, options=chrome_options)
    except Exception as e:
        logger.error(f"Error setting up ChromeDriver: {e}")
        try:
            driver = webdriver.Chrome(options=chrome_options)
        except Exception as e2:
            raise Exception(f"Cannot create WebDriver: {e2}")
    
    # Store platform info
    driver.platform = platform_name
    driver.python_version = PYTHON_VERSION
    
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
def platform_info():
    """Fixture that returns platform information"""
    return {
        'system': platform.system(),
        'platform': platform.platform(),
        'machine': platform.machine(),
        'processor': platform.processor(),
        'python_version': PYTHON_VERSION,
        'is_windows': IS_WINDOWS,
        'is_linux': IS_LINUX,
        'is_macos': IS_MACOS
    }


@pytest.fixture
def ci_environment():
    """Fixture for CI/CD environment detection"""
    return {
        'ci': os.getenv('CI', 'false').lower() == 'true',
        'github_actions': os.getenv('GITHUB_ACTIONS', 'false').lower() == 'true',
        'runner_os': os.getenv('RUNNER_OS', platform.system()),
        'python_version': os.getenv('PYTHON_VERSION', PYTHON_VERSION)
    }


def pytest_configure(config):
    """Pytest configuration for cross-platform"""
    logger.info(f"Running tests on {platform.system()} ({platform.platform()})")
    logger.info(f"Python version: {sys.version}")

