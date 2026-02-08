"""
Web scraping utilities with Selenium and BeautifulSoup.
Respect robots.txt and Terms of Service!
"""
import time
import logging
from bs4 import BeautifulSoup
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import requests

logger = logging.getLogger(__name__)


class WebScraper:
    """Class for web scraping with Selenium and BeautifulSoup"""
    
    def __init__(self, driver: WebDriver):
        """
        Constructor
        
        Args:
            driver: WebDriver instance
        """
        self.driver = driver
        self.wait = WebDriverWait(driver, 10)
        self.scraped_data = []
    
    def check_robots_txt(self, base_url, http_session=None):
        """
        Check robots.txt for permissions.

        Args:
            base_url: Site base URL
            http_session: Requests session (optional)

        Returns:
            dict: Robots.txt information
        """
        from urllib.parse import urljoin, urlparse
        
        try:
            parsed = urlparse(base_url)
            robots_url = f"{parsed.scheme}://{parsed.netloc}/robots.txt"
            
            if http_session:
                response = http_session.get(robots_url, timeout=5)
            else:
                response = requests.get(robots_url, timeout=5)
            
            if response.status_code == 200:
                logger.info(f"Found robots.txt: {robots_url}")
                return {
                    'exists': True,
                    'url': robots_url,
                    'content': response.text[:500]  # First 500 chars
                }
            else:
                return {
                    'exists': False,
                    'url': robots_url,
                    'status_code': response.status_code
                }
        except Exception as e:
            logger.warning(f"Error checking robots.txt: {e}")
            return {
                'exists': False,
                'error': str(e)
            }
    
    def get_page_source(self):
        """Get page source and parse with BeautifulSoup"""
        try:
            page_source = self.driver.page_source
            soup = BeautifulSoup(page_source, 'lxml')
            return soup
        except Exception as e:
            logger.error(f"Error parsing page source: {e}")
            return None
    
    def scrape_text_content(self, selector=None):
        """
        Scrape text content din pagină
        
        Args:
            selector: CSS selector (optional)
            
        Returns:
            list: Lista de text content
        """
        soup = self.get_page_source()
        if not soup:
            return []
        
        try:
            if selector:
                elements = soup.select(selector)
                texts = [elem.get_text(strip=True) for elem in elements if elem.get_text(strip=True)]
            else:
                # Get all text content
                texts = [text.strip() for text in soup.stripped_strings if text.strip()]
            
            return texts
        except Exception as e:
            logger.error(f"Error scraping text content: {e}")
            return []
    
    def scrape_links(self, base_url=None):
        """
        Scrape toate linkurile din pagină
        
        Args:
            base_url: Base URL for absolute URLs
            
        Returns:
            list: Lista de links
        """
        soup = self.get_page_source()
        if not soup:
            return []
        
        try:
            links = []
            for link in soup.find_all('a', href=True):
                href = link['href']
                
                # Convert relative to absolute
                if base_url and not href.startswith('http'):
                    from urllib.parse import urljoin
                    href = urljoin(base_url, href)
                
                text = link.get_text(strip=True)
                links.append({
                    'url': href,
                    'text': text,
                    'title': link.get('title', '')
                })
            
            return links
        except Exception as e:
            logger.error(f"Error scraping links: {e}")
            return []
    
    def scrape_table_data(self, table_selector=None):
        """
        Scrape date din tabele
        
        Args:
            table_selector: CSS selector for table (optional)
            
        Returns:
            list: Lista de date din tabel (rows)
        """
        soup = self.get_page_source()
        if not soup:
            return []
        
        try:
            tables = soup.find_all('table') if not table_selector else soup.select(table_selector)
            table_data = []
            
            for table in tables:
                rows = []
                for tr in table.find_all('tr'):
                    cells = [td.get_text(strip=True) for td in tr.find_all(['td', 'th'])]
                    if cells:
                        rows.append(cells)
                
                if rows:
                    table_data.append(rows)
            
            return table_data
        except Exception as e:
            logger.error(f"Error scraping table data: {e}")
            return []
    
    def scrape_elements_by_selector(self, selector, attribute=None):
        """
        Scrape elemente după CSS selector
        
        Args:
            selector: CSS selector
            attribute: Attribute de extras (optional)
            
        Returns:
            list: Lista de elemente cu datele extrase
        """
        soup = self.get_page_source()
        if not soup:
            return []
        
        try:
            elements = soup.select(selector)
            data = []
            
            for elem in elements:
                item = {
                    'text': elem.get_text(strip=True),
                    'tag': elem.name
                }
                
                if attribute:
                    item['attribute'] = elem.get(attribute, '')
                
                # Add all attributes
                item['attributes'] = elem.attrs
                
                data.append(item)
            
            return data
        except Exception as e:
            logger.error(f"Error scraping elements: {e}")
            return []
    
    def wait_and_scrape(self, selector, timeout=10):
        """
        Așteaptă ca element să fie prezent și scrape
        
        Args:
            selector: CSS selector
            timeout: Timeout în secunde
            
        Returns:
            list: Scraped data
        """
        try:
            from selenium.webdriver.common.by import By
            
            # Convert CSS selector to By strategy
            if selector.startswith('#'):
                by_locator = (By.ID, selector[1:])
            elif selector.startswith('.'):
                by_locator = (By.CLASS_NAME, selector[1:])
            else:
                by_locator = (By.CSS_SELECTOR, selector)
            
            self.wait.until(EC.presence_of_element_located(by_locator))
            
            # Wait a bit for content to load
            time.sleep(1)
            
            # Scrape
            return self.scrape_elements_by_selector(selector)
        except TimeoutException:
            logger.warning(f"Element not found: {selector}")
            return []
        except Exception as e:
            logger.error(f"Error in wait_and_scrape: {e}")
            return []
    
    def get_scraped_data(self):
        """Returnează toate datele scraped"""
        return self.scraped_data
    
    def clear_scraped_data(self):
        """Șterge datele scraped"""
        self.scraped_data = []

