package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.MemberInfoUpdateForm;
import com.ssafy.moment.domain.dto.request.PasswordCheckForm;
import com.ssafy.moment.domain.dto.request.SignupForm;
import com.ssafy.moment.domain.dto.response.MemberRes;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseDto<?> signup(@RequestBody SignupForm signupForm) {
        memberService.signup(signupForm);
        return ResponseDto.success("SIGNUP SUCCESS");
    }

    @PatchMapping
    public ResponseDto<String> inactivate(HttpServletRequest request) {
        memberService.inactivate(request);
        return ResponseDto.success("INACTIVATION SUCCESS");
    }

    @PatchMapping("/password/reset")
    public ResponseDto<?> resetPassword(HttpServletRequest request) {
        memberService.resetPassword(request);
        return ResponseDto.success("RESET PASSWORD SUCCESS");
    }

    @PostMapping("/password")
    public ResponseDto<?> checkPassword(HttpServletRequest request, @RequestBody PasswordCheckForm form) {
        memberService.checkPassword(request, form);
        return ResponseDto.success("CORRECT PASSWORD");
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
    public ResponseDto<?> getArticles(@PathVariable int memberId,
                                      @PageableDefault(size = 6) Pageable pageable) {
        return ResponseDto.success(memberService.getArticlesByMember(memberId, pageable));
    }

    @GetMapping("/{memberId}/bookmarks")
    public ResponseDto<?> getBookmarks(@PathVariable int memberId,
                                       @PageableDefault(size = 6)Pageable pageable) {
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

    @GetMapping("/follows/{targetMemberId}")
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
        memberService.updateProfileImg(request, file);
        return ResponseDto.success("UPLOAD SUCCESS");
    }

}