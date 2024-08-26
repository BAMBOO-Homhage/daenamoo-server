package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.Day;
import daenamoo.homepage.domain.Subject;
import daenamoo.homepage.domain.SubjectName;
import lombok.Data;

@Data
public class SubjectResponseDto {
    private SubjectName name;
    private Day day;
    private int count;

    public SubjectResponseDto(Subject subject) {
        this.name = subject.getName();
        this.day = subject.getDay();
        this.count = subject.getCount();
    }
}
