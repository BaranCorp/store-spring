package pl.wsei.storespring.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsei.storespring.dto.BasketDTO;
import pl.wsei.storespring.exception.ResourceNotFoundException;
import pl.wsei.storespring.model.Basket;
import pl.wsei.storespring.model.Item;
import pl.wsei.storespring.repository.BasketRepository;
import pl.wsei.storespring.repository.ItemRepository;

import java.util.List;

@Service
public class BasketService {

	private BasketRepository basketRepository;
	private ItemRepository itemRepository;

	@Autowired
	public BasketService(
			BasketRepository basketRepository,
			ItemRepository itemRepository
	) {
		this.basketRepository = basketRepository;
		this.itemRepository = itemRepository;
	}

	public Basket createBasket(BasketDTO basketDto) {
		Basket basket = new Basket(
			basketDto.getId(),
			basketDto.getItems().stream()
					.map(it -> new Item(it.getId(), it.getName(), it.getQuantity(), it.getPrice()))
					.toList()
		);
		itemRepository.saveAll(basket.getItems());
		return basketRepository.save(basket);
	}

	public void deleteBasket(Long id) {
		Basket basket = getById(id);
		itemRepository.deleteAll(basket.getItems());
		basketRepository.delete(basket);
	}

	public Basket getById(Long id) {
		return basketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Basket not found"));
	}

	public Basket updateItem(
		Long id,
		UpdateItemCommand command
	) {
		Basket basket = getById(id).updateItem(command);
		itemRepository.saveAll(basket.getItems());
		return basketRepository.save(basket);
	}

	public Basket addItem(Long id, AddItemCommand command) {
		Basket updatedBasket = getById(id).addItem(command);
		itemRepository.saveAll(updatedBasket.getItems());
		return basketRepository.save(updatedBasket);
	}

	public Basket removeItem(
		Long id,
		RemoveItemCommand command
	) {
		Pair<Basket, Item> basketWithItemToRemove = getById(id).removeItem(command);
		itemRepository.delete(basketWithItemToRemove.getRight());
		return basketRepository.save(basketWithItemToRemove.getLeft());
	}

	public Basket clearBasket(Long id) {
		Pair<Basket, List<Item>> basketWithItemsToDelete = getById(id).clearBasket();
		itemRepository.deleteAll(basketWithItemsToDelete.getRight());
		return basketRepository.save(basketWithItemsToDelete.getLeft());
	}

	public Integer overallPrice(Long id) {
		return getById(id).overallPrice();
	}

	public record UpdateItemCommand(
		Long itemId,
		Integer quantity,
		Integer price
	) {}

	public record AddItemCommand(
		String name,
		Integer quantity,
		Integer price
	) {}

	public record RemoveItemCommand(
		Long itemId
	) {}

}