package cn.luckycurve.avdc.service.impl;

import cn.luckycurve.avdc.service.PushMessageService;
import cn.luckycurve.avdc.util.HttpUtil;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PushMessageServiceImpl implements PushMessageService {
    @Override
    public void pushMessage(String message) {
        HashMap<@Nullable String, @Nullable String> body = Maps.newHashMap();
        body.put("token", "aa7ea9fc16f546b6be55fa06965f8d33");
        body.put("title", "定期知乎热榜");
        body.put("content", message);
        body.put("template", "txt");
        HttpUtil.post("http://www.pushplus.plus/send", Maps.newHashMap(), body);
    }
}
