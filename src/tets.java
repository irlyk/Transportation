//public static int[] needs = {80, 60, 170, 80};
//public static int[] res = {110, 190, 90};
//public static int[][] c = {{8, 1, 9, 7},
//        {4, 6, 2, 12},
//        {3, 5, 8, 9}};

//public static int[] needs = {80, 80, 60, 80};
//public static int[] res = {100, 140, 60};
//public static int[][] c = {{5, 4, 3, 4},
//        {3, 2, 5, 5},
//        {1, 6, 3, 2}};


//public void CicleStart(){
//        SetPotentials();
//        int iter =0;
//        while(iter < 4) {
//        testPot();
//        iter++;
//        System.out.println();
//        start_coordinate = findLessZero();
//        System.out.println("Стартовая точка: " + start_coordinate);
//        stack = new Stack<>();
//        stack.push(start_coordinate);
//        Cicle("up");
//        printStack();
//        isEnd = false;
//        reCalculate();
//        System.out.println();
//        System.out.println("Результат:");
//        for(int i=0; i < result.length; i++){
//        for(int j=0; j < result[0].length; j++)
//        System.out.print(result[i][j] + " \t");
//        System.out.println();
//        }
//        SetPotentials();
//        }
//
//        }

//public coordinate findLessZero() {
//        Integer max = 0;
//        int row = 0, col = 0;
//        coordinate cor = null;
//        for (int i = 0; i < pot.length - 1; i++) {
//        for (int j = 0; j < pot[0].length - 1; j++) {
//        if (pot[i][j] < max) {
//        max = pot[i][j];
//        row = i;
//        col = j;
//        }
//        }
//        }
//        if(max < 0)
//        cor = new coordinate(row, col, "start");
//        return cor;
//        }
//
//public boolean HasLessZero(){
//        boolean check = false;
//        for(int i = 0; i < pot.length - 1; i++)
//        for(int j = 0; j < pot[0].length - 1; j++)
//        if(pot[i][j] < 0)
//        check = true;
//        return check;
//        }
//
//public void PrintPot(){
//        System.out.println("Таблица потенциалов");
//        for(int i = 0; i < pot.length; i++) {
//        for (int j = 0; j < pot[0].length; j++)
//        if(pot[i][j] != null)
//        System.out.print(pot[i][j] + "  \t");
//        else
//        System.out.print("_  \t");
//        System.out.println();
//        }
//        System.out.println();
//        }
//
//
//public void SetPotentials(){
//        pot = new Integer[m + 1][n + 1];
//        for(int i = 0; i < m; i++){
//        boolean check = false;
//        for(int j = 0; j< n; j++){
//        if (result[i][j] > 0){
//        pot[i][n] =  0;
//        check = true;
//        break;
//        }
//        }
//        if(check) break;
//        }
//        for(int i = 0; i < m; i++) {
//        for (int j = 0; j < n; j++) {
//        if (result[i][j] > 0) {
//        if (pot[i][n] != null && pot[m][j] == null) {
//        pot[m][j] = c[i][j] - pot[i][n];
//        }
//        if (pot[i][n] == null && pot[m][j] != null) {
//        pot[i][n] = c[i][j] - pot[m][j];
//        }
//        }
//        }
//        }
//
//        for(int i = 0; i < m; i++)
//        for(int j = 0; j < n; j++) {
//        if (pot[i][j] == null)
//        if(pot[i][n] == null || pot[m][j]==null ) continue;
//        pot[i][j] = c[i][j] - (pot[i][n] + pot[m][j]);
//        }
//        PrintPot();
//        }


//public class tets {
//    private int n;
//    private int m;
//    private double[][] result;
//    private double[] res;
//    private double[] needs;
//    private double[][] c;
//    Integer[] res_pot;
//    Integer[] needs_pot;
//
//    public tets(double[][] c, double[] res, double[] needs ){
//        this.c = c;
//        this.res = res;
//        this.needs = needs;
//        this.m = res.length;
//        this.n = needs.length;
//        result = new double[m][n];
//        for(int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++)
//                result[i][j] = 0;
//        }
//    }
//
//    public void setIpor(int i, int j){
//        for(int k = 0; k < needs.length; k++){
//            if(result[i][k] > 0){
//                if(needs_pot[k] == null){
//                    needs_pot[k] = c[i][k] - res_pot[i];
//                }
//            }
//        }
//    }
//
//    public void test(){
//        res_pot = new Integer[m];
//        needs_pot = new Integer[n];
//        for (int i = 0; i < res_pot.length; i++) {
//            for (int j = 0; j < needs_pot.length; j++) {
//                if (result[i][j] > 0) {
//                    if (res_pot[i] == null) {
//                        if (needs_pot[j] == null) {
//                            res_pot[i] = 0;
//                            needs_pot[j] = (int) (c[i][j]);
//                        } else {
//                            res_pot[i] = (int) (c[i][j] - needs_pot[j]);
//                        }
//                    } else {
//                        if (needs_pot[j] == null) {
//                            needs_pot[j] = (int) (c[i][j] - res_pot[i]);
//                        }
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < res_pot.length; i++) {
//            for (int j = 0; j < needs_pot.length; j++) {
//                if (result[i][j] > 0)
//                    System.out.print("--\t");
//                else {
//                    int check = res_pot[i] + needs_pot[j];
//                    if (check > c[i][j])
//                        System.out.print("-!\t");
//                }
//            }
//            System.out.print(res_pot[i] + "\n");
//        }
//        for (int i = 0; i < needs_pot.length; i++)
//            System.out.print(needs_pot[i] + "\t");
//        System.out.println();
//    }
//}
