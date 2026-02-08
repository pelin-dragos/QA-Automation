"""
Test Suite: Cross-Platform Testing.
Identical tests that run on Windows, Linux, macOS.
"""
import pytest
import sys
import os

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../../')))
from pages.login_page import LoginPage
from utils.platform_utils import PlatformUtils


@pytest.mark.cross_platform
class TestCrossPlatformLogin:
    """Login tests on all platforms"""
    
    def test_login_success(self, driver, base_url, platform_info):
        """Test: Login reușit pe toate platformele"""
        login_page = LoginPage(driver)
        login_page.login("standard_user", "secret_sauce")
        
        assert login_page.is_logged_in(), \
            f"Login should succeed on {platform_info['system']}"
        
        # Log platform info
        print(f"Platform: {platform_info['system']}")
        print(f"Python: {platform_info['python_version']}")
        print(f"Browser: {driver.platform if hasattr(driver, 'platform') else 'Unknown'}")
    
    def test_login_failure(self, driver, base_url, platform_info):
        """Test: Login eșuat pe toate platformele"""
        login_page = LoginPage(driver)
        login_page.login("invalid_user", "invalid_password")
        
        assert not login_page.is_logged_in(), \
            f"Login should fail on {platform_info['system']}"


@pytest.mark.cross_platform
class TestCrossPlatformNavigation:
    """Navigation tests on all platforms"""
    
    def test_page_loads(self, driver, base_url, platform_info):
        """Test: Verificare încărcare pagină pe toate platformele"""
        login_page = LoginPage(driver)
        login_page.navigate_to()
        
        assert login_page.is_element_present(login_page.USERNAME_FIELD), \
            f"Page should load on {platform_info['system']}"
    
    def test_elements_present(self, driver, base_url, platform_info):
        """Test: Verificare elemente prezente pe toate platformele"""
        login_page = LoginPage(driver)
        login_page.navigate_to()
        
        assert login_page.is_element_present(login_page.USERNAME_FIELD)
        assert login_page.is_element_present(login_page.PASSWORD_FIELD)
        assert login_page.is_element_present(login_page.LOGIN_BUTTON)
        
        print(f"✅ All elements present on {platform_info['system']}")


@pytest.mark.cross_platform
class TestPlatformDetection:
    """Tests for platform detection"""
    
    def test_platform_info_detection(self, platform_info):
        """Test: Verificare platform detection"""
        assert 'system' in platform_info
        assert 'platform' in platform_info
        assert 'python_version' in platform_info
        
        print(f"System: {platform_info['system']}")
        print(f"Platform: {platform_info['platform']}")
        print(f"Machine: {platform_info['machine']}")
        print(f"Python: {platform_info['python_version']}")
    
    def test_platform_specific_handling(self, platform_info):
        """Test: Verificare platform-specific handling"""
        utils = PlatformUtils()
        config = utils.get_platform_specific_config()
        
        assert 'headless' in config
        assert 'window_size' in config
        
        print(f"Platform-specific config: {config}")
        
        # Platform should be detected correctly
        if platform_info['is_windows']:
            assert config['download_path'] is not None
        elif platform_info['is_linux']:
            assert config['download_path'] is not None
        elif platform_info['is_macos']:
            assert config['download_path'] is not None
    
    def test_ci_environment_detection(self, ci_environment):
        """Test: Verificare CI/CD environment detection"""
        assert 'ci' in ci_environment
        assert 'runner_os' in ci_environment
        assert 'python_version' in ci_environment
        
        print(f"CI Environment: {ci_environment}")
        
        # In CI, runner_os should be set
        if ci_environment['github_actions']:
            assert ci_environment['runner_os'] in ['Windows', 'Linux', 'macOS']


@pytest.mark.cross_platform
@pytest.mark.smoke
class TestCrossPlatformSmoke:
    """Smoke tests for cross-platform"""
    
    def test_smoke_platform_compatibility(self, driver, base_url, platform_info):
        """Test: Smoke test - verificare compatibilitate platformă"""
        login_page = LoginPage(driver)
        login_page.navigate_to()
        
        # Verify basic functionality works on all platforms
        assert login_page.is_element_present(login_page.LOGIN_BUTTON), \
            f"Smoke test should pass on {platform_info['system']}"
        
        print(f"✅ Smoke test passed on {platform_info['system']}")


@pytest.mark.cross_platform
class TestPlatformConsistency:
    """Tests for consistency verification between platforms"""
    
    def test_same_functionality_across_platforms(self, driver, base_url, platform_info):
        """Test: Verificare că funcționalitatea este identică pe toate platformele"""
        login_page = LoginPage(driver)
        
        # Navigate
        login_page.navigate_to()
        
        # Verify elements are present (should be same on all platforms)
        username_present = login_page.is_element_present(login_page.USERNAME_FIELD)
        password_present = login_page.is_element_present(login_page.PASSWORD_FIELD)
        button_present = login_page.is_element_present(login_page.LOGIN_BUTTON)
        
        assert username_present, f"Username field should be present on {platform_info['system']}"
        assert password_present, f"Password field should be present on {platform_info['system']}"
        assert button_present, f"Login button should be present on {platform_info['system']}"
        
        print(f"✅ Consistent functionality verified on {platform_info['system']}")

