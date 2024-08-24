package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Register;
import daenamoo.homepage.domain.Subject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRegisterRequestDto {

    private Long subjectId;
    private LocalDateTime createdAt;

    public Register toEntity(Member member, Subject subject) {
        return Register.builder()
                .member(member)
                .subject(subject)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
