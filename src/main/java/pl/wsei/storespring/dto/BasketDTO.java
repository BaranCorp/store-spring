package pl.wsei.storespring.dto;

import pl.wsei.storespring.model.Basket;
import java.util.List;
import java.util.stream.Collectors;

public class BasketDTO {
	
	Long id;
	
	List<ItemDTO> items;

	Integer discountPercent;

	public static BasketDTO createFrom(Basket basket) {
		BasketDTO basketDTO = new BasketDTO();
		basketDTO.id = basket.getId();
		basketDTO.items = basket.getItems().stream()
				.map(it -> {
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setId(it.getId());
					itemDTO.setName(it.getName());
					itemDTO.setQuantity(it.getQuantity());
					itemDTO.setPrice(it.getPrice());
					return itemDTO;
				})
				.collect(Collectors.toList());
		return basketDTO;
	}

	public static class ItemDTO {

		Long id;

		String name;

		Integer quantity;

		Integer price;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Integer getPrice() {
			return price;
		}

		public void setPrice(Integer price) {
			this.price = price;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}
}
