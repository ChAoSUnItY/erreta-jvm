package io.github.chaosunity;

import java.util.Objects;

public final class Location {
    public int row;
    public int column;
    public boolean isRow;

    public Location(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("Row must be greater than 0");
        }

        this.row = row;
        column = 0;
        isRow = true;
    }

    public Location(int row, int column) {
        if (row < 0) {
            throw new IllegalArgumentException("Row must be greater than 0");
        } else if (column < 0) {
            throw new IllegalArgumentException("Column must be greater than 0");
        }

        this.row = row;
        this.column = column;
        isRow = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row && column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
