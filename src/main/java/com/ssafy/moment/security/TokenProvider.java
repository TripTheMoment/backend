package com.ssafy.moment.security;

import com.ssafy.moment.domain.UserDetailsImpl;
import com.ssafy.moment.domain.dto.TokenDto;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.RefreshToken;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class TokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1 * 10;  // 10 분
    private static final long REFRESH_TOKEN_EXPRIRE_TIME = 1000 * 60 * 60 * 24 * 1;  // 1 일

    private final Key key;

    // 암호화
    public TokenProvider(@Value("${jwt.secret}") String secretKey,
        RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenDto generateTokenDto(Member member) {
        long now = (new Date().getTime());

        // AccessToken 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
            .setSubject(member.getId().toString())
            .claim(AUTHORITIES_KEY, member.getUserRole().toString())
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        // RefreshToken 생성
        // AccessToken의 재발급 목적으로 만들어진 토큰이므로 만료기한, 멤버ID 외 별다른 정보를 담지 않는다
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
            .setSubject(member.getId().toString())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        RefreshToken refreshTokenObject = RefreshToken.builder()
            .id(member.getId().toString())
            .member(member)
            .keyValue(refreshToken)
            .build();

        refreshTokenRepository.save(refreshTokenObject);

        return TokenDto.builder()
            .grantType(BEARER_PREFIX)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public Member getMemberFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
            || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            throw new CustomException(ErrorCode.NOT_FOUND_AUTHENTICATION);
        }
        return ((UserDetailsImpl) authentication.getPrincipal()).getMember();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    @Transactional(readOnly = true)
    public RefreshToken isPresentRefreshToken(Member member) {
        return refreshTokenRepository.findByMember(member)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN));
    }

    @Transactional
    public void deleteRefreshToken(Member member) {
        RefreshToken refreshToken = isPresentRefreshToken(member);
        refreshTokenRepository.delete(refreshToken);
    }

//    public Long getMemberIdByToken(String accessToken) {
//        String token;
//        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
//            token = accessToken.substring(7);
//        } else {
//            return null;
//        }
//        Claims claims;
//        try {
//            claims = Jwts
//                .parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        } catch (ExpiredJwtException e) {
//            return null;
//        }
//
//        return Long.parseLong(claims.getSubject());
//    }

    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String getMemberIdFromRefreshToken(HttpServletRequest request) throws ParseException {
        String jwt = request.getHeader("RefreshToken");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        assert jwt != null;
        String[] parts = jwt.split("\\.");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new String(decoder.decode(parts[1])));
        return jsonObject.get("sub").toString();
    }

    @Transactional
    public Member getMemberFromToken(HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) {
            throw new CustomException(ErrorCode.BLANK_TOKEN_HEADER);
        }

        Member member = validateMember(request);
        if (null == member) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        return member;
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        Member member = getMemberFromAuthentication();

        return member;
    }

//    // access token 만료 시간 구하기
//    private LocalDateTime getExpiredDateTime(String token) {
//        Claims claims = Jwts.parserBuilder()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//
//        return claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//    }

}