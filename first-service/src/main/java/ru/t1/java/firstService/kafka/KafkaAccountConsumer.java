package ru.t1.java.firstService.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import dto.account.NewAccountDto;
import ru.t1.java.general.service.AccountService;

import java.util.List;


@Component
@RequiredArgsConstructor
public class KafkaAccountConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = {"${t1.kafka.topic.account}"},
            containerFactory = "kafkaAccountListenerContainerFactory")
    public void listener(@Payload List<NewAccountDto> newAccountDto,
                         Acknowledgment ack) {
        try {
            newAccountDto.forEach(accountService::create);
        } finally {
            ack.acknowledge();
        }
    }
}