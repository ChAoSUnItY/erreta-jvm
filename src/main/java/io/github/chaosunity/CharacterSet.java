package io.github.chaosunity;

public abstract class CharacterSet {
    public char colon() {
        return ':';
    }

    public char topLeftCornerHard() {
        return '┌';
    }

    public char bottomLeftCornerHard() {
        return '└';
    }

    public char leftConnector() {
        return '├';
    }

    public char horizontalLine() {
        return '─';
    }

    public char verticalLine() {
        return '│';
    }

    public char dot() {
        return '·';
    }

    public char anchor() {
        return '┬';
    }

    public char anchorHorizontalLine() {
        return '─';
    }

    public char anchorVerticalLine() {
        return '│';
    }

    public char bottomLeftCornerRound() {
        return '╰';
    }

    public final char get(Character character) {
        return switch (character) {
            case COLON -> colon();
            case TOP_LEFT_CORNER_HARD -> topLeftCornerHard();
            case BOTTOM_LEFT_CORNER_HARD -> bottomLeftCornerHard();
            case LEFT_CONNECTOR -> leftConnector();
            case HORIZONTAL_LINE -> horizontalLine();
            case VERTICAL_LINE -> verticalLine();
            case DOT -> dot();
            case ANCHOR -> anchor();
            case ANCHOR_HORIZONTAL_LINE -> anchorHorizontalLine();
            case ANCHOR_VERTICAL_LINE -> anchorVerticalLine();
            case BOTTOM_LEFT_CORNER_ROUND -> bottomLeftCornerRound();
        };
    }

    public static CharacterSet create() {
        return System.console() != null && System.getenv().get("TERM") != null ? UnicodeCharacterSet.INSTANCE : AsciiCharacterSet.INSTANCE;
    }

    public static class UnicodeCharacterSet extends CharacterSet {
        static final UnicodeCharacterSet INSTANCE = new UnicodeCharacterSet();
    }

    public static class AsciiCharacterSet extends CharacterSet {
        static final AsciiCharacterSet INSTANCE = new AsciiCharacterSet();

        @Override
        public char anchor() {
            return '┬';
        }

        @Override
        public char anchorHorizontalLine() {
            return '─';
        }

        @Override
        public char bottomLeftCornerRound() {
            return '└';
        }
    }
}
