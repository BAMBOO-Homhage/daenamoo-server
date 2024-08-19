package daenamoo.homepage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyMemberResponseDto {

    private Long id;
    private String name;
    private List<MemberStudyResponseDto> memberStudies;
}
