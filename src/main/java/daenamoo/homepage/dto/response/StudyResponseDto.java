package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyResponseDto {
    private Long id;
    private String name;
    private int headCount;
    private int totalStudyCount;
    private int studyCount;
    private boolean isBook;

    public StudyResponseDto(Study study) {
        this.id = study.getId();
        this.name = study.getName();
        this.headCount = study.getHeadCount();
        this.totalStudyCount = study.getTotalStudyCount();
        this.studyCount = study.getStudyCount();
        this.isBook = study.is_book();
    }
}
