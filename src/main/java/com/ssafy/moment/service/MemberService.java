package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.MemberInfoUpdateForm;
import com.ssafy.moment.domain.dto.request.PasswordResetReq;
import com.ssafy.moment.domain.dto.request.SignupReq;
import com.ssafy.moment.domain.dto.response.BookmarkRes;
import com.ssafy.moment.domain.dto.response.FollowerRes;
import com.ssafy.moment.domain.dto.response.FollowingRes;
import com.ssafy.moment.domain.dto.response.MemberRes;
import com.ssafy.moment.domain.entity.Bookmark;
import com.ssafy.moment.domain.entity.Follow;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.AttractionBookmarkRepository;
import com.ssafy.moment.repository.FollowRepository;
import com.ssafy.moment.repository.MemberRepository;
import com.ssafy.moment.security.TokenProvider;
import com.ssafy.moment.util.MailUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AttractionBookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    private final TokenProvider tokenProvider;
    private final MailUtil mailUtil;

    @Transactional
    public boolean signup(SignupReq req) {
        if (memberRepository.findByEmail(req.getEmail()).isPresent()) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
            .email(req.getEmail())
            .password(new BCryptPasswordEncoder().encode(req.getPassword()))
            .name(req.getName())
            .userRole("MEMBER")
            .emailAuthYn(false)
            .emailAuthKey(uuid)
            .build();

        memberRepository.save(member);

        mailUtil.sendAuthMail(req.getEmail(), uuid);

        return true;
    }

    @Transactional
    public void inactivate(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        member.inactive();
        memberRepository.save(member);

        System.out.println("[Member] : " + member.getEmail() +", "+ member.getName());
    }

    @Transactional
    public void updatePassword(PasswordResetReq req, HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        member.updatePassword(req.getPassword());
        memberRepository.save(member);
    }

    @Transactional
    public void resetPassword(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        String newPw = getTempPassword();
        member.updatePassword(newPw);
        memberRepository.save(member);

        mailSend(member.getEmail(), newPw);
    }

    private String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    private void mailSend(String email, String password) {
        mailUtil.sendPwMail(email, password);
        System.out.println("전송 완료!");
    }

    public MemberRes getDetail(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        member = getMember(member.getId());

        int followingCnt = Long.valueOf(followRepository.countByFromMember(member)).intValue();
        int followerCnt = Long.valueOf(followRepository.countByToMember(member)).intValue();
        List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);

        //TODO: MemberRes에 List<Article> 주입

        return  MemberRes.builder()
            .email(member.getEmail())
            .name(member.getName())
            .articles(null)
            .followingCnt(followingCnt)
            .followerCnt(followerCnt)
            .bookmarks(bookmarks.stream().map(e -> BookmarkRes.from(e)).collect(Collectors.toList()))
            .createdAt(member.getCreatedAt())
            .build();
    }

    private Member getMember(int memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void update(HttpServletRequest request, MemberInfoUpdateForm form) {
        Member member = tokenProvider.getMemberFromToken(request);
        member = getMember(member.getId());
        member.updateName(form.getName());

        memberRepository.save(member);
    }

//    public List<BookmarkRes> getBookmarks(HttpServletRequest request) {
//        Member member = tokenProvider.getMemberFromToken(request);
//        List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);
//        return bookmarks.stream()
//            .map(e -> BookmarkRes.from(e))
//            .collect(Collectors.toList());
//    }

    @Transactional
    public void deleteBookmark(HttpServletRequest request, int bookmarkId) {
        Member member = tokenProvider.getMemberFromToken(request);
        Bookmark bookmark = getBookmarkById(bookmarkId);

        if (member.getId() != bookmark.getMember().getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        bookmarkRepository.deleteById(bookmarkId);
    }

    private Bookmark getBookmarkById(int bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

    public MemberRes getOtherMember(HttpServletRequest request, int otherMemberId) {
        Member member = tokenProvider.getMemberFromToken(request);
        member = memberRepository.findById(member.getId()).get();
        Member otherMember = memberRepository.findById(otherMemberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));

        int followingCnt = Long.valueOf(followRepository.countByFromMember(otherMember)).intValue();
        int followerCnt = Long.valueOf(followRepository.countByToMember(otherMember)).intValue();
        List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);

        //TODO: MemberRes에 List<Article> 주입

        MemberRes memberRes = MemberRes.builder()
            .id(otherMemberId)
            .email(otherMember.getEmail())
            .name(otherMember.getName())
            .articles(null)
            .followingCnt(followingCnt)
            .followerCnt(followerCnt)
            .bookmarks(bookmarks.stream().map(e -> BookmarkRes.from(e)).collect(Collectors.toList()))
            .createdAt(otherMember.getCreatedAt())
            .build();

        if (followRepository.existsByFromMemberAndToMember(member, otherMember)) {
            memberRes.setFollowYn(true);
        }

        return memberRes;
    }

    public List<FollowingRes> getFollowings(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        return followRepository.findByFromMember(member)
            .stream().map(e -> FollowingRes.from(e))
            .collect(Collectors.toList());
    }

    public List<FollowerRes> getFollowers(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        return followRepository.findByToMember(member)
            .stream().map(e -> FollowerRes.from(e))
            .collect(Collectors.toList());
    }

    @Transactional
    public void createFollow(HttpServletRequest request, int targetMemberId) {
        Member fromMember = tokenProvider.getMemberFromToken(request);
        Member toMember = getMember(targetMemberId);

        if (followRepository.existsByFromMemberAndToMember(fromMember, toMember)) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_FOLLOW);
        }

        Follow follow = Follow.of(fromMember, toMember);
        followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(HttpServletRequest request, int targetMemberId) {
        Member fromMember = tokenProvider.getMemberFromToken(request);
        Member toMember = getMember(targetMemberId);

        Follow follow = followRepository.findByFromMemberAndToMember(fromMember, toMember)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_FOLLOW));

        followRepository.delete(follow);
    }

}