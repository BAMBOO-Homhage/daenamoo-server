package daenamoo.homepage;

import daenamoo.homepage.domain.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomepageApplication {
	public static void main(String[] args) {

		Member member = new Member();
		member.setName("회원1");
		System.out.println("member = " + member.getName());

		SpringApplication.run(HomepageApplication.class, args);
	}
}
