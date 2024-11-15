package capstone.petmily.service;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetRecommendServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private PetRecommendService petRecommendService;
    @Value("${spring.api.serviceKey}")
    private String serviceKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void openApiRequest() throws IOException {

        String breedCode = "000200";
        String cityCode = "6260000";
        String districtCode = "3360000";

        DefaultUriBuilderFactory builder = new DefaultUriBuilderFactory();
        builder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        String uriString = builder.builder()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/1543061/abandonmentPublicSrvc/abandonmentPublic")
                .queryParam("serviceKey", serviceKey)
                .queryParam("bgnde", "")
                .queryParam("endde", "")
                .queryParam("upkind", "")
                .queryParam("kind", breedCode)
                .queryParam("upr_cd", cityCode)
                .queryParam("org_cd", districtCode)
                .queryParam("care_reg_no", "")
                .queryParam("state", "protect")
                .queryParam("neuter_yn", "")
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1")
                .queryParam("_type", "json")
                .build()
                .toString();

        URI uri = URI.create(uriString);

        String mockJsonResponse = "{"
                + "\"response\": {"
                + "\"body\": {"
                + "\"items\": {"
                + "\"item\": [{"
                + "\"kindCd\": \"[고양이] 한국 고양이\","
                + "\"age\": \"2024(년생)\","
                + "\"weight\": \"0.38(Kg)\""
                + "}]"
                + "}"
                + "}"
                + "}"
                + "}";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockJsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, String.class)).thenReturn(responseEntity);
        JSONObject response = petRecommendService.openApiRequest(breedCode, cityCode, districtCode);

        assertNotNull(response);
        assertEquals("[고양이] 한국 고양이", response.get("kindCd"));
        assertEquals("2024(년생)", response.get("age"));
        assertEquals("0.38(Kg)", response.get("weight"));
    }
}