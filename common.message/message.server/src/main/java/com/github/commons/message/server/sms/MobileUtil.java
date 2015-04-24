package com.github.commons.message.server.sms;

import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.message.server.MessageException;

/**
 * 提供手机号码相关的所有验证操作工具方法 接口人 jliao@corp.netease.com
 */
public class MobileUtil {

    private static Logger    log           = LoggerFactory.getLogger(MobileUtil.class);

    private String           smsGatewayUrl = "http://smsknl.163.com:8089/servlet/CorpIdentifyNotCheck?";

    private static final int CONN_TIME_OUT = 5000;

    /**
     * 验证手机号的有效性，规则6-20位的纯数字字符串
     * 
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    // 校验固定电话 支持7位分机号 010-88888888-1010
    public static boolean verifyTelephone(String phone) {
        Pattern pattern = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,7}))?$");
        Matcher match = pattern.matcher(phone);
        return match.find();
    }

    /**
     * 发送文本短信
     * 
     * @param mobiles 发送目标，可以包含多个
     * @param msgprop 增值业务的内部代号，决定信息的业务属性
     * @param level 0；最高优先级，信息会优先提交给运营商 3；一般优先级 5；群发级 @return
     */
    public boolean sendTxtSms(String message, String msgprop, String level, String... mobiles) throws MessageException {
        String resultstr = "";

        assertNotBlank(message, "message");
        assertNotBlank(msgprop, "msgprop");
        assertArrayNotBlank(mobiles, "mobiles");

        try {
            // url 网易企信通的地址
            StringBuffer smsUrl = new StringBuffer(smsGatewayUrl).append("msgprop=").append(msgprop).append("&message=").append(Tools.HexToStr(message.getBytes())).append("&corpinfo=1").append("&msgtype=0");

            if (StringUtils.isNotBlank(level)) {
                smsUrl.append("&level=").append(level);
            }

            if (mobiles.length == 0) {
                smsUrl.append("&phone=").append(mobiles[0]).append("&frmphone=").append(level);
            } else {
                StringBuffer sb = new StringBuffer();
                int i = 0;
                for (String mobile : mobiles) {
                    if (i == 0) {
                        smsUrl.append("&frmphone=").append(mobile);
                        i++;
                    }
                    sb.append(mobile).append("|");
                }

                String mobile = sb.substring(0, sb.length() - 1);
                smsUrl.append("&phone=").append(mobile);
            }

            log.debug("[SEND] subUrl {}", smsUrl.toString());

            resultstr = openUrl(smsUrl.toString());

            log.debug("[RESULT] subUrl - {} - result: ", smsUrl.toString(), resultstr);

        } catch (Throwable e) {
            throw new MessageException("Send sms exception.", e);
        }

        if (StringUtils.equals(resultstr, "ok")) {
            return true;
        }

        return false;
    }

    public void setSmsGatewayUrl(String smsGatewayUrl) {
        this.smsGatewayUrl = smsGatewayUrl;
    }

    private void assertNotBlank(String value, String fieldName) {

        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " can't be blank.");
        }
    }

    private void assertArrayNotBlank(String[] values, String fieldName) {

        if (values == null) {
            throw new IllegalArgumentException(fieldName + " can't be blank.");
        }
    }

    protected String openUrl(String url) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {

            HttpGet httpget = new HttpGet(url);

            URL url_ = new URL(url);

            httpClient = HttpClients.createDefault();

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONN_TIME_OUT).setConnectTimeout(CONN_TIME_OUT).setConnectionRequestTimeout(CONN_TIME_OUT).build();
            httpget.setConfig(requestConfig);

            HttpClientContext context = HttpClientContext.create();
            response = httpClient.execute(httpget, context);

            if (response.getStatusLine().getStatusCode() != SC_OK) {
                throw new MessageException("Sms send failed, Server response: "
                                           + response.getStatusLine().getStatusCode());
            }

            return fetchResponse(response);
        } catch (MessageException e) {
            throw e;
        } catch (Throwable e) {
            log.error("[get] invoke url :[" + url + "] exception.", e);
        } finally {

            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Throwable e) {
                log.error("[get] close exception.", e);
            }
        }

        return null;
    }

    private String fetchResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity resEntity = response.getEntity();

        if (resEntity == null) {
            throw new RuntimeException("Connect failed. Response resEntity is null.");
        }

        InputStream content = resEntity.getContent();

        if (content == null) {
            throw new RuntimeException("Connect failed. Response content is null.");
        }

        List<String> readLines = IOUtils.readLines(content);

        StringBuilder sb = new StringBuilder();
        for (String line : readLines) {
            sb.append(line);
        }

        return sb.toString();
    }
}
