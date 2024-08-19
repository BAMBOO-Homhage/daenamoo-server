package daenamoo.homepage.domain;

import daenamoo.homepage.dto.request.CreateSubjectRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubjectName name;

    @Enumerated(EnumType.STRING)
    private Day day;

    private int count;

    // 수정 메서드
    public void updateSubject(CreateSubjectRequestDto createSubjectRequestDto) {
        this.name = createSubjectRequestDto.getName();
        this.day = createSubjectRequestDto.getDay();
        this.count = createSubjectRequestDto.getCount();
    }
}
