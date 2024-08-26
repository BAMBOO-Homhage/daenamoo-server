package daenamoo.homepage.service;

import daenamoo.homepage.domain.Subject;
import daenamoo.homepage.dto.request.CreateSubjectRequestDto;
import daenamoo.homepage.dto.response.MemberResponseDto;
import daenamoo.homepage.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // 과목 생성
    @Transactional
    public Long createSubject(CreateSubjectRequestDto createSubjectRequestDto) {

        Subject subject = createSubjectRequestDto.toEntity();
        subjectRepository.save(subject);
        return subject.getId();
    }

    // 과목 조회
    public List<Subject> findSubjects() {
        List<Subject> findSubjects = subjectRepository.findAll();
        return findSubjects;
    }

    // 과목 수정
    @Transactional
    public Long updateSubject(Long id, CreateSubjectRequestDto createSubjectRequestDto) {

        Subject findSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다."));

        findSubject.updateSubject(createSubjectRequestDto);
        return findSubject.getId();
    }

    // 과목 삭제
    @Transactional
    public void deleteSubject(Long subjectId) {
        Subject findSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다."));
        subjectRepository.delete(findSubject);
    }
}

