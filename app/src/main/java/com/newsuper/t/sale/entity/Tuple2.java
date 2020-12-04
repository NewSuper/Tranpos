package com.newsuper.t.sale.entity;

public class Tuple2<A, B> {
    public final A first;
    public final B second;

    public Tuple2(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * 创建一个包含2个元素的元组
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @param <A>    第一个元素类型
     * @param <B>    第二个元素类型
     * @return 元组
     */
    public static <A, B> Tuple2<A, B> with(final A first, final B second) {
        return new Tuple2<>(first, second);
    }

    /**
     * 反转元组
     *
     * @return 反转后的元组
     */
    public Tuple2<B, A> reverse() {
        return new Tuple2<>(this.second, this.first);
    }
}
