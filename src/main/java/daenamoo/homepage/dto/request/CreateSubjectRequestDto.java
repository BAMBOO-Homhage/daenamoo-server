package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Day;
import daenamoo.homepage.domain.Subject;
import daenamoo.homepage.domain.SubjectName;
import lombok.Data;

@Data
public class CreateSubjectRequestDto {

    private SubjectName name;
    private Day day;
    private int count;

    public Subject toEntity() {
        return Subject.builder()
                .name(name)
                .day(day)
                .count(count)
                .build();
    }
}
