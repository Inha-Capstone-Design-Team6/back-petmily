package capstone.petmily.service;

import capstone.petmily.dto.RegionInfoResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionInfoService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${spring.api.serviceKey}")
    private String serviceKey;

    private static final Map<String, String> cityCodeMap = new HashMap<>();

    static {

        cityCodeMap.put("서울특별시", "6110000");
        cityCodeMap.put("부산광역시", "6260000");
        cityCodeMap.put("대구광역시", "6270000");
        cityCodeMap.put("인천광역시", "6280000");
        cityCodeMap.put("광주광역시", "6290000");
        cityCodeMap.put("세종특별자치시", "5690000");
        cityCodeMap.put("대전광역시", "6300000");
        cityCodeMap.put("울산광역시", "6310000");
        cityCodeMap.put("경기도", "6410000");
        cityCodeMap.put("강원특별자치도", "6530000");
        cityCodeMap.put("충청북도", "6430000");
        cityCodeMap.put("충청남도", "6440000");
        cityCodeMap.put("전북특별자치도", "6540000");
        cityCodeMap.put("전라남도", "6460000");
        cityCodeMap.put("경상북도", "6470000");
        cityCodeMap.put("경상남도", "6480000");
        cityCodeMap.put("제주특별자치도", "6500000");
    }

    public RegionInfoResponseDto.RegionInfoResultDto regionInfo() throws IOException{

        List<RegionInfoResponseDto.RegionInfo> regionInfoList = new ArrayList<>();

        for(Map.Entry<String, String> entry : cityCodeMap.entrySet()) {

            JSONArray districtInfoArray = districtInfoRequest(entry.getValue());
            List<RegionInfoResponseDto.DistrictInfo> districtInfoList = new ArrayList<>();

            if (districtInfoArray != null) {
                for (int i = 0; i < districtInfoArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) districtInfoArray.get(i);

                    districtInfoList.add(RegionInfoResponseDto.DistrictInfo.builder()
                            .districtName((String) jsonObject.get("orgdownNm"))
                            .districtCode((String) jsonObject.get("orgCd"))
                            .build());
                }
            } else {
                System.out.println("No district data found for city: " + entry.getKey());

                districtInfoList.add(null);
            }


            regionInfoList.add(RegionInfoResponseDto.RegionInfo.builder()
                    .cityName(entry.getKey())
                    .cityCode(entry.getValue())
                    .districtInfoList(districtInfoList)
                    .build()
            );
        }

        return RegionInfoResponseDto.RegionInfoResultDto.builder()
                .regionInfoList(regionInfoList)
                .build();
    }

    public JSONArray districtInfoRequest(String cityCode) throws IOException {

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
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String result = response.getBody();

        JSONArray districtInfoArray = null;
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject) parseBody.get("items");
            districtInfoArray = (JSONArray) parseItems.get("item");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return districtInfoArray;
    }
}
