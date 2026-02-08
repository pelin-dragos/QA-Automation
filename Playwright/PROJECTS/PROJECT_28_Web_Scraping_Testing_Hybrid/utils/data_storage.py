"""
Data storage și processing utilities cu pandas
"""
import json
import os
import logging
from datetime import datetime
from typing import List, Dict, Any
import pandas as pd

logger = logging.getLogger(__name__)


class DataStorage:
    """Class for storage and processing of scraped data"""

    def __init__(self, storage_dir="data"):
        """
        Constructor.

        Args:
            storage_dir: Directory for storage
        """
        self.storage_dir = storage_dir
        
        # Create directory if not exists
        if not os.path.exists(self.storage_dir):
            os.makedirs(self.storage_dir)
    
    def save_to_json(self, data: List[Dict], filename: str):
        """
        Salvează date în JSON
        
        Args:
            data: Date de salvat
            filename: Nume fișier (fără extensie)
        """
        filepath = os.path.join(self.storage_dir, f"{filename}.json")
        
        try:
            with open(filepath, 'w', encoding='utf-8') as f:
                json.dump(data, f, indent=2, ensure_ascii=False)
            
            logger.info(f"Data saved to {filepath}")
            return filepath
        except Exception as e:
            logger.error(f"Error saving to JSON: {e}")
            return None
    
    def save_to_csv(self, data: List[Dict], filename: str):
        """
        Salvează date în CSV cu pandas
        
        Args:
            data: Date de salvat (list of dicts)
            filename: Nume fișier (fără extensie)
        """
        filepath = os.path.join(self.storage_dir, f"{filename}.csv")
        
        try:
            df = pd.DataFrame(data)
            df.to_csv(filepath, index=False, encoding='utf-8')
            
            logger.info(f"Data saved to {filepath}")
            return filepath
        except Exception as e:
            logger.error(f"Error saving to CSV: {e}")
            return None
    
    def save_to_dataframe(self, data: List[Dict]) -> pd.DataFrame:
        """
        Convertește date în pandas DataFrame
        
        Args:
            data: Date de convertit
            
        Returns:
            DataFrame: pandas DataFrame
        """
        try:
            df = pd.DataFrame(data)
            return df
        except Exception as e:
            logger.error(f"Error creating DataFrame: {e}")
            return pd.DataFrame()
    
    def process_data(self, data: List[Dict], operations: List[str] = None):
        """
        Process date cu pandas operations
        
        Args:
            data: Date de procesat
            operations: Lista de operations (e.g., ['sort', 'filter', 'aggregate'])
            
        Returns:
            DataFrame: Processed data
        """
        df = self.save_to_dataframe(data)
        
        if df.empty:
            return df
        
        # Basic operations
        if operations:
            if 'sort' in operations:
                # Sort by first column if exists
                if len(df.columns) > 0:
                    df = df.sort_values(by=df.columns[0])
            
            if 'filter' in operations:
                # Remove empty rows
                df = df.dropna(how='all')
        
        return df
    
    def aggregate_data(self, data: List[Dict], group_by: str, aggregate: str):
        """
        Agregare date cu pandas
        
        Args:
            data: Date de agregat
            group_by: Column for group by
            aggregate: Aggregation operation (count, sum, mean, etc.)
            
        Returns:
            DataFrame: Aggregated data
        """
        df = self.save_to_dataframe(data)
        
        if df.empty or group_by not in df.columns:
            return pd.DataFrame()
        
        try:
            if aggregate == 'count':
                result = df.groupby(group_by).size().reset_index(name='count')
            elif aggregate == 'sum':
                numeric_cols = df.select_dtypes(include=['number']).columns
                if len(numeric_cols) > 0:
                    result = df.groupby(group_by)[numeric_cols[0]].sum().reset_index()
                else:
                    result = pd.DataFrame()
            else:
                result = df.groupby(group_by).agg(aggregate)
            
            return result
        except Exception as e:
            logger.error(f"Error aggregating data: {e}")
            return pd.DataFrame()
    
    def load_from_json(self, filename: str) -> List[Dict]:
        """
        Load data from JSON.

        Args:
            filename: File name (without extension)

        Returns:
            list: Loaded data
        """
        filepath = os.path.join(self.storage_dir, f"{filename}.json")
        
        try:
            if os.path.exists(filepath):
                with open(filepath, 'r', encoding='utf-8') as f:
                    data = json.load(f)
                logger.info(f"Data loaded from {filepath}")
                return data
            else:
                logger.warning(f"File not found: {filepath}")
                return []
        except Exception as e:
            logger.error(f"Error loading from JSON: {e}")
            return []
    
    def load_from_csv(self, filename: str) -> pd.DataFrame:
        """
        Load data from CSV.

        Args:
            filename: File name (without extension)

        Returns:
            DataFrame: pandas DataFrame
        """
        filepath = os.path.join(self.storage_dir, f"{filename}.csv")
        
        try:
            if os.path.exists(filepath):
                df = pd.read_csv(filepath)
                logger.info(f"Data loaded from {filepath}")
                return df
            else:
                logger.warning(f"File not found: {filepath}")
                return pd.DataFrame()
        except Exception as e:
            logger.error(f"Error loading from CSV: {e}")
            return pd.DataFrame()

