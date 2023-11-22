package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.MemberInfoUpdateForm;
import com.ssafy.moment.domain.dto.request.PasswordResetReq;
import com.ssafy.moment.domain.dto.request.SignupReq;
import com.ssafy.moment.domain.dto.response.MemberRes;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseDto<?> resetPassword(HttpServletRequest request) {
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
        memberService.update(request, form);
        return ResponseDto.success("MEMBER UPDATE SUCCESS");
    }

    @GetMapping("/{memberId}/articles")
    public ResponseDto<?> getArticles(@PathVariable int memberId, Pageable pageable) {
        return ResponseDto.success(memberService.getArticlesByMember(memberId, pageable));
    }

    @GetMapping("/{memberId}/bookmarks")
    public ResponseDto<?> getBookmarks(@PathVariable int memberId, Pageable pageable) {
        return ResponseDto.success(memberService.getBookmarksByMember(memberId, pageable));
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

    @GetMapping("/follows/followings")
    public ResponseDto<?> getFollowings(HttpServletRequest request) {
        return ResponseDto.success(memberService.getFollowings(request));
    }

    @GetMapping("/follows/followers")
    public ResponseDto<?> getFollowers(HttpServletRequest request) {
        return ResponseDto.success(memberService.getFollowers(request));
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

    @PatchMapping("/profile")
    public ResponseDto<?> updateProfileImg(HttpServletRequest request, @RequestPart(value = "file", required = false) final MultipartFile file) {
        try {
            memberService.updateProfileImg(request, file);
            return ResponseDto.success("UPLOAD SUCCESS");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.fail("UPLOAD FAIL");
        }
    }

}