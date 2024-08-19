package daenamoo.homepage.auth.userDetails;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //Username(Email) 로 CustomUserDetail 을 가져오기
    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {

        log.info("[ CustomUserDetailsService ] StudentId 을 이용하여 Member 를 검색합니다.");
        Optional<Member> findMember = memberRepository.findByStudentId(studentId);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            return new CustomUserDetails(member.getStudentId(), member.getPassword(), member.getRole());
        }
        throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
    }
}
