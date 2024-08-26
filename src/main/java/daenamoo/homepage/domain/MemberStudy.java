package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class MemberStudy {

    @Id @GeneratedValue
    @Column(name = "ms_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private int oCount = 0;
    private int xCount = 0;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public void setMemberAndStudy(Study study, Member member) {
        this.member = member;
        this.study = study;
    }

    public void addOcount() {
        this.oCount += 1;
    }

    public void addXcount() {
        this.xCount += 1;
    }

    public double attendanceRate() {
        int total = oCount + xCount;
        if (total == 0){
            throw new IllegalArgumentException("출석률을 계산할 수 없습니다.");
        }
        return (double) oCount / total * 100;
    }
}
