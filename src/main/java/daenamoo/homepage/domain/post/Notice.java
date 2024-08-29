package daenamoo.homepage.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("N")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends Post {
    private boolean isNotice;

    @Enumerated(EnumType.STRING)
    private PostCategory category;
}


