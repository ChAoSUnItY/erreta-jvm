package io.github.chaosunity;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class TextSpan implements Iterable<Integer> {
    public int start;
    public int end;
    public int length;
    public int lastOffset;

    public TextSpan(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("End position must larger than start position");
        }

        this.start = start;
        this.end = end;
        length = end - start;
        lastOffset = Math.max(start, end - 1);
    }

    public boolean contains(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot less than 0");
        }

        return start <= offset && lastOffset >= offset;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSpan integers = (TextSpan) o;
        return start == integers.start && end == integers.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format("{%d, %d}", start, end);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Enumerator(this);
    }

    public final class Enumerator implements Iterator<Integer> {
        private final TextSpan span;
        private int current;

        public Enumerator(TextSpan span) {
            this.span = span;
            current = span.start - 1;
        }

        @Override
        public boolean hasNext() {
            return current < span.end - 1;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                throw new NoSuchElementException("TextSpan's start position exceeds its end position");
            } else {
                return ++current;
            }
        }

        public int getCurrent() {
            return current;
        }
    }
}
