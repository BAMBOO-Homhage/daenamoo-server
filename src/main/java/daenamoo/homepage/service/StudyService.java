package daenamoo.homepage.service;

import daenamoo.homepage.domain.Study;
import daenamoo.homepage.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    //전체 스터디 조회
    public List<Study> findStudies() {
        return studyRepository.findAll();
    }

    //특정 스터디 조회
    public Study findStudy(Long studyId) {
        return studyRepository.findById(studyId).get();
    }

    //스터디 생성
    @Transactional
    public Long createStudy(Study study) {
        studyRepository.save(study);
        return study.getId();
    }
}
