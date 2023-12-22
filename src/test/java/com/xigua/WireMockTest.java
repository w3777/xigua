package com.xigua;

import com.alibaba.fastjson2.JSONObject;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.xigua.entity.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @ClassName WireMockTest
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/22 10:56
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WireMockTest {
    private WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
//        wireMockServer = new WireMockServer(8080); // 选择合适的端口
//        wireMockServer.start();
//        WireMock.configureFor("xigua.com", 777);
    }

    @AfterEach
    public void teardown() {
//        wireMockServer.stop();
    }

    @Test
    public void testYourServiceWithWireMock() {
        String url = "test";
        // 定义 WireMock 的模拟规则
        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Hello, WireMock!\"}")));

        // 现在，当你的应用程序发送请求到 /your/endpoint 时，将得到模拟的响应
        // 编写你的测试逻辑...
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpGet httpGet = new HttpGet("http://xigua.com:777/test"); // 替换为你想要请求的URL
            response = httpClient.execute(httpGet);

            // 获取响应内容
            if (response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                System.err.println("Response: " + responseBody);
            } else {
                System.err.println("GET request failed: " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    void test01() throws IOException {
        // 创建 WireMockServer 实例并指定端口
        WireMockServer wireMockServer = new WireMockServer(777);
        wireMockServer.start(); // 启动 WireMock 服务器

        // 配置 WireMock 库与 "xigua" 主机的端口为 777 进行交互
        WireMock.configureFor("xigua", 777);

        // 模拟的端点 URL
        String endpoint = "/test";

        // 配置一个模拟的 GET 请求并返回预定义的 JSON 响应
        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Hello, WireMock!\"}")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet("http://xigua:777/test"); // 替换为你想要请求的URL
        response = httpClient.execute(httpGet);
        
        // 获取响应内容
        if (response.getStatusLine().getStatusCode() == 200) {
            String responseBody = EntityUtils.toString(response.getEntity());
            System.err.println("Response: " + responseBody);
        } else {
            System.err.println("GET request failed: " + response.getStatusLine().getStatusCode());
        }

        // 停止 WireMock 服务器
        wireMockServer.stop();
    }
}
