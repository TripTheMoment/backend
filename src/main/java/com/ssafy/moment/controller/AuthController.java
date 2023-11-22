package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.LoginReq;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginReq req, HttpServletResponse res) {
        return authService.login(req, res);
    }

    @PostMapping("/logout")
    public ResponseDto<String> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseDto.success("LOGOUT SUCCESS");
    }

    @GetMapping("/check")
    public ResponseDto<Boolean> checkEmail(@RequestParam String email) {
        return ResponseDto.success(authService.checkEmail(email));
    }

    @GetMapping("/email-auth")
    public String emailAuth(Model model, @RequestParam String id) {
        boolean result = authService.emailAuth(id);
        model.addAttribute("result", result);
        return "ACTIVATION SUCCESS";
    }

    @GetMapping("/renew")
    public ResponseDto<String> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        authService.renewAccessToken(request, response);
        return ResponseDto.success("RENEW SUCCESS");
    }

}