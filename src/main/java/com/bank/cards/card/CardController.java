package com.bank.cards.card;

import com.bank.cards.transaction.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/api/cards")
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/api/cards/{id}")
    public CardDto getCard(@PathVariable long id) {
        return cardService.getCard(id);
    }

    @GetMapping("/api/cards/{id}/details")
    public CardDetailsDto getCardDetails(@PathVariable long id) {
        return cardService.getCardDetails(id);
    }

    @GetMapping("/api/cards/{id}/summary")
    public CardListItemDto getCardSummary(@PathVariable long id) {
        return cardService.getCardSummary(id);
    }

    @GetMapping("/api/cards/{id}/export")
    public CardExportDto getCardExport(@PathVariable long id) {
        return cardService.getCardExport(id);
    }

    @GetMapping("/api/cards/{id}/transactions")
    public List<TransactionDto> getCardTransactions(@PathVariable long id) {
        return cardService.getCardTransactions(id);
    }

    @GetMapping("/api/cards/{id}/transactions/page")
    public Page<TransactionDto> getCardTransactionsPage(@PathVariable long id,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "occurredAt", "id"));
        return cardService.getCardTransactionsPage(id, pageable);
    }

    @GetMapping("/api/cards/{id}/transactions/search")
    public List<TransactionDto> searchCardTransactions(@PathVariable long id,
                                                       @RequestParam LocalDateTime from,
                                                       @RequestParam LocalDateTime to) {
        return cardService.getCardTransactionsBetween(id, from, to);
    }

    @GetMapping("/api/clients/{clientId}/cards")
    public List<CardShortDto> getClientCards(@PathVariable long clientId,
                                             @RequestParam(required = false) CardStatus status) {
        return cardService.getCardShortsByClient(clientId, status);
    }

    @GetMapping("/api/clients/{clientId}/cards-report")
    public ClientCardsReportDto getClientCardsReport(@PathVariable long clientId) {
        return cardService.getCardsReport(clientId);
    }

    @GetMapping("/api/clients/{clientId}/cards/expiring")
    public List<CardDto> getExpiringCards(@PathVariable long clientId,
                                          @RequestParam int days) {
        return cardService.getExpiringCards(clientId, days);
    }

    @PostMapping("/api/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @PutMapping("/api/cards/{id}")
    public CardDto updateCard(@PathVariable long id, @RequestBody UpdateCardRequest request) {
        return cardService.updateCard(id, request);
    }

    @DeleteMapping("/api/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeCard(@PathVariable long id) {
        cardService.closeCard(id);
    }
}
