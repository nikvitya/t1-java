package ru.t1.java.demo.kafka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties
public class DefaultKafkaConfig {
    @Value("${t1.kafka.consumer.group-id}")
    String groupId;

    @Value("${t1.kafka.bootstrap.server}")
    protected String servers;

    @Value("${t1.kafka.session.timeout.ms:45000}")
    protected String sessionTimeout;

    @Value("${t1.kafka.max.partition.fetch.bytes:300000}")
    protected String maxPartitionFetchBytes;

    @Value("${t1.kafka.max.poll.records:1}")
    protected String maxPollRecords;

    @Value("${t1.kafka.max.poll.interval.ms:300000}")
    protected String maxPollIntervalsMs;

    @Value("${t1.kafka.consumer.heartbeat.interval}")
    protected String heartbeatInterval;

    @Value("${t1.kafka.topic.metric}")
    protected String metricTopic;

    @Value("${t1.kafka.topic.account}")
    protected String accountTopic;

    @Value("${t1.kafka.topic.transaction}")
    protected String transactionTopic;

    @Value("${t1.kafka.topic.client_id_registered}")
    protected String clientRegisteredTopic;

    @Value("${t1.kafka.topic.transactions.accepted}")
    private String transactionAcceptedTopic;

    @Value("${t1.kafka.topic.transactions.result}")
    private String transactionResultTopic;

    @Value("${t1.kafka.limit.transactionCountPerTime:5}")
    private Long transactionCountPerTime;

    @Value("${t1.kafka.limit.transactionTimeIntervalMs:10000}")
    private Long transactionTimeIntervalMs;
}
