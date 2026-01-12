package com.doctorreborn.hytale.test.energy.unittests.utils;

import org.jspecify.annotations.Nullable;

import com.doctorreborn.hytale.api.energy.v2.Energy;
import com.shailist.hytale.api.transfer.v1.storage.Storage;
import com.shailist.hytale.api.transfer.v1.storage.StorageView;
import com.shailist.hytale.api.transfer.v1.transaction.Transaction;
import com.shailist.hytale.api.transfer.v1.transaction.TransactionContext;

public final class TestEnergyStorageUtil {
    public static long insert(Storage<Energy> storage, long maxAmount) {
        return insert(storage, maxAmount, null);
    }

    public static long insert(Storage<Energy> storage, long maxAmount, @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.insert(Energy.INSTANCE, maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static long extract(Storage<Energy> storage, long maxAmount) {
        return extract(storage, maxAmount, null);
    }

    public static long extract(Storage<Energy> storage, long maxAmount, @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.extract(Energy.INSTANCE, maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static long extract(StorageView<Energy> storageView, long maxAmount) {
        return extract(storageView, maxAmount, null);
    }

    public static long extract(StorageView<Energy> storageView, long maxAmount,
            @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storageView.extract(Energy.INSTANCE, maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static <S extends Object & Storage<Energy> & StorageView<Energy>> long extract(S storage, long maxAmount) {
        return extract(storage, maxAmount, null);
    }

    public static <S extends Object & Storage<Energy> & StorageView<Energy>> long extract(S storage, long maxAmount,
            @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.extract(Energy.INSTANCE, maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }
}
