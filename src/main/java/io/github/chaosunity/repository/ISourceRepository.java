package io.github.chaosunity.repository;

import io.github.chaosunity.Source;
import io.github.chaosunity.utils.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface ISourceRepository permits InMemorySourceRepository {
    Pair<@NotNull Boolean, @Nullable Source> tryGet(@NotNull String id);
}
