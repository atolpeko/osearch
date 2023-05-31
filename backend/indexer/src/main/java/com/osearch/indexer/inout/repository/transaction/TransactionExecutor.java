package com.osearch.indexer.inout.repository.transaction;

import java.util.function.Supplier;

/**
 * Used to run code in transaction.
 */
public interface TransactionExecutor {

    /**
     * Run code in transaction.
     *
     * @param supplier  supplier to execute
     *
     * @return supplier.get()
     */
    <T> T inTransaction(Supplier<T> supplier);
}
