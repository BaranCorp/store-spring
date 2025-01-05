package pl.wsei.storespring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import org.apache.commons.lang3.tuple.Pair;
import pl.wsei.storespring.exception.ResourceNotFoundException;
import pl.wsei.storespring.service.BasketService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Basket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	@JoinColumn(name = "basket_id")
	private List<Item> items;

	private Integer discountPercent;

	public Basket() {}

	public Basket(
		Long id,
		List<Item> items
	) {
		this.id = id;
		this.items = items;
		this.discountPercent = 0;
	}

	public Basket updateItem(BasketService.UpdateItemCommand command) {
		findItemById(command.itemId())
			.map(it -> {
				it.setQuantity(command.quantity());
				it.setPrice(command.price());
				return it;
			})
			.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + command.itemId() + " was not found"));
		return this;
	}

	public Basket addItem(BasketService.AddItemCommand command) {
		throwExceptionWhenItemIsAlreadyInBasket(command.name());

		Item item = new Item(
			command.name(),
			command.quantity(),
			command.price()
		);

		items.add(item);
		return this;
	}

	private void throwExceptionWhenItemIsAlreadyInBasket(String name) {
		boolean itemExists = items.stream()
				.anyMatch(it -> it.getName().equals(name));

		if (itemExists) {
			throw new IllegalArgumentException("Item with name: " + name + " already exists");
		}
	}

	public Pair<Basket, Item> removeItem(BasketService.RemoveItemCommand command) {
		Item item = findItemById(command.itemId())
				.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + command.itemId() + " was not found"));
		items.remove(item);
		return Pair.of(this, item);
	}

	public Pair<Basket, List<Item>> clearBasket() {
		List<Item> itemsToDelete = items;
		items = Collections.emptyList();
		return Pair.of(this, itemsToDelete);
	}

	Optional<Item> findItemById(Long id) {
		return items.stream()
				.filter(item -> item.getId().equals(id))
				.findFirst();
	}

	public Integer overallPrice() {
		Integer overallPrice = items.stream()
				.map(it -> it.getPrice() * it.getQuantity())
				.reduce(Integer::sum)
				.orElse(0);
		if (discountPercent > 0) {
			int discount = (int) (overallPrice * ((double) discountPercent / 100));
			return overallPrice - discount;
		}
		return overallPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	protected void setItems(List<Item> items) {
		this.items = items;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		Basket basket = (Basket) o;
		return Objects.equals(id, basket.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
