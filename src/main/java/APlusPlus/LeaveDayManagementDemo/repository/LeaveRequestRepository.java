package APlusPlus.LeaveDayManagementDemo.repository;

import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

}
