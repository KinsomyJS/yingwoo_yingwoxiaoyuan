package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by FJS0420 on 2016/10/17.
 */

public class XMLtestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmltext);
        new Thread(new httpThread()).start();
    }



}

class httpThread implements Runnable {
    String requestXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tip=\"http://www.dsc.com.tw/tiptop/TIPTOPServiceGateWay\">\n" +
            "<soapenv:Header/>\n" +
            "<soapenv:Body>\n" +
            "<tip:LoginCheckRequest>\n" +
            "<tip:request>&lt;Request>\n" +
            "<Access>\n" +
            "<Authentication user=\"kc09204\" password=\"kc09204\" />\n" +
            "<Connection application=\"APP\" source=\"192.168.1.2\" />\n" +
            "<Organization name=\"SP2\" />\n" +
            "<Locale language=\"zh_cn\" />\n" +
            "</Access>\n" +
            "<RequestContent>\n" +
            "<Parameter>\n" +
            "<Record>\n" +
            "<Field name=\"username\" value=\"kc09204\" />\n" +
            "<Field name=\"password\" value=\"kc09204\" />\n" +
            "</Record>\n" +
            "</Parameter>\n" +
            "<Document/>\n" +
            "</RequestContent>\n" +
            "</Request></tip:request>\n" +
            "</tip:LoginCheckRequest>\n" +
            "</soapenv:Body>\n" +
            "</soapenv:Envelope>\n";
    @Override
    public void run() {
        URL url = null;
        try {
            Log.d("xmltest","测试开始");
            url = new URL("http://tm.lexy.cn/web/ws/r/aws_ttsrv2_toptest?wsdl");
            byte[] xmlbyte = requestXml.getBytes("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);// 允许输出
            conn.setDoInput(true);
            conn.setUseCaches(false);// 不使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(xmlbyte.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("X-ClientType", "2");//发送自定义的头信息

            conn.getOutputStream().write(xmlbyte);
            conn.getOutputStream().flush();
            conn.getOutputStream().close();


            if (conn.getResponseCode() != 200){
                Log.d("xmltest","请求url失败");
                throw new RuntimeException("请求url失败");
            }

            InputStream is = conn.getInputStream();// 获取返回数据

            // 使用输出流来输出字符(可选)
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            String string = out.toString("UTF-8");
            Log.d("xmltest",string);
//            System.out.println(string);
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
