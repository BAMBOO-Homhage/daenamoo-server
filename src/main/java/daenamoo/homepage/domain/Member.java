package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String studentId;
    private String name;
    private String pw;
    private String major;
    private boolean admin;

    @OneToMany(mappedBy = "member")
    private List<Award> awords = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<LibraryPost> libraryPosts = new ArrayList<>();

    //연관관계 메서드
    public void addMemberStudy(MemberStudy memberStudy) {
        memberStudies.add(memberStudy);
        memberStudy.setMember(this);
    }
}
