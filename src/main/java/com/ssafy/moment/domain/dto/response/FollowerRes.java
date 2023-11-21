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
public class FollowerRes {

    private int id;
    private FollowMemberRes member;

    public static FollowerRes from(Follow follow) {
        return FollowerRes.builder()
            .id(follow.getId())
            .member(FollowMemberRes.from(follow.getFromMember()))
            .build();
    }
}
