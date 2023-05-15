import java.util.*;

public class Calculator2 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter an expression: ");
            String expression = sc.nextLine();

            double result = evaluateExpression(expression);
            System.out.println("Result: " + result);

        } catch (ArithmeticException e) {
            System.out.println("The values cannot be divided by zero");
        } catch (NumberFormatException e) {
            System.out.println("Expression entered is not in number format");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }catch (IndexOutOfBoundsException e) {
            System.out.println("index is out of bounds");
        }
    }

    public static double evaluateExpression(String expression) {
        expression = expression.replaceAll(" ", "");

        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        String number = "";

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1)))) {
                number += c;
            } else if (Character.isDigit(c) || c == '.') {
                number += c;
            } else {
                if (!number.isEmpty()) {
                    numbers.add(Double.parseDouble(number));
                    number = "";
                }
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    operators.add(c);
                } else if (c == '√') {
                    operators.add(c);
                    numbers.add(0.0); // Add a placeholder value for the square root operand
                } else {
                    throw new IllegalArgumentException("Invalid operator: " + c);
                }
            }
        }

        if (!number.isEmpty()) {
            numbers.add(Double.parseDouble(number));
        }

        // Evaluate square root operations first
        while (operators.contains('√')) {
            int index = operators.indexOf('√');
            double operand = numbers.get(index + 1);
            double result = evaluateOperation(operand, 0.0, '√');
            numbers.set(index, result);
            numbers.remove(index + 1);
            operators.remove(index);
        }

        // Evaluate remaining operations
        while (!operators.isEmpty()) {
            double a = numbers.remove(0);
            double b = numbers.remove(0);
            char op = operators.remove(0);
            double result = evaluateOperation(a, b, op);
            numbers.add(0, result);
        }

        return numbers.get(0);
    }



    public static double evaluateOperation(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                System.out.println("Hey there love");
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero not allowed");
                }
                return a / b;
            case '√':
                return Math.sqrt(a);
            default:
                throw new IllegalArgumentException("Invalid Operator: " + op);
        }
    }
}

