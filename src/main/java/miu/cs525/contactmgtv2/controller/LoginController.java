package miu.cs525.contactmgtv2.controller;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.LoginRequestDto;
import miu.cs525.contactmgtv2.dto.response.LoginResponseDto;
import miu.cs525.contactmgtv2.security.JwtUtil;
import miu.cs525.contactmgtv2.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final JwtUtil jwtUtil;

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(loginService.login(requestDto));
    }
}
