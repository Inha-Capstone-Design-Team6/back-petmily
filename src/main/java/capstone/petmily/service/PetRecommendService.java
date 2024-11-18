package capstone.petmily.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class PetRecommendService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${spring.api.serviceKey}")
    private String serviceKey;

    public JSONObject openApiRequest(String breedCode, String cityCode, String districtCode) throws IOException{

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
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String result = response.getBody();

        JSONObject animal = null;
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) parseItems.get("item");
            animal = (JSONObject) array.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return animal;
    }
}
