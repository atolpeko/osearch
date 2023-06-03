package com.osearch.indexer.inout.repository.transaction;

import java.util.function.Supplier;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class TransactionExecutorImpl implements TransactionExecutor {

    @Transactional
    public <T> T inTransaction(Supplier<T> supplier) {
        log.debug("Executing in transaction");
        return supplier.get();
    }
}
