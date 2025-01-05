package pl.wsei.storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsei.storespring.model.Basket;
import pl.wsei.storespring.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
