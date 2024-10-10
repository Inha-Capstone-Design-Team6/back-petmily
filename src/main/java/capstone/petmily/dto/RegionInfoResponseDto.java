package capstone.petmily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RegionInfoResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegionInfoResultDto {

        List<RegionInfo> regionInfoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegionInfo {

        String cityName;
        String cityCode;
        List<DistrictInfo> districtInfoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictInfo {

        String districtName;
        String districtCode;
    }
}
