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
    public ResponseDto<?> login(@RequestBody LoginReq req, HttpServletResponse res) {
        try {
            return authService.login(req, res);
        } catch (Exception e) {
            ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        try {
            authService.logout(request);
            return ResponseDto.success("LOGOUT SUCCESS");
        } catch (Exception e) {
            ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/check")
    public ResponseDto<?> checkEmail(@RequestParam String email) {
        try {
            return ResponseDto.success(authService.checkEmail(email));
        } catch (Exception e) {
            ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/email-auth")
    public String emailAuth(Model model, @RequestParam String id) {
        boolean result = authService.emailAuth(id);
        model.addAttribute("result", result);
        return "ACTIVATION SUCCESS";
    }

    @GetMapping("/renew")
    public ResponseDto<?> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            authService.renewAccessToken(request, response);
            return ResponseDto.success("RENEW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

}