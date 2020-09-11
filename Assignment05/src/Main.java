import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.stream;

public class Main {

    private static final double EPSILON = 1e-10;
    private static double augmentedMatrix[][];
    private static double matrixA[][];
    private static double matrixB[];
    private static String varName[];

    public static void processInput(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        String equation[] = new String[10];
        int n = 0;
        HashSet<Character> var = new HashSet<Character>();

        //count number of variables
        while (scanner.hasNext()) {
            equation[n++] = scanner.nextLine();
            System.out.println(equation[n - 1]);
            String parts[] = equation[n - 1].split("=");
            //String left = parts[0];
            int len = parts[0].length();
            for (int i = 0; i < len; i++) {
                char ch = parts[0].charAt(i);
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                    var.add(ch);
            }
        }


        int noOfVar;

        System.out.println();
        if (var.size() > 1) {
            noOfVar = var.size();
            varName = new String[noOfVar];
            augmentedMatrix = new double[n][var.size() + 1];
            char varArray[] = new char[var.size()];
            int x = 0;
            for (Character val : var) {
                varArray[x++] = val;
                varName[x - 1] = val.toString();
            }

            Arrays.sort(varArray);
            for (int i = 0; i < n; i++) {
                String parts[] = equation[i].split("=");
                //String left = parts[0];
                int len = parts[0].length();
                augmentedMatrix[i][var.size()] = Double.parseDouble(parts[1]);
                int varNo = 0;
                for (char ch : varArray) {
                    int indexOfVar = parts[0].indexOf((int) ch);
                    if (indexOfVar == -1) {
                        augmentedMatrix[i][varNo++] = 0;
                        continue;
                    } else {
                        ArrayList<Character> coeff = new ArrayList<>();
                        int sign = 1;
                        for (int it = indexOfVar - 1; it >= 0; it--) {
                            char cc = parts[0].charAt(it);
                            //System.out.print("cc = " + cc + " ");
                            if (cc == ' ')
                                continue;
                            else if (cc == '+') {
                                sign = 1;
                                break;
                            } else if (cc == '-') {
                                sign = -1;
                                break;
                            } else coeff.add(cc);
                        }

                        String str = coeff.toString();

                        StringBuilder builder = new StringBuilder(coeff.size());
                        for (Character character : coeff) {
                            builder.append(character);
                        }

                        builder.reverse();

                        String str1 = builder.toString();
                        if (str1.equals(" ") || str1.equals("")) {
                            str1 = "1.0";
                        }
                        //System.out.println(str1);
                        augmentedMatrix[i][varNo++] = sign * Double.parseDouble(str1);
                    }
                }

            }
        } else {
            char ch = var.iterator().next();
            HashSet<String> Var = new HashSet<>();
            for (int i = 0; i < n; i++) {
                String parts[] = equation[i].split("=");
                //String left = parts[0];
                int len = parts[0].length(), j = 0;
                while (j < len) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(ch);
                    boolean flag = false;
                    if (parts[0].charAt(j) == ch) {
                        for (int it = j + 1; it < len; it++) {
                            char ch1 = parts[0].charAt(it);
                            if (ch1 == '+' || ch1 == '-' || ch1 == ' ') {
                                j = it + 1;
                                break;
                            } else
                                builder.append(ch1);
                        }
                        flag = true;
                    }
                    //System.out.println(builder.toString());
                    if (flag)
                        Var.add(builder.toString());
                    j++;
                }

            }

            noOfVar = Var.size();
            varName = new String[noOfVar];

            augmentedMatrix = new double[n][Var.size() + 1];

            String varArray[] = new String[Var.size()];
            int x = 0;
            for (String val : Var) {
                varArray[x++] = val;
                varName[x - 1] = val;
                //System.out.println("val = " +val);
            }

            Arrays.sort(varArray);

            for (int i = 0; i < n; i++) {
                String parts[] = equation[i].split("=");
                //String left = parts[0];
                augmentedMatrix[i][Var.size()] = Double.parseDouble(parts[1]);
                int varNo = 0;
                for (String string : varArray) {
                    //System.out.print("Ch :" + ch);
                    int indexOfVar = parts[0].indexOf(string);
                    if (indexOfVar == -1) {
                        augmentedMatrix[i][varNo++] = 0;
                        continue;
                    } else {
                        ArrayList<Character> coeff = new ArrayList<>();
                        int sign = 1;
                        for (int it = indexOfVar - 1; it >= 0; it--) {
                            char cc = parts[0].charAt(it);
                            //System.out.print("cc = " + cc + " ");
                            if (cc == ' ')
                                continue;
                            else if (cc == '+') {
                                sign = 1;
                                break;
                            } else if (cc == '-') {
                                sign = -1;
                                break;
                            } else coeff.add(cc);
                        }

                        String str = coeff.toString();

                        StringBuilder builder = new StringBuilder(coeff.size());
                        for (Character character : coeff) {
                            builder.append(character);
                        }

                        builder.reverse();

                        String str1 = builder.toString();
                        if (str1.equals(" ") || str1.equals("")) {
                            str1 = "1.0";
                        }
                        //System.out.println(str1);
                        augmentedMatrix[i][varNo++] = sign * Double.parseDouble(str1);
                    }
                }
            }
        }

        System.out.println("Augmented Matrix: ");

        for (int a = 0; a < n; a++) {
            for (int b = 0; b <= noOfVar; b++) {
                System.out.print(augmentedMatrix[a][b] + "     ");
            }
            System.out.println();
        }

        System.out.println();
    }


    static void print(double[][] m) {
        stream(m).forEach(a -> {
            stream(a).forEach(n -> System.out.printf(Locale.US, "%9.6f ", n));
            System.out.println();
        });
        System.out.println();
    }


    public static String round(double val) {
        return String.format("%9.5f", val);
    }

    // 0-indexing
    public static double get_ithRoot(int n, double current[]){
        if (Math.abs(augmentedMatrix[n][n] - 0) <= EPSILON)
            throw new ArithmeticException("Diagonal Element is zero!");
        double sum = 0.0;
        int noOfEqn = augmentedMatrix.length;
        int noOfVar = augmentedMatrix[0].length - 1;

        for (int i = 0; i < noOfVar; i++) {
            if (i == n)
                continue;
            sum += current[i] * augmentedMatrix[n][i];
        }

        return (augmentedMatrix[n][noOfVar] - sum) / augmentedMatrix[n][n];
    }

    public static double getError(double curr, double prev) {
        return Math.abs((curr - prev) / curr) * 100;
    }

    public static void main(String[] args) throws Exception {

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        //System.setOut(out);

        File file = new File("input.txt");
        processInput(file);

        int noOfEqn = augmentedMatrix.length;
        int noOfVar = augmentedMatrix[0].length - 1;

        matrixB = new double[noOfEqn];
        matrixA = new double[noOfEqn][noOfVar];

        //System.out.println("Var : " + noOfVar + " Eqn : " + noOfEqn);

        if (noOfEqn != noOfVar) {
            try {
                throw new ArithmeticException("Number of equation is not equal to number of variable.");

            } catch (ArithmeticException e) {
                System.out.println("Number of equation is not equal to number of variable.");
                System.exit(0);

            }
        } else {
            for (int i = 0; i < noOfEqn; i++) {
                matrixB[i] = augmentedMatrix[i][noOfVar];
                for (int j = 0; j < noOfVar; j++)
                    matrixA[i][j] = augmentedMatrix[i][j];
            }



            Scanner scanner = new Scanner(System.in);
            double var[] = new double[noOfVar];

            //System.setOut(System.out);

            System.out.println("Enter initial Guess for each of the root");
            for (int i = 0; i < noOfVar; i++) {
                System.out.print(varName[i] + " = ");
                var[i] = scanner.nextDouble(); //take initial guess;
                System.out.println();
            }

            System.out.print("Enter number of iterations: ");
            int noOfIter;
            noOfIter = scanner.nextInt();

            System.out.println("Sir, please check the \"output.txt\" file for output in pwd.");


            System.setOut(out);
            System.out.print("Iter No.");
            for (int i = 0; i < noOfVar; i++) {
                System.out.print("\t" + varName[i] + "\t\t RAE" + "(" + varName[i] + ")" + "\t");
            }
            System.out.println();
            for (int i = 0; i < noOfIter; i++) {
                System.out.print("\t" + (i + 1) + "\t");
                for (int j = 0; j < noOfVar; j++) {
                    double prev = var[j];
                    double curr = get_ithRoot(j, var);
                    var[j] = curr;
                    System.out.print(round(curr) + "\t" + "  " + round(getError(curr, prev)) + "\t");
                }

                System.out.println();
            }


            System.out.println("\n" + "Solution:" + "\n");
            for (int i = 0; i < noOfVar; i++) {
                System.out.println(varName[i] + " = " + round(var[i]));
            }


        }
    }
}