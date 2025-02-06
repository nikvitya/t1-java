package ru.t1.java.general.dto.dataSourceErrorLogDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataSourceErrorLogDto {
    Long id;
    String stackTrace;
    String message;
    String signature;
}
