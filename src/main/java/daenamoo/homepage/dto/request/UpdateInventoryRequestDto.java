package daenamoo.homepage.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class UpdateInventoryRequestDto {

    private int week;
    private String content;
}
