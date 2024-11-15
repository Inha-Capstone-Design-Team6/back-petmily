package capstone.petmily.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionInfoServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private RegionInfoService regionInfoService = new RegionInfoService();
    @Value("${spring.api.serviceKey}")
    private String serviceKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void districtInfoRequest() throws IOException {

        String cityCode = "6110000";

        DefaultUriBuilderFactory builder = new DefaultUriBuilderFactory();
        builder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        String uriString = builder.builder()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/1543061/abandonmentPublicSrvc/sigungu")
                .queryParam("serviceKey", serviceKey)
                .queryParam("upr_cd", cityCode)
                .queryParam("_type", "json")
                .build()
                .toString();

        URI uri = URI.create(uriString);

        String mockJsonResponse = "{"
                + "\"response\": {"
                + "\"body\": {"
                + "\"items\": {"
                + "\"item\": ["
                +"{"
                + "\"uprCd\": \"6110000\","
                + "\"orgCd\": \"3220000\","
                + "\"orgdownNm\": \"강남구\""
                + "},"
                +"{"
                + "\"uprCd\": \"6110000\","
                + "\"orgCd\": \"3240000\","
                + "\"orgdownNm\": \"강동구\""
                + "}"
                + "]"
                + "}"
                + "}"
                + "}"
                + "}";

        ResponseEntity<String> response = new ResponseEntity<>(mockJsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, String.class)).thenReturn(response);
        JSONArray array = regionInfoService.districtInfoRequest(cityCode);

        JSONObject object1 = (JSONObject) array.get(0);
        JSONObject object2 = (JSONObject) array.get(1);
        assertNotNull(array);
        assertEquals("6110000", object1.get("uprCd"));
        assertEquals("3220000", object1.get("orgCd"));
        assertEquals("강남구", object1.get("orgdownNm"));
        assertEquals("6110000", object2.get("uprCd"));
        assertEquals("3240000", object2.get("orgCd"));
        assertEquals("강동구", object2.get("orgdownNm"));
    }
}