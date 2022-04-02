package io.github.chaosunity;

class TextBuffer {
    private final String buffer;
    private int position;

    public TextBuffer(String content) {
        buffer = content;
        position = 0;
    }

    public char peek() {
        return peek(0);
    }

    public char peek(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than 0");
        }

        var pos = position + offset;
        if (pos >= buffer.length()) {
            return '\0';
        }

        return buffer.charAt(pos);
    }

    public void move(int position) {
        this.position = position;
    }

    public char read() {
        var result = peek();
        if (result != '\0') {
            position++;
        }

        return result;
    }

    public boolean canRead() {
        return position < buffer.length();
    }

    public int getPosition() {
        return position;
    }

    public char current() {
        return buffer.charAt(position);
    }

    public int length() {
        return buffer.length();
    }

    public String slice(int start, int stop) {
        return buffer.substring(start, stop - start);
    }
}
