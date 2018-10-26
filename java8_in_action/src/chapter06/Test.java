package chapter06;

import chapter04.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author LiuZX liuzhixiang
 * DATE   2018/10/25
 */

public class Test {
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
        System.out.println("--------------------------------test1");
        new Test().test1();

        System.out.println("--------------------------------test2");
        new Test().test2();

        System.out.println("--------------------------------test3");
        new Test().test3();

        System.out.println("--------------------------------test4");
        new Test().test4();

    }

    public void test1() {
        Optional<Dish> maxCalori = menu.stream().max(Comparator.comparing(Dish::getCalories));
        System.out.println(maxCalori.get().toString());
        Integer collect = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("卡路里求和："+collect);
    }

    public void test2() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(new ArrayList<>(), (List<Integer> l, Integer e) -> {
            System.out.println("L:" + l);
            System.out.println("e:" + e);
            l.add(e);
            return l;
        }, (List<Integer> l1, List<Integer> l2) -> {
            l1.addAll(l2);
            return l1;
        });
        System.out.println(numbers);

        Integer sum = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));

    }

    private void test3(){
        Map<CaloricLevel, List<Dish>> collect = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        }));
        System.out.println(collect);
        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);
    }

    private void test4(){
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(
                                dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                else return CaloricLevel.FAT; },toCollection(HashSet::new)
                                 )));
        System.out.println(caloricLevelsByType);
    }
}
