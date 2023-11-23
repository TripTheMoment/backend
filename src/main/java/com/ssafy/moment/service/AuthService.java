package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.TokenDto;
import com.ssafy.moment.domain.dto.request.LoginReq;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.RefreshToken;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.MemberRepository;
import com.ssafy.moment.repository.RefreshTokenRepository;
import com.ssafy.moment.security.TokenProvider;
import com.ssafy.moment.util.PasswordUtil;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<String> login(LoginReq req, HttpServletResponse res) {
        Member member = getMemberByEmail(req.getEmail());
        if (member == null) {
            return ResponseDto.fail(ErrorCode.NOT_FOUND_MEMBER.getDetail());
        }

        if (!PasswordUtil.checkPassword(req.getPassword(), member.getPassword())) {
            return ResponseDto.fail(ErrorCode.WRONG_PASSWORD.getDetail());
        }

        if (!member.isEmailAuthYn()) {
            return ResponseDto.fail(ErrorCode.NOT_ACTIVATED_MEMBER.getDetail());
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
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

    @Transactional
    public ResponseDto<String> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        if (!tokenProvider.validateToken(request.getHeader("RefreshToken"))) {
            throw new CustomException(ErrorCode.INVALIDATE_REFRESH_TOKEN);
        }

        String memberId = tokenProvider.getMemberIdFromRefreshToken(request);
        if (null == memberId) {
            throw new CustomException(ErrorCode.NOT_EXIST_MEMBER_IN_TOKEN);
        }

        Member member = memberRepository.findById(Integer.parseInt(memberId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));

        RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);

        if (!refreshToken.getKeyValue().equals(request.getHeader("RefreshToken"))) {
            log.info("refreshToken.getKeyValue() : " + refreshToken.getKeyValue());
            log.info("request.getHeader : " + request.getHeader("RefreshToken"));
            throw new CustomException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        tokenToHeaders(tokenDto, response);

        return ResponseDto.success("RENEW SUCCESS");
    }

}