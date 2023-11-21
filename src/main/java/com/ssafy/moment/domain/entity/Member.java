package com.ssafy.moment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    private String email;
    private String password;
    private String name;
    @Builder.Default
    private boolean status = true;
    private String userRole;
    private boolean emailAuthYn;
    private String emailAuthKey;

    @OneToMany(mappedBy = "member")
    private List<Article> articles;

    @OneToMany(mappedBy = "fromMemberId")
    private List<Follow> followings;

    @OneToMany(mappedBy = "toMemberId")
    private List<Follow> followers;

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks;

    public void inactive() {
        this.status = false;
    }
    public void updatePassword(String newPw) {
        this.password = newPw;
    }
    public void auth() {
        System.out.println("활성화 메서드 실행");
        this.emailAuthYn = true;
    }
    public void updateName(String name) {
        this.name = name;
    }

}