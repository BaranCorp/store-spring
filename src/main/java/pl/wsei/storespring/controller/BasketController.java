package pl.wsei.storespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pl.wsei.storespring.dto.BasketDTO;
import pl.wsei.storespring.model.Basket;
import pl.wsei.storespring.service.BasketService;

@Tag(name = "Basket", description = "Basket management APIs")
@RestController
@RequestMapping("/api/baskets")
public class BasketController {

	private final BasketService basketService;

	@Autowired
	public BasketController(BasketService basketService) {
		this.basketService = basketService;
	}

	@Operation(summary = "Get basket by ID")
	@GetMapping("/{id}")
	public ResponseEntity<BasketDTO> getBasketById(@PathVariable Long id) {
		Basket basket = basketService.getById(id);
		return ResponseEntity.ok(BasketDTO.createFrom(basket));
	}

	@Operation(summary = "Create a new basket")
	@PostMapping
	public ResponseEntity<Basket> createBasket(@RequestBody BasketDTO basket) {
		Basket createdBasket = basketService.createBasket(basket);
		return ResponseEntity.status(201).body(createdBasket);
	}

	@Operation(summary = "Delete a basket")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
		basketService.deleteBasket(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/items/change-item-quantity-command")
	public ResponseEntity<Basket> updateItem(
		@PathVariable Long id,
		@RequestBody BasketService.UpdateItemCommand command
	) {
		return ResponseEntity.ok(
			basketService.updateItem(
				id,
				command
			)
		);
	}

	@PutMapping("/{id}/items/add-item-command")
	public ResponseEntity<Basket> addItem(
		@PathVariable Long id,
		@RequestBody BasketService.AddItemCommand command
	) {
		return ResponseEntity.ok(
			basketService.addItem(
				id,
				command
			)
		);
	}

	@PutMapping("/{id}/items/remove-item-command")
	public ResponseEntity<Basket> removeItem(
		@PathVariable Long id,
		@RequestBody BasketService.RemoveItemCommand command
	) {
		return ResponseEntity.ok(
			basketService.removeItem(
				id,
				command
			)
		);
	}

	@PutMapping("/{id}/clear-basket-command")
	public ResponseEntity<Basket> clearBasket(
		@PathVariable Long id
	) {
		return ResponseEntity.ok(basketService.clearBasket(id));
	}

}