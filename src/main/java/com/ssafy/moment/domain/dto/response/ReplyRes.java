package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.Reply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRes {

    private MemberOverviewRes member;
    private String content;
    private LocalDateTime createdAt;

    public static ReplyRes of(Reply reply, Member member) {
        return ReplyRes.builder()
            .member(MemberOverviewRes.from(member))
            .content(reply.getContent())
            .createdAt(reply.getCreatedAt())
            .build();
    }

}
