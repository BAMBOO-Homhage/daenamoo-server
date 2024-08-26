package daenamoo.homepage.api;

import daenamoo.homepage.domain.MemberStudy;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.dto.request.CreateStudyRequestDto;
import daenamoo.homepage.dto.response.MemberStudyResponseDto;
import daenamoo.homepage.dto.response.StudyMemberResponseDto;
import daenamoo.homepage.dto.response.StudyResponseDto;
import daenamoo.homepage.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies")
public class StudyApiController {

    private final StudyService studyService;

    //스터디 생성 API
    @Operation(method = "POST",
            summary = "스터디 생성 API",
            description = "CreateStudyRequestDto 형태로 name, headCount, totalStudyCount, is_book을 " +
                    "RequestBody에 담아서 요청합니다.")
    @PostMapping("/new")
    public ResponseEntity<String> createStudy(@Valid @RequestBody CreateStudyRequestDto createStudyRequestDto) {
        try {
            studyService.createStudy(createStudyRequestDto);
            return new ResponseEntity<>("스터디가 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(method = "GET",
            summary = "스터디 전체 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("")
    public Result findStudies() {
        List<Study> studies = studyService.findStudies();
        List<Object> collect = studies.stream()
                .map(s -> new StudyResponseDto(s))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Operation(method = "GET",
            summary = "특정 스터디 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/{id}")
    public Result findStudy(
            @PathVariable("id") Long id
    ) {
        Study study = studyService.findStudy(id);
        StudyResponseDto studyResponseDto = new StudyResponseDto(study);
        return new Result(studyResponseDto);
    }

    //스터디에 회원 추가 API
    @Operation(method = "POST",
            summary = "스터디에 회원 추가 API",
            description = "studyId, memberId를 쿼리 파라미터로 요구합니다. header에 accessToken을 넣어 요청하면 응답합니다.")
    @PostMapping("/{studyId}/members/{memberId}")
    public ResponseEntity<String> addStudyMember(
            @PathVariable("studyId") Long studyId,
            @PathVariable("memberId") Long memberId
    ) {
        Long memberStudyId = studyService.addMember(studyId, memberId);
        if (memberStudyId == null) {
            return ResponseEntity.ok("회원 정보가 없습니다. 회원 추가에 실패했습니다.");
        }
        return ResponseEntity.ok("회원 추가에 성공했습니다.");
    }

    //스터디에 소속된 멤버 조회 API
    @Operation(method = "GET",
            summary = "스터디에 소속된 멤버 조회 API",
            description = "studyId를 쿼리 파라미터로 요구합니다. header에 accessToken을 넣어 요청하면 응답합니다.")
    @GetMapping("/{id}/members")
    public Result findStudyMembers(
            @PathVariable("id") Long id
    ) {
        Study study = studyService.findStudy(id);
        List<MemberStudy> members = studyService.findMembers(id);

        List<StudyMemberResponseDto> collect = members.stream()
                .map(ms -> new StudyMemberResponseDto(ms))
                .collect(Collectors.toList());


        return new Result(collect);
    }
}

