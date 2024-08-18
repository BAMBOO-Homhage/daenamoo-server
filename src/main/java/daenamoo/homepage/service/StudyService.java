package daenamoo.homepage.service;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.MemberStudy;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.dto.request.CreateStudyRequestDto;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.MemberStudyRepository;
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
    private final MemberRepository memberRepository;
    private final MemberStudyRepository memberStudyRepository;

    // 전체 스터디 조회
    public List<Study> findStudies() {
        return studyRepository.findAll();
    }

    // 특정 스터디 조회
    public Study findStudy(Long studyId) {
        return studyRepository.findById(studyId).get();
    }

    // 스터디 생성
    @Transactional
    public Long createStudy(CreateStudyRequestDto createStudyRequestDto) {
        Study study = createStudyRequestDto.toEntity();
        studyRepository.save(study);
        return study.getId();
    }

    // 스터디에 멤버 추가
    @Transactional
    public Long addMember(Long studyId, Long memberId) {
        Study study = studyRepository.findById(studyId).get();
        Member member = memberRepository.findById(memberId).get();

        MemberStudy memberStudy = new MemberStudy();
        memberStudy.setMemberAndStudy(study, member);

        study.getMemberStudies().add(memberStudy);
        member.getMemberStudies().add(memberStudy);

        memberStudyRepository.save(memberStudy);

        return memberStudy.getId();
    }

    // 스터디 진행현황 조회
    public double getStudyStatus(Long studyId) {
        Study study = studyRepository.findById(studyId).get();
        int totalStudyCount = study.getTotalStudyCount();
        int studyCount = study.getStudyCount();
        double studyStatus = (double) studyCount / totalStudyCount;

        return studyStatus;
    }
}
