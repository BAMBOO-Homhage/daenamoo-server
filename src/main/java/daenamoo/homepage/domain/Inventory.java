package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Inventory {

    @Id @GeneratedValue
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private int week;
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;
}
