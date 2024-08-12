package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //멤버 전체 조회 API
    @GetMapping("/members")
    public Result findMembers() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getStudentId(), m.getName(), m.getMajor(), m.isAdmin()))
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
                findMember.isAdmin()
        );

        return new Result(memberDto);
    }

    //멤버 수정 API
    @PatchMapping("/members/{id}")
    public ResponseEntity<String> updateMember(
            @Valid @RequestBody Member member,
            @PathVariable("id") Long id
    ) {
        try {
            Member findMember = memberService.findOne(id);
            memberService.update(findMember.getId(),
                    member.getStudentId(),
                    member.getName(),
                    member.getPw(),
                    member.getMajor(),
                    member.isAdmin());

            return ResponseEntity.ok("update Member successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String studentId;
        private String name;
        private String major;
        private boolean admin;
    }
}
