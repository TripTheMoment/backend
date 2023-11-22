package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOverviewRes {

    private static String defaultUrl = "https://greenjay-bucket.s3.ap-northeast-2.amazonaws.com/";

    private int id;
    private String name;
    private String profileImgUrl;

    public static MemberOverviewRes from(Member member) {
        return MemberOverviewRes.builder()
            .id(member.getId())
            .profileImgUrl((member.getProfileImgKeyName() == null) ? null : (defaultUrl + member.getProfileImgKeyName()))
            .name(member.getName())
            .build();
    }

}
