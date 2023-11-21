package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Follow;
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
public class FollowingRes {

    private int id;
    private MemberOverviewRes member;

    public static FollowingRes from(Follow follow) {
        return FollowingRes.builder()
            .id(follow.getId())
            .member(MemberOverviewRes.from(follow.getToMember()))
            .build();
    }

}
