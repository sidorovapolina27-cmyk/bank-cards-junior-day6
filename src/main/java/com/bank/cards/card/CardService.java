package com.bank.cards.card;

import com.bank.cards.client.ClientNotFoundException;
import com.bank.cards.client.ClientRepository;
import com.bank.cards.transaction.Transaction;
import com.bank.cards.transaction.TransactionDto;
import com.bank.cards.transaction.TransactionMapper;
import com.bank.cards.transaction.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    public CardService(CardRepository cardRepository,
                       ClientRepository clientRepository,
                       TransactionRepository transactionRepository,
                       CardMapper cardMapper,
                       TransactionMapper transactionMapper) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
        this.cardMapper = cardMapper;
        this.transactionMapper = transactionMapper;
    }

    public CardDto getCard(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        return cardMapper.toDto(card);
    }

    public CardDetailsDto getCardDetails(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        var client = clientRepository.findById(card.getClientId())
                .orElseThrow(() -> new CardNotFoundException(id));
        return cardMapper.toDetailsDto(card, client);
    }

    public CardListItemDto getCardSummary(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        return cardMapper.toListItemDto(card);
    }

    public CardExportDto getCardExport(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        return cardMapper.toExportDto(card);
    }

    public ClientCardsReportDto getCardsReport(long clientId) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        List<CardListItemDto> cards = cardMapper.toListItemDtoList(
                cardRepository.findByClientIdOrderByIdAsc(clientId));
        int totalActive = (int) cards.stream()
                .filter(card -> card.getStatus() == CardStatus.ACTIVE)
                .count();
        return new ClientCardsReportDto(clientId, client.getFullName(), cards, totalActive);
    }

    public List<CardDto> getCardsByClient(long clientId) {
        return getCardsByClient(clientId, null);
    }

    public List<CardDto> getCardsByClient(long clientId, CardStatus status) {
        List<Card> cards = (status == null)
                ? cardRepository.findByClientIdOrderByIdAsc(clientId)
                : cardRepository.findByClientIdAndStatusOrderByIdAsc(clientId, status);
        return cardMapper.toDtoList(cards);
    }

    public List<CardShortDto> getCardShortsByClient(long clientId, CardStatus status) {
        List<Card> cards = (status == null)
                ? cardRepository.findByClientIdOrderByIdAsc(clientId)
                : cardRepository.findByClientIdAndStatusOrderByIdAsc(clientId, status);
        return cardMapper.toShortDtoList(cards);
    }

    public List<CardDto> getExpiringCards(long clientId, int days) {
        LocalDate today = LocalDate.now();
        return cardMapper.toDtoList(
                cardRepository.findExpiringCards(clientId, today, today.plusDays(days)));
    }

    public List<CardDto> getAllCards() {
        return cardMapper.toDtoList(cardRepository.findAllByOrderByCreatedAtDescIdDesc());
    }

    public List<TransactionDto> getCardTransactions(long cardId) {
        requireCard(cardId);
        return transactionMapper.toDtoList(
                transactionRepository.findByCardIdOrderByOccurredAtDescIdDesc(cardId));
    }

    public Page<TransactionDto> getCardTransactionsPage(long cardId, Pageable pageable) {
        requireCard(cardId);
        Page<Transaction> page = transactionRepository.findByCardId(cardId, pageable);
        return new PageImpl<>(transactionMapper.toDtoList(page.getContent()), pageable, page.getTotalElements());
    }

    public List<TransactionDto> getCardTransactionsBetween(long cardId, LocalDateTime from, LocalDateTime to) {
        requireCard(cardId);
        return transactionMapper.toDtoList(
                transactionRepository.findByCardIdAndOccurredAtBetweenOrderByOccurredAtDescIdDesc(cardId, from, to));
    }

    public CardDto createCard(CreateCardRequest request) {
        Card card = new Card();
        card.setClientId(request.getClientId());
        card.setPan(request.getPan());
        card.setCardHolderName(request.getCardHolderName());
        card.setCardType(request.getCardType());
        card.setPaymentSystem(request.getPaymentSystem());
        card.setValidFrom(request.getValidFrom());
        card.setValidTo(request.getValidTo());
        card.setProductName(request.getProductName());
        card.setStatus(CardStatus.NEW);
        card.setCreatedAt(LocalDateTime.now());
        return cardMapper.toDto(cardRepository.save(card));
    }

    public CardDto updateCard(long id, UpdateCardRequest request) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        card.setProductName(request.getProductName());
        card.setValidTo(request.getValidTo());
        return cardMapper.toDto(cardRepository.save(card));
    }

    public void closeCard(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        if (card.getStatus() == CardStatus.CLOSED) {
            throw new CardOperationException("Карта " + id + " уже закрыта");
        }
        card.setStatus(CardStatus.CLOSED);
        cardRepository.save(card);
    }

    private void requireCard(long cardId) {
        cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }
}
