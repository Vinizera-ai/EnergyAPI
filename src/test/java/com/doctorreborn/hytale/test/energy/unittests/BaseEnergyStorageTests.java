/*
 * Copyright (C) 2026 DoctorReborn and contributors
 *
 * This file is part of Energy API for Hytale.
 *
 * Energy API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Energy API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Energy API. If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2025 Shai List and contributors
 * Licensed under the MIT license. See LICENSE-SHAILIST file in the project root for details.
 *
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 */

package com.doctorreborn.hytale.test.energy.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.doctorreborn.hytale.api.energy.v1.EnergyStorage;
import com.doctorreborn.hytale.api.energy.v1.EnergyStorageUtil;
import com.doctorreborn.hytale.api.energy.v1.EnergyStorageView;
import com.doctorreborn.hytale.api.energy.v1.base.FilteringEnergyStorage;
import com.doctorreborn.hytale.api.energy.v1.base.SingleBufferEnergyStorage;
import com.doctorreborn.hytale.test.energy.unittests.utils.TestEnergyStorageUtil;

public class BaseEnergyStorageTests {
    @Test
    public void testSingleBufferEnergyStorage() {
        SingleBufferEnergyStorage storage = new SingleBufferEnergyStorage(10L);

        assertEquals(0L, storage.getAmount());
        assertEquals(10L, storage.getCapacity());

        // Insertion into an empty storage should succeed.
        assertEquals(10L, TestEnergyStorageUtil.insert(storage, 10L));
        assertEquals(10L, storage.getAmount());

        assertTrue(EnergyStorageUtil.findExtractableEnergy(storage, null));
        assertEquals(10L, EnergyStorageUtil.findExtractableAmount(storage, null));

        // Extraction from a non-empty storage should succeed.
        assertEquals(10L, TestEnergyStorageUtil.extract(storage, 10L));
        assertEquals(0L, storage.getAmount());

        // Extraction from an empty storage should fail.
        assertEquals(0L, TestEnergyStorageUtil.extract(storage, 10L));
        assertEquals(0L, storage.getAmount());

        assertFalse(EnergyStorageUtil.findExtractableEnergy(storage, null));
        assertEquals(0L, EnergyStorageUtil.findExtractableAmount(storage, null));
    }

    @Test
    public void testFilteringEnergyStorage() {
        SingleBufferEnergyStorage storage = new SingleBufferEnergyStorage(10L);
        EnergyStorage noLessThanTen = new FilteringEnergyStorage(storage) {
            @Override
            protected boolean canExtract(long amount) {
                return amount >= 10L;
            }

            @Override
            protected boolean canInsert(long amount) {
                return amount >= 10L;
            }
        };

        // Inserting a non filtered amount should fail.
        assertEquals(0L, TestEnergyStorageUtil.insert(noLessThanTen, 5L));
        // Inserting a filtered amount should succeed.
        assertEquals(10L, TestEnergyStorageUtil.insert(noLessThanTen, 10L));

        assertTrue(EnergyStorageUtil.findExtractableEnergy(noLessThanTen, null));
        assertEquals(10L, EnergyStorageUtil.findExtractableAmount(noLessThanTen, null));

        // Extracting a non filtered amount should fail.
        assertEquals(0L, TestEnergyStorageUtil.extract(noLessThanTen, 5L));
        // Extracting filtered amount should succeed.
        assertEquals(10L, TestEnergyStorageUtil.extract(noLessThanTen, 10L));

        assertFalse(EnergyStorageUtil.findExtractableEnergy(noLessThanTen, null));
        assertEquals(0L, EnergyStorageUtil.findExtractableAmount(noLessThanTen, null));
    }

    /**
     * Regression test for <a href="https://github.com/FabricMC/fabric/issues/3414">
     * {@code nonEmptyIterator} not handling views that become empty during
     * iteration correctly</a>.
     */
    @Test
    public void testNonEmptyIteratorWithModifiedView() {
        SingleBufferEnergyStorage storage = new SingleBufferEnergyStorage(10L);

        Iterator<EnergyStorageView> iterator = storage.nonEmptyIterator();
        TestEnergyStorageUtil.insert(storage, 10L);
        // Iterator should have a next element now
        assertTrue(iterator.hasNext());
        assertEquals(storage, iterator.next());

        iterator = storage.nonEmptyIterator();
        TestEnergyStorageUtil.extract(storage, 10L);
        // Iterator should not have a next element...
        assertFalse(iterator.hasNext());
    }
}
