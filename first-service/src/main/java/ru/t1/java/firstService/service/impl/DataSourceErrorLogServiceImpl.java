package ru.t1.java.firstService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.general.repository.DataSourceErrorLogRepository;
import ru.t1.java.firstService.service.DataSourceErrorLogService;
import ru.t1.java.general.util.DataSourceErrorLogMapper;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DataSourceErrorLogServiceImpl implements DataSourceErrorLogService {
    private final DataSourceErrorLogRepository repository;
    private final DataSourceErrorLogMapper mapper;

    @Override
    public void create(NewDataSourceErrorLogDto newDataSourceErrorLogDto) {
        mapper.toDto(repository.save(mapper.toLog(newDataSourceErrorLogDto)));
    }



}
