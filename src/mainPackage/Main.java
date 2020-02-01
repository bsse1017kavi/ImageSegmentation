package mainPackage;

import imagePackage.Segmenter;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Segmenter segmenter = new Segmenter();
        segmenter.initialize();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of segments:");
        int k = scanner.nextInt();

        segmenter.segment(k);
    }
}
