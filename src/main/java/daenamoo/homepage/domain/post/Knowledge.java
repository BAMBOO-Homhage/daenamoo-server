package daenamoo.homepage.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("K")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Knowledge extends Post{
    private String category;
}
