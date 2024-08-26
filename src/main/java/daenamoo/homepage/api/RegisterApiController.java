package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.domain.Register;
import daenamoo.homepage.dto.request.CreateRegisterRequestDto;
import daenamoo.homepage.dto.response.RegisterResponseDto;
import daenamoo.homepage.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registers")
public class RegisterApiController {

    private final RegisterService registerService;

    @Operation(method = "POST",
            summary = "수강신청 생성 API",
            description = "")
    @PostMapping("/new")
    public ResponseEntity<String> createRegister(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CreateRegisterRequestDto createRegisterRequestDto
    ) {
        try {
            registerService.createRegister(customUserDetails.getUsername(), createRegisterRequestDto);
            return new ResponseEntity<>("수강신청이 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(method = "GET",
            summary = "수강신청 목록 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("")
    public ResponseEntity<?> readRegisters() {
        try {
            List<Register> registers = registerService.findRegisters();
            List<RegisterResponseDto> collect = registers.stream()
                    .map(r -> new RegisterResponseDto(r))
                    .collect(Collectors.toList());

            Result result = new Result(collect);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("수강신청 목록 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "수강신청 삭제 API",
            description = "")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegister(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        try {
            registerService.deleteRegister(customUserDetails.getUsername(), id);
            return ResponseEntity.status(HttpStatus.OK).body("수강신청 취소가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            // 잘못된 입력(없는 학생 ID나 수강 신청) 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            // 수강 신청 삭제 중의 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수강 신청 삭제 중 오류가 발생했습니다.");
        } catch (Exception e) {
            // 그 외 알 수 없는 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
        }
    }
}
