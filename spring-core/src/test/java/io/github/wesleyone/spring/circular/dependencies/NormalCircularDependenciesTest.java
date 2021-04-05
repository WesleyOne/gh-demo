package io.github.wesleyone.spring.circular.dependencies;

import io.github.wesleyone.spring.circular.dependencies.beans.BeanA;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanB;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author http://wesleyone.github.io/
 */
public class NormalCircularDependenciesTest {

    @Test
    public void test_normal() {
        // 实例化BeanA
        BeanA a = new BeanA();
        // 实例化BeanB
        BeanB b = new BeanB();
        // 实例b设置属性a，完成初始化
        b.setA(a);
        // 实例a设置属性b，完成初始化
        a.setB(b);
        Assert.assertEquals(a, b.getA());
        Assert.assertEquals(b, a.getB());
    }

    @Test
    public void test_normal_like_spring() throws IllegalAccessException, InstantiationException {
        // 一级缓存，存单例名称-最终完整初始化后的对象
        Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
        // 二级缓存，存单例名称-仅完成实例化的对象
        Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
        // 三级缓存, 存单例名称-类的实例化方法
        Map<String, Supplier<?>> singletonFactories = new ConcurrentHashMap<>(16);
        // 缓存创建中标记，存单例名称
        Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap(16));

        /*
         * 实例化BeanA
         */

        // 获取名称为a的BeanA单例对象(getSingleton)
        // 查询一级缓存不存在"a"，但是没有创建中标记，则直接去创建
        Assert.assertFalse((singletonObjects.get("a") == null
                && singletonsCurrentlyInCreation.contains("a")));
        // 以上获取不到，所以要开始创建"a",先打个创建标记
        singletonsCurrentlyInCreation.add("a");
        // 实例化BeanA（instantiateBean）
        BeanA a = BeanA.class.newInstance();
        // 存入三级缓存，保存获取"a"实例的方法
        singletonFactories.put("a", () -> a);

        /*
         * 实例化BeanB
         */

        // 组装属性b（populateBean）
        // 获取名称为b的BeanB单例对象(getSingleton)
        // 查询一级缓存不存在"b"，但是没有创建中标记，则直接去创建
        Assert.assertFalse((singletonObjects.get("b") == null
                && singletonsCurrentlyInCreation.contains("b")));
        // 以上获取不到，所以要开始创建"b",先打个创建标记
        singletonsCurrentlyInCreation.add("b");
        // 实例化BeanB（instantiateBean）
        BeanB b = BeanB.class.newInstance();
        // 存入三级缓存，保存获取"b"实例的方法
        singletonFactories.put("b", () -> b);

        /*
         * 实例b设置属性a，完成b初始化
         */

        // 组装属性a（populateBean）
        // 获取名称为a的单例对象(getSingleton)
        // 查询一级缓存不存在"a"，并且有**创建中标记**，则继续查询下级缓存
        Assert.assertTrue((singletonObjects.get("a") == null && singletonsCurrentlyInCreation.contains("a")));
        // 继续查询二级缓存不存在"a"，则继续查询下级缓存
        Assert.assertTrue(earlySingletonObjects.get("a") == null);
        // 继续查询三级缓存，存在"a"，则使用缓存的方法获取"a"的实例化对象
        Assert.assertTrue(singletonFactories.get("a") != null);
        Object instantiateBeanOfA = singletonFactories.get("a").get();
        // 然后将"a"的实例添加到二级缓存，并从三级缓存中删除
        earlySingletonObjects.put("a",instantiateBeanOfA);
        singletonFactories.remove("a");
        // 设置b实例的属性a
        b.setA((BeanA) instantiateBeanOfA);
        // 删除b创建中标记
        Assert.assertTrue(singletonsCurrentlyInCreation.remove("b"));
        // 然后将"b"的实例添加到一级缓存，并从二、三级缓存中删除
        singletonObjects.put("b", b);
        earlySingletonObjects.remove("b");// b实际没有二级缓存
        singletonFactories.remove("b");

        /*
         * 实例a设置属性b，完成a初始化
         */

        // 设置a实例的属性b
        a.setB((BeanB) singletonObjects.get("b"));
        // 删除a创建中标记
        Assert.assertTrue(singletonsCurrentlyInCreation.remove("a"));
        // 然后将"a"的实例添加到一级缓存，并从二级缓存中删除
        singletonObjects.put("a", a);
        earlySingletonObjects.remove("a");
        singletonFactories.remove("a");

        // 校验
        Assert.assertEquals(singletonObjects.get("a"), ((BeanB)singletonObjects.get("b")).getA());
        Assert.assertEquals(singletonObjects.get("b"), ((BeanA)singletonObjects.get("a")).getB());
    }
}
