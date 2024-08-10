package daenamoo.homepage.service;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //학번으로 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByStudentId(member.getStudentId());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    //회원 수정
    @Transactional
    public void update(Long id, String studentId, String name, String pw, String major, boolean admin) {
        Member member = memberRepository.findById(id).get();
        member.setStudentId(studentId);
        member.setName(name);
        member.setPw(pw);
        member.setMajor(major);
        member.setAdmin(admin);
    }
}
