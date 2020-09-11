import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


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
                        // -2.5A + 3B
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
                        // System.out.println("eqn "+(i+1)+" str = " + str + " str1 = " + str1);
                        //System.out.println("str = " +str1);*/
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
                int len = parts[0].length();
                augmentedMatrix[i][Var.size()] = Double.parseDouble(parts[1]);
                int varNo = 0;
                for (String string : varArray) {
                    //System.out.print("Ch :" + ch);
                    int indexOfVar = parts[0].indexOf(string);
                    //System.out.print("Index = " + indexOfVar);

                    if (indexOfVar == -1) {
                        augmentedMatrix[i][varNo++] = 0;
                        continue;
                    } else {
                        // -2.5A + 3B
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

                        /*Collections.reverse(coeff);*/
                        String str = coeff.toString();

                        StringBuilder builder = new StringBuilder(coeff.size());
                        for (Character character : coeff) {
                            builder.append(character);
                        }

                        builder.reverse();

                        String str1 = builder.toString();
                        // System.out.println("eqn "+(i+1)+" str = " + str + " str1 = " + str1);
                        //System.out.println("str = " +str1);*/
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


        //return augmentedMatrix;

    }

    public static double[] GaussianElimination(double[][] A, double[] b) { // Solve Ax = b
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {

                try {
                    throw new ArithmeticException("Matrix is singular or nearly singular");
                } catch (ArithmeticException e) {
                    System.out.println("Matrix is singular or nearly singular");
                    System.exit(0);
                }
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }


    public static void main(String[] args) throws Exception {
        //System.out.println(Double.parseDouble("  "));
        File file = new File("in1.txt");
        FileWriter fileWriter = new FileWriter("output.txt");
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

            double x[] = GaussianElimination(matrixA, matrixB);
            System.out.println();
            for (int i = 0; i < x.length; i++) {
                System.out.println(varName[i] + " = " + x[i]);
                fileWriter.write(varName[i] + " = " + x[i]);
                fileWriter.write("\n");
            }

            fileWriter.close();

        }
    }
}
