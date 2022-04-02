package io.github.chaosunity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class TextLine {
    public int index;
    public int offset;
    public char[] lineBreak;
    public String text;
    public int length;
    public TextSpan span;

    public TextLine(int index, @Nullable String text, int offset, char @Nullable [] lineBreak) {
        this.index = index;
        this.text = text != null ? text : "";
        this.offset = offset;
        this.lineBreak = lineBreak != null ? lineBreak : new char[]{};
        length = this.text.length();
        span = new TextSpan(this.offset, this.offset + length);
    }

    @Contract("null -> fail")
    public static @NotNull List<TextLine> split(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Cannot split null string");
        }

        var lines = new ArrayList<TextLine>();
        var buffer = new TextBuffer(text);
        var index = 0;
        var offset = 0;

        while (buffer.canRead()) {
            var current = buffer.read();
            if (current == '\r' && buffer.peek() == '\n') {
                buffer.read();

                var line = new TextLine(index, buffer.slice(offset, buffer.getPosition() - 2), offset, new char[]{'\r', '\n'});

                lines.add(line);
                offset += line.length + line.lineBreak.length;
                index++;
            } else if (current == '\n') {
                var line = new TextLine(index, buffer.slice(offset, buffer.getPosition() - 1), offset, new char[]{'\n'});

                lines.add(line);
                offset += line.length + line.lineBreak.length;
                index++;
            }
        }

        if (offset < buffer.length()) {
            lines.add(new TextLine(index, buffer.slice(offset, buffer.getPosition()), offset, null));
        }

        return lines;
    }
}
