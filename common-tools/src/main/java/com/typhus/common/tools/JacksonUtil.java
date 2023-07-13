package com.typhus.common.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * jackson实现的格式化工具
 *
 * @author typhus-xxj
 * @version JacksonUtil.java, v 0.1 2023年07月13日 10:59 typhus-xxj Exp $
 */
public class JacksonUtil {

    /**
     * json OBJECT_MAPPER
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * xml OBJECT_MAPPER
     */
    private static final ObjectMapper XML_MAPPER = new XmlMapper();

    static {
        // 序列化时，跳过null属性
        JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，遇到空bean（无属性）时不会失败
        JSON_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时，遇到未知属性（在bean上找不到对应属性）时不会失败
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 反序列化时，将空数组([])当做null来处理（以便把空数组反序列化到对象属性上）
        JSON_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        // 不通过fields来探测（仅通过标准getter探测）
        JSON_MAPPER.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        // 按字典顺序排序
        JSON_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // 允许非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
        JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        // 序列化时，跳过null属性
        XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，遇到空bean（无属性）时不会失败
        XML_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时，遇到未知属性（在bean上找不到对应属性）时不会失败
        XML_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 反序列化时，将空数组([])当做null来处理（以便把空数组反序列化到对象属性上）
        XML_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        // 不通过fields来探测（仅通过标准getter探测）
        XML_MAPPER.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        // 按字典顺序排序
        XML_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // 允许非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
        XML_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

    }

    /**
     * 对象转换为string
     *
     * @param object       对象
     * @param objectMapper jackson转换对象
     * @return 字符串
     */
    private static String bean2String(Object object, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 对象转换为json string
     *
     * @param object 对象
     * @return json字符串
     */
    public static String bean2String(Object object) {
        try {
            return bean2String(object, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert object to json", e);
        }
    }

    /**
     * 对象转换为xml string
     *
     * @param object 对象
     * @return xml字符串
     */
    public static String bean2StringWithXml(Object object) {
        try {
            return bean2String(object, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert object to xml", e);
        }
    }

    /**
     * string转换为对象
     *
     * @param str          字符串
     * @param tClass       对象类型
     * @param objectMapper jackson转换对象
     * @param <T>          T
     * @return 对象
     */
    private static <T> T string2Bean(String str, Class<T> tClass, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.readValue(str, tClass);
    }

    /**
     * json string转换为对象
     *
     * @param jsonStr json字符串
     * @param tClass  对象类型
     * @param <T>     T
     * @return 对象
     */
    public static <T> T string2Bean(String jsonStr, Class<T> tClass) {
        try {
            return string2Bean(jsonStr, tClass, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * xml string转换为对象
     *
     * @param xmlStr xml字符串
     * @param tClass 对象类型
     * @param <T>    T
     * @return 对象
     */
    public static <T> T string2BeanWithXml(String xmlStr, Class<T> tClass) {
        try {
            return string2Bean(xmlStr, tClass, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert xml string:" + xmlStr, e);
        }
    }

    /**
     * string转换为对象数组
     *
     * @param str          字符串
     * @param tClass       对象类型
     * @param objectMapper jackson转换对象
     * @param <T>          T
     * @return 对象数组
     */
    private static <T> T[] string2Array(String str, Class<T> tClass, ObjectMapper objectMapper) throws JsonProcessingException {
        Class<?> arrayClass = Array.newInstance(tClass, 0).getClass();
        return (T[]) objectMapper.readValue(str, arrayClass);
    }

    /**
     * string转换为对象数组
     *
     * @param jsonStr json字符串
     * @param tClass  对象类型
     * @param <T>     T
     * @return 对象数组
     */
    public static <T> T[] string2Array(String jsonStr, Class<T> tClass) {
        try {
            return string2Array(jsonStr, tClass, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为对象数组
     *
     * @param xmlStr xml字符串
     * @param tClass 对象类型
     * @param <T>    T
     * @return 对象数组
     */
    public static <T> T[] string2ArrayWithXml(String xmlStr, Class<T> tClass) {
        try {
            return string2Array(xmlStr, tClass, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert xml string:" + xmlStr, e);
        }
    }

    /**
     * string转换为泛型list
     *
     * @param str          字符串
     * @param tClass       对象类型
     * @param objectMapper jackson转换对象
     * @param <T>          T
     * @return 对象list
     */
    private static <T> List<T> string2List(String str, Class<T> tClass, ObjectMapper objectMapper) throws JsonProcessingException {
        TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return ParameterizedTypeImpl.make(List.class, new Type[]{tClass}, null);
            }
        };
        return objectMapper.readValue(str, typeRef);
    }

    /**
     * string转换为泛型list
     *
     * @param jsonStr json字符串
     * @param tClass  对象类型
     * @param <T>     T
     * @return 对象list
     */
    public static <T> List<T> string2List(String jsonStr, Class<T> tClass) {
        try {
            return string2List(jsonStr, tClass, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为泛型list
     *
     * @param xmlStr xml字符串
     * @param tClass 对象类型
     * @param <T>    T
     * @return 对象list
     */
    public static <T> List<T> string2ListWithXml(String xmlStr, Class<T> tClass) {
        try {
            return string2List(xmlStr, tClass, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert xml string:" + xmlStr, e);
        }
    }

    /**
     * string转换为泛型map
     *
     * @param str          字符串
     * @param keyClass     key类型
     * @param valueClass   value类型
     * @param objectMapper jackson转换对象
     * @param <K>          K
     * @param <V>          V
     * @return map
     */
    public static <K, V> Map<K, V> string2Map(String str, Class<K> keyClass, Class<V> valueClass, ObjectMapper objectMapper)
            throws JsonProcessingException {
        TypeReference<Map<K, V>> typeRef = new TypeReference<Map<K, V>>() {
            @Override
            public Type getType() {
                return ParameterizedTypeImpl.make(Map.class, new Type[]{keyClass, valueClass}, null);
            }
        };
        return objectMapper.readValue(str, typeRef);
    }

    /**
     * string转换为泛型map
     *
     * @param jsonStr    json字符串
     * @param keyClass   key类型
     * @param valueClass value类型
     * @param <K>        K
     * @param <V>        V
     * @return map
     */
    public static <K, V> Map<K, V> string2Map(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        try {
            return string2Map(jsonStr, keyClass, valueClass, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为泛型map
     *
     * @param xmlStr     xml字符串
     * @param keyClass   key类型
     * @param valueClass value类型
     * @param <K>        K
     * @param <V>        V
     * @return map
     */
    public static <K, V> Map<K, V> string2MapWithXml(String xmlStr, Class<K> keyClass, Class<V> valueClass) {
        try {
            return string2Map(xmlStr, keyClass, valueClass, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert xml string:" + xmlStr, e);
        }
    }

    /**
     * string转换为嵌套对象，jackson自定义反泛序列最灵活的用法，理论上能支持各种层级复杂反序列化
     *
     * @param str          字符串
     * @param valueTypeRef jackson 泛序列化时确定泛型的类
     * @param objectMapper jackson转换对象
     * @param <T>          T
     * @return 对象
     */
    private static <T> T string2Bean(String str, TypeReference<T> valueTypeRef, ObjectMapper objectMapper)
            throws JsonProcessingException {
        return objectMapper.readValue(str, valueTypeRef);
    }

    /**
     * string转换为嵌套对象，jackson自定义反泛序列最灵活的用法，理论上能支持各种层级复杂反序列化
     *
     * @param jsonStr      json字符串
     * @param valueTypeRef jackson 泛序列化时确定泛型的类
     * @param <T>          T
     * @return 对象
     */
    public static <T> T string2Bean(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return string2Bean(jsonStr, valueTypeRef, JSON_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为嵌套对象，jackson自定义反泛序列最灵活的用法，理论上能支持各种层级复杂反序列化
     *
     * @param xmlStr       xml字符串
     * @param valueTypeRef jackson 泛序列化时确定泛型的类
     * @param <T>          T
     * @return 对象
     */
    public static <T> T string2BeanWithXml(String xmlStr, TypeReference<T> valueTypeRef) {
        try {
            return string2Bean(xmlStr, valueTypeRef, XML_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert xml string:" + xmlStr, e);
        }
    }

    /**
     * 两层嵌套多泛型对象。比如Result<R,T,K,V>。更多级别嵌套使用{@link JacksonUtil#string2Bean(String, TypeReference)}
     *
     * @param jsonStr json字符串
     * @param types   内部嵌套的class。 比如Result<R,T,K,V>中的 R,T,K,V
     * @param clazz   外层class
     * @param <T>     T
     * @return 对象
     */
    public static <T> T string2Bean(String jsonStr, Type[] types, Class<T> clazz) {
        final ParameterizedTypeImpl type = ParameterizedTypeImpl.make(clazz, types, clazz.getDeclaringClass());
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        return string2Bean(jsonStr, typeReference);
    }

    /**
     * 两层嵌套多泛型对象。比如Result<R,T,K,V>。更多级别嵌套使用{@link JacksonUtil#string2Bean(String, TypeReference)}
     *
     * @param xmlStr xml字符串
     * @param types  内部嵌套的class。 比如Result<R,T,K,V>中的 R,T,K,V
     * @param clazz  外层class
     * @param <T>    T
     * @return 对象
     */
    public static <T> T string2BeanWithXml(String xmlStr, Type[] types, Class<T> clazz) {
        final ParameterizedTypeImpl type = ParameterizedTypeImpl.make(clazz, types, clazz.getDeclaringClass());
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        return string2BeanWithXml(xmlStr, typeReference);
    }

    /**
     * 创建JsonNode，不可变
     *
     * @param jsonStr
     * @return
     */
    public static JsonNode createJsonNode(String jsonStr) {
        try {
            return JSON_MAPPER.readTree(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert JsonNode string:" + jsonStr, e);
        }
    }


    /**
     * 创建ObjectNode，JsonNode子类，可变
     *
     * @return com.fasterxml.jackson.databind.node.ObjectNode
     * @date 2023/7/13 11:01
     **/
    public static ObjectNode createObjectNode() {
        return JSON_MAPPER.createObjectNode();
    }
}
