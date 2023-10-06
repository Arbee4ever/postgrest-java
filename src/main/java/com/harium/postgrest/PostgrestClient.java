package com.harium.postgrest;

import com.harium.postgrest.json.JsonHelper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class PostgrestClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    protected final String baseUrl;

    protected final OkHttpClient client;

    protected String schema;

    protected boolean httpsEnabled = true;

    public PostgrestClient(String baseUrl, String schema) {
        this.baseUrl = extractHost(baseUrl);
        this.schema = schema;

        client = new OkHttpClient();
    }
    
    public PostgrestClient(String baseUrl) {
        this(baseUrl, "public");
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public PostgrestClient withHttps(boolean https) {
        this.httpsEnabled = https;
        return this;
    }

    private String extractHost(String baseUrl) {
        final String protocolToken = "://";

        if (baseUrl.startsWith("http")) {
            return baseUrl.substring(baseUrl.indexOf(protocolToken) + protocolToken.length());
        }
        return baseUrl;
    }

    public String findAll(String table) throws IOException {
        Request request = buildRequest(table)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String find(String table, Condition condition) throws IOException {
        Request request = buildRequest(table, condition)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String insert(String table, String json) throws IOException {
        return upsert(table, json, false);
    }

    public String insert(String table, Pair... pairs) throws IOException {
        return insert(table, Insert.row(pairs));
    }

    public String insert(String table, Insert.Row row) throws IOException {
        String json = JsonHelper.buildJsonFromRow(row);
        return upsert(table, json, false);
    }

    public String insert(String table, List<Insert.Row> rows) throws IOException {
        String json = JsonHelper.buildJsonFromRow(rows);
        return upsert(table, json, false);
    }

    public String save(String table, String json) throws IOException {
        return upsert(table, json, true);
    }

    public String save(String table, Pair... pairs) throws IOException {
        return save(table, Insert.row(pairs));
    }

    public String save(String table, Insert.Row row) throws IOException {
        String json = JsonHelper.buildJsonFromRow(row);
        return upsert(table, json, true);
    }

    public String save(String table, List<Insert.Row> rows) throws IOException {
        String json = JsonHelper.buildJsonFromRow(rows);
        return upsert(table, json, true);
    }

    private String upsert(String table, String json, boolean upsert) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder requestBuilder = buildRequest(table);

        if (upsert) {
            requestBuilder.addHeader("Prefer", "resolution=merge-duplicates");
        }

        Request request = requestBuilder.post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String delete(String table, Condition condition) throws IOException {
        Request request = buildRequest(table, condition).delete().build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    protected HttpUrl buildTableUrl(String table) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment(table);

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected HttpUrl buildTableUrl(String table, Condition condition) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment(table)
                .addQueryParameter(condition.getQueryParam(), condition.getQueryValue());

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected Request.Builder buildRequest(String table) {
        HttpUrl httpUrl = buildTableUrl(table);

        return new Request.Builder()
                .url(httpUrl)
                .addHeader("Accept-Profile", getSchema())
                .addHeader("Content-Profile", getSchema());
    }

    protected Request.Builder buildRequest(String table, Condition condition) {
        HttpUrl httpUrl = buildTableUrl(table, condition);

        return new Request.Builder()
                .url(httpUrl)
                .addHeader("Accept-Profile", getSchema())
                .addHeader("Content-Profile", getSchema());
    }
}
