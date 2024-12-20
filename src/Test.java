
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение (например, 2 + 3 или III * IV):");
        String input = scanner.nextLine();

        try {
            String[] parts = input.split(" ");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат ввода");
            }

            int num1 = convertInput(parts[0]);
            int num2 = convertInput(parts[2]);
            char operator = parts[1].charAt(0);

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
            }

            if ((isRoman(parts[0]) && !isRoman(parts[2])) || (!isRoman(parts[0]) && isRoman(parts[2]))) {
                throw new IllegalArgumentException("Используйте либо арабские, либо римские цифры");
            }

            int result = calculate(num1, num2, operator);

            if (isRoman(parts[0]) && isRoman(parts[2])) {
                System.out.println(toRoman(result));
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static int convertInput(String input) {
        try {
            if (isRoman(input)) {
                return fromRoman(input);
            } else {
                return Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа: " + input);
        }
    }

    private static int calculate(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Неверная арифметическая операция: " + operator);
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static String toRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Римские числа не могут быть отрицательными или равными нулю");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();
        int i = 12;

        while (number > 0) {
            int div = number / values[i];
            number %= values[i];
            for (int j = 0; j < div; j++) {
                result.append(romanSymbols[i]);
            }
            i--;
        }

        return result.toString();
    }

    private static int fromRoman(String roman) {
        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            int current = romanValue(roman.charAt(i));

            if (i + 1 < roman.length()) {
                int next = romanValue(roman.charAt(i + 1));

                if (current >= next) {
                    result += current;
                } else {
                    result += (next - current);
                    i++;
                }
            } else {
                result += current;
            }
        }

        return result;
    }

    private static int romanValue(char symbol) {
        switch (symbol) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Недопустимый символ в римском числе: " + symbol);
        }
    }
}
