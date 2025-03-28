package APlusPlus.LeaveDayManagementDemo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoDTO {
    long id;
    String name;
    String email;
    String role;
}
