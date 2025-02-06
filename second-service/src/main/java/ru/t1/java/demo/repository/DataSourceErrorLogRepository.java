package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.general.model.DataSourceErrorLog;

public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, Long> {
}
