package lv.tsi.metodi;

public class Matrix {

    private int cols;
    private int rows;
    private double[][] values;
    private double[][] matrixA;
    private double[] vectorB;
    private double[] vectorX;
    private double detA;


    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }



    public double getDetA() {
        return detA;
    }

    public double[][] getMatrixA() {
        return matrixA;
    }

    public double[] getVectorB() {
        return vectorB;
    }

    public double[] getVectorX(int type) {
        switch (type) {
            case 1:
                gauss(this.values);
                break;
            case 2:
                lu(this.values);            ;
            break;

        }



        print(vectorX);
        return this.vectorX;

    }



    public Matrix(double[][] matrix) {

       this.values = matrix;
       this.rows = matrix.length;
       this.cols = matrix[0].length;

       if (cols - rows != 1) {
           System.out.println("Error: Matrix is not square");
       }
        else {
           separeateMatrix(matrix);
           determinant(this.matrixA, 1);


       }


    }// konstruktor

    private void determinant(double[][] subMinor, double elemParentMinor) {

        if (subMinor.length > 1) {
            double[][] tmpMinor = new double[subMinor.length - 1][subMinor[0].length - 1];
            for (int c = 0; c < subMinor[0].length; c++) {
                for (int i = 1; i < subMinor.length; i++) {
                    for (int j = 0; j < subMinor[0].length; j++) {
                        if (j < c)
                            tmpMinor[i - 1][j] = subMinor[i][j];
                        else if (j > c)
                            tmpMinor[i - 1][j - 1] = subMinor[i][j];
                    }
                }
                double paramForSub = Math.pow(-1, c + 2) * subMinor[0][c] * elemParentMinor;
                determinant(tmpMinor, paramForSub);
            }
        } else
            this.detA += elemParentMinor * subMinor[0][0];
    }

    private void separeateMatrix(double[][] values) {


        double[][] tempMatrixA = new double[values.length][values.length];
        double[] tempVektorB = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {

                tempMatrixA[i][j] = values[i][j];


            }
            tempVektorB[i] = values[i][values.length];

        }

        this.vectorB = tempVektorB;
        this.matrixA = tempMatrixA;

    }

    private void gauss(double mas[][]){

        int n = this.rows;
        int[] otv = new int[this.rows];

        for (int i=0; i<this.rows;i++ ) {

            otv[i] = i;
        }

        for (int k = 0; k<n; k++){
            glavelem(k,mas,n,otv);

            for(int j = n; j>= k; j--){

                mas[k][j] /= mas[k][k];
            }
            for (int i = k + 1; i < n; i++){
                for (int j = n; j >= k; j--){

                    mas[i][j] -= mas[k][j] * mas[i][k];

                }
            }

        }
        System.out.println(" posle prjamogo hoda gaussa");
        print(mas);

        this.vectorX = backGaussDownUP(mas);

        }

    private void glavelem(int k, double mas[][], int n, int otv[]) {
                int i, j, i_max = k, j_max = k;
                double temp;
                //Ищем максимальный по модулю элемент
                for (i = k; i < n; i++)
                    for (j = k; j < n; j++)
                        if (Math.abs(mas[i_max][j_max]) < Math.abs(mas[i][j])) {
                            i_max = i;
                            j_max = j;
                        }
                //Переставляем строки

                for (j = k; j < n + 1; j++) {
                    temp = mas[k][j];
                    mas[k][j] = mas[i_max][j];
                    mas[i_max][j] = temp;

                }
            }

    private double[] backGaussDownUP(double[][] mas){
                int n = this.rows;
                double[] x = new double[this.rows];
                x[0] = mas[0][0];

                for (int i = 0; i < n; i++) //Обратный ход
                    x[i] = mas[i][n];

                for (int i = n - 2; i >= 0; i--)
                    for (int j = i + 1; j < n; j++)
                        x[i] -= x[j] * mas[i][j];


        return x;
        }


    private double[] backGaussUpDown(double[][] mas){
        int n = this.rows;
        double[] y = new double[this.rows];
        y[0] = mas[0][rows]/mas[0][0];

       // i=1, j=0;
       // y[1] = (mas[1][3] - mas[1][0] * y[0]) / mas[1][1];

       // i=2, j=
       // y[2] = (mas[2][3] - mas[2][0] * y[0] * y[1] ) / mas[2][2];

        for (int i=1;i<rows;i++){

                y[i] = (mas[i][rows] - mas[i][0] * y[i-1] * y[i]) / mas[i][i];

        }
        System.out.println("Y:");
        print(y);
        return y;
    }

    private void lu(double[][] mas) {

        double[][] matrixC = new double[rows][cols];
        double[][] matrixD = new double[rows][cols];


        double sum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                matrixC[i][j] = 0;
            }
        }
        System.out.println("Prepare matrix C");
        print(matrixC);

        for (int i = 0; i < rows; i++) {

            matrixD[i][i] = 1;
        }
        System.out.println("Prepare matrix D");
        print(matrixD);


        for (int i = 0; i < rows; i++) {
            matrixC[i][0] = mas[i][0]; // 1 step
            matrixD[0][i] = mas[0][i] / matrixC[0][0];
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < rows; j++) {
                if (i >= j) //нижний треугольник
                {
                    sum = 0;
                    for (int k = 0; k < j; k++)
                        sum += matrixC[i][k] * matrixD[k][j];

                    matrixC[i][j] = mas[i][j] - sum;
                } else // верхний
                {
                    sum = 0;
                    for (int k = 0; k < i; k++)
                        sum += matrixC[i][k] * matrixD[k][j];

                    matrixD[i][j] = (mas[i][j] - sum) / matrixC[i][i];


                }

            }

        }

        System.out.println("Matrix C");
        print(matrixC);
        System.out.println("Matrix D");
        print(matrixD);

        //obratnij hod gaussa

        for (int i=0; i<rows;i++){

            matrixC[i][rows]=mas[i][rows];
        }

        double[] vectorY = new double[rows];

        System.out.println("C * Y = B");
        print(matrixC);



        vectorY = backGaussUpDown(matrixC);
        System.out.println("Y:");
        print(vectorY);




        }

    private void print(double[][] mas){
        int rows = mas.length;
        int cols = this.cols;
        System.out.println();

        for (int i=0;i<rows;i++){
            for (int j = 0; j<cols;j++) {
                System.out.print(mas[i][j] + "  \t\t");
            }
            System.out.println();
        }

         System.out.println();



    }

    private void print(double[] mas){
        int rows = mas.length;

        System.out.println();

        for (int i=0;i<rows;i++){


                System.out.print(mas[i] + "  \t\t");

        }

        System.out.println();



    }

    }




