package chapter03;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.function.Function;

/**
 * @author LiuZX liuzhixiang
 * DATE   2018/10/22
 */

public class ExecuteAround {
    public static void main(String[] args) throws IOException{
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("--------------");
        String oneLine = processFile(BufferedReader::readLine);
        System.out.println(oneLine);

        System.out.println("--------------");
        String twoLine = processFile(bufferedReader -> bufferedReader.readLine() + bufferedReader.readLine());
        System.out.println(twoLine);

        Comparator.comparing(Apple::getWeight);

        Comparator.comparing(new Function<Apple, Integer>() {
            @Override
            public Integer apply(Apple apple) {
                return apple.getWeight();
            }
        });


    }

    public static String processFileLimited() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("./data.txt"));
        return bf.readLine();
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("./data.txt"));
        return p.process(bf);
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor{
        String process(BufferedReader bufferedReader) throws IOException;
    }

    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple() {
        }

        public Apple(int weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
