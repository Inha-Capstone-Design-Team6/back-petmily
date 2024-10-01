package capstone.petmily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecommendResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class adoptRecommendResultDto {

        String desertionNo; // 유기 번호
        String image;
        String happenDt;    // 접수일
        String happenPlace; // 발견 장소
        String kindCd;      // 품종
        String colorCd;     // 색상
        String age;
        String weight;
        String noticeNo;    // 공고 번호
        String noticeSdt;   // 공고 시작일
        String noticeEdt;   // 공고 종료일
        String processState;// 상태: 종료(반환), 보호중
        String sexCd;       // 성별
        String neuterYn;    // 중성화 여부
        String specialMark; // 특징 : 온순함, 공격성 있음, 건강함, 하네스 목걸이 및 의류 착용, ...
        String careNm;      // 보호소 이름
        String careTel;     // 보호소 전화번호
        String careAddr;    // 보호 장소
        String orgNm;       // 관할 기관
        String chargeNm;    // 담당자
        String officetel;   // 담당자 연락처
        String noticeComment;   // 특이 사항
    }
}
