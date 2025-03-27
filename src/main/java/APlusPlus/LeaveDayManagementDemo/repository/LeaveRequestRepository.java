package APlusPlus.LeaveDayManagementDemo.repository;

import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    @Query(value = "SELECT * FROM leave_request l WHERE l.start_date >= :startDate AND l.start_date <= :endDate", nativeQuery = true)
    Page<LeaveRequest> getAllByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query(value = "SELECT * FROM leave_request l WHERE l.user_id = :userId", nativeQuery = true)
    Page<LeaveRequest> getAllByUserId(long userId, Pageable pageable);
}
