package notai.domain.auth.controller;

import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import notai.domain.auth.dto.request.EmailCheckRequest;
import notai.domain.auth.dto.request.NicknameCheckRequest;
import notai.domain.auth.dto.request.PasswordCheckRequest;
import notai.domain.auth.dto.request.RegisterRequest;
import notai.global.dto.MessageResponse;
import notai.domain.auth.service.AuthService;
import notai.domain.user.dto.response.UserDetailResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserDetailResponse> userRegister(
        @Valid @RequestBody RegisterRequest request) {

        UserDetailResponse response = authService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 비밀번호 중복 확인
    @PostMapping("/password-check")
    public ResponseEntity<MessageResponse> passwordCheck(
        @Valid @RequestBody PasswordCheckRequest request) {

        MessageResponse response = authService.checkPassword(request);

        return ResponseEntity.ok().body(response);
    }

    // 별명 중복 확인
    @PostMapping("/nickname-check")
    public ResponseEntity<MessageResponse> nicknameCheck(
        @Valid @RequestBody NicknameCheckRequest request) {

        MessageResponse response = authService.checkNickname(request);

        return ResponseEntity.ok().body(response);
    }

    // 이메일 중복 확인
    @PostMapping("/email-check")
    public ResponseEntity<MessageResponse> emailCheck(
        @Valid @RequestBody EmailCheckRequest request) {

        MessageResponse response = authService.checkEmail(request);

        return ResponseEntity.ok().body(response);
    }

    // 액세스 토큰 갱신
    @PostMapping("/refresh-token")
    public ResponseEntity<Void> tokenRefresh(@RequestHeader String refresh) {

        Map<String, String> response = authService.refreshToken(refresh.split(" ")[1]);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + response.get("access"));
        headers.add("refresh", "Bearer " + response.get("refresh"));

        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }
}
