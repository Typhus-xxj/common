package com.typhus.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * bean转换
 *
 * @author typhus-xxj
 * @version ConvertUtils.java, v 0.1 2023年07月12日 18:10 typhus-xxj Exp $
 */
public class ConvertUtils {

    /**
     * 日志对象
     */
    public static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    /**
     * 采用Spring BeanUtils#copyProperties方式 将source转化为target，浅拷贝
     *
     * @param source 转化来源对象
     * @param target 转化目标对象
     * @param <F>    转化来源对象泛型
     * @param <T>    转来后对象的泛型
     * @return 转换后的对象target
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> T convert(F source, T target) {
        return convertOrigin(source, target, null);
    }

    /**
     * 采用Spring BeanUtils#copyProperties方式 将source转化为target，浅拷贝
     *
     * @param source   转化来源对象
     * @param target   转化目标对象
     * @param callBack 转换后的回调
     * @param <F>      转化来源对象泛型
     * @param <T>      转来后对象的泛型
     * @return 转换后的对象target
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> T convertOrigin(F source, T target, BiConsumer<F, T> callBack) {
        if (null == source || null == target) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        if (callBack != null) {
            // 回调
            callBack.accept(source, target);
        }
        return target;
    }

    /**
     * 采用Spring BeanUtils#copyProperties方式 将source转化为target，浅拷贝
     * 如果 targetSupplier为null会抛出{@link NullPointerException}
     *
     * @param source         转化来源对象
     * @param targetSupplier 获取目标对象的函数式接口实现
     * @param <F>            转化来源对象泛型
     * @param <T>            转来后对象的泛型
     * @return 转换后的对象target
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> T convert(F source, Supplier<T> targetSupplier) {
        return convert(source, targetSupplier, null);
    }

    /**
     * 采用Spring BeanUtils#copyProperties方式 将source转化为target，浅拷贝
     * 如果 targetSupplier为null会抛出{@link NullPointerException}
     *
     * @param source         转化来源对象
     * @param targetSupplier 获取目标对象的函数式接口实现
     * @param callBack       转换后的回调
     * @param <F>            转化来源对象泛型
     * @param <T>            转来后对象的泛型
     * @return 转换后的对象target
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> T convert(F source, Supplier<T> targetSupplier, BiConsumer<F, T> callBack) {
        if (null == source) {
            return null;
        }
        T target = Objects.requireNonNull(targetSupplier).get();
        BeanUtils.copyProperties(source, target);
        if (callBack != null) {
            // 回调
            callBack.accept(source, target);
        }
        return target;
    }

    /**
     * 采用Spring BeanUtils#copyProperties方式将List<F>转化为List<T>，浅拷贝
     *
     * @param fromList 转化来源list
     * @param type     转化后的Class
     * @param <F>      转化来源对象泛型
     * @param <T>      转化后的对象泛型
     * @return 转化后的list
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> List<T> convert(List<F> fromList, final Class<T> type) {
        return convert(fromList, type, null);
    }

    /**
     * 采用Spring BeanUtils#copyProperties方式将List<F>转化为List<T>，浅拷贝
     *
     * @param fromList 转化来源list
     * @param type     转化后的Class
     * @param callBack 转换后的回调
     * @param <F>      转化来源对象泛型
     * @param <T>      转化后的对象泛型
     * @return 转化后的list
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static <F, T> List<T> convert(List<F> fromList, final Class<T> type, BiConsumer<F, T> callBack) {
        if (fromList == null || fromList.isEmpty()) {
            return Collections.emptyList();
        }
        return convert(fromList, f -> {
            T t;
            try {
                t = type.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(f, t);
                if (callBack != null) {
                    // 回调
                    callBack.accept(f, t);
                }
            } catch (Exception e) {
                logger.error("convert error, f={}", JacksonUtil.bean2String(f));
                throw new RuntimeException(e);
            }
            return t;
        });
    }

//    /**
//     * 采用Spring BeanUtils#copyProperties方式将List<F>转化为List<T>，浅拷贝
//     *
//     * @param fromPageResult 转化来源list
//     * @param type     转化后的Class
//     * @param callBack 转换后的回调
//     * @param <F>      转化来源对象泛型
//     * @param <T>      转化后的对象泛型
//     * @return 转化后的list
//     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
//     */
//    public static <F, T> PageResult<T> convert(PageResult<F> fromPageResult, final Class<T> type, BiConsumer<F, T> callBack) {
//        return convert(fromPageResult, f -> {
//            T t;
//            try {
//                t = type.getDeclaredConstructor().newInstance();
//                BeanUtils.copyProperties(f, t);
//                if (callBack != null) {
//                    // 回调
//                    callBack.accept(f, t);
//                }
//            } catch (Exception e) {
//                LOGGER.error("convert error, f={}", JacksonUtil.bean2String(f));
//                throw new RuntimeException(e);
//            }
//            return t;
//        });
//    }

    /**
     * 采用自定义转换方式将List<F>转化为List<T><br/>
     * <p1>避免使用guava的com.google.common.collect.Lists#transform()，此方法返回的列表是fromList的转换视图,
     * 对fromList更改将反映在返回的列表中，反之亦然。由于函数是不可逆的，因此转换是单向的，返回的列表中不支持add 、 addAll和set方法。
     * </p1>
     *
     * @param fromList 转化来源list
     * @param function F转化成T的具体实现
     * @param <F>      转化来源对象泛型
     * @param <T>      转化后的对象泛型
     * @return 转化后的list
     */
    public static <F, T> List<T> convert(List<F> fromList, Function<F, T> function) {
        if (fromList == null || fromList.isEmpty()) {
            return Collections.emptyList();
        }
        return fromList.stream()
                .filter(Objects::nonNull)
                .map(function)
                .collect(Collectors.toList());
    }

//    /**
//     * 分页对象转换，PageResult<F> -> PageResult<T>
//     *
//     * @param fromPageResult 转化来源pageResult
//     * @param type 转化目标实体对象CLASS
//     * @param <F> 转化来源对象泛型
//     * @param <T> 转化后的对象泛型
//     * @return 转化后的分页对象PageResult
//     */
//    public static <F,T> PageResult<T> convert(PageResult<F> fromPageResult, Class<T> type) {
//        PageResult<T> pageResult =
//                new PageResult<>(fromPageResult.getCurrentPage(), fromPageResult.getPageSize(), fromPageResult.getTotalNum());
//        pageResult.setItems(convert(fromPageResult.getItems(), type));
//        return pageResult;
//    }

//    /**
//     * 采用自定义转换方式将PageResult<F>转化为PageResult<T><br/>
//     *
//     * @param fromPageResult 转化来源pageResult
//     * @param function F转化成T的具体实现
//     * @param <F>      转化来源对象泛型
//     * @param <T>      转化后的对象泛型
//     * @return 转化后的分页对象PageResult
//     */
//    public static <F,T> PageResult<T> convert(PageResult<F> fromPageResult, Function<F, T> function) {
//        PageResult<T> pageResult =
//                new PageResult<>(fromPageResult.getCurrentPage(), fromPageResult.getPageSize(), fromPageResult.getTotalNum());
//        pageResult.setItems(convert(fromPageResult.getItems(), function));
//        return pageResult;
//    }

//    /**
//     * 分页参数转换
//     *
//     * @param formPageQueryParam 转化来源PageQueryParam
//     * @param targetSupplier 获取目标对象的函数式接口实现
//     * @param <F> 转化来源对象泛型
//     * @param <T> 转化后的对象泛型
//     * @return 转化后的分页对象PageQueryParam
//     */
//    public static <F,T> PageQueryParam<T> convert(PageQueryParam<F> formPageQueryParam, Supplier<T> targetSupplier) {
//        PageQueryParam<T> pageQueryParam = new PageQueryParam<>();
//        pageQueryParam.setPage(formPageQueryParam.getPage());
//        pageQueryParam.setLimit(formPageQueryParam.getLimit());
//        pageQueryParam.setCount(formPageQueryParam.isCount());
//        pageQueryParam.setOrderBy(formPageQueryParam.getOrderBy());
//        T targetReq = Objects.requireNonNull(targetSupplier).get();
//        convert(formPageQueryParam.getReq(), targetReq);
//        pageQueryParam.setReq(targetReq);
//        return pageQueryParam;
//    }
//
//    /**
//     * 分页参数转换
//     *
//     * @param fromPageQueryParam 转化来源PageQueryParam
//     * @param function           F转化成T的具体实现
//     * @param <F>                转化来源对象泛型
//     * @param <T>                转化后的对象泛型
//     * @return 转化后的分页对象PageQueryParam
//     */
//    public static <F, T> PageQueryParam<T> convert(PageQueryParam<F> fromPageQueryParam, Function<F, T> function) {
//        PageQueryParam<T> pageQueryParam = new PageQueryParam<>();
//        pageQueryParam.setPage(fromPageQueryParam.getPage());
//        pageQueryParam.setLimit(fromPageQueryParam.getLimit());
//        pageQueryParam.setCount(fromPageQueryParam.isCount());
//        pageQueryParam.setOrderBy(fromPageQueryParam.getOrderBy());
//        pageQueryParam.setReq(function.apply(fromPageQueryParam.getReq()));
//        return pageQueryParam;
//    }
//
//    /**
//     * 分页参数转换
//     *
//     * @param formPageQueryParam 转化来源PageQueryParam
//     * @param targetSupplier 获取目标对象的函数式接口实现
//     * @param <F> 转化来源对象泛型
//     * @param <T> 转化后的对象泛型
//     * @return 转化后的分页对象PageQueryParam
//     */
//    public static <F,T> PageQueryParam<T> convert(PageQueryParam<F> formPageQueryParam, Supplier<T> targetSupplier, BiConsumer<F, T> callBack) {
//        PageQueryParam<T> result = convert(formPageQueryParam, targetSupplier);
//        if (callBack != null) {
//            // 回调
//            callBack.accept(formPageQueryParam.getReq(), result.getReq());
//        }
//        return result;
//    }


}
