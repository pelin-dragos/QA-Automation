"""
Data validation utilities for scraped data
"""
import re
import logging
from typing import List, Dict, Any

logger = logging.getLogger(__name__)


class DataValidator:
    """Class for validating scraped data"""
    
    @staticmethod
    def validate_url(url: str) -> bool:
        """
        Validate URL format.

        Args:
            url: URL string

        Returns:
            bool: True if URL is valid
        """
        pattern = re.compile(
            r'^https?://'  # http:// or https://
            r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+[A-Z]{2,6}\.?|'  # domain...
            r'localhost|'  # localhost...
            r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})'  # ...or ip
            r'(?::\d+)?'  # optional port
            r'(?:/?|[/?]\S+)$', re.IGNORECASE
        )
        return bool(pattern.match(url))
    
    @staticmethod
    def validate_email(email: str) -> bool:
        """
        Validate email format.

        Args:
            email: Email string

        Returns:
            bool: True if email is valid
        """
        pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
        return bool(re.match(pattern, email))
    
    @staticmethod
    def validate_text(text: str, min_length: int = 0, max_length: int = None) -> Dict[str, Any]:
        """
        Validează text content
        
        Args:
            text: Text string
            min_length: Minimum length
            max_length: Maximum length (optional)
            
        Returns:
            dict: Validation result
        """
        result = {
            'valid': True,
            'errors': []
        }
        
        if not text or not isinstance(text, str):
            result['valid'] = False
            result['errors'].append('Text is empty or not a string')
            return result
        
        if len(text) < min_length:
            result['valid'] = False
            result['errors'].append(f'Text too short (min: {min_length})')
        
        if max_length and len(text) > max_length:
            result['valid'] = False
            result['errors'].append(f'Text too long (max: {max_length})')
        
        return result
    
    @staticmethod
    def validate_number(value: Any, min_value: float = None, max_value: float = None) -> Dict[str, Any]:
        """
        Validează număr
        
        Args:
            value: Value de validat
            min_value: Minimum value (optional)
            max_value: Maximum value (optional)
            
        Returns:
            dict: Validation result
        """
        result = {
            'valid': True,
            'errors': []
        }
        
        try:
            num_value = float(value)
        except (ValueError, TypeError):
            result['valid'] = False
            result['errors'].append(f'Not a valid number: {value}')
            return result
        
        if min_value is not None and num_value < min_value:
            result['valid'] = False
            result['errors'].append(f'Value too small (min: {min_value})')
        
        if max_value is not None and num_value > max_value:
            result['valid'] = False
            result['errors'].append(f'Value too large (max: {max_value})')
        
        return result
    
    @staticmethod
    def validate_scraped_links(links: List[Dict]) -> Dict[str, Any]:
        """
        Validează linkuri scraped
        
        Args:
            links: Lista de link dictionaries
            
        Returns:
            dict: Validation result
        """
        result = {
            'valid': True,
            'errors': [],
            'valid_links': [],
            'invalid_links': []
        }
        
        validator = DataValidator()
        
        for link in links:
            url = link.get('url', '')
            if validator.validate_url(url):
                result['valid_links'].append(link)
            else:
                result['invalid_links'].append(link)
                result['errors'].append(f'Invalid URL: {url}')
        
        if result['invalid_links']:
            result['valid'] = False
        
        return result
    
    @staticmethod
    def validate_table_data(table_data: List[List[str]]) -> Dict[str, Any]:
        """
        Validează date din tabel
        
        Args:
            table_data: Lista de rows (lista de cells)
            
        Returns:
            dict: Validation result
        """
        result = {
            'valid': True,
            'errors': [],
            'row_count': len(table_data),
            'column_counts': []
        }
        
        if not table_data:
            result['valid'] = False
            result['errors'].append('Table data is empty')
            return result
        
        # Check consistency (all rows should have same number of columns)
        column_counts = [len(row) for row in table_data]
        result['column_counts'] = column_counts
        
        if len(set(column_counts)) > 1:
            result['valid'] = False
            result['errors'].append('Inconsistent column counts across rows')
        
        return result
    
    @staticmethod
    def validate_data_completeness(data: List[Dict], required_fields: List[str]) -> Dict[str, Any]:
        """
        Validează completitudinea datelor
        
        Args:
            data: Lista de dictionaries cu date
            required_fields: Lista de fields obligatorii
            
        Returns:
            dict: Validation result
        """
        result = {
            'valid': True,
            'errors': [],
            'complete_records': 0,
            'incomplete_records': 0
        }
        
        for i, record in enumerate(data):
            missing_fields = [field for field in required_fields if field not in record or not record[field]]
            
            if missing_fields:
                result['incomplete_records'] += 1
                result['errors'].append(f'Record {i} missing fields: {missing_fields}')
            else:
                result['complete_records'] += 1
        
        if result['incomplete_records'] > 0:
            result['valid'] = False
        
        return result

