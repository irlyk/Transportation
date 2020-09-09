import java.util.Scanner;

public class Main {

public static int[] needs = {80, 60, 170, 80};
public static int[] res = {110, 190, 90};
public static int[][] c = {{8, 1, 9, 7},
        {4, 6, 2, 12},
        {3, 5, 8, 9}};



    public static void main(String[] args){
        ReadAll();
        WriteTable();

        boolean print = true;
        TransportData test;

        System.out.println("Северозападный угол:");
        test = new TransportData(c, res, needs, print);
        test.SeveroZapad();
        if(!print) System.out.println("Опорный план:");
        test.printResult();
        test.CicleStart();
        if(!print){
            System.out.println("Окончательная таблица:");
            test.printResult();
        }
        System.out.println("Общая стоимость перевозок: " + test.getFunctionValue());
        System.out.println("Количество итераций - " + test.getIterations());
        System.out.println("***********");

        System.out.println("Минимальный элемент:");
        test = new TransportData(c, res, needs, print);
        test.minEl();
        if(!print) System.out.println("Опорный план:");
        test.printResult();
        test.CicleStart();
        if(!print){
            System.out.println("Окончательная таблица:");
            test.printResult();
        }
        System.out.println("Общая стоимость перевозок: " + test.getFunctionValue());
        System.out.println("Количество итераций - " + test.getIterations());
        System.out.println("***********");

        System.out.println("Метод Фогеля:");
        test = new TransportData(c, res, needs, print);
        test.Fogel();
        if(!print) System.out.println("Опорный план:");
        test.printResult();
        test.CicleStart();
        if(!print){
            System.out.println("Окончательная таблица:");
            test.printResult();
        }
        System.out.println("Общая стоимость перевозок: " + test.getFunctionValue());
        System.out.println("Количество итераций - " + test.getIterations());
        System.out.println("***********");
    }

    public  static void WriteTable(){
        System.out.println();
        System.out.println("Массив ресурсов:");
        for( int i = 0; i<res.length; i++)
            System.out.print(res[i] + "\t");
        System.out.println();
        System.out.println();
        System.out.println("Массив потребностей:");
        for( int i = 0; i<needs.length; i++)
            System.out.print(needs[i] + "\t");
        System.out.println();
        System.out.println();
        System.out.println("Транспортная таблица:");
        for( int i = 0; i<c.length; i++) {
            for (int j = 0; j < c[0].length; j++)
                System.out.print(c[i][j] + "  \t");
            System.out.println();
        }
        System.out.println();
    }

    public static void ReadAll(){
        System.out.println();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество потребностей n:");
        int n = in.nextInt();
        System.out.println("Введите сами потребности в виде: B1 B2 ... Bn");
        needs = new int[n];
        for(int i = 0; i < n; i++){
            needs[i] = in.nextInt();
        }
        System.out.println();
        System.out.println("Введите количество ресурсов m");
        int m = in.nextInt();
        System.out.println("Введите сами ресурсы в виде: A1 A2 ... Am");
        res = new int[m];
        for(int i = 0; i < m; i++){
            res[i] = in.nextInt();
        }
        System.out.println();
        System.out.println("Введите матрицу c в виде C11, C12, ... C1n");
        System.out.println("                         C21, C22, ... C2n");
        System.out.println("                         ...");
        System.out.println("                         Cm1, Cm2, ... Cmn");
        c = new int[m][n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++)
                c[i][j] = in.nextInt();
        }
        System.out.println();
    }
}
