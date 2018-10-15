package lv.tsi.metodi;

import java.util.Scanner;

public class Main {




    public static void main(String[] args) {
	// write your code here

        double mas[][] = {
                {4, -3, 2, -21},
                {1, 2, 9, -14},
                {-1, 8, 1, 24}


        };




    Matrix matrix = new Matrix(mas);
    int cols = matrix.getCols();
    int rows = matrix.getRows();



    if (matrix.getDetA()!=0) {

        System.out.println(" determinant = " + matrix.getDetA());


              double[] x = new double[matrix.getRows()];
              x = matrix.getVectorX(2);





    }

    else
        System.out.println("Матрица выражденая");










    }





}
