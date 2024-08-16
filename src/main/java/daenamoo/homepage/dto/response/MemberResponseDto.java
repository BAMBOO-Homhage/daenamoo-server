package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.AuthType;
import daenamoo.homepage.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseDto {
    private String studentId;
    private String name;
    private String major;
    private AuthType authType;

    public MemberResponseDto(Member member) {
        this.studentId = member.getStudentId();
        this.name = member.getName();
        this.major = member.getMajor();
        this.authType = member.getAuthType();
    }
}
