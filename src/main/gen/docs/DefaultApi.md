# DefaultApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiPersonSavePost**](DefaultApi.md#apiPersonSavePost) | **POST** /api/person/save | Speichert eine Person in CSV |


<a id="apiPersonSavePost"></a>
# **apiPersonSavePost**
> apiPersonSavePost(person)

Speichert eine Person in CSV

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Person person = new Person(); // Person | 
    try {
      apiInstance.apiPersonSavePost(person);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#apiPersonSavePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **person** | [**Person**](Person.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

