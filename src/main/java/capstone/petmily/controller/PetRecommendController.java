package capstone.petmily.controller;

import capstone.petmily.ApiResponse;
import capstone.petmily.converter.RecommendConverter;
import capstone.petmily.dto.RecommendResponseDto;
import capstone.petmily.service.PetRecommendService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets/recommend")
public class PetRecommendController {

    private final PetRecommendService petRecommendService;

    @GetMapping("/adopt")
    public ApiResponse<RecommendResponseDto.adoptRecommendResultDto> adoptRecommend(@RequestParam(name = "breed_code") String breedCode,
                                      @RequestParam(name = "city_code") String cityCode,
                                      @RequestParam(name = "district_code") String districtCode) throws IOException {

        JSONObject animal = petRecommendService.openApiRequest(breedCode, cityCode, districtCode);

        if(animal == null)
            return ApiResponse.onSuccess("No animals found", null);
        else
            return ApiResponse.onSuccess(RecommendConverter.toAdoptRecommendResultDto(animal));
    }
}
