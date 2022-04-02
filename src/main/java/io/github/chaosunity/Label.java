package io.github.chaosunity;

import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Label {
    private final @Nullable TextSpan span;
    private final @Nullable Location location;
    private @Nullable Integer length;

    public @NotNull String sourceId;
    public @NotNull String message;
    public @NotNull Ansi.Color color;
    public @Nullable String note;
    public int Priority = 0;

    public Label(@NotNull String sourceId, @NotNull TextSpan span, @NotNull String message) {
        location = null;
        this.span = span;

        this.sourceId = sourceId;
        this.message = message;
        color = Ansi.Color.WHITE;
    }

    public Label(@NotNull String sourceId, @NotNull Location location, @NotNull String message) {
        this.location = location;
        span = null;
        length = 1;

        this.sourceId = sourceId;
        this.message = message;
        color = Ansi.Color.WHITE;
    }

    public Label withLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        this.length = length;
        return this;
    }

    public TextSpan getSourceSpan(Source source) throws ErrataException {
        if (span != null) {
            if (length != null) {
                return new TextSpan(span.start, span.start + length);
            }

            return span;
        }

        if (location == null || length == null) {
            throw new IllegalStateException("Location info for label has not been set");
        }

        return source.GetSourceSpan(location, length);
    }
}
