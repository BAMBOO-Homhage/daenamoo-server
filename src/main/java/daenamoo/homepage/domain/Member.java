package daenamoo.homepage.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String studentId;
    private String name;
    private String password;
    private String major;
    private String email;
    private String phoneNumber;

    private String Role;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @OneToMany(mappedBy = "member")
    private List<Award> awords = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<LibraryPost> libraryPosts = new ArrayList<>();

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    //연관관계 메서드
    public void addMemberStudy(MemberStudy memberStudy) {
        memberStudies.add(memberStudy);
        memberStudy.setMember(this);
    }

    //수정 메서드
}
