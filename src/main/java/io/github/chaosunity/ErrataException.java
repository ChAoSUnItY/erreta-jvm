package io.github.chaosunity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ErrataException extends Exception {
    public Map<String, @Nullable Object> context = new HashMap<>();

    public ErrataException() {
        super();
    }

    public ErrataException(String message) {
        super(message);
    }

    public ErrataException(@Nullable String message, @Nullable Exception innerException) {
        super(message, innerException);
    }

    public ErrataException withContext(@NotNull String key, @Nullable Object value) {
        context.put(key, value);
        return this;
    }
}