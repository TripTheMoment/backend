package com.ssafy.moment.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String profileImgKeyName;
    @Builder.Default
    private boolean status = true;
    private String userRole;
    private boolean emailAuthYn;
    private String emailAuthKey;

//    @OneToMany(mappedBy = "member")
//    @JsonManagedReference
//    private List<Article> articles;
//
//    @OneToMany(mappedBy = "fromMember")
//    @JsonManagedReference
//    private List<Follow> followings;
//
//    @OneToMany(mappedBy = "toMember")
//    @JsonManagedReference
//    private List<Follow> followers;
//
//    @OneToMany(mappedBy = "member")
//    @JsonManagedReference
//    private List<AttractionBookmark> bookmarks;

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
    public void update(String name, String encodedPw) {
        this.name = name;
        this.password = encodedPw;
    }
    public void updateProfileImgKeyName(String profileImgKeyName) {
        this.profileImgKeyName = profileImgKeyName;
    }

}