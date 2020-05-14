package com.example.trello.services.impl;

import com.example.trello.dtos.CreateCardDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.models.CardEntity;
import com.example.trello.models.ListEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.repos.CardRepo;
import com.example.trello.services.CardService;
import com.example.trello.services.ListService;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    private ListService listService;
    private CardRepo cardRepo;
    private UserService userService;

    @Autowired
    public CardServiceImpl(
            CardRepo cardRepo,
            ListService listService,
            UserService userService
    ) {
        this.listService = listService;
        this.cardRepo = cardRepo;
        this.userService = userService;
    }

    public CardEntity create(Long listId, CreateCardDto createCardDto, Long userId) {
        ListEntity listEntity = listService.findForUser(listId, userId);
        CardEntity card = CardEntity.builder()
                .archived(false)
                .list(listEntity)
                .title(createCardDto.getTitle())
                .deadline(createCardDto.getDeadline())
                .build();

        log.info("Card was created in list with id " + listEntity.getId());
        return cardRepo.save(card);
    }

    public CardEntity archive(Long cardId, Long userId) {
        CardEntity card = cardRepo.findForUser(cardId, userId).orElseThrow(() -> new NotFoundException("Card not found"));
        card.setArchived(true);
        log.info("Card with id " + card.getId() + " was archived");
        return cardRepo.save(card);
    }

    public CardEntity move(Long cardId, Long listId, Long userId) {
        CardEntity card = cardRepo.findForUser(cardId, userId).orElseThrow(() -> new NotFoundException("Card not found"));
        ListEntity list = listService.findForUser(listId, userId);
        card.setList(list);
        log.info("card " + card.getId() + " was moved to list " + list.getId());
        return cardRepo.save(card);
    }
    public void deleteOne(Long cardId, Long userId) {
        CardEntity card = cardRepo.findForUser(cardId, userId).orElseThrow(() -> new NotFoundException("Card not found"));
        cardRepo.delete(card);
        log.info("Card with id " + card.getId() + " was deleted");
    }

    public CardEntity findForUser(Long cardId, Long userId) {
        return cardRepo.findForUser(cardId, userId).orElseThrow(() -> new NotFoundException("Card not found"));
    }
}
