"""
Conftest for tests without Allure.
Imports fixtures from main conftest.
"""
import sys
import os

# Import main conftest
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../..')))
# Fixtures from main conftest will be available automatically


