package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.MemberStudy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyMemberResponseDto {

    private Long id;
    private String memberName;
    private String major;
    private String email;
    private String phoneNumber;

    public StudyMemberResponseDto(MemberStudy memberStudy) {
        this.id = memberStudy.getId();
        this.memberName = memberStudy.getMember().getName();
        this.major = memberStudy.getMember().getMajor();
        this.email = memberStudy.getMember().getEmail();
        this.phoneNumber = memberStudy.getMember().getPhoneNumber();
    }
}
