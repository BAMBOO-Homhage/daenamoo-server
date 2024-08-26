package daenamoo.homepage.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Q")
@Getter
@SuperBuilder
@NoArgsConstructor
public class QandA extends Post{
}
