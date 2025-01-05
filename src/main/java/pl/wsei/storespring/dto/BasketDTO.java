package pl.wsei.storespring.dto;

import pl.wsei.storespring.model.Basket;
import java.util.List;
import java.util.stream.Collectors;

public class BasketDTO {
	
	Long id;
	
	List<ItemDTO> items;

	public static BasketDTO createFrom(Basket basket) {
		BasketDTO basketDTO = new BasketDTO();
		basketDTO.id = basket.getId();
		basketDTO.items = basket.getItems().stream()
				.map(it -> {
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setId(it.getId());
					itemDTO.setName(it.getName());
					itemDTO.setQuantity(it.getQuantity());
					return itemDTO;
				})
				.collect(Collectors.toList());
		return basketDTO;
	}

	public static class ItemDTO {

		Long id;

		String name;

		Integer quantity;

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
}
