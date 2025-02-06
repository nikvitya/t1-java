package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import dto.dataSourceErrorLog.DataSourceErrorLogDto;
import dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.general.model.DataSourceErrorLog;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataSourceErrorLogMapper {
    DataSourceErrorLogDto toDto(DataSourceErrorLog dataSourceErrorLog);
    DataSourceErrorLog toLog(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
