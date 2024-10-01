package capstone.petmily.converter;

import capstone.petmily.dto.RecommendResponseDto;
import org.json.simple.JSONObject;

public class RecommendConverter {

    public static RecommendResponseDto.adoptRecommendResultDto toAdoptRecommendResultDto(JSONObject animal){

        return RecommendResponseDto.adoptRecommendResultDto.builder()
                .desertionNo((String) animal.get("desertionNo"))
                .image((String) animal.get("image"))
                .happenDt((String) animal.get("happenDt"))
                .happenPlace((String) animal.get("happenPlace"))
                .kindCd((String) animal.get("kindCd"))
                .colorCd((String) animal.get("colorCd"))
                .age((String) animal.get("age"))
                .weight((String) animal.get("weight"))
                .noticeNo((String) animal.get("noticeNo"))
                .noticeSdt((String) animal.get("noticeSdt"))
                .noticeEdt((String) animal.get("noticeEdt"))
                .processState((String) animal.get("processState"))
                .sexCd((String) animal.get("sexCd"))
                .neuterYn((String) animal.get("neuterYn"))
                .specialMark((String) animal.get("specialMark"))
                .careNm((String) animal.get("careNm"))
                .careTel((String) animal.get("careTel"))
                .careAddr((String) animal.get("careAddr"))
                .orgNm((String) animal.get("orgNm"))
                .chargeNm((String) animal.get("chargeNm"))
                .officetel((String) animal.get("officetel"))
                .noticeComment((String) animal.get("noticeComment"))
                .build();
    }
}
