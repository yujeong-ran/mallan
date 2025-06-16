package com.mallan.yujeongran.icebreaking.admin.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.admin.dto.request.CreateLoginIdRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.request.DeleteLoginIdRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.response.CreateLoginIdResponseDto;
import com.mallan.yujeongran.icebreaking.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "관리자 계정 관련 API")
public class AdminController {

    private final AdminService adminService;

    @Value("${ADMIN_SECRET_KEY}")
    private String adminSecretKey;

    @PostMapping("/create")
    @Operation(summary = "관리자 계정 생성 API", description = "아이디와 비밀번호를 설정하여 관리자 계정을 생성합니다.")
    public ResponseEntity<CommonResponse<CreateLoginIdResponseDto>> createAdmin(
            @RequestBody CreateLoginIdRequestDto request
    ) {
        if(!adminSecretKey.equals((request.getSecretKey()))){
            return ResponseEntity.status(403).body(CommonResponse.failure("관리자 시크릿 키가 틀립니다."));
        }

        CreateLoginIdResponseDto response = adminService.createAdmin(request);
        return ResponseEntity.ok(CommonResponse.success("관리자 계정 생성 성공!", response));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "관리자 계정 삭제 API", description = "아이디와 비밀번호를 입력하여 계정을 삭제합니다.")
    public ResponseEntity deleteAdmin(
            @RequestBody DeleteLoginIdRequestDto request
    ) {
        if(!adminSecretKey.equals((request.getSecretKey()))){
            return ResponseEntity.status(403).body(CommonResponse.failure("관리자 시크릿 키가 틀립니다."));
        }

        adminService.deleteAdmin(request.getLoginId(), request.getPassword());
        return ResponseEntity.ok(CommonResponse.success("관리자 계정 삭제 성공!"));
    }

}
