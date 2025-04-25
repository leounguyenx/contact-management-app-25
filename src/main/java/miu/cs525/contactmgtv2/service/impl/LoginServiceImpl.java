package miu.cs525.contactmgtv2.service.impl;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.LoginRequestDto;
import miu.cs525.contactmgtv2.dto.response.LoginResponseDto;
import miu.cs525.contactmgtv2.exception.AuthenticationFailException;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.User;
import miu.cs525.contactmgtv2.repository.UserRepository;
import miu.cs525.contactmgtv2.security.JwtUtil;
import miu.cs525.contactmgtv2.service.LoginService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.email();
        String password = loginRequestDto.password();

        // Find user
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        // Basic password match (⚠️ Replace with encoded password check in real app)
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationFailException("Invalid password. Please try again!");
        }

        // Generate token
        String token = jwtUtil.generateToken(username);

        return new LoginResponseDto(token, "Login successful");
    }
}
