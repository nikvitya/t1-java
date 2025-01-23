package ru.t1.java.demo.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.t1.java.demo.dto.dataSourceErrorLog.DataSourceErrorLogDto;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.model.DataSourceErrorLog;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataSourceErrorLogMapper {
    DataSourceErrorLogDto toDto(DataSourceErrorLog dataSourceErrorLog);
    DataSourceErrorLog toLog(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
