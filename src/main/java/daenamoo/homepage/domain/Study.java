package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Study {

    @Id @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    private String name;
    private int headCount;
    private int totalStudyCount;
    private int studyCount;
    private boolean is_book;

    @OneToMany(mappedBy = "study")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<Inventory> inventories = new ArrayList<>();

    public void setName(String name) {
        if (this.name != null && this.name.equals(name)) {
            throw new IllegalStateException("중복된 스터디 이름입니다.");
        }
        this.name = name;
    }

    //연관관계 메서드
    public void addMemberStudy(MemberStudy memberStudy) {
        memberStudies.add(memberStudy);
        memberStudy.setStudy(this);
    }
}
