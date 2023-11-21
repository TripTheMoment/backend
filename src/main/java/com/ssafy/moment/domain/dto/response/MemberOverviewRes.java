package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Member;
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
public class MemberOverviewRes {

    private int id;
    private String name;
    private String profileImgUrl;

    public static MemberOverviewRes from(Member member) {
        return MemberOverviewRes.builder()
            .id(member.getId())
            .profileImgUrl(member.getProfileImgUrl())
            .name(member.getName())
            .build();
    }

}
