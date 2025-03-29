package APlusPlus.LeaveDayManagementDemo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    long id;
    String name;
    String email;
    int leaveDays;
}
