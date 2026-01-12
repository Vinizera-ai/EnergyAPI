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
 */

package com.doctorreborn.hytale.test.energy.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.doctorreborn.hytale.api.energy.v2.base.SingleBufferEnergyStorage;
import com.doctorreborn.hytale.api.energy.v2.base.BufferedEnergyConsumer;
import com.doctorreborn.hytale.test.energy.unittests.utils.TestEnergyStorageUtil;

public class BaseEnergyConsumerTests {
    @Test
    public void testSingleBufferEnergyConsumer() {
        SingleBufferEnergyStorage storage = new SingleBufferEnergyStorage(10L);
        BufferedEnergyConsumer<SingleBufferEnergyStorage> consumer = new BufferedEnergyConsumer<>(storage, 5L);

        assertEquals(0L, storage.getAmount());
        assertEquals(10L, storage.getCapacity());

        // Insertion into an empty storage should succeed.
        assertEquals(10L, TestEnergyStorageUtil.insert(storage, 10L));
        assertEquals(10L, storage.getAmount());

        assertEquals(5L, consumer.consume());
        assertEquals(5L, consumer.getLastConsumed());
    }
}
