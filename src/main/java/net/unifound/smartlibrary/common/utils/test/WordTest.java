package net.unifound.smartlibrary.common.utils.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.unifound.smartlibrary.common.utils.UserAgentGetter;

public class WordTest {

    private static Word word;

    private static void init() {
        word = new Word();
        word.setA(null);
        word.setB(2);
        word.setD("d");
        word.setE(null);

    }
    private static void showJsonBySelf() {
        init();
        System.out.println(JSON.toJSONString(word));
        System.out.println(JSON.toJSONString(word, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty));
        System.out.println(JSON.toJSONString(word, SerializerFeature.PrettyFormat));
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue));
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));

    }

    public static void main(String[] args) {
    }
}
