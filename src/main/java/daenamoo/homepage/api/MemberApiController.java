package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.auth.dto.JwtDto;
import daenamoo.homepage.auth.util.JwtUtil;
import daenamoo.homepage.domain.AuthType;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.MemberStudy;
import daenamoo.homepage.dto.CreateMemberRequestDto;
import daenamoo.homepage.dto.LoginRequestDto;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Operation(method = "POST",
            summary = "회원가입",
            description = "회원가입API입니다. CreateMemberRequestDto 형태로 RequestBody에 담아서 요청합니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateMemberRequestDto createUserRequestDto) {

        long userId = memberService.join(createUserRequestDto);

        // 결과 데이터 생성
        Map<String, Long> result = new HashMap<>();
        result.put("userId", userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
    }

    @Operation(method = "POST",
            summary = "로그인",
            description = "로그인합니다. studentId와 password를 body에 담아서 전송합니다.")
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginRequestDto loginRequestDTO) {

        // 사용자 조회
        Member member = memberRepository.findByStudentId(loginRequestDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 학번입니다."));

        // CustomUserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                member.getStudentId(),
                member.getPassword(),
                member.getRole()
        );

        // 토큰 만료 시간 설정
        Instant expiration = Instant.now().plusMillis(jwtUtil.getAccessExpMs());

        // JWT 토큰 생성
        String accessToken = jwtUtil.tokenProvider(userDetails, expiration);
        String refreshToken = jwtUtil.createJwtRefreshToken(userDetails);

        // JWT DTO 생성
        JwtDto jwtDto = JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("테스트 성공했습니다.");
    }

    //멤버 전체 조회 API
    @GetMapping("/members")
    public Result findMembers() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getStudentId(), m.getName(), m.getMajor(), m.getAuthType()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //멤버 조회 API
    @GetMapping("/members/{id}")
    public Result findMember(
            @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        MemberDto memberDto = new MemberDto(
                findMember.getStudentId(),
                findMember.getName(),
                findMember.getMajor(),
                findMember.getAuthType()
        );

        return new Result(memberDto);
    }

    //멤버 수정 API
//    @PatchMapping("/members/{id}")
//    public ResponseEntity<String> updateMember(
//            @Valid @RequestBody Member member,
//            @PathVariable("id") Long id
//    ) {
//        try {
//            Member findMember = memberService.findOne(id);
//            memberService.update(findMember.getId(),
//                    member.getStudentId(),
//                    member.getName(),
//                    member.getPw(),
//                    member.getMajor(),
//                    member.isAdmin());
//
//            return ResponseEntity.ok("update Member successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//        }
//    }

    //멤버의 스터디 조회
    @GetMapping("/members/{id}/studies")
    public Result findStudyInMember(
            @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        List<MemberStudy> studies = memberService.findOneStudies(findMember.getId());

        List<MemberStudyDto> collect = studies.stream()
                .map(ms -> new MemberStudyDto(ms.getStudy().getName(), ms.getOCount(), ms.getXCount()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String studentId;
        private String name;
        private String major;
        private AuthType authType;
    }

    @Data
    @AllArgsConstructor
    static class MemberStudyDto {
        private String name;
        private int oCount;
        private int xCount;
    }
}
