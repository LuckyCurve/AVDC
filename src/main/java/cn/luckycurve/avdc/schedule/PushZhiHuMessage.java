package cn.luckycurve.avdc.schedule;

import cn.luckycurve.avdc.config.ZhiHuConfig;
import cn.luckycurve.avdc.service.PushMessageService;
import cn.luckycurve.avdc.util.CookieUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Configuration
public class PushZhiHuMessage {

    @Resource
    ZhiHuConfig zhiHuConfig;

    @Resource
    PushMessageService pushMessageService;

    @PostConstruct
    public void init() {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        ThreadFactory threadFactory = builder.setNameFormat("zhiHu-message-push-thread").build();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory);

        executor.scheduleAtFixedRate(this::pushZhiHuMessage, 0, 12, TimeUnit.HOURS);
    }

    @SneakyThrows
    public void pushZhiHuMessage() {
        String cookieString = zhiHuConfig.getCookie();
        Map<String, String> cookie = CookieUtil.cookieParse(cookieString);

        Document document = Jsoup.connect("https://www.zhihu.com/hot").cookies(cookie).get();
        Elements elements = document.getElementsByClass("HotItem-title");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            builder.append("热榜第 ").append(i + 1).append(" 名 title：").append(elements.get(i).text()).append("\n");
        }

        pushMessageService.pushMessage(builder.toString());
    }
}
