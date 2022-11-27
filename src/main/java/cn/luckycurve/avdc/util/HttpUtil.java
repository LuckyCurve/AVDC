package cn.luckycurve.avdc.util;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import okhttp3.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author LuckyCurve
 */
public class HttpUtil {

    public final static OkHttpClient CLIENT = new OkHttpClient();

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    @SneakyThrows
    public static String post(String url, Map<String, String> headers, Map<String, String> body) {
        headers = Optional.ofNullable(headers).orElse(Maps.newHashMap());
        body = Optional.ofNullable(body).orElse(Maps.newHashMap());

        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(body)))
                .build();

        Response response = CLIENT.newCall(request).execute();

        return Objects.requireNonNull(response.body()).string();
    }
}
