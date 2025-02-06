package ru.t1.java.firstService.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Component;
import ru.t1.java.firstService.dto.transaction.NewDataSourceErrorLogDto;
import ru.t1.java.general.kafka.KafkaDataSourceExceptionProducer;
import ru.t1.java.general.service.DataSourceErrorLogService;

import java.util.Arrays;


@Aspect
@Component
@RequiredArgsConstructor
public class LogDataSourceErrorAspect {

    private final DataSourceErrorLogService dataSourceErrorLogService;
    private final KafkaDataSourceExceptionProducer kafkaDataSourceExceptionProducer;

    @AfterThrowing(
            pointcut = "@annotation(ru.t1.java.general.annotation.LogDataSourceError)",
            throwing = "ex")
    public void logging(JoinPoint joinPoint, Throwable ex) {
        NewDataSourceErrorLogDto newDataSourceErrorLogDto = NewDataSourceErrorLogDto.builder()
                .stackTrace(Arrays.toString(ex.getStackTrace()).substring(0, 255))
                .message(ex.getMessage())
                .signature(joinPoint.getSignature().getName())
                .build();

        try {
            kafkaDataSourceExceptionProducer.send(newDataSourceErrorLogDto);
        } catch (KafkaException e) {
            dataSourceErrorLogService.create(newDataSourceErrorLogDto);
        }
    }
}
