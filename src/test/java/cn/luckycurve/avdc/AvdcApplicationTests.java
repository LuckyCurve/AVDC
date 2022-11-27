package cn.luckycurve.avdc;

import cn.luckycurve.avdc.util.CookieUtil;
import cn.luckycurve.avdc.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@SpringBootTest
class AvdcApplicationTests {

    @Test
    void ioUtil() {

    }

    @Test
    void contextLoads() throws IOException {
        String cookieString = "_zap=b48c9b05-8a71-4dc6-be07-f6b783416e6a; d_c0=APDXzYlW4BWPTsK1rqToZXH65Jo3tCV6gP8=|1668612323; ISSW=1; YD00517437729195%3AWM_NI=6v3qjBDAv8nHgtSWk0FtggxAs%2BEVSyxyJiQUKuR7JlBvWVwLfqX4sid7WqcrFW%2BJg6GIwgjXLo4IVqwdvlOC17U6Vs6dTMlVtLvHqI08W4LXumTWwukKav2x3DAM5QjvZEI%3D; YD00517437729195%3AWM_NIKE=9ca17ae2e6ffcda170e2e6ee9bc133acec88afea73868e8ba6d14f839b9eacc446a8ab9eacd43ef78caab1f92af0fea7c3b92af6aafd92e94a889fbad9ed44a5ac9db8ea3c86aca988fb4bb1f58bb7d273bca7868ad45f89ab9ed8c96882aa99b7f648a6af98d5f263a8e99992e27a8689e5abe63e8597b988e95490b1fcaad57bb5bcba8dce49f3e99bd7d579f6bc9caeef5eb894baa9f443f4e899d5cf54b7b39cb8f67fa995af97b174f19c8faeb85baaaa96a6cc37e2a3; YD00517437729195%3AWM_TID=opqWzY%2BO4aBFEEFRFRKRYMqFKAqoCeKm; _xsrf=e743e632-668d-4bc8-8de6-c7664f271b3f; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1668700515,1669487075,1669540854,1669551660; SESSIONID=BOaEgBe8euihSNXKkNbWJGNuqFpMPLzvecR8VO5LPwT; JOID=W1sUAUyp3fhO_cd4TKL543ll4JJR4ZCaDpSPH3fCopMlq4cqBY2KfSb5z3lKu-WNO4X6VkqzvxzO-a0eQrQHIfU=; osd=U1ESC06h1_5E_89ySqj763Nj6pBZ65aQDJyFGX3AqpkjoYUiD4uAfy7zyXNIs--LMYfyXEy5vRTE_6ccSr4BK_c=; __snaker__id=qWW6GA6xGKYLE1MR; gdxidpyhxdE=zrGgmrWcxZ5kxlYydTH4nVICKJzIv6gyh%2F%5C79WdjYRkAkzRA%2B3KAGUl38Wvm%2F3NZaGH09ctwmcwQS%2BukLCZPxdLEwWjvNL95Z3z%5CG4xKvYsTM%5CdEXuX5ouzOl23E7nIp5N17qa%2BlUgudtt2%5CC3ksMb6Xh5c0YqvwIywvJmkfi5yUcgtx%3A1669552561494; captcha_session_v2=2|1:0|10:1669551662|18:captcha_session_v2|88:aE8xK2RJS1BxYVVaRG9LOHQ3N05XeHNNdm1WbEllaUJqdjdXUmZPUFA5Uk9Ec3k0SzlILzlaLzlrRFd1MHhtMw==|5bd36f2ad264dc80f07d25135bdb1acc20ab02732a7141c183666727ce517736; captcha_ticket_v2=2|1:0|10:1669551673|17:captcha_ticket_v2|704:eyJ2YWxpZGF0ZSI6IkNOMzFfZW5VcG1WVDFrYW1MS3RYdENEb2tpa2ZKZTB4RVZvUmc1WW1uNlQtRE04SGVKUkduV3ctRlh1QU9TenI1cFY1bjhsTFdSQUFiNUpjQlU5dC5RQ2dRbHE1MDFWSkUwbVlualRrZGJLVklPdWhJelBkbkVmdktPNWFDcUt5b0xwTS5vUTY4Mm9pMWpVZGVXV3ctdklJVHVIMjhGcmd4c0YwUkdLTTJBX3ptb0I2U29BMFFYd3lDdEhuMTRXcDg4Vm1XUjVVTHAySXVTWHZHTkdWSTlPZ3VRejc3c2xZNUsuaHJOaXcxNS1JMS50VXBNR3cweFUwZ2t0eGpucUt1enNzTjc0NWRHa1U1Y2dpYmJpVmc0TXZsWjR1RTQ3cE9lUTFTSTY5Y3dYYlA2a0ZPMVJFNmFBU1Q3bHZJdHIwMEwyUkFHRXJfLmNFNVFjN1JaTnI0aE9nbkVtWjhpWWRwV2I5ZEh0VzFkLmJHeWRlbEk2TzZfbVp0SGZyRXNBeThJUWVFLVhDdzFNQTByOEhEbzFkcGlkWEYtMVdISWtrdUVxQ1VCWWpsUzdnTHU0OS1Rb0xibjI0LXJCZktkeU55Y2JFbUJDekJJZDJHNTBLTXpOQjVNWDkwUzA4TXMwV0V5eDhHNWxHeEJSeWVvdDduTjF4cFRqd1k3am5SVnpGMyJ9|5013d73044841c17d4300183e6fb13cca4e306b3e6010fef4bd8898c94182a69; z_c0=2|1:0|10:1669551694|4:z_c0|92:Mi4xN3dJM0N3QUFBQUFBOE5mTmlWYmdGU1lBQUFCZ0FsVk5UcVJ3WkFCOGREVHVWYmlseEx5bWVNczdIZXJPVDRMS1NR|b8354ac928f0bc3840eb0152db4f65ed1fb79448df576064f97b6e253565484c; q_c1=33a2036f498e46ed9d12ca56dfe2881a|1669551694000|1669551694000; tst=h; KLBRSID=b5ffb4aa1a842930a6f64d0a8f93e9bf|1669551830|1669551658; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1669551831";
        Map<String, String> cookie = CookieUtil.cookieParse(cookieString);

        Document document = Jsoup.connect("https://www.zhihu.com/hot").cookies(cookie).get();
        Elements elements = document.getElementsByClass("HotItem-title");
        for (Element element : elements) {
            System.out.println(element.text());
        }

        System.out.println(elements.size());
    }

}
