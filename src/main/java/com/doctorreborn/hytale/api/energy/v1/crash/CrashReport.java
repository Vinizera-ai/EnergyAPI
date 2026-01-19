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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Minimal crash reporting contract used by energy operations.
 */
public final class CrashReport {
    private final Throwable cause;
    private final String title;
    private final List<CrashReportCategory> categories = new ArrayList<>();

    private CrashReport(Throwable cause, String title) {
        this.cause = Objects.requireNonNull(cause, "Cause may not be null");
        this.title = Objects.requireNonNull(title, "Title may not be null");
    }

    public static CrashReport forThrowable(Throwable cause, String title) {
        return new CrashReport(cause, title);
    }

    public CrashReportCategory addCategory(String name) {
        CrashReportCategory category = new CrashReportCategory(name);
        categories.add(category);
        return category;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getTitle() {
        return title;
    }

    public List<CrashReportCategory> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(title);
        builder.append(System.lineSeparator());
        builder.append(cause);
        for (CrashReportCategory category : categories) {
            builder.append(System.lineSeparator());
            builder.append(category);
        }
        return builder.toString();
    }
}
