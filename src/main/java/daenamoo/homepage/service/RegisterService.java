package daenamoo.homepage.service;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Register;
import daenamoo.homepage.domain.Subject;
import daenamoo.homepage.dto.request.CreateRegisterRequestDto;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.RegisterRepository;
import daenamoo.homepage.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;

    // 수강 신청 생성
    @Transactional
    public Long createRegister(String studentId, CreateRegisterRequestDto createRegisterRequestDto) {

        Member member = memberRepository.findByStudentId(studentId).get();
        Subject subject = subjectRepository.findById(createRegisterRequestDto.getSubjectId())
                .orElseThrow(() -> new IllegalStateException("해당 과목이 존재하지 않습니다."));

        Register register = createRegisterRequestDto.toEntity(member, subject);
        registerRepository.save(register);

        return register.getId();
    }

    // 수강 신청 목록 조회
    public List<Register> findRegisters() {
        return registerRepository.findAll();
    }
}
