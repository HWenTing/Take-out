package com.example.hp.materialtest.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * 用于处理json的类
 *
 * @author 87663
 *
 */
public class GsonUtil {

    /**
     * 用于处理Gson将json转成MAP时，数字的默认类是Double的问题
     * 通过这样，数字会优先转成Integer，不够长就是Long，如果再不够长
     * 或者是浮点数，那么就会转成Double类型
     *

     */
    public static class GsonTypeAdapter extends TypeAdapter<Object> {
        @Override
        public Object read(JsonReader in) throws IOException {
            // 反序列化
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:

                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:

                    Map<String, Object> map = new HashMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:

                    return in.nextString();

                case NUMBER:

                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

//                     判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        if (lngNum > Integer.MAX_VALUE || lngNum < Integer.MIN_VALUE)
                            return lngNum;
                        else
                            return (int) lngNum;
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化不处理
        }
    }

//    private static Gson gson;

//    static {
//        gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() {
//        }.getType(), new GsonTypeAdapter()).create();
//    }
    private static Gson gson=new Gson();
    /**
     * 将json转成Map
     * @param json 需要转换的json
     * @return 参数映射
     */
    public static Map<String, Object> fromJsonToMap(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
    }

    // public static void main(String[] args) {
    // String json="{\"name\"=\"yu\",\"age\"=23}";
    // System.out.println(fromJsonToMap(json));
    // }

}