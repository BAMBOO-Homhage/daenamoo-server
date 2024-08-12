package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.MemberStudy;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.service.StudyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@RestController
@RequiredArgsConstructor
public class StudyApiController {

    private final StudyService studyService;

    //스터디 생성 API
    @PostMapping("/studies/new")
    public ResponseEntity<String> createStudy(@Valid @RequestBody Study study) {
        try {
            studyService.createStudy(study);
            return new ResponseEntity<>("Study created successfully", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //스터디 전체 목록 조회 API
    @GetMapping("/studies")
    public Result findStudies() {
        List<Study> studies = studyService.findStudies();
        List<Object> collect = studies.stream()
                .map(s -> new StudyDto(s.getId(), s.getName(), s.getHeadCount(), s.getTotalStudyCount(), s.getStudyCount(), s.is_book()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //특정 스터디 조회 API
    @GetMapping("/studies/{id}")
    public Result findStudy(
            @PathVariable("id") Long id
    ) {
        Study study = studyService.findStudy(id);
        StudyDto studyDto = new StudyDto(
                study.getId(),
                study.getName(),
                study.getHeadCount(),
                study.getTotalStudyCount(),
                study.getStudyCount(),
                study.is_book()
        );
        return new Result(studyDto);
    }

    //스터디에 회원 추가 API
    @PostMapping("/studies/{studyId}/members/{memberId}")
    public ResponseEntity<String> addStudyMember(
            @PathVariable("studyId") Long studyId,
            @PathVariable("memberId") Long memberId
    ) {
        Long memberStudyId = studyService.addMember(studyId, memberId);
        if (memberStudyId == null) {
            return ResponseEntity.ok("회원 추가 실패");
        }
        return ResponseEntity.ok("Member added to study successfully");
    }

    //스터디에 소속된 멤버 조회 API
    @GetMapping("/studies/{id}/members")
    public Result findStudyMembers(
            @PathVariable("id") Long id
    ) {
        Study study = studyService.findStudy(id);
        List<MemberStudyDto> collect = study.getMemberStudies().stream()
                .map(ms -> new MemberStudyDto(ms.getMember().getName(), ms.getO_count(), ms.getX_count()))
                .collect(Collectors.toList());
        StudyMemberDto smDto = new StudyMemberDto(study.getId(), study.getName(), collect);

        return new Result(smDto);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class StudyDto {
        private Long id;
        private String name;
        private int headCount;
        private int totalStudyCount;
        private int studyCount;
        private boolean isBook;
    }

    @Data
    @AllArgsConstructor
    static class StudyMemberDto {
        private Long id;
        private String name;
        List<MemberStudyDto> memberStudies;
    }

    @Data
    @AllArgsConstructor
    static class MemberStudyDto {
        private String memberName;
        private int o_count;
        private int x_count;
    }
}
