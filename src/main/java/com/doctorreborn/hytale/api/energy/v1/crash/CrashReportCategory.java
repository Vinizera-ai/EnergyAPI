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

package com.doctorreborn.hytale.api.energy.v1.crash;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A category within a crash report that stores structured details.
 */
public final class CrashReportCategory {
    private final String name;
    private final Map<String, Object> details = new LinkedHashMap<>();

    CrashReportCategory(String name) {
        this.name = Objects.requireNonNull(name, "Name may not be null");
    }

    public CrashReportCategory setDetail(String key, Object value) {
        details.put(Objects.requireNonNull(key, "Key may not be null"), value);
        return this;
    }

    public CrashReportCategory setDetail(String key, Supplier<?> supplier) {
        details.put(Objects.requireNonNull(key, "Key may not be null"), supplier);
        return this;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getDetails() {
        return Map.copyOf(details);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        for (Map.Entry<String, Object> entry : details.entrySet()) {
            builder.append(System.lineSeparator());
            builder.append(" - ");
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(renderValue(entry.getValue()));
        }
        return builder.toString();
    }

    private static String renderValue(Object value) {
        if (value instanceof Supplier<?> supplier) {
            try {
                return Objects.toString(supplier.get());
            } catch (Exception e) {
                return "Error while rendering detail: " + e.getMessage();
            }
        }
        return Objects.toString(value);
    }
}
