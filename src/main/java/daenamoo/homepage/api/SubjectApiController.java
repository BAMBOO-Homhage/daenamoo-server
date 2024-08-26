package daenamoo.homepage.api;

import daenamoo.homepage.api.ResultDto.Result;
import daenamoo.homepage.domain.Subject;
import daenamoo.homepage.dto.request.CreateStudyRequestDto;
import daenamoo.homepage.dto.request.CreateSubjectRequestDto;
import daenamoo.homepage.dto.response.SubjectResponseDto;
import daenamoo.homepage.repository.SubjectRepository;
import daenamoo.homepage.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectApiController {

    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;

    // 과목 생성 API
    @Operation(method = "POST",
            summary = "과목 생성 API",
            description = "CreateSubjectRequestDto 형태로 name, date, count 를 RequestBody에 담아서 요청합니다.")
    @PostMapping("/new")
    public ResponseEntity<String> createStudy(@Valid @RequestBody CreateSubjectRequestDto createSubjectRequestDto) {
        try {
            subjectService.createSubject(createSubjectRequestDto);
            return new ResponseEntity<>("과목이 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 과목 조회 API
    @Operation(method = "GET",
            summary = "과목 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("")
    public Result readSubjects() {
        try {
            List<Subject> subjects = subjectService.findSubjects();
            List<SubjectResponseDto> collect = subjects.stream()
                    .map(s -> new SubjectResponseDto(s))
                    .collect(Collectors.toList());

            return new Result(collect);
        } catch (Exception e) {
            System.out.println(e);
            return new Result("과목 조회에 실패했습니다.");
        }
    }

    // 과목 수정 API
    @Operation(method = "PATCH",
            summary = "과목 수정 API",
            description = "header에 accessToken을 넣어 요청하면 응답합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateSubject(@PathVariable("id") Long id, @Valid @RequestBody CreateSubjectRequestDto createSubjectRequestDto) {
        try {
            subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("없는 과목입니다."));

            subjectService.updateSubject(id, createSubjectRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body("과목 수정에 성공했습니다.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 수정에 실패했습니다.");
        }
    }

    // 과목 삭제 API
    @Operation(method = "DELETE",
            summary = "과목 삭제 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(
            @PathVariable("id") Long id
    ) {
        try {
            subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("없는 과목입니다."));

            subjectService.deleteSubject(id);
            return ResponseEntity.status(HttpStatus.OK).body("과목 삭제에 성공했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 삭제에 실패했습니다.");
        }
    }
}