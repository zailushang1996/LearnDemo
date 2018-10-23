package chapter03;

import jdk.nashorn.internal.ir.ReturnNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
}
