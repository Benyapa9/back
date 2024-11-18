package ac.th.fearfreeanimals.repository;


import ac.th.fearfreeanimals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    
    Optional<User> findByUsername(String username);


    
    Optional<User> findByAccessCode(String accessCode);

    boolean existsByUsername(String username);

    long countByRoleName(String roleName);

   
    boolean existsByAccessCode(String accessCode);
    
    

}

