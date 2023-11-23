package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.MemberInfoUpdateForm;
import com.ssafy.moment.domain.dto.request.PasswordCheckForm;
import com.ssafy.moment.domain.dto.request.PasswordResetReq;
import com.ssafy.moment.domain.dto.request.SignupReq;
import com.ssafy.moment.domain.dto.response.MemberRes;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseDto<?> signup(@RequestBody SignupReq signupReq) {
        try {
            memberService.signup(signupReq);
            return ResponseDto.success("SIGNUP SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseDto<String> inactivate(HttpServletRequest request) {
        try {
            memberService.inactivate(request);
            return ResponseDto.success("INACTIVATION SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PatchMapping("/password")
    public ResponseDto<String> updatePassword(@RequestBody PasswordResetReq req, HttpServletRequest request) {
        try {
            memberService.updatePassword(req, request);
            return ResponseDto.success("UPDATE PASSWORD SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PatchMapping("/password/reset")
    public ResponseDto<?> resetPassword(HttpServletRequest request) {
        try {
            memberService.resetPassword(request);
            return ResponseDto.success("RESET PASSWORD SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/password")
    public ResponseDto<?> checkPassword(HttpServletRequest request, @RequestBody PasswordCheckForm form) {
        try {
            memberService.checkPassword(request, form);
            return ResponseDto.success("CORRECT PASSWORD");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/detail")
    public ResponseDto<?> getDetail(HttpServletRequest request) {
        try {
            MemberRes member = memberService.getDetail(request);
            return ResponseDto.success(member);
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PatchMapping("/detail")
    public ResponseDto<?> updateMemberInfo(HttpServletRequest request, @RequestBody
        MemberInfoUpdateForm form) {
        try {
            memberService.update(request, form);
            return ResponseDto.success("MEMBER UPDATE SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/{memberId}/articles")
    public ResponseDto<?> getArticles(@PathVariable int memberId,
                                      @PageableDefault(size = 6) Pageable pageable) {
        try {
            return ResponseDto.success(memberService.getArticlesByMember(memberId, pageable));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/{memberId}/bookmarks")
    public ResponseDto<?> getBookmarks(@PathVariable int memberId,
                                       @PageableDefault(size = 6)Pageable pageable) {
        try {
            return ResponseDto.success(memberService.getBookmarksByMember(memberId, pageable));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping("/bookmarks/{id}")
    public ResponseDto<?> deleteBookmark(HttpServletRequest request, @PathVariable int id) {
        try {
            memberService.deleteBookmark(request, id);
            return ResponseDto.success("BOOKMARK DELETE SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/{memberId}")
    public ResponseDto<?> getOtherMember(HttpServletRequest request, @PathVariable int memberId) {
        try {
            return ResponseDto.success(memberService.getOtherMember(request, memberId));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/follows/followings")
    public ResponseDto<?> getFollowings(HttpServletRequest request) {
        try {
            return ResponseDto.success(memberService.getFollowings(request));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/follows/followers")
    public ResponseDto<?> getFollowers(HttpServletRequest request) {
        try {
            return ResponseDto.success(memberService.getFollowers(request));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping("/follows/{targetMemberId}")
    public ResponseDto<?> createFollow(HttpServletRequest request, @PathVariable int targetMemberId) {
        try {
            memberService.createFollow(request, targetMemberId);
            return ResponseDto.success("CREATE FOLLOW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping ("/follows/{targetMemberId}")
    public ResponseDto<?> deleteFollow(HttpServletRequest request, @PathVariable int targetMemberId) {
        try {
            memberService.deleteFollow(request, targetMemberId);
            return ResponseDto.success("DELETE FOLLOW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
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