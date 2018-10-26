package chapter05;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LiuZX liuzhixiang
 * DATE   2018/10/23
 */

public class Practice {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario","Milan");
    Trader alan = new Trader("Alan","Cambridge");
    Trader brian = new Trader("Brian","Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String[] args) {
        System.out.println("============================================================test1");
        new Practice().test1();
        System.out.println("============================================================test2");
        new Practice().test2();
        System.out.println("============================================================test3");
        new Practice().test3();
        System.out.println("============================================================test5");
        new Practice().test5();
        System.out.println("============================================================test6");
        new Practice().test6();
        System.out.println("============================================================test7");
        new Practice().test7();
        System.out.println("============================================================test8");
        new Practice().test8();
        System.out.println("============================================================testReduce");
        new Practice().testReduce();
    }

    private void test1() {
        List<Transaction> result = transactions.stream().filter(y -> y.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(result);
    }

    private void test2() {
        List<String> result = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(result);
    }

    /**
     * 查找所有来自于剑桥的交易员姓名，并按姓名排序。
     */
    private void test3() {
        List<String> cambridge = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .distinct()
                .map(Trader::getName)
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        System.out.println(cambridge);
    }

    /**
     * 查找所有来自于剑桥的交易员，并按姓名排序。
     */
    private void test4() {
        List<Trader> cambridge = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序
     */
    private void test5() {
        String result = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a + b);
        System.out.println(result);
    }

    /**
     * 有没有交易员是在米兰工作的
     */
    private void test6() {
        boolean hasTraderInMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(hasTraderInMilan);
    }

    private void test7() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(System.out::println);
    }

    private void test8() {
        Optional<Integer> result = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(result.isPresent() ? result.get() : "no max value");
    }

    private void testReduce() {
//        ArrayList<Integer> accResult_ = Arrays.asList(1,2,3,4,5).parallelStream()
//                .reduce(new ArrayList<Integer>(),
//                        new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
//                            @Override
//                            public ArrayList<Integer> apply(ArrayList<Integer> acc, Integer item) {
//
//                                acc.add(item);
//                                System.out.println("item: " + item);
//                                System.out.println("acc+ : " + acc);
//                                System.out.println("BiFunction");
//                                return acc;
//                            }
//                        }, new BinaryOperator<ArrayList<Integer>>() {
//                            @Override
//                            public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
//                                System.out.println("BinaryOperator");
//                                acc.addAll(item);
//                                System.out.println("item: " + item);
//                                System.out.println("acc+ : " + acc);
//                                System.out.println("--------");
//                                return acc;
//                            }
//                        });
//        System.out.println("accResult_: " + accResult_);

        ArrayList<List<String>> strings = new ArrayList<>();
        strings.add(Arrays.asList("1", "2", "3", "4"));
        strings.add(Arrays.asList("2", "3", "4", "5"));
        strings.add(Arrays.asList("3", "4", "5", "6"));

        // 非并行流
        Integer reduce1 = strings.stream().flatMap(e -> e.stream()).reduce(0,
                (acc, e) -> acc + Integer.valueOf(e), (u, t) -> {
                    // 非并行流，不会执行第三个参数
                    System.out.println("u----:" + u);
                    // 这里的返回值并没有影响返回结果
                    return null;
                });
        System.out.println("reduce1:" + reduce1);

        // 并行流
        Integer reduce2 = strings.parallelStream().flatMap(e -> e.stream()).reduce(0,
                (acc, e) ->{
                    System.out.println("acc---:" + acc);
                    System.out.println("e---:" + e);
                    return acc + Integer.valueOf(e);}, (u, t) -> {
                    // u，t分别为并行流每个子任务的结果
                    System.out.println("u----:" + u);
                    System.out.println("t----:" + t);
                    return u + t;
                });
        System.out.println("reduce2:" + reduce2);

    }



}
