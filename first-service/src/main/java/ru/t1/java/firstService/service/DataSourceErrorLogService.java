package ru.t1.java.firstService.service;

import dto.dataSourceErrorLog.NewDataSourceErrorLogDto;

public interface DataSourceErrorLogService {
    void create(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
