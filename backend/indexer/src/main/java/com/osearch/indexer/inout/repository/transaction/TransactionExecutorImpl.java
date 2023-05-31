package com.osearch.indexer.inout.repository.transaction;

import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Log4j2
@RequiredArgsConstructor
public class TransactionExecutorImpl implements TransactionExecutor {
    private final TransactionTemplate transactionTemplate;

    public <T> T inTransaction(Supplier<T> supplier) {
        log.info("Executing transaction");
        return transactionTemplate.execute(status -> supplier.get());
    }
}
