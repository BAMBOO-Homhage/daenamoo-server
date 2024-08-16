package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Study;
import lombok.Data;

@Data
public class CreateStudyRequestDto {

    private String name;
    private int headCount;
    private int totalStudyCount;
    private boolean is_book;

    public Study toEntity(){
        return Study.builder()
                .name(name)
                .headCount(headCount)
                .totalStudyCount(totalStudyCount)
                .studyCount(0)
                .is_book(is_book)
                .build();
    }
}
