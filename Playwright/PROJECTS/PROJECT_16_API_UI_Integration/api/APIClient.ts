/**
 * API Client for API calls in tests
 * Helper methods for HTTP requests using fetch API
 */

export interface APIResponse<T = any> {
  data: T;
  status: number;
  statusText: string;
}

export class APIClient {
  private baseUrl: string;
  private headers: Record<string, string>;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl.replace(/\/$/, ''); // Remove trailing slash
    this.headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    };
  }

  /**
   * GET request
   */
  async get<T = any>(endpoint: string, params?: Record<string, string>): Promise<APIResponse<T>> {
    const url = this.buildUrl(endpoint, params);
    console.log(`GET ${url}`);

    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: this.headers,
      });

      const data = await response.json();
      
      if (!response.ok) {
        throw new Error(`GET ${url} failed: ${response.status} ${response.statusText}`);
      }

      console.log(`GET ${url} - Status: ${response.status}`);
      return {
        data,
        status: response.status,
        statusText: response.statusText,
      };
    } catch (error) {
      console.error(`GET ${url} failed:`, error);
      throw error;
    }
  }

  /**
   * POST request
   */
  async post<T = any>(endpoint: string, data?: any): Promise<APIResponse<T>> {
    const url = `${this.baseUrl}${endpoint}`;
    console.log(`POST ${url}`);

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: this.headers,
        body: JSON.stringify(data),
      });

      const responseData = await response.json();
      
      if (!response.ok) {
        throw new Error(`POST ${url} failed: ${response.status} ${response.statusText}`);
      }

      console.log(`POST ${url} - Status: ${response.status}`);
      return {
        data: responseData,
        status: response.status,
        statusText: response.statusText,
      };
    } catch (error) {
      console.error(`POST ${url} failed:`, error);
      throw error;
    }
  }

  /**
   * PUT request
   */
  async put<T = any>(endpoint: string, data?: any): Promise<APIResponse<T>> {
    const url = `${this.baseUrl}${endpoint}`;
    console.log(`PUT ${url}`);

    try {
      const response = await fetch(url, {
        method: 'PUT',
        headers: this.headers,
        body: JSON.stringify(data),
      });

      const responseData = await response.json();
      
      if (!response.ok) {
        throw new Error(`PUT ${url} failed: ${response.status} ${response.statusText}`);
      }

      console.log(`PUT ${url} - Status: ${response.status}`);
      return {
        data: responseData,
        status: response.status,
        statusText: response.statusText,
      };
    } catch (error) {
      console.error(`PUT ${url} failed:`, error);
      throw error;
    }
  }

  /**
   * DELETE request
   */
  async delete(endpoint: string): Promise<APIResponse> {
    const url = `${this.baseUrl}${endpoint}`;
    console.log(`DELETE ${url}`);

    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: this.headers,
      });

      if (!response.ok) {
        throw new Error(`DELETE ${url} failed: ${response.status} ${response.statusText}`);
      }

      console.log(`DELETE ${url} - Status: ${response.status}`);
      return {
        data: {},
        status: response.status,
        statusText: response.statusText,
      };
    } catch (error) {
      console.error(`DELETE ${url} failed:`, error);
      throw error;
    }
  }

  /**
   * GET request care returnează JSON
   */
  async getJson<T = any>(endpoint: string, params?: Record<string, string>): Promise<T> {
    const response = await this.get<T>(endpoint, params);
    return response.data;
  }

  /**
   * POST request care returnează JSON
   */
  async postJson<T = any>(endpoint: string, data?: any): Promise<T> {
    const response = await this.post<T>(endpoint, data);
    return response.data;
  }

  /**
   * PUT request care returnează JSON
   */
  async putJson<T = any>(endpoint: string, data?: any): Promise<T> {
    const response = await this.put<T>(endpoint, data);
    return response.data;
  }

  /**
   * Creează un resource via POST
   */
  async createResource<T = any>(endpoint: string, data: any): Promise<T> {
    return this.postJson<T>(endpoint, data);
  }

  /**
   * Get a resource via GET
   */
  async getResource<T = any>(endpoint: string, resourceId: number | string): Promise<T> {
    return this.getJson<T>(`${endpoint}/${resourceId}`);
  }

  /**
   * Update a resource via PUT
   */
  async updateResource<T = any>(endpoint: string, resourceId: number | string, data: any): Promise<T> {
    return this.putJson<T>(`${endpoint}/${resourceId}`, data);
  }

  /**
   * Delete a resource via DELETE
   */
  async deleteResource(endpoint: string, resourceId: number | string): Promise<APIResponse> {
    return this.delete(`${endpoint}/${resourceId}`);
  }

  /**
   * Get all resources from an endpoint
   */
  async getAllResources<T = any>(endpoint: string, params?: Record<string, string>): Promise<T> {
    return this.getJson<T>(endpoint, params);
  }

  /**
   * Build URL with query parameters
   */
  private buildUrl(endpoint: string, params?: Record<string, string>): string {
    const url = `${this.baseUrl}${endpoint}`;
    
    if (!params || Object.keys(params).length === 0) {
      return url;
    }

    const queryString = new URLSearchParams(params).toString();
    return `${url}?${queryString}`;
  }
}

