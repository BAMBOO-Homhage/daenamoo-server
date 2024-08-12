package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
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

    @PostMapping("/studies/new")
    public ResponseEntity<String> createStudy(@Valid @RequestBody Study study) {
        try {
            studyService.createStudy(study);
            return new ResponseEntity<>("Study created successfully", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/studies")
    public Result findStudies() {
        List<Study> studies = studyService.findStudies();
        List<Object> collect = studies.stream()
                .map(s -> new StudyDto(s.getId(), s.getName(), s.getHeadCount(), s.getTotalStudyCount(), s.getStudyCount(), s.is_book()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

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
}
