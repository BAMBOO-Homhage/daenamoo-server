package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members")
    public Result findMembers() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getStudentId(), m.getName(), m.getMajor(), m.isAdmin()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/members/{id}")
    public Result findMember(
        @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        MemberDto memberDto = new MemberDto(findMember.getStudentId(), findMember.getName(), findMember.getMajor(), findMember.isAdmin());
        return new Result(memberDto);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
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
