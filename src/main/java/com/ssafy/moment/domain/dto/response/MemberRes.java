package com.ssafy.moment.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRes {

    private int id;
    private String email;
    private String name;
    private String profileImgUrl;
    private boolean followYn;
    private int followingCnt;
    private int followerCnt;
    private LocalDateTime createdAt;

}