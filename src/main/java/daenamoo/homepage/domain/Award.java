package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Award {

    @Id @GeneratedValue
    @Column(name = "aword_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate date;
}
