package io.github.chaosunity.repository;

import io.github.chaosunity.Source;
import io.github.chaosunity.utils.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class InMemorySourceRepository implements ISourceRepository {
    private final Map<String, Source> lookup = new HashMap<>();

    public void register(@NotNull Source source) {
        lookup.put(source.id, source);
    }

    public void register(@NotNull String id, @NotNull String source) {
        lookup.put(id, new Source(id, source));
    }

    @Override
    public Pair<@NotNull Boolean, @Nullable Source> tryGet(@NotNull String id) {
        var lookupResult = lookup.get(id);

        return new Pair<>(lookupResult != null, lookupResult);
    }
}
