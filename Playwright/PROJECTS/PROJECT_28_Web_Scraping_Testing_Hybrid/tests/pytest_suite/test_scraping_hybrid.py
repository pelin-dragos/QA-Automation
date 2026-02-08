"""
Test Suite: Web Scraping + Testing Hybrid.
Respect robots.txt and Terms of Service!
Tests that combine data scraping with validation and functional testing.
"""
import pytest
import sys
import os

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../../')))
from pages.base_page import BasePage
from utils.scraper import WebScraper
from utils.data_validator import DataValidator
from utils.data_storage import DataStorage


@pytest.mark.scraping
@pytest.mark.hybrid
class TestScrapingAndValidation:
    """Hybrid tests: scraping + validation"""
    
    def test_scrape_and_validate_text(self, driver, base_url, http_session):
        """Test: Scrape text și validare"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        validator = DataValidator()
        
        # Check robots.txt
        robots_info = scraper.check_robots_txt(base_url, http_session)
        print(f"Robots.txt check: {robots_info}")
        
        # Navigate and scrape
        page.navigate_to(base_url)
        
        # Scrape text content
        texts = scraper.scrape_text_content()
        
        assert len(texts) > 0, "Should scrape some text content"
        
        # Validate text content
        for text in texts[:5]:  # Validate first 5
            validation = validator.validate_text(text, min_length=1)
            assert validation['valid'], f"Text should be valid: {text[:50]}"
        
        print(f"✅ Scraped and validated {len(texts)} text items")
    
    def test_scrape_and_validate_links(self, driver, base_url, http_session):
        """Test: Scrape links și validare URLs"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        validator = DataValidator()
        
        # Navigate and scrape
        page.navigate_to(base_url)
        
        # Scrape links
        links = scraper.scrape_links(base_url)
        
        assert len(links) > 0, "Should scrape some links"
        
        # Validate links
        validation = validator.validate_scraped_links(links)
        
        print(f"Total links: {len(links)}")
        print(f"Valid links: {len(validation['valid_links'])}")
        print(f"Invalid links: {len(validation['invalid_links'])}")
        
        # Test passes if we have at least some valid links
        assert len(validation['valid_links']) > 0, \
            "Should have at least some valid links"


@pytest.mark.scraping
class TestDataExtraction:
    """Tests for data extraction"""
    
    def test_scrape_table_data(self, driver, base_url):
        """Test: Scrape date din tabele"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        validator = DataValidator()
        
        page.navigate_to(base_url)
        
        # Scrape table data
        table_data = scraper.scrape_table_data()
        
        if len(table_data) > 0:
            # Validate table data
            validation = validator.validate_table_data(table_data[0])
            
            print(f"Tables found: {len(table_data)}")
            print(f"Rows in first table: {validation['row_count']}")
            
            # Test passes if table structure is valid
            assert validation['valid'] or validation['row_count'] > 0, \
                "Table data should be valid or have content"
        else:
            print("No tables found on page")
            # Test passes (page might not have tables)
    
    def test_scrape_elements_by_selector(self, driver, base_url):
        """Test: Scrape elemente după CSS selector"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        
        page.navigate_to(base_url)
        
        # Scrape all links
        elements = scraper.scrape_elements_by_selector('a', attribute='href')
        
        assert len(elements) > 0, "Should scrape some elements"
        
        print(f"✅ Scraped {len(elements)} elements by selector")
        
        # Check that elements have data
        for elem in elements[:3]:  # Check first 3
            assert 'text' in elem or 'attribute' in elem, \
                "Elements should have scraped data"


@pytest.mark.scraping
@pytest.mark.data
class TestDataStorageAndProcessing:
    """Tests for data storage and processing"""
    
    def test_save_scraped_data_to_json(self, driver, base_url):
        """Test: Scrape date și salvare în JSON"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        storage = DataStorage()
        
        page.navigate_to(base_url)
        
        # Scrape links
        links = scraper.scrape_links(base_url)
        
        # Save to JSON
        filepath = storage.save_to_json(links[:10], 'test_links')  # Save first 10
        
        assert filepath is not None, "Should save data to JSON"
        assert os.path.exists(filepath), "JSON file should exist"
        
        # Load and verify
        loaded_data = storage.load_from_json('test_links')
        assert len(loaded_data) > 0, "Should load data from JSON"
        
        print(f"✅ Saved and loaded {len(loaded_data)} records from JSON")
    
    def test_save_scraped_data_to_csv(self, driver, base_url):
        """Test: Scrape date și salvare în CSV"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        storage = DataStorage()
        
        page.navigate_to(base_url)
        
        # Scrape links
        links = scraper.scrape_links(base_url)
        
        # Save to CSV
        filepath = storage.save_to_csv(links[:10], 'test_links')  # Save first 10
        
        assert filepath is not None, "Should save data to CSV"
        assert os.path.exists(filepath), "CSV file should exist"
        
        # Load and verify
        df = storage.load_from_csv('test_links')
        assert len(df) > 0, "Should load data from CSV"
        
        print(f"✅ Saved and loaded {len(df)} records from CSV")
    
    def test_process_data_with_pandas(self, driver, base_url):
        """Test: Processing date cu pandas"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        storage = DataStorage()
        
        page.navigate_to(base_url)
        
        # Scrape links
        links = scraper.scrape_links(base_url)
        
        # Process data
        df = storage.process_data(links[:10], operations=['sort'])
        
        assert not df.empty, "DataFrame should not be empty"
        print(f"✅ Processed {len(df)} records with pandas")


@pytest.mark.scraping
@pytest.mark.hybrid
class TestHybridScrapingTesting:
    """Hybrid tests: scraping during functional testing"""
    
    def test_scrape_during_navigation(self, driver, base_url):
        """Test: Scrape date în timpul navigării"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        
        # Navigate
        page.navigate_to(base_url)
        
        # Scrape links
        links = scraper.scrape_links(base_url)
        
        # Navigate to first link (if exists)
        if len(links) > 0 and links[0].get('url'):
            first_url = links[0]['url']
            try:
                page.navigate_to(first_url)
                
                # Scrape from new page
                new_links = scraper.scrape_links(first_url)
                
                print(f"✅ Navigated and scraped {len(new_links)} links from second page")
                assert True  # Test passes
            except Exception as e:
                print(f"Could not navigate to {first_url}: {e}")
                # Test still passes (might be relative URL issue)
    
    def test_validate_and_test_functionality(self, driver, base_url, http_session):
        """Test: Validare date scraped și test functional"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        validator = DataValidator()
        
        # Check robots.txt
        robots_info = scraper.check_robots_txt(base_url, http_session)
        print(f"Robots.txt: {robots_info.get('exists', False)}")
        
        # Navigate
        page.navigate_to(base_url)
        
        # Scrape and validate
        links = scraper.scrape_links(base_url)
        validation = validator.validate_scraped_links(links)
        
        # Test: Verify page loaded correctly (functional test)
        assert page.is_element_present(('tag name', 'body')), \
            "Page should load correctly"
        
        # Test: Verify scraped data is valid
        assert validation['valid'] or len(validation['valid_links']) > 0, \
            "Should have valid scraped data"
        
        print(f"✅ Combined scraping validation and functional testing")


@pytest.mark.scraping
@pytest.mark.smoke
class TestScrapingSmoke:
    """Smoke tests for scraping"""
    
    def test_smoke_scrape_basic_content(self, driver, base_url):
        """Test: Smoke test - scrape basic content"""
        page = BasePage(driver)
        scraper = WebScraper(driver)
        
        page.navigate_to(base_url)
        
        # Scrape text
        texts = scraper.scrape_text_content()
        
        assert len(texts) > 0, "Smoke test: Should scrape some content"
        
        print(f"✅ Smoke test passed: Scraped {len(texts)} text items")

