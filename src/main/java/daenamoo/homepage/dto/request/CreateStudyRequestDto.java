package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Study;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudyRequestDto {

    @NotBlank(message = "[ERROR] 스터디명 입력은 필수 입니다.")
    private String name;
    @NotBlank(message = "[ERROR] 인원 입력은 필수 입니다.")
    private int headCount;
    @NotBlank(message = "[ERROR] 총 스터디 주차 입력은 필수 입니다.")
    private int totalStudyCount;
    @NotBlank(message = "[ERROR] 스터디 형식 입력은 필수 입니다.")
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
