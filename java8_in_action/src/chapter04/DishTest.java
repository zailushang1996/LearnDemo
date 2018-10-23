package chapter04;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiuZX liuzhixiang
 * DATE   2018/10/23
 */

public class DishTest {

    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH) );

    public static void main(String[] args) {

        new DishTest().testAtJava7();
        System.out.println("=============");

        new DishTest().testAtJava8();

        new DishTest().test2();
        System.out.println("=======>test3");
        new DishTest().test3();
        System.out.println("=======>test4");
        new DishTest().test4();
        System.out.println("=======>test5");
        new DishTest().test5();
        System.out.println("=======>test6");
        new DishTest().test6();
        System.out.println("=======>test7");
        new DishTest().test7();
        System.out.println("=======>test8");
        new DishTest().test8();
        System.out.println("=======>test9");
        new DishTest().test9();
    }

    /**
     * 使用java7对集合进行过滤、排序等操作
     */
    private void testAtJava7() {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish lowCaloricDish : lowCaloricDishes) {
            lowCaloricDishesName.add(lowCaloricDish.getName());
        }
        System.out.println(lowCaloricDishesName);

    }

    /**
     * 使用Java8对集合进行过滤、排序等操作
     */
    private void testAtJava8() {
        List<String> lowCaloricDishesName = menu.stream().filter(d -> d.getCalories()<400).sorted(Comparator.comparingInt(Dish::getCalories))
                .map(Dish::getName).collect(Collectors.toList());
        System.out.println(lowCaloricDishesName);
    }

    private void test1() {

        List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegetarianMenu);
    }

    private void test2() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);
    }

    private void test3() {
        List<Dish> dishes = menu.stream().filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(dishes.toString());
    }

    private void test4() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(dishes);
    }

    private void test5() {
        List<Dish> meatMenu = menu.stream().filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(meatMenu);
    }

    private void test6() {
        List<String> collect = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private void test7() {
        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        List<Integer> collect = words.stream().map(String::length)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private void test8() {
        String[] words ={"GoodBye","World"};
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<Stream<String>> collect = Arrays.stream(words)
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);


        List<String> result11 = Arrays.stream(words).map(word -> word.split(""))
                .flatMap(new Function<String[], Stream<String>>() {
                    @Override
                    public Stream<String> apply(String[] strings) {
                        return Arrays.stream(strings);
                    }
                })
                .distinct()
                .collect(Collectors.toList());


        List<String> result = Arrays.stream(words).map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(result);

        List<int[]> result1 = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());


        List<int[]> collect2 = numbers1.stream().flatMap(new Function<Integer, Stream<int[]>>() {
            @Override
            public Stream<int[]> apply(Integer i) {
                return numbers2.stream().map(new Function<Integer, int[]>() {
                    @Override
                    public int[] apply(Integer j) {
                        return new int[]{i, j};
                    }
                });
            }
        }).collect(Collectors.toList());



        System.out.println(result1.toString());
        System.out.println(collect2);
    }


    private void test9() {
        menu.stream().filter(Dish::isVegetarian)
                .findAny().ifPresent(dish -> System.out.println(dish.getName()));
    }

}
