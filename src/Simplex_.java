import java.util.ArrayList;
import java.util.List;

public class Simplex_ {

    private float[][] table;
    private int n , m, res_len;
    private List<Integer> basis;
    private float[] result;

    public Simplex_(float[][] source){
        this.m =source.length;
        this.n = source[0].length;
        basis = new ArrayList<>();
        this.table = ConvertTable(source);
        this.res_len = source[0].length - 1;
    }

    private float[][] ConvertTable(float[][] old_table) {
        float[][] new_table = new float[m][n + m - 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < new_table[0].length; j++) {
                if (j < n)
                    new_table[i][j] = old_table[i][j];
                else
                    new_table[i][j] = 0;
            }
            if ((n + i) < new_table[0].length) {
                new_table[i][n + i] = 1;
                basis.add(n + i);
            }

            if (i == m - 1)
                for (int j = 0; j < new_table[0].length; j++)
                    new_table[i][j] = 0 - new_table[i][j];
        }
        n = new_table[0].length;
        return new_table;
    }

    public void Calculate_(boolean output) {
        if(output) {
            System.out.println("Вывод базиса:");
            for (int s : basis)
                System.out.println("элемент " + s + " под индексом " + basis.indexOf(s) + " \t");
            System.out.println();
            PrintTable(table);
        }
        int mRow, mCol, iter = 0;
        while (!Check_Resh()) {
            if (output)
                System.out.println("Итерация " + ((iter++) + 1));
            mCol = FindMCol();
            mRow = FindMRow(mCol);
            basis.remove(mRow);
            basis.add(mRow, mCol);
            if(output){
                System.out.println("min row: " + mRow + "; min col: " + mCol + ";");
                System.out.println("Вывод базиса:");
                for( int s : basis)
                    System.out.println("элемент " + s +" под индексом " + basis.indexOf(s) + " \t" );
                System.out.println();
            }
            float[][] new_table = new float[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++)
                    new_table[i][j] = table[i][j] - ((table[mRow][j] * table[i][mCol])/table[mRow][mCol]);
            }
            for(int i = 0; i < m; i++) {
                if (i == mRow)
                    continue;
                new_table[i][mCol] = -(table[i][mCol] / table[mRow][mCol]);
            }
            for(int i= 0; i<n; i++) {
                if (i == mCol)
                    continue;
                new_table[mRow][i] = table[mRow][i] / table[mRow][mCol];
            }
            new_table[mRow][mCol] = 1 / table[mRow][mCol];
            table = new_table;
            if (output)
                PrintTable(table);
        }

        result = new float[res_len];
        for (int i = 0; i < result.length; i++) {
            int k = basis.indexOf(i + 1);
            if (k != -1)
                result[i] = table[k][0];
            else
                result[i] = 0;
        }
        if (output) {
            System.out.println("Решение:");
            for (int i = 0; i < result.length; i++)
                System.out.print("X" + (i + 1) + " = " + result[i] + "; \t");
            System.out.println();
        }
    }

    public void Calculate(boolean output) {
        if(output) {
            System.out.println("Вывод базиса:");
            for (int s : basis)
                System.out.println("элемент " + s + " под индексом " + basis.indexOf(s) + " \t");
            System.out.println();
            PrintTable(table);
        }
        int mRow, mCol, iter = 0;
        while (!Check_Resh()) {
            if (output)
                System.out.println("Итерация " + ((iter++) + 1));
            mCol = FindMCol();
            mRow = FindMRow(mCol);
            basis.remove(mRow);
            basis.add(mRow, mCol);
            if(output){
                System.out.println("min row: " + mRow + "; min col: " + mCol + ";");
                System.out.println("Вывод базиса:");
                for( int s : basis)
                    System.out.println("элемент " + s +" под индексом " + basis.indexOf(s) + " \t" );
                System.out.println();
            }
            float[][] new_table = new float[m][n];
            for (int j = 0; j < n; j++)
                new_table[mRow][j] = table[mRow][j] / table[mRow][mCol];
            for (int i = 0; i < m; i++) {
                if (i == mRow)
                    continue;
                for (int j = 0; j < n; j++)
                    new_table[i][j] = table[i][j] - table[i][mCol] * new_table[mRow][j];
            }
            table = new_table;
            if (output)
                PrintTable(table);
        }

        result = new float[res_len];
        for (int i = 0; i < result.length; i++) {
            int k = basis.indexOf(i + 1);
            if (k != -1)
                result[i] = table[k][0];
            else
                result[i] = 0;
        }
        if (output) {
            System.out.println("Решение:");
            for (int i = 0; i < result.length; i++)
                System.out.print("X" + (i + 1) + " = " + result[i] + "; \t");
            System.out.println();
        }
    }

    public float GetResult(float[][] calculate_table) {
        float res = 0;
        for (int i = 0; i < result.length; i++) {
            res += calculate_table[calculate_table.length - 1][i + 1] * result[i];
        }
        res += calculate_table[calculate_table.length - 1][0];
        return res;
    }

    private int FindMRow(int mCol) {
        int mainRow = 0;
        for (int i = 0; i < m - 1; i++)
            if (table[i][mCol] > 0) {
                mainRow = i;
                break;
            }
        for (int i = mainRow + 1; i < m - 1; i++)
            if ((table[i][mCol] > 0) && ((table[i][0] / table[i][mCol]) < (table[mainRow][0] / table[mainRow][mCol])))
                mainRow = i;
        return mainRow;
    }

    private int FindMCol() {
        int mCol = 1;
        for (int j = 2; j < n; j++)
            if (table[m - 1][j] < table[m - 1][mCol])
                mCol = j;
        return mCol;
    }

    private boolean Check_Resh() {
        boolean flag = true;
        for (int j = 1; j < n; j++) {
            if (table[m - 1][j] < 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public void PrintFunc(float[][] table) {
        System.out.println();
        System.out.print("F = ");
        int k = table.length - 1;
        for (int i = 1; i < table[0].length; i++) {
            if (table[k][i] >= 0 && i > 1)
                System.out.print("+" + table[k][i] + "X" + i + " ");
            else
                System.out.print(table[k][i] + "X" + i + " ");
        }
        System.out.println();
    }

    public void PrintTable(float[][] print_table) {
        System.out.println();
        for (int i = 0; i < print_table[0].length; i++) {
            if (i == 0)
                System.out.print("b\t");
            else
                System.out.print("X" + i + "\t");
        }
        System.out.println();
        for (int i = 0; i < print_table.length; i++) {
            for (int j = 0; j < print_table[0].length; j++) {
                System.out.print(print_table[i][j] + "\t");
            }
            if (i == print_table.length - 1)
                System.out.print(" - F(X)");
            System.out.println();
        }
        System.out.println();
    }

}

