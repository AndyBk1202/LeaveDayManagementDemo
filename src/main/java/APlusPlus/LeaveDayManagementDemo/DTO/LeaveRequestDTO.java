package APlusPlus.LeaveDayManagementDemo.DTO;

import APlusPlus.LeaveDayManagementDemo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDTO {
    long id;
    LocalDate startDate;
    LocalDate endDate;
    String reason;
    String status;
    String userEmail;
}
