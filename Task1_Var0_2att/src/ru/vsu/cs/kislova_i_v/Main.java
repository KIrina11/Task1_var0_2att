package ru.vsu.cs.kislova_i_v;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] A = readIntArray2FromFile("input.txt");
        int detA = countMatrixDet(A, A.length);
        System.out.println(detA);
    }

    public static int[] toPrimitive(Integer[] arr) {
        if (arr == null) {
            return null;
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            // автоматическая распаковка из объекта
            result[i] = arr[i];
        }
        return result;
    }

    public static int[] toIntArray(String str) {
        Scanner scanner = new Scanner(str);
        scanner.useLocale(Locale.ROOT);
        scanner.useDelimiter("(\\s|[,;])+");
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }

        // из List<Integer> можно получить Integer[]
        Integer[] arr = list.toArray(new Integer[0]);
        // Integer[] -> int[]
        return Main.toPrimitive(arr);
    }

    /**
     * Конвертация массива строк в двухмерный массив чисел int[][]
     */
    public static int[][] toIntArray2(String[] lines) {
        int[][] arr2 = new int[lines.length][];
        for (int r = 0; r < lines.length; r++) {
            arr2[r] = toIntArray(lines[r]);
        }
        return arr2;
    }

    /**
     * Чтение всего текстового файла в массив строк
     */
    public static String[] readLinesFromFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            // обязательно, чтобы закрыть открытый файл
        }
        return lines.toArray(new String[0]);
    }

    /**
     * Чтение двухмерного массива из текстового файла
     */
    public static int[][] readIntArray2FromFile(String fileName) {
        try {
            return toIntArray2(readLinesFromFile(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static int countMatrixDet(int[][] A, int n) {
        int detA = 0;

        if (n == 1) {
            return A[0][0];
        } else if (n == 2) {
            return A[0][0] * A[1][1] - A[0][1] * A[1][0];
        } else {
            for (int i = 0; i < n; i++) {
                int k = 1;
                for (int j = 0; j < n; j++) {
                    detA += (int) (Math.pow(-1, k + 1) * A[i][j] * countMatrixDet(getMinor(A, i, j),n-1));
                    k += 1;
                }
            }
        }
        return detA;
    }

    //нахождение минора - удаление i-ой строки и j-ого столбца
    public static int[][] getMinor(int[][] array, int row, int col) {
        int offsetRow = 0; //Смещение индекса строки в матрице
        int offsetCol = 0; //Смещение индекса столбца в матрице
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            //Пропустить row-ую строку
            if (i == row) {
                offsetRow = 1; //Как только встретили строку, которую надо пропустить, делаем смещение для исходной матрицы
            }
            for (int j = 0; j < n - 1; j++) {
                //Пропустить col-ый столбец
                if (j == col) {
                    offsetCol = 1; //Встретили нужный столбец, пропускаем его смещением
                    break;
                }
                array[i][j] = array[i + offsetRow][j + offsetCol];
            }
        }
        return array;
    }
}
