package chapter03;

import java.util.function.Function;

/**
 * @author LiuZX liuzhixiang
 * DATE   2018/10/23
 */

public class FunctionCompose {
    public static void main(String[] args) {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        Integer result = h.apply(1);
        System.out.println(result);

        Function<Integer, Integer> compose = g.compose(f);
        Integer compose_result = compose.apply(2);
        System.out.println("compose_result:"+compose_result);

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline = addHeader.andThen(Letter::addFooter).andThen(Letter::checkSpelling);
        String trans_result = transformationPipeline.apply("liuzhixiangnihao labda 你哈");
        System.out.println(trans_result);
    }



}
