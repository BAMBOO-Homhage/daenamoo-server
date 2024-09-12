package daenamoo.homepage.service;

import daenamoo.homepage.domain.AuthType;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.dto.request.CreateMemberRequestDto;
import daenamoo.homepage.dto.request.CreateStudyRequestDto;
import daenamoo.homepage.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired StudyService studyService;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception {
        //given
        CreateMemberRequestDto dto = new CreateMemberRequestDto(
                "202010000",
                "kim",
                "password",
                "휴먼",
                "123@g.com",
                "000-000"
        );
        //when
        Member member = dto.toEntity(passwordEncoder);
        Long id = memberService.join(dto);
        //then
        Assertions.assertEquals(member.getStudentId(), memberRepository.findById(id).get().getStudentId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        CreateMemberRequestDto dto1 = new CreateMemberRequestDto("202010000", "kim", "password", "휴먼", "123@g.com", "000-000");
        CreateMemberRequestDto dto2 = new CreateMemberRequestDto("202010000", "kim", "password", "휴먼", "123@g.com", "000-000");
        //when
        memberService.join(dto1);
        memberService.join(dto2);
        //then
        Assertions.fail("예외가 발생해야 한다.");
    }

//    @Test
//    public void 회원_수정() throws Exception {
//        //given
//        Member member = new Member();
//        member.setName("kim");
//        //when
//        memberService.join(member);
//        memberService.update(member.getId(), "202010766", "kkiimm", "kimkim", "휴먼", true);
//        //then
//        Assertions.assertEquals("kkiimm", member.getName());
//    }

    @Test
    public void 회원_권한_운영진으로_변경() throws Exception {
        //given
        CreateMemberRequestDto memberDto = new CreateMemberRequestDto("202010000", "kim", "password", "휴먼", "123@g.com", "000-000");
        //when
        Long memberId = memberService.join(memberDto);
        memberService.changeRoleManager(memberId);
        //then
        Assertions.assertEquals(memberRepository.findById(memberId).get().getAuthType(), AuthType.ROLE_MANAGER);
    }

    @Test
    public void 회원_스터디_조회() throws Exception {
        //given
        CreateMemberRequestDto memberDto = new CreateMemberRequestDto("202010000", "kim", "password", "휴먼", "123@g.com", "000-000");
        CreateStudyRequestDto studyDto1 = new CreateStudyRequestDto("study1", 3, 12, true);
        CreateStudyRequestDto studyDto2 = new CreateStudyRequestDto("study2", 3, 12, true);
        //when
        Long memberId = memberService.join(memberDto);
        Long studyId1 = studyService.createStudy(studyDto1);
        Long studyId2 = studyService.createStudy(studyDto2);

        studyService.addMember(studyId1, memberId);
        studyService.addMember(studyId2, memberId);
        //then
        Member member = memberRepository.findById(memberId).get();
        List<String> expected = Arrays.asList("study1", "study2");

        List<String> actual = member.getMemberStudies().stream()
                .map(memberStudy -> memberStudy.getStudy().getName())
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }
}