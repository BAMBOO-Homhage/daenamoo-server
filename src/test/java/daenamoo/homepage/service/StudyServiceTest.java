package daenamoo.homepage.service;

import daenamoo.homepage.dto.request.CreateMemberRequestDto;
import daenamoo.homepage.dto.request.CreateStudyRequestDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudyServiceTest {

    @Autowired StudyService studyService;
    @Autowired MemberService memberService;

    @Test
    public void 스터디_생성() throws Exception {
        //given
        CreateStudyRequestDto studyDto = new CreateStudyRequestDto("study1", 3, 12, true);
        //when
        Long studyId = studyService.createStudy(studyDto);
        //then
        Assertions.assertEquals("study1", studyService.findStudy(studyId).getName());
    }
    
    @Test
    public void 스터디에_멤버_추가() throws Exception {
        //given
        CreateStudyRequestDto studyDto = new CreateStudyRequestDto("study1", 3, 12, true);
        CreateMemberRequestDto memberDto1 = new CreateMemberRequestDto("202010001", "kim", "password", "휴먼", "123@g.com", "000-000");
        CreateMemberRequestDto memberDto2 = new CreateMemberRequestDto("202010002", "kim", "password", "휴먼", "123@g.com", "000-000");

        //when
        Long studyId = studyService.createStudy(studyDto);
        Long memberId1 = memberService.join(memberDto1);
        Long memberId2 = memberService.join(memberDto2);
        studyService.addMember(studyId, memberId1);
        studyService.addMember(studyId, memberId2);

        //then
        List<String> expected = Arrays.asList("202010001", "202010002");

        List<String> actual = studyService.findMembers(studyId).stream()
                .map(memberStudy -> memberStudy.getMember().getStudentId())
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 스터디에_회원_중복_예외() throws Exception {
        //given
        CreateMemberRequestDto memberDto = new CreateMemberRequestDto("202010000", "kim", "password", "휴먼", "123@g.com", "000-000");
        CreateStudyRequestDto studyDto = new CreateStudyRequestDto("study1", 3, 12, true);
        //when
        Long memberId = memberService.join(memberDto);
        Long studyId = studyService.createStudy(studyDto);
        studyService.addMember(studyId, memberId);
        studyService.addMember(studyId, memberId);
        //then
        Assertions.fail("회원 중복 추가 예외가 발생해야 한다.");
    }
}