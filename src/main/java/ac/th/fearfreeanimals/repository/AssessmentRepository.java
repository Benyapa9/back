package ac.th.fearfreeanimals.repository;

import ac.th.fearfreeanimals.entity.Assessments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessments, Long> {
}