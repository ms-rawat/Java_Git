
/**
** Java Program to Implement Strassen Algorithm
**/

import java.util.Scanner;

/** Class Strassen **/
public class Hm {
    public static void printMat(int[][] a, int r, int c) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        

    }

    /** Function to multiply matrices **/
    public int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] R = new int[n][n];
        /** base case **/
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
        else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            /** Dividing matrix A into 4 halves **/
            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);
            /** Dividing matrix B into 4 halves **/
            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n / 2);

            /**
             * M1 = (A11 + A22)(B11 + B22)
             * M2 = (A21 + A22) B11
             * M3 = A11 (B12 - B22)
             * M4 = A22 (B21 - B11)
             * M5 = (A11 + A12) B22
             * M6 = (A21 - A11) (B11 + B12)
             * M7 = (A12 - A22) (B21 + B22)
             **/

            int[][] M1 = multiply(add(A11, A22), add(B11, B22));
            // printMat(M1, M1.length, M1.length);
            int[][] M2 = multiply(add(A21, A22), B11);
            // printMat(M2, M2.length, M2.length);

            int[][] M3 = multiply(A11, sub(B12, B22));
            // printMat(M2, M2.length, M2.length);

            int[][] M4 = multiply(A22, sub(B21, B11));
            // printMat(M4, M4.length, M4.length);

            int[][] M5 = multiply(add(A11, A12), B22);
            // printMat(M5, M5.length, M5.length);

            int[][] M6 = multiply(sub(A21, A11), add(B11, B12));
            // printMat(M6, M6.length, M6.length);

            int[][] M7 = multiply(sub(A12, A22), add(B21, B22));
            // printMat(M7, M7.length, M7.length);

            /**
             * C11 = M1 + M4 - M5 + M7
             * C12 = M3 + M5
             * C21 = M2 + M4
             * C22 = M1 - M2 + M3 + M6
             **/
            int[][] C11 = add(sub(add(M1, M4), M5), M7);
            printMat(C11, C11.length, C11.length);

            int[][] C12 = add(M3, M5);
            printMat(C12, C11.length, C12.length);

            int[][] C21 = add(M2, M4);
            printMat(C21, C21.length, C21.length);

            int[][] C22 = add(sub(add(M1, M3), M2), M6);
            printMat(C22, C22.length, C22.length);

            /** join 4 halves into one result matrix **/
            join(C11, R, 0, 0);
            join(C12, R, 0, n / 2);
            join(C21, R, n / 2, 0);
            join(C22, R, n / 2, n / 2);
        }
        /** return result **/
        return R;
    }

    /** Funtion to sub two matrices **/
    public int[][] sub(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    /** Funtion to add two matrices **/
    public int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    /** Funtion to split parent matrix into child matrices **/
    public void split(int[][] P, int[][] C, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    /** Funtion to join child matrices intp parent matrix **/
    public void join(int[][] C, int[][] P, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }

    /** Main function **/
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Strassen Multiplication Algorithm Test\n");
        /** Make an object of Strassen class **/
        Hm s = new Hm();

        // System.out.println("Enter order n :");
        // int N = scan.nextInt();
        /** Accept two 2d matrices **/
        // System.out.println("Enter N order matrix 1\n");
        int[][] A = { { 4, 1, 0, 3 },
                { 2, 2, 2, 1 },
                { 0, 1, 0, 2 },
                { 1, 4, 3, 1 } };

        // System.out.println("Enter N order matrix 2\n");
        int[][] B = { { 1, 1, 1, 2 },
                { 2, 1, 2, 2 },
                { 0, 1, 0, 1 },
                { 3, 3, 1, 1 } };

        int[][] C = s.multiply(A, B);
        int N = 4;
        System.out.println("\nProduct of matrices A and  B : ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(C[i][j] + " ");
            System.out.println();
        }

    }
}
