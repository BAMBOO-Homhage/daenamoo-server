package daenamoo.homepage.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteRegisterRequestDto {

    @NotNull
    private Long registerId;
}
