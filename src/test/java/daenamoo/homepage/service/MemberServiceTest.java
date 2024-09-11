package daenamoo.homepage.service;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.dto.request.CreateMemberRequestDto;
import daenamoo.homepage.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

//    @Test(expected = IllegalStateException.class)
//    public void 중복_회원_예외() throws Exception {
//        //given
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member1.setName("kim");
//        //when
//        memberService.join(member1);
//        memberService.join(member2);
//        //then
//        Assertions.fail("예외가 발생해야 한다.");
//    }
//
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
//
//    @Test
//    public void 회원_스터디_조회() throws Exception {
//        //given
//        Member member = new Member();
//        member.setName("kim");
//
//        Study study1 = new Study();
//        study1.setName("study1");
//        Study study2 = new Study();
//        study2.setName("study2");
//        //when
//        memberService.join(member);
//        studyService.createStudy(study1);
//        studyService.createStudy(study2);
//
//        studyService.addMember(study1.getId(), member.getId());
//        studyService.addMember(study2.getId(), member.getId());
//        //then
//        List<String> expected = Arrays.asList("study1", "study2");
//
//        List<String> actual = member.getMemberStudies().stream()
//                .map(memberStudy -> memberStudy.getStudy().getName())
//                .collect(Collectors.toList());
//
//        Assertions.assertEquals(expected, actual);
//    }
}