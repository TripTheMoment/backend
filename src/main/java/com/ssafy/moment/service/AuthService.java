package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.TokenDto;
import com.ssafy.moment.domain.dto.request.LoginReq;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.MemberRepository;
import com.ssafy.moment.security.TokenProvider;
import com.ssafy.moment.util.PasswordUtil;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<String> login(LoginReq req, HttpServletResponse res) {
        Member member = getMemberByEmail(req.getEmail());
        if (member == null) {
            return ResponseDto.fail(ErrorCode.NOT_FOUND_MEMBER.getDetail());
        }

        // 비밀번호 확인
        if (!PasswordUtil.checkPassword(req.getPassword(), member.getPassword())) {
            return ResponseDto.fail(ErrorCode.WRONG_PASSWORD.getDetail());
        }

        // 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);

        // 응답 헤더에 토큰 담기
        tokenToHeaders(tokenDto, res);

        return ResponseDto.success("LOGIN SUCCESS");
    }

    private Member getMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    private void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
    }

    @Transactional
    public boolean logout(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);

        tokenProvider.deleteRefreshToken(member);
        tokenProvider.saveBlacklistToken(request);

        return true;
    }

    public boolean checkEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean emailAuth(String uuid) {
        System.out.println("service.emailAuth 실행");
        Optional<Member> optMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optMember.isPresent()) {
            return false;
        }

        Member member = optMember.get();
        member.auth();
        System.out.println("활성화 상태 : " + member.isEmailAuthYn());
        memberRepository.save(member);

        return true;
    }

}