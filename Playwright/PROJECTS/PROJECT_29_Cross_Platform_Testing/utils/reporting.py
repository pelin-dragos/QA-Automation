"""
Cross-platform reporting utilities
"""
import json
import os
import logging
from datetime import datetime
import platform

logger = logging.getLogger(__name__)


class CrossPlatformReporter:
    """Class for generating cross-platform reports"""

    def __init__(self, reports_dir="reports"):
        """
        Constructor.

        Args:
            reports_dir: Directory for reports
        """
        self.reports_dir = reports_dir
        
        # Create directory if not exists
        if not os.path.exists(self.reports_dir):
            os.makedirs(self.reports_dir)
    
    def generate_comparative_report(self, test_results_by_platform):
        """
        Generate comparative report between platforms.

        Args:
            test_results_by_platform: Dict cu results per platform
                {
                    'windows': [test_results],
                    'linux': [test_results],
                    'macos': [test_results]
                }
        """
        report = {
            'timestamp': datetime.now().isoformat(),
            'platforms': {},
            'comparison': {}
        }
        
        # Process results per platform
        for platform_name, results in test_results_by_platform.items():
            passed = len([r for r in results if r.get('status') == 'passed'])
            failed = len([r for r in results if r.get('status') == 'failed'])
            total = len(results)
            
            report['platforms'][platform_name] = {
                'total': total,
                'passed': passed,
                'failed': failed,
                'pass_rate': (passed / total * 100) if total > 0 else 0,
                'tests': results
            }
        
        # Generate comparison
        if len(test_results_by_platform) > 1:
            report['comparison'] = self._compare_platforms(report['platforms'])
        
        # Save JSON report
        json_file = os.path.join(self.reports_dir, 'cross_platform_report.json')
        with open(json_file, 'w') as f:
            json.dump(report, f, indent=2)
        
        # Generate text report
        text_file = os.path.join(self.reports_dir, 'cross_platform_report.txt')
        self._generate_text_report(report, text_file)
        
        logger.info(f"Cross-platform report generated: {json_file}")
        return report
    
    def _compare_platforms(self, platforms_data):
        """Compare results between platforms"""
        comparison = {
            'consistency': {},
            'differences': []
        }
        
        # Check consistency (same pass/fail across platforms)
        if len(platforms_data) >= 2:
            platform_names = list(platforms_data.keys())
            
            # Compare pass rates
            pass_rates = {name: data['pass_rate'] for name, data in platforms_data.items()}
            
            # Find differences
            min_pass = min(pass_rates.values())
            max_pass = max(pass_rates.values())
            
            if max_pass - min_pass > 5:  # More than 5% difference
                comparison['differences'].append({
                    'type': 'pass_rate_variance',
                    'min': min_pass,
                    'max': max_pass,
                    'difference': max_pass - min_pass
                })
        
        return comparison
    
    def _generate_text_report(self, report, output_file):
        """Generate text report"""
        with open(output_file, 'w') as f:
            f.write("=" * 80 + "\n")
            f.write("CROSS-PLATFORM TEST REPORT\n")
            f.write("=" * 80 + "\n\n")
            
            f.write(f"Timestamp: {report['timestamp']}\n\n")
            
            # Platform results
            f.write("=" * 80 + "\n")
            f.write("PLATFORM RESULTS\n")
            f.write("=" * 80 + "\n\n")
            
            for platform_name, data in report['platforms'].items():
                f.write(f"Platform: {platform_name.upper()}\n")
                f.write(f"  Total Tests: {data['total']}\n")
                f.write(f"  Passed: {data['passed']}\n")
                f.write(f"  Failed: {data['failed']}\n")
                f.write(f"  Pass Rate: {data['pass_rate']:.1f}%\n")
                f.write("\n")
            
            # Comparison
            if report.get('comparison'):
                f.write("=" * 80 + "\n")
                f.write("PLATFORM COMPARISON\n")
                f.write("=" * 80 + "\n\n")
                
                if report['comparison'].get('differences'):
                    f.write("Differences Found:\n")
                    for diff in report['comparison']['differences']:
                        f.write(f"  {diff['type']}: {diff.get('difference', 'N/A')}\n")
                else:
                    f.write("No significant differences between platforms.\n")
    
    def print_comparison(self, report):
        """Print comparison to console"""
        print("\n" + "=" * 80)
        print("CROSS-PLATFORM TEST COMPARISON")
        print("=" * 80)
        
        for platform_name, data in report['platforms'].items():
            print(f"\n{platform_name.upper()}:")
            print(f"  Total: {data['total']}, Passed: {data['passed']}, Failed: {data['failed']}")
            print(f"  Pass Rate: {data['pass_rate']:.1f}%")
        
        if report.get('comparison') and report['comparison'].get('differences'):
            print("\n" + "=" * 80)
            print("DIFFERENCES:")
            for diff in report['comparison']['differences']:
                print(f"  {diff['type']}: {diff.get('difference', 'N/A')}")
        
        print("=" * 80 + "\n")

