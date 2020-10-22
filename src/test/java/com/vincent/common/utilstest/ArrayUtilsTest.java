package com.vincent.common.utilstest;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Map;

/**
 * commons-lang3 下的 ArrayUtils 的用法总结
 */
public class ArrayUtilsTest {
    @Test
    public void t() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] arr2 = {8, 7, 6, 5, 4, 3, 2, 1};
        int[] a = null;

        //isEmpty(final Object[] array)：判断数组是否为空；
        System.out.println("isEmpty -> ：" + ArrayUtils.isEmpty(arr));
        System.out.println("isEmpty -> ：" + ArrayUtils.isEmpty(a));

        //isNotEmpty(final float[] array)：判断数组是否不为空；
        System.out.println("isNotEmpty -> ：" + ArrayUtils.isNotEmpty(a));
        System.out.println("isNotEmpty -> ：" + ArrayUtils.isNotEmpty(arr));

        //isSameLength(final char[] array1, final char[] array2)：判断两个数组的长度是否相同，要同类型；
        System.out.println("isSameLength -> ：" + ArrayUtils.isSameLength(arr, arr2));

        //isSameType(final Object array1, final Object array2)：判断两个数组的类型是否相同；
        System.out.println("isSameType -> ：" + ArrayUtils.isSameType(arr, arr2));

        //isSorted(final int[] array)：判断数组是否以自然顺序排序；
        System.out.println("isSorted -> ：" + ArrayUtils.isSorted(arr));
        System.out.println("isSorted -> ：" + ArrayUtils.isSorted(arr2));
        System.out.println();


        int[] array = {1, 2, 3};
        //add(final T[] array, final T element)：该方法向指定的数组中添加一个元素；
        int[] add = ArrayUtils.add(array, 7);
        System.out.println("add -> ：" + ArrayUtils.toString(add));

        //addAll(final T[] array1, final T... array2)：合并两个数组；
        int[] arrayAll = {4, 5, 6};
        int[] addAll = ArrayUtils.addAll(arrayAll, 7, 8, 9);
        System.out.println("addAll -> ：" + ArrayUtils.toString(addAll));

        int[] array2 = {1, 3, 5, 7, 9, 9, 99};
        //remove(final T[] array, final int index)：移除数组红指定索引位置的元素；
        int[] remove = ArrayUtils.remove(array2, 2);
        System.out.println("remove -> ：" + ArrayUtils.toString(remove));
        //removeAll(final char[] array, final int... indices)：移除数组红指定的多个索引位置的元素；
        int[] removeAll = ArrayUtils.removeAll(array2, 0, 1, 2);
        System.out.println("removeAll -> ：" + ArrayUtils.toString(removeAll));
        //removeElement(final char[] array, final char element)：从数组中删除第一次出现的指定元素；
        int[] removeElement = ArrayUtils.removeElement(array2, 9);
        System.out.println("removeElement -> ：" + ArrayUtils.toString(removeElement));
        //removeAllOccurences(final char[] array, final char element)：从数组中移除指定的元素；
        int[] removeAllOccurences = ArrayUtils.removeAllOccurences(array2, 99);
        System.out.println("removeAllOccurences -> ：" + ArrayUtils.toString(removeAllOccurences));
        //removeElements(final char[] array, final char... values)：从数组中移除指定数量的元素，返回新数组；
        int[] removeElements = ArrayUtils.removeElements(array2, 1, 2, 3);
        System.out.println("removeAllOccurences -> ：" + ArrayUtils.toString(removeElements));

        //getLength(final Object array)：获取数组的长度；
        System.out.println("getLength -> ：" + ArrayUtils.getLength(array2));

        //contains(final Object[] array, final Object objectToFind)：判断数组中是否包含某一个元素；
        System.out.println("contains -> ：" + ArrayUtils.contains(array2, 7));
        System.out.println("contains -> ：" + ArrayUtils.contains(array2, 33));

        //indexOf(final Object[] array, final Object objectToFind)：查找数组中是否存在某元素，返回索引位置；
        System.out.println("indexOf -> ：" + ArrayUtils.indexOf(array2, 9));
        System.out.println("indexOf -> ：" + ArrayUtils.indexOf(array2, 9, 3));

        //lastIndexOf(final Object[] array, final Object objectToFind)：从尾部开始查找指定元素；
        System.out.println("lastIndexOf -> ：" + ArrayUtils.lastIndexOf(array2, 9));
        System.out.println("lastIndexOf -> ：" + ArrayUtils.lastIndexOf(array2, 9, 3));

        //insert(final int index, final T[] array, final T... values)：向数组指定索引位置添加元素；
        int[] insert = ArrayUtils.insert(2, array2, 33);
        System.out.println("insert -> ：" + ArrayUtils.toString(insert));
        System.out.println();

        //nullToEmpty(final String[] array)：将 null 数组转换为对应类型的空数组；
        String[] strArray = null;
        String[] strings = ArrayUtils.nullToEmpty(strArray);
        System.out.println("nullToEmpty -> ：" + ArrayUtils.toString(strings));
        System.out.println();

        //toMap(final Object[] array)：将二维数组转换为 map，一维数组转换抛出异常；
        String[][] map = {{"name", "zhangsan"}, {"age", "23"}, {"money", "6700"}};
        Map<Object, Object> toMap = ArrayUtils.toMap(map);
        toMap.forEach((k, v) -> System.out.println("toMap -> ：key => " + k + "，value => " + v));
        System.out.println();

        //reverse(final char[] array)：反转数组，不返回新数组，可以指定反转的区域；
        ArrayUtils.reverse(array2);
        System.out.println("reverse -> ：" + ArrayUtils.toString(array2));

        //subarray(final char[] array, int startIndexInclusive, int endIndexExclusive)：数组的截取，包头不包尾；
        int[] sub = {7, 5, 3, 9, 8, 4};
        int[] subarray = ArrayUtils.subarray(sub, 2, 4);
        System.out.println("subarray -> ：" + ArrayUtils.toString(subarray));

        //swap(final char[] array, final int offset1, final int offset2)：交换数组中指定位置的两个元素；
        int[] swap = {1, 2, 3};
        ArrayUtils.swap(swap, 1, 0);
        System.out.println("swap -> ：" + ArrayUtils.toString(swap));
        int[] swap2 = {1, 2, 3};
        ArrayUtils.swap(swap2, 0, 5);
        System.out.println("swap2 -> ：" + ArrayUtils.toString(swap));
        int[] swap3 = {1, 2, 3};
        ArrayUtils.swap(swap, -1, 1);
        System.out.println("swap3 -> ：" + ArrayUtils.toString(swap3));

        //toObject(final int[] array)：将原始数据类型的数组转换为对象类型的数组；
        int[] toObject = {12, 11, 14};
        Integer[] integers = ArrayUtils.toObject(toObject);
        System.out.println("toObject -> ：" + ArrayUtils.toString(toObject));

        //toPrimitive(final Integer[] array)：将对象数据类型的数组转换为原始数据类型的数组；
        int[] ints = ArrayUtils.toPrimitive(integers);
        System.out.println("toPrimitive -> ：" + ArrayUtils.toString(ints));

        //toStringArray(final Object[] array)：将 Object 类型的数组转换为 String 类型的数组；
        String[] toStringArray = ArrayUtils.toStringArray(integers);
        System.out.println("toStringArray -> ：" + ArrayUtils.toString(toStringArray));
    }
}
