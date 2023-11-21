package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.MemberInfoUpdateForm;
import com.ssafy.moment.domain.dto.request.PasswordResetReq;
import com.ssafy.moment.domain.dto.request.SignupReq;
import com.ssafy.moment.domain.dto.response.MemberRes;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseDto<String> signup(@RequestBody SignupReq signupReq) {
        if (memberService.signup(signupReq)) {
            return ResponseDto.success("SIGNUP SUCCESS");
        }
        return ResponseDto.fail(ErrorCode.ALREADY_REGISTERED_EMAIL.getDetail());
    }

    @PatchMapping
    public ResponseDto<String> inactivate(HttpServletRequest request) {
        memberService.inactivate(request);
        return ResponseDto.success("INACTIVATION SUCCESS");
    }

    @PatchMapping("/password")
    public ResponseDto<String> updatePassword(@RequestBody PasswordResetReq req, HttpServletRequest request) {
        memberService.updatePassword(req, request);
        return ResponseDto.success("UPDATE PASSWORD SUCCESS");
    }

    @PatchMapping("/password/reset")
    public ResponseDto<?> resetPassword(@RequestBody PasswordResetReq req, HttpServletRequest request) {
        memberService.resetPassword(request);
        return ResponseDto.success("RESET PASSWORD SUCCESS");
    }

    @GetMapping("/detail")
    public ResponseDto<?> getDetail(HttpServletRequest request) {
        MemberRes member = memberService.getDetail(request);
        return ResponseDto.success(member);
    }

    @PatchMapping("/detail")
    public ResponseDto<?> updateMemberInfo(HttpServletRequest request, @RequestBody
        MemberInfoUpdateForm form) {
        MemberRes member = memberService.update(request, form);
        return ResponseDto.success(member);
    }

    @GetMapping("/bookmarks")
    public ResponseDto<?> getBookmarks(HttpServletRequest request) {
        return ResponseDto.success(memberService.getBookmarks(request));
    }

    @DeleteMapping("/bookmarks/{id}")
    public ResponseDto<?> deleteBookmark(HttpServletRequest request, @PathVariable int id) {
        memberService.deleteBookmark(request, id);
        return ResponseDto.success("BOOKMARK DELETE SUCCESS");
    }

    @GetMapping("/{memberId}")
    public ResponseDto<?> getOtherMember(HttpServletRequest request, @PathVariable int memberId) {
        return ResponseDto.success(memberService.getOtherMember(request, memberId));
    }

    @PostMapping("/follows/{targetMemberId}")
    public ResponseDto<?> createFollow(HttpServletRequest request, @PathVariable int targetMemberId) {
        memberService.createFollow(request, targetMemberId);
        return ResponseDto.success("CREATE FOLLOW SUCCESS");
    }

    @DeleteMapping ("/follows/{targetMemberId}")
    public ResponseDto<?> deleteFollow(HttpServletRequest request, @PathVariable int targetMemberId) {
        memberService.deleteFollow(request, targetMemberId);
        return ResponseDto.success("DELETE FOLLOW SUCCESS");
    }

}