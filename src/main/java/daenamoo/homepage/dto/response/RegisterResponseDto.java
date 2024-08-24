package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.Register;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponseDto {

    private Long memberId;
    private Long subjectId;
    private LocalDateTime craetedAt;

    public RegisterResponseDto(Register register) {
        this.memberId = register.getMember().getId();
        this.subjectId = register.getSubject().getId();
        this.craetedAt = LocalDateTime.now();
    }
}
