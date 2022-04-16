package com.harium.postgrest.json;

import com.harium.postgrest.Insert;
import com.harium.postgrest.Pair;

import java.util.Arrays;
import java.util.List;

public class JsonHelper {

    public static String buildJsonFromRow(Insert.Row row) {
        return buildJsonFromPairs(row.pairs);
    }

    public static String buildJsonFromRow(Insert.Row... rows) {
        return buildJsonFromRow(Arrays.asList(rows));
    }

    public static String buildJsonFromRow(List<Insert.Row> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < rows.size(); i++) {
            Insert.Row row = rows.get(i);
            builder.append(buildJsonFromRow(row));
            if (i < rows.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    static String buildJsonFromPairs(List<Pair> pairs) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < pairs.size(); i++) {
            Pair pair = pairs.get(i);
            builder.append(pair.key);
            builder.append(":");
            builder.append(pair.value);
            if (i < pairs.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    public static String buildJsonFromInsert(Insert insert) {
        return buildJsonFromRow(insert.rows);
    }
}
