import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


class Matrix {
    private int M;             // number of rows
    private int N;             // number of columns
    private double[][] data;   // M-by-N array

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    public Matrix(double data[][]) {
        this.M = data.length;
        this.N = data[0].length;
        this.data = data;
    }

    // create A column Matrix
    public Matrix(double X[]) {
        //System.out.println(X.length);
        //Matrix B = new Matrix(X.length,1);
        this.M = X.length;
        this.N = 1;
        data = new double[M][N];

        for (int i = 0; i < this.M; i++)
            data[i][0] = X[i];
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public double[][] getData() {
        return data;
    }

    // swap rows i and j
    private void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A * B
    public Matrix multiply(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }


    // B = cA
    public Matrix multiplyScalar(double c) {
        Matrix A = this;
        Matrix B = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                B.data[i][j] = c * A.data[i][j];
        return B;
    }

    // print matrix to standard output
    public void printMatrix() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
                System.out.printf("%9.5f ", data[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    public boolean isValidEntry(int m, int n) {
        return (m > 0 || m <= M) && (n > 0 || n <= N);
    }

    public double getEntry(int m, int n) {
        // Return the matrix value at row m and column n.

        if (!isValidEntry(m, n)) {
            System.out.println("Elements outside the matrix cannot be accessed");
            return Double.NaN;
        }

        return data[m - 1][n - 1];
    }

    //find determinant of a matrix
    public double getArrayDeterminant(double data[][], int N) {

        double det = 0;
        if (N == 1) {
            det = data[0][0];
        } else if (N == 2) {
            det = data[0][0] * data[1][1] - data[1][0] * data[0][1];
        } else {
            det = 0;
            for (int j1 = 0; j1 < N; j1++) {
                double[][] m = new double[N - 1][];
                for (int k = 0; k < (N - 1); k++) {
                    m[k] = new double[N - 1];
                }
                for (int i = 1; i < N; i++) {
                    int j2 = 0;
                    for (int j = 0; j < N; j++) {
                        if (j == j1)
                            continue;
                        m[i - 1][j2] = data[i][j];
                        j2++;
                    }
                }
                det += Math.pow(-1.0, 1.0 + j1 + 1.0) * data[0][j1] * getArrayDeterminant(m, N - 1);
            }
        }
        return det;
    }

    public double getMatrixDeterminant() {
        Matrix A = this;
        if (A.N != A.M)
            throw new RuntimeException("Illegal matrix dimensions.");
        //System.out.println(A.data.length);
        return getArrayDeterminant(A.data, A.data.length);

    }

    public double[][] foo(double A[][], int skipR, int skipC) {
        int m = A.length;
        int n = A[0].length;

        double data[][] = new double[m - 1][n - 1];
        int a = 0, b = 0;
        for (int i = 0; i < m; i++) {
            if (i == skipR)
                continue;
            for (int j = 0; j < n; j++) {
                if (j == skipC)
                    continue;
                data[a][b++] = A[i][j];
            }
            a++;
            b = 0;
        }

        return data;
    }

    public Matrix getMinor() {
        Matrix A = this;
        if (A.N != A.M)
            throw new RuntimeException("Illegal matrix dimensions.");
        Matrix B = new Matrix(M, N);

        for (int i = 0; i < A.M; i++) {
            for (int j = 0; j < A.N; j++) {
                B.data[i][j] = getArrayDeterminant(foo(A.data, i, j), N - 1);
            }
        }

        return B;
    }

    public Matrix getCofactor() {
        Matrix A = this;
        if (A.N != A.M)
            throw new RuntimeException("Illegal matrix dimensions.");
        Matrix B = A.getMinor();

        int sign;
        for (int i = 0; i < A.M; i++) {
            for (int j = 0; j < A.N; j++) {
                if ((i + j) % 2 == 0)
                    sign = 1;
                else
                    sign = -1;
                B.data[i][j] *= sign;
            }
        }

        return B;
    }

    public Matrix getAdjoint() {
        Matrix A = this;
        Matrix B = A.getCofactor();
        return B.transpose();
    }

    public Matrix getInverse() {
        Matrix A = this;
        Matrix B = A.getAdjoint();
        double det = A.getMatrixDeterminant();
        if (det == 0) {
            System.out.println("Matrix is not invertible. Determinant is 0.");
            throw new RuntimeException("Determinant zero");
        }
        //System.out.println(det);
        return B.multiplyScalar(1 / det);
    }

}


public class Problem2 {
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

        System.out.println();

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

            Matrix A = new Matrix(matrixA);
            Matrix B = new Matrix(matrixB);

            System.out.println("Matrix A: ");
            A.printMatrix();

            System.out.println("Minor Matrix of A: ");
            A.getMinor().printMatrix();

            System.out.println("Cofactor Matrix of A: ");
            A.getCofactor().printMatrix();

            System.out.println("Adjugate Matrix of A: ");
            A.getAdjoint().printMatrix();

            System.out.println("Inverse Matrix of A: ");
            A.getInverse().printMatrix();

            System.out.println("Matrix B:");
            B.printMatrix();
            //System.out.println(A.getN() + " " + B.getM());
            Matrix ANS = (A.getInverse()).multiply(B);

            double x[][] = ANS.getData();

            System.out.println("Solution: " + "\n");
            for (int i = 0; i < x.length; i++)
                System.out.println(varName[i] + " = " + round(x[i][0]));

            out.close();

        }
    }
}
