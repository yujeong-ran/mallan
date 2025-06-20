package com.mallan.yujeongran.icebreaking.admin.service;

import com.mallan.yujeongran.icebreaking.admin.dto.request.AdminLoginRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.request.CreateLoginIdRequestDto;
import com.mallan.yujeongran.icebreaking.admin.dto.response.AdminLoginResponseDto;
import com.mallan.yujeongran.icebreaking.admin.dto.response.CreateLoginIdResponseDto;
import com.mallan.yujeongran.icebreaking.admin.enitity.Admin;
import com.mallan.yujeongran.icebreaking.admin.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateLoginIdResponseDto createAdmin(CreateLoginIdRequestDto request) {
        if (adminRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Admin admin = Admin.builder()
                .loginId(request.getLoginId())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .build();

        Admin saved = adminRepository.save(admin);

        return CreateLoginIdResponseDto.builder()
                .loginId(saved.getLoginId())
                .password("설정한 비밀번호를 BCrypt로 암호화했습니다.")
                .build();
    }

    public void deleteAdmin(String loginId, String password) {
        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 요청한 아이디가 없습니디."));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        adminRepository.delete(admin);
    }

    public AdminLoginResponseDto login(AdminLoginRequestDto request, HttpSession session){
        Admin admin = adminRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));

        if(!passwordEncoder.matches(request.getPassword(), admin.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("ADMIN_LOGIN_ID", request.getLoginId());

        return AdminLoginResponseDto.builder()
                .loginId(request.getLoginId())
                .message("관리자 계정으로 로그인 하였습니다.")
                .build();
    }

}
