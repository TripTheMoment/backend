package com.ssafy.moment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_BY_ID(HttpStatus.BAD_REQUEST, "아이디에 해당하는 데이터가 없습니다."),

    //회원가입
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다."),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"),
    EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이메일 발송 중 에러가 발생했습니다."),

    //로그인 & 로그아웃
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "틀린 비밀번호입니다."),
    BLANK_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "헤더에 토큰이 없습니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다. 다시 로그인해주세요."),
    NOT_EXIST_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "DB에 해당 멤버의 리프레시 토큰이 없습니다."),
    BLACKLIST_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "블랙리스트에 있는 액세스 토큰입니다."),
    INVALIDATE_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),
    INVALIDATE_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 액세스 토큰입니다."),
    MISMATCH_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "요청받은 리프레시 토큰이 DB의 리프레시 토큰과 일치하지 않습니다."),
    NOT_EXPIRED_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "아직 만료되지 않은 액세스 토큰입니다."),
    NOT_FOUND_AUTHENTICATION(HttpStatus.BAD_REQUEST, "인증 정보가 없습니다."),
    NO_AUTHORITY(HttpStatus.BAD_REQUEST, "해당 요청에 대한 권한이 없습니다."),
    NOT_EXIST_MEMBER_IN_TOKEN(HttpStatus.BAD_REQUEST, "토큰에서 멤버 정보를 찾을 수 없습니다."),
    NOT_ACTIVATED_MEMBER(HttpStatus.BAD_REQUEST, "활성화되지 않은 회원입니다."),

    // 관광지 정보 조회
    NOT_FOUND_INFO(HttpStatus.BAD_REQUEST, "아이디에 해당하는 관광지 정보가 없습니다."),

    // 북마크
    ALREADY_EXIST_BOOKMARK(HttpStatus.BAD_REQUEST, "이미 북마크가 등록되어있습니다."),

    // 팔로우
    ALREADY_EXIST_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우가 등록되어있습니다."),
    NOT_FOUND_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

}