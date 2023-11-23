package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.LoginReq;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseDto<?> login(@RequestBody LoginReq req, HttpServletResponse res) {
        return authService.login(req, res);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseDto<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseDto.success("LOGOUT SUCCESS");
    }

    @GetMapping("/check")
    @ResponseBody
    public ResponseDto<?> checkEmail(@RequestParam String email) {
        return ResponseDto.success(authService.checkEmail(email));
    }

    @GetMapping("/email-auth")
    public String emailAuth(Model model, @RequestParam String id) {
        boolean result = authService.emailAuth(id);
        model.addAttribute("result", result);
        return "/member/email-auth";
    }

    @GetMapping("/renew")
    @ResponseBody
    public ResponseDto<?> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        authService.renewAccessToken(request, response);
        return ResponseDto.success("RENEW SUCCESS");
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

}