package daenamoo.homepage.domain;

import jakarta.persistence.*;

@Entity
public class Subject {

    @Id @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Date date;

    private int count;
}
