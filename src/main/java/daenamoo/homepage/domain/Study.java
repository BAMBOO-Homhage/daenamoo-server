package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
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

    @Builder.Default
    @OneToMany(mappedBy = "study")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @Builder.Default
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
