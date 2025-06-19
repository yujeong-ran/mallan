package com.mallan.yujeongran.icebreaking.admin.controller;

import com.mallan.yujeongran.common.model.CommonResponse;
import com.mallan.yujeongran.icebreaking.admin.dto.request.AdminLoginRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.request.CreateLoginIdRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.request.DeleteLoginIdRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.response.AdminLoginResponseDto;
import com.mallan.yujeongran.icebreaking.admin.dto.response.CreateLoginIdResponseDto;
import com.mallan.yujeongran.icebreaking.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인 API", description = "아이디와 비밀번호를 입력하여 관라지 계정으로 로그인합니다.")
    public ResponseEntity<CommonResponse<AdminLoginResponseDto>> loginAdmin(
            @RequestBody AdminLoginRequestDto request,
            HttpSession session
    ){
        AdminLoginResponseDto response = adminService.login(request, session);
        return ResponseEntity.ok(CommonResponse.success("로그인 성공!", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "관리자 로그아웃 API", description = "관리자 세션을 종료합니다.")
    public ResponseEntity<CommonResponse<Void>> logoutAdmin(HttpServletResponse response, HttpSession session) {
        // Delete All Sessions
        session.invalidate();

        // Delete JSESSIONID Cookie
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(CommonResponse.success("로그아웃 성공!"));
    }

    @GetMapping("/me")
    @Operation(summary = "세션 인증 여부 확인 API", description = "관라지만이 접근 가능한 페이지에서 해당 API를 이용하려 접근 가능 여부를 체크합니다.")
    public ResponseEntity<?> getSessionAdmin(HttpSession session) {
        String loginId = (String) session.getAttribute("ADMIN_LOGIN_ID");
        if (loginId == null) {
            return ResponseEntity.status(401).body(CommonResponse.failure("로그인이 필요합니다."));
        }

        return ResponseEntity.ok(CommonResponse.success("세션 유지 중", AdminLoginResponseDto.builder()
                .loginId(loginId)
                .message("세션 유효함")
                .build()));
    }

}
