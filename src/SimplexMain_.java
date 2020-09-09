import java.util.Scanner;

public class SimplexMain_ {

    public static void main(String[] args) {
        float[][] table = ReadTable();
        Simplex_ simplex = new Simplex_(table);
        simplex.PrintTable(table);
        simplex.PrintFunc(table);
        simplex.Calculate(true);
        float result = simplex.GetResult(table);
        System.out.println("Значение функции F = " + result);
        Analis(table,result);
    }

    public static float[][] ReadTable()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество переменных n и количество ограничений m:");
        String[] str =  in.nextLine().split("\\s");
        int n = Integer.parseInt(str[0]), m = Integer.parseInt(str[1]);
        System.out.println("Введите ограничения в виде: b1 x11 x22 ... x1n");
        System.out.println("                            ....... ");
        System.out.println("                            bm xm1 xm2 ... xmn");
        System.out.println("                            0  c1  c2  ... cn ");
        float[][] read_table = new float[m + 1][ n + 1];
        for (int i = 0; i < read_table.length; i++)
        {
            str = in.nextLine().split("\\s");
            for (int j = 0; j < str.length; j++)
            {
                if (j < read_table[0].length)
                    read_table[i][j] = Float.parseFloat(str[j]);
                else
                {
                    System.out.println("ERROR: Введёная строка слишком большая");
                    System.exit(1);
                }
            }
        }
        return read_table;
    }


    static void Analis(float[][] table, float result) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Старт Анализа:");
        System.out.println("Введите шаг");
        float s = in.nextFloat();
        for (int i = 0; i < table.length - 1; i++) {
            float[][] new_table = CopyTable(table);
            boolean check;
            int st = 0, maxiter = 100000000;
            float delta = 0, old_res = result, cur_res;
            new_table[i][0] +=s;
            Simplex_ t = new Simplex_(new_table);
            //t.PrintTable(new_table);
            t.Calculate(false);
            new_table[i][0] -=s;
            cur_res = t.GetResult(new_table);
            if (cur_res != result) {
                System.out.println("Ресурс " + (i + 1) + " дефицитный; значение функции = " + cur_res );
                check = true;

            } else {
                System.out.println("Ресурс " + (i + 1) + " не дефицитный; проверка: F' = " + cur_res);
                check = false;
            }
            while (st < maxiter) {
                st += 1;
                if (check) {
                    new_table[i][0] +=s;
                    t = new Simplex_(new_table);
                    t.Calculate(false);
                    cur_res = t.GetResult(new_table);
                    if (st % 100000 == 0) {
                        System.out.print('*');
                        //    Console.WriteLine("i = " + st + " Значений функции со сдвигом " + delta + " F2 = " + res1 +" \t Предыдущие значение F1 = " + old_res);
                    }
                    if (cur_res <= old_res) {
                        System.out.println();
                        System.out.println("Значений функции с дельтой F = " + old_res);
                        System.out.println("Дельта ресурса " + (i + 1) + " = " + delta);
                        break;
                    }
                    old_res = cur_res;
                } else {
                    new_table[i][0] -=s;
                    t = new Simplex_(new_table);
                    t.Calculate(false);
                    cur_res = t.GetResult(new_table);
                    if (st % 100000 == 0) {
                        System.out.print('*');
                        //    Console.WriteLine("i = " + st + " Значение функции со сдвигом "+ delta + " F1 = " + res2 + " \t Значение функции изначальной таблицы F2 = " + res1);
                    }
                    if (cur_res != result) {
                        System.out.println();
                        System.out.println("Значений функции с дельтой F1 = " + cur_res + "; Новое значение функции F2 = " + result);
                        System.out.println("Дельта ресурса " + (i + 1) + " = " + (-delta));
                        break;
                    }
                }
                delta += s;
            }
            if (st >= maxiter)
                System.out.println("Ресурс можно изменять до бесконечности");
            System.out.println();
        }
        System.out.println("Конец анализа");
    }

    public static float[][] CopyTable(float[][] table){
        float[][] new_table = new float[table.length][table[0].length];
        for(int i = 0; i < table.length; i++)
            for (int j = 0; j < table[0].length; j++)
                new_table[i][j] = table[i][j];
        return  new_table;
    }

}

