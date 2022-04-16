package com.harium.postgrest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Insert {

    public List<Row> rows = new ArrayList<>();

    protected Insert(Row... rows) {
        Collections.addAll(this.rows, rows);
    }

    public static Row row(Pair... pairs) {
        return new Row(pairs);
    }

    public static Insert bulk(Row... rows) {
        return new Insert(rows);
    }

    public static class Row {
        public List<Pair> pairs = new ArrayList<>();

        public Row(Pair... pairs) {
            this.pairs.addAll(Arrays.asList(pairs));
        }

        public Row column(String column, boolean value) {
            this.pairs.add(new Pair(column, value));
            return this;
        }

        public Row column(String column, int value) {
            this.pairs.add(new Pair(column, value));
            return this;
        }

        public Row column(String column, double value) {
            this.pairs.add(new Pair(column, value));
            return this;
        }

        public Row column(String column, String value) {
            this.pairs.add(new Pair(column, value));
            return this;
        }
    }

}
