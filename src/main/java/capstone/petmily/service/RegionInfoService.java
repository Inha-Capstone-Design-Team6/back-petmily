package capstone.petmily.service;

import capstone.petmily.dto.RegionInfoResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionInfoService {

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

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=m6tHI0xEBj9D7Mhb%2FRuo2gTIHbbo7nAt9PQP0ar9qdvymlU8%2F1S6EWGcGE08H1E5wuDTfDpIS5%2BCPDRTvZ83tw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode(cityCode, "UTF-8")); /*시군구 상위코드(시도코드) (입력 시 데이터 O, 미입력 시 데이터 X)*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        String result = sb.toString();

        rd.close();
        conn.disconnect();

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
