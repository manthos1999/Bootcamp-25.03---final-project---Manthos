package bootcamp_2025_03_manthos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampErrorEntity {

    private String message;
    private Integer errorCode;
    private String errorDescription;

}