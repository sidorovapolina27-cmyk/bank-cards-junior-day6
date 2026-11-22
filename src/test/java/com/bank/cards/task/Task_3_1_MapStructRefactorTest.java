package com.bank.cards.task;

import com.bank.cards.card.CardMapper;
import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Задание 3.1. Переход с ручного маппера на MapStruct.
 */
@Tag("task-3-1")
@DisplayName("Задание 3.1: CardMapper — MapStruct")
class Task_3_1_MapStructRefactorTest extends BaseIntegrationTest {

    @Autowired
    private CardMapper cardMapper;

    @Test
    @DisplayName("Сгенерирован класс CardMapperImpl")
    void generatedImplClassExists() throws Exception {
        Class<?> impl = Class.forName("com.bank.cards.card.CardMapperImpl");

        assertThat(impl).isNotNull();
    }

    @Test
    @DisplayName("Бин CardMapper — не ручной класс, а сгенерированная реализация")
    void mapperBeanIsGeneratedImpl() {
        String beanClassName = cardMapper.getClass().getName();

        assertThat(beanClassName)
                .as("CardMapper должен стать интерфейсом: бином должен быть CardMapperImpl")
                .isEqualTo("com.bank.cards.card.CardMapperImpl");
    }
}
