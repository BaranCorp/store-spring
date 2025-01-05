package pl.wsei.storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsei.storespring.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
