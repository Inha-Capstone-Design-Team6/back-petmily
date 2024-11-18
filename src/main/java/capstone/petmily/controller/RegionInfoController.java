package capstone.petmily.controller;

import capstone.petmily.dto.RegionInfoResponseDto;
import capstone.petmily.service.RegionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionInfoController {

    private final RegionInfoService regionInfoService;

    @GetMapping
    public RegionInfoResponseDto.RegionInfoResultDto regionInfo() throws IOException {

        return regionInfoService.regionInfo();
    }
}
