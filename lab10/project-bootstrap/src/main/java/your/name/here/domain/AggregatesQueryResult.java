package your.name.here.domain;

import java.util.List;

public final class AggregatesQueryResult {
    private final List<String> columns;
    private final List<List<String>> rows;

    public AggregatesQueryResult(List<String> columns, List<List<String>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public List<String> columns() {
        return columns;
    }

    public List<List<String>> rows() {
        return rows;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AggregatesQueryResult) obj;
        return java.util.Objects.equals(this.columns, that.columns) &&
                java.util.Objects.equals(this.rows, that.rows);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(columns, rows);
    }

    @java.lang.Override
    public String toString() {
        return "AggregatesQueryResult[" +
                "columns=" + columns + ", " +
                "rows=" + rows + ']';
    }

}