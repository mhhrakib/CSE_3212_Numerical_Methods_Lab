import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Test {

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


    static double dotProduct(double[] a, double[] b) {
        return range(0, a.length).mapToDouble(i -> a[i] * b[i]).sum();
    }

    static double[][] matrixMul(double[][] A, double[][] B) {
        double[][] result = new double[A.length][B[0].length];
        double[] aux = new double[B.length];

        for (int j = 0; j < B[0].length; j++) {

            for (int k = 0; k < B.length; k++)
                aux[k] = B[k][j];

            for (int i = 0; i < A.length; i++)
                result[i][j] = dotProduct(A[i], aux);
        }
        return result;
    }

    static double[][] pivotize(double[][] m) {
        int n = m.length;
        double[][] id = range(0, n).mapToObj(j -> range(0, n)
                .mapToDouble(i -> i == j ? 1 : 0).toArray())
                .toArray(double[][]::new);

        for (int i = 0; i < n; i++) {
            double maxm = m[i][i];
            int row = i;
            for (int j = i; j < n; j++)
                if (m[j][i] > maxm) {
                    maxm = m[j][i];
                    row = j;
                }

            if (i != row) {
                double[] tmp = id[i];
                id[i] = id[row];
                id[row] = tmp;
            }
        }
        return id;
    }

    static double[][][] lu(double[][] A) {
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        double[][] P = pivotize(A);
        double[][] A2 = matrixMul(P, A);

        for (int j = 0; j < n; j++) {
            L[j][j] = 1;
            for (int i = 0; i < j + 1; i++) {
                double s1 = 0;
                for (int k = 0; k < i; k++)
                    s1 += U[k][j] * L[i][k];
                U[i][j] = A2[i][j] - s1;
            }
            for (int i = j; i < n; i++) {
                double s2 = 0;
                for (int k = 0; k < j; k++)
                    s2 += U[k][j] * L[i][k];
                L[i][j] = (A2[i][j] - s2) / U[j][j];
            }
        }
        return new double[][][]{L, U, P};
    }

    static void print(double[][] m) {
        stream(m).forEach(a -> {
            stream(a).forEach(n -> System.out.printf(Locale.US, "%9.6f ", n));
            System.out.println();
        });
        System.out.println();
    }


    public static double[] backwardSubstitution(double A[][], double b[]) {
        int n = b.length;
        double x[] = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }

            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    public static double[] forwardSubstitution(double A[][], double b[]) {
        int n = b.length;
        Collections.reverse(Arrays.asList(A));
        /*System.out.println("printing A");
        print(A);*/
        double tmp[] = b;
        b = reverse(tmp);
        for (int i = 0; i < n; i++) {
            double[] temp = A[i];
            A[i] = reverse(temp);
        }
        double x[] = backwardSubstitution(A, b);
        return reverse(x);
    }

    public static double[] reverse(double[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            double temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    public static String round(double val) {
        return String.format("%.6f", val);
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Sir, please check the \"output.txt\" file for output in pwd.");
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);

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


            double[][] a = {{1.0, 3, 5}, {2.0, 4, 7}, {1.0, 1, 0}};

            double[][] b = {{11.0, 9, 24, 2}, {1.0, 5, 2, 6}, {3.0, 17, 18, 1},
                    {2.0, 5, 7, 1}};

            double[][][] LUA = lu(matrixA);

            System.out.println("Lower Traiangular Matrix: " + "\n");
            print(LUA[0]);
            System.out.println("Upper Triangular Matrix: " + "\n");
            print(LUA[1]);

            double L[][] = LUA[0];
            double U[][] = LUA[1];
            double P[][] = LUA[2];
            //print(P);

            double temp[] = new double[matrixB.length];
            for (int i = 0; i < matrixB.length; i++)
                temp[i] = matrixB[i];
            for (int i = 0; i < P.length; i++) {
                for (int j = 0; j < P.length; j++) {
                    if (P[i][j] >= 0.1) {
                        matrixB[i] = temp[j];
                        break;
                    }
                }
            }
            //solving Ly = b
            double y[] = forwardSubstitution(L, matrixB);
            // solving Ux = y;
            double x[] = backwardSubstitution(U, y);
            System.out.println("Solution:" + "\n");
            for (int i = 0; i < noOfVar; i++) {
                System.out.println(varName[i] + " = " + round(x[i]));
            }
        }
    }
}