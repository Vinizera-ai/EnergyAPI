package com.doctorreborn.hytale.test.energy.unittests.utils;

import org.jspecify.annotations.Nullable;

import com.doctorreborn.hytale.api.energy.v1.EnergyStorage;
import com.doctorreborn.hytale.api.energy.v1.EnergyStorageView;
import com.shailist.hytale.api.transfer.v1.transaction.Transaction;
import com.shailist.hytale.api.transfer.v1.transaction.TransactionContext;

public final class TestEnergyStorageUtil {
    public static long insert(EnergyStorage storage, long maxAmount) {
        return insert(storage, maxAmount, null);
    }

    public static long insert(EnergyStorage storage, long maxAmount, @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.insert(maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static long extract(EnergyStorage storage, long maxAmount) {
        return extract(storage, maxAmount, null);
    }

    public static long extract(EnergyStorage storage, long maxAmount, @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.extract(maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static long extract(EnergyStorageView storageView, long maxAmount) {
        return extract(storageView, maxAmount, null);
    }

    public static long extract(EnergyStorageView storageView, long maxAmount,
            @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storageView.extract(maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }

    public static <S extends Object & EnergyStorage & EnergyStorageView> long extract(S storage, long maxAmount) {
        return extract(storage, maxAmount, null);
    }

    public static <S extends Object & EnergyStorage & EnergyStorageView> long extract(S storage, long maxAmount,
            @Nullable TransactionContext transaction) {
        try (Transaction nestedTransaction = Transaction.openNested(transaction)) {
            var result = storage.extract(maxAmount, nestedTransaction);
            nestedTransaction.commit();
            return result;
        }
    }
}
