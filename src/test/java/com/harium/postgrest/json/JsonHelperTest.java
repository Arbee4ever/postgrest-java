package com.harium.postgrest.json;

import com.harium.postgrest.Insert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonHelperTest {

    @Test
    public void testSimpleJsonFromPairs() {
        assertEquals("{\"col1\":}", JsonHelper.buildJsonFromRow(Insert.row().column("col1", "")));
        assertEquals("{\"col1\":\"value1\"}", JsonHelper.buildJsonFromRow(Insert.row().column("col1", "value1")));
        assertEquals("{\"col1\":\"value1\",\"col2\":\"value2\"}", JsonHelper.buildJsonFromRow(Insert.row().column("col1", "value1").column("col2", "value2")));
    }

    @Test
    public void testJsonFromBulk() {
        assertEquals("[{\"col1\":\"value1\"},{\"col1\":\"value11\"}]", JsonHelper.buildJsonFromInsert(Insert.bulk(Insert.row().column("col1", "value1"), Insert.row().column("col1", "value11"))));
    }

}
