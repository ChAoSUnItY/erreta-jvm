package io.github.chaosunity;

import io.github.chaosunity.render.LineRange;
import io.github.chaosunity.utils.Triple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Source {
    public String id;
    public String name;
    public List<TextLine> lines;
    public int length;
    public String text;

    public Source(@NotNull String id, @NotNull String name, @NotNull String text) {
        this.id = id;
        this.name = name;
        lines = TextLine.split(text);
        length = lines.stream().mapToInt(textLine -> textLine.length + textLine.lineBreak.length).sum();
        this.text = text;
    }

    public Source(@NotNull String id, @NotNull String text) {
        this(id, id, text);
    }

    @Contract("_ -> new")
    public @NotNull Triple<TextLine, Integer, Integer> getLineOffset(int offset) throws ErrataException {
        if (offset < 0) {
            throw new ErrataException("Offset cannot be less than 0")
                    .withContext("Offset", offset);
        }

        if (offset > length) {
            throw new ErrataException("Offset exceeded the source length")
                    .withContext("Offset", offset)
                    .withContext("Source length", length);
        }

        var lineIndex = getLineIndex(offset);
        var line = lines.get(lineIndex);
        var columnIndex = offset - line.offset;

        return new Triple<>(line, lineIndex, columnIndex);
    }

    public @NotNull LineRange getLineRange(@NotNull TextSpan span) throws ErrataException {
        var start = getLineOffset(span.start).second();
        var end = getLineOffset(Math.max(span.start, span.end)).second();
        return new LineRange(start, end);
    }

    public TextSpan GetSourceSpan(@NotNull Location location, int length) throws ErrataException {
        var row = location.row - 1;
        var column = location.column - 1;

        if (row >= lines.size()) {
            throw new ErrataException("Label row exceeded number of lines")
                    .withContext("Row", row)
                    .withContext("Column", column)
                    .withContext("Line count", lines.size());
        }

        var line = lines.get(row);
        if (column > line.length - 1) {
            throw new ErrataException("Label column cannot start at the end of the line")
                    .withContext("Row", row)
                    .withContext("Column", column)
                    .withContext("Current line length", line.length)
                    .withContext("Line count", lines.size());
        }

        if (location.isRow) {
            return line.span;
        }

        return new TextSpan(line.offset + column, line.offset + column + length);
    }

    private int getLineIndex(int offset) throws ErrataException {
        if (offset < 0) {
            throw new ErrataException("Offset cannot be less than 0")
                    .withContext("Offset", offset);
        }

        var index = 0;

        for (var line: lines) {
            if (offset >= line.offset && offset <= line.offset + line.length) {
                return index;
            }

            index++;
        }

        throw new ErrataException("Line index could not be found")
                .withContext("Offset", offset)
                .withContext("Line count", lines.size());
    }
}
