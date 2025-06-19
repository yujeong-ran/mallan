package com.mallan.yujeongran.icebreaking.admin.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.admin.dto.response.ManagementInfoResponseDto;
import com.mallan.yujeongran.icebreaking.admin.service.ManagementInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
@Tag(name = "Management Info API", description = "관리자 관리 정보 출력 API")
public class ManagementInfoController {

    private final ManagementInfoService managementInfoService;

    @GetMapping
    @Operation(summary = "관리 정보 출력 API", description = "대시보드에 필요한 정보를 출력합니다.")
    public ResponseEntity<CommonResponse<ManagementInfoResponseDto>> getDashboardInfo() {
        ManagementInfoResponseDto info = managementInfoService.getLatestInfo();
        return ResponseEntity.ok(CommonResponse.success("대시보드 정보 조회 성공!", info));
    }

}
