package daenamoo.homepage.dto;

import daenamoo.homepage.domain.AuthType;
import daenamoo.homepage.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateMemberRequestDto {

    @NotBlank(message = "[ERROR] 학번 입력은 필수 입니다.")
    public String studentId;
    @NotBlank(message = "[ERROR] 이름 입력은 필수 입니다.")
    public String name;
    @NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
    public String password;
    @NotBlank(message = "[ERROR] 전공 입력은 필수 입니다.")
    public String major;
    @NotBlank(message = "[ERROR] 이메일 입력은 필수 입니다.")
    public String email;
    @NotBlank(message = "[ERROR] 전화번호 입력은 필수 입니다.")
    public String phoneNumber;

    public Member toEntity(PasswordEncoder passwordEncoder){
        String encodePassword = passwordEncoder.encode(password);
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .password(encodePassword)
                .major(major)
                .email(email)
                .phoneNumber(phoneNumber)
                .authType(AuthType.ROLE_USER)
                .Role("USER")
                .build();
    }
}
