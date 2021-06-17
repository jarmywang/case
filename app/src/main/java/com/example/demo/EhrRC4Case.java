package com.example.demo;

//import com.google.gson.JsonParser;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;

/**
 * 2号人事部开发平台Java解密示例
 * Created by Wang on 2018/3/26.
 */
public class EhrRC4Case {

    private int[] sbox;
    private static final int SBOX_LENGTH = 256;
    private int i;
    private int j;

    public static void main(String[] args) {
        String corp_secret = "{你的企业的corp_secret}";
        String access_token = "{你的企业的access_token}";

        // 这里以获取部门信息为例介绍如何解密数据
        // 网络请求以okhttp为例
        // json解析以gson为例
        // 解密步骤：
        // 1、Base64解密
        // 2、RC4解密
        try {
//            Request request = new Request.Builder().url("https://openapi.2haohr.com/api/departments/?access_token=" + access_token).build();
//            OkHttpClient client = new OkHttpClient.Builder().build();
//            okhttp3.Response response = client.newCall(request).execute();
//
//            // 获取到json数据，内容格式：{"errcode":0,"data":"[加密内容]","errmsg":""}
//            String responseStr = response.body().string();
//
//            // 将"data"字段的加密内容取出来
//            String dataStr = new JsonParser().parse(responseStr).getAsJsonObject().get("data").getAsString();

            // 首先Base64解密
            byte[] bytesDecodeBase64 = "abc".getBytes();
            corp_secret = "6cfc0a60";
            // 然后RC4解密
            EhrRC4Case rc4Util = new EhrRC4Case(corp_secret);
            byte[] bytesDecodeRC4 = rc4Util.decrypt(bytesDecodeBase64);

            // 得到解密后的data字段内容
            String dataReal = new String(bytesDecodeRC4);

            System.out.println("dataReal=" + new String(rc4Util.decrypt(dataReal.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EhrRC4Case(String corp_secret) {
        byte[] key = corp_secret.getBytes();
        sbox = initSBox(key);
    }

    public byte[] decrypt(final byte[] msg) { // rc4加解密都是同一个过程
        return encrypt(msg);
    }

    public byte[] encrypt(final byte[] msg) {
        byte[] code = new byte[msg.length];
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ (int) msg[n]);
        }
        return code;
    }

    private int[] initSBox(byte[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + key[i % key.length] + SBOX_LENGTH) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

}
