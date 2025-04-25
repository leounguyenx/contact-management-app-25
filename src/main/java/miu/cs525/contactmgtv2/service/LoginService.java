package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.LoginRequestDto;
import miu.cs525.contactmgtv2.dto.response.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
