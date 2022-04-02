package io.github.chaosunity.render;

import com.sun.org.apache.xml.internal.serializer.ToStream;
import io.github.chaosunity.ErrataException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class LineRange implements Iterable<Integer> {
    public int start;
    public int end;
    public int length;

    public LineRange(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("End position must be greater than start position");
        }

        this.start = start;
        this.end = end;
    }

    public boolean contains(int offset) throws ErrataException {
        if (offset < 0) {
            throw new ErrataException("Offset cannot be less than 0")
                    .withContext("Start", start)
                    .withContext("End", end)
                    .withContext("Multiline", isMultiLine())
                    .withContext("length", length)
                    .withContext("Offset", offset);
        }

        return start <= offset && end > offset;
    }

    public boolean isMultiLine() {
        return start != end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineRange integers = (LineRange) o;
        return start == integers.start && end == integers.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format("%d..%d", start, end);
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Enumerator(this);
    }

    private static final class Enumerator implements Iterator<Integer> {
        private final LineRange span;

        public int current;

        public Enumerator(LineRange span) {
            this.span = span;
        }

        @Override
        public boolean hasNext() {
            return current >= span.end;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("LineRange's start position exceeds its end position");
            }

            return ++current;
        }

        public void reset() {
            current = span.start - 1;
        }
    }
}
