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

import com.doctorreborn.hytale.api.energy.v1.base.SingleBufferEnergyStorage;
import com.doctorreborn.hytale.api.energy.v1.base.SingleBufferEnergyProducer;
import com.doctorreborn.hytale.test.energy.unittests.utils.TestEnergyStorageUtil;

public class BaseEnergyProducerTests {
    @Test
    public void testSingleBufferEnergyProducer() {
        SingleBufferEnergyStorage storage = new SingleBufferEnergyStorage(10L);
        SingleBufferEnergyProducer producer = new SingleBufferEnergyProducer(storage, 5L);

        assertEquals(0L, storage.getAmount());
        assertEquals(10L, storage.getCapacity());

        assertEquals(5L, producer.produce());
        assertEquals(5L, producer.getLastProduced());

        // Extraction from a non-empty storage should succeed.
        assertEquals(5L, TestEnergyStorageUtil.extract(storage, 5L));
        assertEquals(0L, storage.getAmount());
    }
}
