package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Study;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRequestDto {

    @NotBlank(message = "[ERROR] 스터디명 입력은 필수 입니다.")
    private String name;
    @NotNull(message = "[ERROR] 인원 입력은 필수 입니다.")
    @Min(3)
    private int headCount;
    @NotNull(message = "[ERROR] 총 스터디 주차 입력은 필수 입니다.")
    private int totalStudyCount;
    @NotNull(message = "[ERROR] 스터디 형식 입력은 필수 입니다.")
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
