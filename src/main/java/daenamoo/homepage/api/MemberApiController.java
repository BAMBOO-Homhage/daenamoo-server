package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.auth.dto.JwtDto;
import daenamoo.homepage.auth.util.JwtUtil;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.MemberStudy;
import daenamoo.homepage.dto.request.CreateMemberRequestDto;
import daenamoo.homepage.dto.request.LoginRequestDto;
import daenamoo.homepage.dto.response.MemberResponseDto;
import daenamoo.homepage.dto.response.MemberStudyResponseDto;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
            description = "회원가입 API입니다. CreateMemberRequestDto 형태로 RequestBody에 담아서 요청합니다.")
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

    // 인증 테스트 APi - 삭제 예정
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("테스트 성공했습니다.");
    }

    @Operation(method = "GET",
            summary = "멤버 조회",
            description = "멤버 전체를 조회합니다. header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("")
    public Result findMembers() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberResponseDto> collect = findMembers.stream()
                .map(m -> new MemberResponseDto(m)) // MemberDto 구성은 추후 변경 가능
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //멤버 조회 API
    @Operation(method = "GET",
            summary = "특정 멤버 조회",
            description = "멤버 한명을 조회합니다. header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/{id}")
    public Result findMember(
            @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        MemberResponseDto memberDto = new MemberResponseDto(findMember);

        return new Result(memberDto);
    }

    //멤버 수정 API     // 무엇을 수정 가능하게 해야 하는가.. 비밀번호, 전공, 이름, 이메일, 전화번호..?
//    @PatchMapping("/")
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
    @Operation(method = "GET",
            summary = "특정 멤버의 스터디 조회",
            description = "멤버 한명의 스터디를 조회합니다. header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/{id}/studies")
    public Result findStudyInMember(
            @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        List<MemberStudy> studies = memberService.findOneStudies(findMember.getId());

        List<MemberStudyResponseDto> collect = studies.stream()
                .map(ms -> new MemberStudyResponseDto(ms))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 멤버 권한 변경 API - USER
    @Operation(method = "PACTH",
            summary = "멤버의 권한을 USER로 변경",
            description = "header에 accessToken을 넣어 요청하면 응답합니다.")
    @PatchMapping("/{id}/role/user")
    public ResponseEntity<String> changeRoleUser(
            @PathVariable("id") Long id
    ) {
        try {
            Member findMember = memberService.findOne(id);
            if (findMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 멤버를 찾을 수 없습니다.");
            }

            memberService.changeRoleUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("USER로 권한이 변경되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("권한 변경 중 오류가 발생했습니다.");
        }
    }

    // 멤버 권한 변경 API - MANAGER
    @Operation(method = "PACTH",
            summary = "멤버의 권한을 MANAGER로 변경",
            description = "header에 accessToken을 넣어 요청하면 응답합니다.")
    @PatchMapping("/{id}/role/manager")
    public ResponseEntity<String> changeRoleManager(
            @PathVariable("id") Long id
    ) {
        try {
            Member findMember = memberService.findOne(id);
            if (findMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 멤버를 찾을 수 없습니다.");
            }

            memberService.changeRoleManager(id);
            return ResponseEntity.status(HttpStatus.OK).body("MANAGER로 권한이 변경되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("권한 변경 중 오류가 발생했습니다.");
        }
    }

    // 멤버 권한 변경 API - MANAGER
    @Operation(method = "PACTH",
            summary = "멤버의 권한을 MASTER로 변경",
            description = "header에 accessToken을 넣어 요청하면 응답합니다.")
    @PatchMapping("/{id}/role/master")
    public ResponseEntity<String> changeRoleMaster(
            @PathVariable("id") Long id
    ) {
        try {
            Member findMember = memberService.findOne(id);
            if (findMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 멤버를 찾을 수 없습니다.");
            }

            memberService.changeRoleMaster(id);
            return ResponseEntity.status(HttpStatus.OK).body("MASTER로 권한이 변경되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("권한 변경 중 오류가 발생했습니다.");
        }
    }
}
