import java.util.*;

public class TransportData {

    private int[][] result;
    private int[][] c; // m_x_n
    private int[] res;
    private int[] needs;
    private int n;
    private int m;
    private Integer[] res_pot;
    private Integer[] needs_pot;
    int iterations;
    private boolean isEnd = false;
    private boolean print;
    private Stack<coordinate> points;
    private Stack<coordinate> old_points;

    private class coordinate {
        private int i;
        private int j;
        private String way;

        public coordinate(int i, int j, String way) {
            this.i = i;
            this.j = j;
            this.way = way;
        }

        public int GetI() {
            return i;
        }

        public int GetJ() {
            return j;
        }

        public String GetWay() {
            return way;
        }

        @Override
        public String toString(){
            return ("i = " + i + "; j = " + j);
        }
    }

    private Stack<coordinate> stack;
    private coordinate start_coordinate;

    public int[][] GetResul(){
        return result;
    }

    public int getIterations(){return iterations;}

    public int getFunctionValue(){
        int value = 0;
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                if(result[i][j] > 0){
                    value += result[i][j] * c[i][j];
                }
            }
        }
        return value;
    }

    public void CicleStart(){
        while(true) {
            try{
                start_coordinate = testPot();
                if (start_coordinate == null )
                    break;
                iterations ++;
                if(print) System.out.println("Стартовая точка: " + start_coordinate);
                stack = new Stack<>();
                stack.push(start_coordinate);
                Cicle("up");
                if(print) printStack();
                isEnd = false;
                reCalculate();
                if(print) {
                    System.out.println();
                    printResult();
                }
            }catch (Exception e){
                start_coordinate = take_next_point();
                if ( start_coordinate == null){
                    System.out.println("Крнец. Ошибка программы");
                    return;
                }
                else continue;

            }
        }
        if(print) System.out.println("Конец задачи");
    }

    private void reCalculate(){
        int min = 999999999;
        boolean check = true;
        ArrayDeque<coordinate> plus_minus = new ArrayDeque<>();
        coordinate first = null, second = null;
        if(!stack.isEmpty()) first = stack.pop();
        if(!stack.isEmpty()) second = stack.pop();

        if((first.GetI() == second.GetI() && second.GetI() == start_coordinate.GetI()) || (first.GetJ() == second.GetJ() && second.GetJ() == start_coordinate.GetJ())){
            stack.push(second);
        }
        else {
            stack.push(second);
            stack.push(first);
        }

        first = stack.pop();
        plus_minus.push(first);
        while(!stack.isEmpty()){
            second = stack.pop();
            if(!second.GetWay().equals(first.GetWay())){
                plus_minus.addFirst(second);
            }
            first = second;
        }

        if(print) {
            System.out.println("Цикл +-");
            for (coordinate s : plus_minus) {
                System.out.println(s);
            }
        }

        for(coordinate s: plus_minus){
            if(check){
                check = false;
            }
            else{
                if(result[s.GetI()][s.GetJ()] < min) {
                    min = result[s.GetI()][s.GetJ()];
                }
                check = true;
            }
        }
        if(print) System.out.println("Минимал: " + min);
        check = true;
        for(coordinate s: plus_minus){
            if(check){
                result[s.GetI()][s.GetJ()] += min;
                check = false;
            }
            else{
                result[s.GetI()][s.GetJ()] -= min;
                check = true;
            }
        }
    }

    private String choseWay(String current){
        switch (current){
            case "up":{
                return "right";
            }
            case "right":{
                return "down";
            }
            case "down":{
                return "left";
            }
            case "left":{
                return "pop";
            }
            default:{
                return "123";
            }
        }
    }

    private void Cicle (String way) {
        switch (way) {
            case "up": {
                if (stack.isEmpty()) break;
                coordinate cor = stack.pop();
                stack.push(cor);
                if(cor.GetWay().equals("down") || cor.GetI() == 0 ){
                    Cicle("right");
                    if (isEnd) return;
                }
                boolean check = false;
                for (int i = (cor.GetI() - 1); i >= 0; i--) {
                    if (result[i][cor.GetJ()] > 0) {
                        check = true;
                        stack.push(new coordinate(i, cor.GetJ(), "up"));
                        if(print) System.out.println("При поиске вверх найдена точка: i = " + i + "; j = " + cor.GetJ());
                        break;
                    }
                    if (i == start_coordinate.GetI() && cor.GetJ() == start_coordinate.GetJ()) {
                        if(print) System.out.println("Цикл закончен");
                        Cicle("end");
                        return;
                    }
                }
                if (check) {
                    Cicle("up");

                }
                else  {
                    if (cor.GetWay() != "left")
                        Cicle("right");
                    else
                        Cicle("down");
                }
                return;
            }
            case "right": {
                if (stack.isEmpty()) break;
                coordinate cor = stack.pop();
                stack.push(cor);
                if(cor.GetWay().equals("left") || cor.GetJ() == result[0].length){
                    Cicle("down");
                    if (isEnd) return;
                }
                boolean check = false;
                for (int j = (cor.GetJ() + 1); j < result[0].length; j++) {
                    if (result[cor.GetI()][j] > 0) {
                        check = true;
                        stack.push(new coordinate(cor.GetI(), j,"right"));
                        if(print) System.out.println("При поиске вправо найдена точка: i = " + cor.GetI() + "; j = " + j);
                        break;
                    }
                    if (cor.GetI() == start_coordinate.GetI() && j == start_coordinate.GetJ()) {
                        if(print) System.out.println("Цикл закончен");
                        Cicle("end");
                        return;
                    }
                }
                if (check) Cicle("up");
                else{
                    if (cor.GetWay() != "up")
                        Cicle("down");
                    else
                        Cicle("left");
                }
                return;
            }
            case "down": {
                if (stack.isEmpty()) break;
                coordinate cor = stack.pop();
                stack.push(cor);
                if(cor.GetWay().equals("up") || cor.GetI() == result.length){
                    Cicle("left");
                    if (isEnd) return;
                }
                boolean check = false;
                for (int i = cor.GetI() + 1; i < result.length; i++) {
                    if (result[i][cor.GetJ()] > 0) {
                        check = true;
                        stack.push(new coordinate(i, cor.GetJ(),"down"));
                        if(print) System.out.println("При поиске вниз найдена точка: i = " + i + "; j = " + cor.GetJ());
                        break;
                    }
                    if (i == start_coordinate.GetI() && cor.GetJ() == start_coordinate.GetJ()) {
                        if(print) System.out.println("Цикл закончен");
                        Cicle("end");
                        return;
                    }
                }
                if (check) Cicle("up");
                else{
                    if (cor.GetWay() != "right")
                        Cicle("left");
                    else
                        Cicle("pop");
                }
                return;
            }
            case "left": {
                if (stack.isEmpty()) break;
                coordinate cor = stack.pop();
                stack.push(cor);
                if(cor.GetWay().equals("right") || cor.GetJ() == 0){
                    Cicle("pop");
                    if (isEnd) return;
                }
                boolean check = false;
                for (int j = cor.GetJ() - 1; j >= 0; j--) {
                    if (result[cor.GetI()][j] > 0) {
                        check = true;
                        stack.push(new coordinate(cor.GetI(), j,"left"));
                        if(print) System.out.println("При поиске влево найдена точка: i = " + cor.GetI() + "; j = " + j);
                        break;
                    }
                    if (cor.GetI() == start_coordinate.GetI() && j == start_coordinate.GetJ()) {
                        if(print) System.out.println("Цикл закончен");
                        Cicle("end");
                        return;
                    }
                }
                if (check) Cicle("up");
                else {
                    Cicle("pop");
                }
                return;
            }
            case "pop": {
                while(true){
                    if (stack.isEmpty()) break;
                    coordinate cor = stack.pop();
                    if(print) System.out.println("Выкинута точка " + cor);
                    if (cor.GetWay().equals("start")) break;
                    String next_way = choseWay(cor.GetWay());
                    if(next_way.equals("pop")) continue;
                    else {
                        Cicle(next_way);
                        break;
                    }
                }
            }
            case "end":
                isEnd = true;
                return;
        }
    }

    private void printStack(){
        int i = 1;
        for(coordinate s : stack){
            System.out.println((i++) + " " + s);
        }
    }

    public void setIpot(int i) {
        for (int k = 0; k < needs.length; k++) {
            if (result[i][k] > 0) {
                if (needs_pot[k] == null) {
                    needs_pot[k] = c[i][k] - res_pot[i];
                    setJpot(k);
                }
            }
        }
    }

    public void setJpot(int j){
        for (int k = 0; k < res_pot.length; k++) {
            if (result[k][j] > 0) {
                if (res_pot[k] == null) {
                    res_pot[k] = c[k][j] - needs_pot[j];
                    setIpot(k);
                }
            }
        }
    }

    public coordinate testPot() {
        res_pot = new Integer[m];
        needs_pot = new Integer[n];
        for (int i = 0; i < res_pot.length; i++) {
            for (int j = 0; j < needs_pot.length; j++) {
                if (result[i][j] > 0) {
                    if (res_pot[i] == null && needs_pot[j] == null) {
                        res_pot[i] = 0;
                        setIpot(i);
                    }
                }
            }
        }
        int max = -999999999;
        coordinate st = null;
        if(print){
            System.out.println("Потенциалы:");
            System.out.println("'$' - заполненная ячейка, '_' - пустая ячейка, '!' - не сопадает условие потенциалов");
        }
        points = new Stack<>();
        for (int i = 0; i < res_pot.length; i++) {
            for (int j = 0; j < needs_pot.length; j++) {
                if (result[i][j] > 0){
                    if(print) System.out.print("$\t");
                }
                else {
                    int check = res_pot[i] + needs_pot[j];
                    if (check > c[i][j]) {
                        points.push(new coordinate(i, j, "start"));
                        if (print) System.out.print("!\t");
                        if (check > max) {
                            st = new coordinate(i, j, "start");
                            max = check;
                        }
                    }
                    else{
                        if(print) System.out.print("_\t");
                    }
                }
            }
            if(print) System.out.print(res_pot[i] + "\n");
        }
        if(print) {
            for (int i = 0; i < needs_pot.length; i++)
                System.out.print(needs_pot[i] + "\t");
            System.out.println();
        }
        return st;
    }

    public TransportData(int[][] c, int[] res, int[] needs, boolean print ){
        this.c = c;
        this.res = res;
        this.needs = needs;
        this.m = res.length;
        this.n = needs.length;
        this.print = print;
        result = new int[m][n];
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                result[i][j] = 0;
        }
    }

    public void minEl(){
        int[] res_copy = res.clone();
        int[] needs_copy = needs.clone();
        int[][] new_c = new int[c.length][c[0].length];
        for(int i = 0; i < new_c.length; i++ )
            for (int j = 0; j < new_c[0].length; j++)
                new_c[i][j] = c[i][j];
        boolean check = true;
        while(true){
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < res_copy.length; i++) {
                if (res_copy[i] > 0)
                    check = false;
            }
            if (check) break;
            coordinate st = null;
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(needs_copy[j] == 0 || res_copy[i] == 0) continue;
                    if(new_c[i][j] < min){
                        st = new coordinate(i , j, "");
                        min = new_c[i][j];
                    }
                }
            }
            if(st != null){
                result[st.GetI()][st.GetJ()] = Math.min(res_copy[st.GetI()], needs_copy[st.GetJ()]);
                res_copy[st.GetI()] -= result[st.GetI()][st.GetJ()];
                needs_copy[st.GetJ()] -= result[st.GetI()][st.GetJ()];
                new_c[st.GetI()][st.GetJ()] = Integer.MAX_VALUE;
            }
            else {break;}
        }
        iterations = 0;
    }

    public void SeveroZapad(){
        int[] res_copy = res.clone();
        int[] needs_copy = needs.clone();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m && needs_copy[i] != 0; j++){
                if(res_copy[j] == 0) continue;
                result[j][i] = Math.min(res_copy[j], needs_copy[i]);
                res_copy[j] -= result[j][i];
                needs_copy[i] -= result[j][i];
            }
        }
        iterations = 0;
    }

    public void Fogel(){
        int[] res_copy = res.clone();
        int[] needs_copy = needs.clone();
        boolean check;
        while (true){
            int[] diff_res = new int[res_copy.length];
            int[] diff_need = new int[needs_copy.length];
            check = true;
            for(int i = 0; i < res_copy.length; i++) {
                if (res_copy[i] > 0)
                    check = false;
            }

            if(check) break;

            for(int i = 0; i < m; i++){
                if(res_copy[i] == 0) continue;
                int first_min = Integer.MAX_VALUE, second_min = Integer.MAX_VALUE, col = 0;
                for(int j = 0; j < n; j++){
                    if(needs_copy[j] == 0) continue;
                    if(c[i][j] < first_min){
                        first_min = c[i][j];
                        col = j;
                    }
                }
                for(int j = 0; j < n; j++){
                    if(needs_copy[j] == 0) continue;
                    if(c[i][j] < second_min && j != col){
                        second_min = c[i][j];
                    }
                }
                if(first_min == Integer.MAX_VALUE && second_min == Integer.MAX_VALUE) diff_res[i] = 0;
                else diff_res[i] = second_min - first_min;
            }

            for(int i = 0; i < n; i++){
                if(needs_copy[i] == 0) continue;
                int first_min = Integer.MAX_VALUE, second_min = Integer.MAX_VALUE, row = 0;
                for(int j = 0; j < m; j++){
                    if(res_copy[j] == 0) continue;
                    if(c[j][i] < first_min){
                        first_min = c[j][i];
                        row = j;
                    }
                }
                for(int j = 0; j < m; j++){
                    if(res_copy[j] == 0) continue;
                    if(c[j][i] < second_min && j != row){
                        second_min = c[j][i];
                    }
                }
                if(first_min == Integer.MAX_VALUE && second_min == Integer.MAX_VALUE) diff_need[i] = 0;
                else diff_need[i] = second_min - first_min;
            }

            if(print) {
                System.out.println("Разность в ресурсах:");
                for (int i = 0; i < diff_res.length; i++) {
                    System.out.print(i + ": " + diff_res[i] + "\t");
                }
                System.out.println();
                System.out.println("Разность в потребностях:");
                for (int i = 0; i < diff_need.length; i++) {
                    System.out.print(i + ": " + diff_need[i] + "\t");
                }
                System.out.println();
            }

            int row_max = 0, col_max = 0;
            for(int i = 1; i < diff_res.length; i++){
                if(diff_res[i] > diff_res[row_max])
                    row_max = i;
            }

            for(int i = 1; i < diff_need.length; i++){
                if(diff_need[i] > diff_need[col_max])
                    col_max = i;
            }

            if(print) {
                System.out.print("max_row: "+ row_max + "; max_col: " + col_max + ";");
                System.out.println();
            }
            boolean row_or_col = true;
            if(diff_need[col_max] > diff_res[row_max]){
                row_or_col = false;
            }
            if(row_or_col){
                if(print) System.out.println("По строке");
                int col_min = 0;
                int min = Integer.MAX_VALUE;
                for(int i = 0; i < diff_need.length; i++){
                    if(needs_copy[i] == 0) continue;
                    if(c[row_max][i] < min) {
                        min = c[row_max][i];
                        col_min = i;
                    }
                }
                if(print) System.out.println("Нарастим (" + row_max + ";" + col_min + ")");
                result[row_max][col_min] = Math.min(res_copy[row_max], needs_copy[col_min]);
                res_copy[row_max] -= result[row_max][col_min];
                needs_copy[col_min] -= result[row_max][col_min];
            }
            else{
                if(print) System.out.println("По столбцу");
                int row_min = 0;
                int min = Integer.MAX_VALUE;
                for(int i = 0; i < diff_res.length; i++){
                    if(res_copy[i] == 0) continue;
                    if(c[i][col_max] < min){
                        min = c[i][col_max];
                        row_min = i;
                    }
                }
                if(print) System.out.println("Нарастим (" + row_min + ";" + col_max + ")");
                result[row_min][col_max] = Math.min(res_copy[row_min], needs_copy[col_max]);
                res_copy[row_min] -= result[row_min][col_max];
                needs_copy[col_max] -= result[row_min][col_max];
            }

            if(print) {
                System.out.println("Оставшиеся ресурсы:");
                for (int i = 0; i < res_copy.length; i++) {
                    System.out.print(res_copy[i] + "\t");
                }
                System.out.println();

                System.out.println("Оставшиеся потребности:");
                for (int i = 0; i < needs_copy.length; i++) {
                    System.out.print(needs_copy[i] + "\t");
                }
                System.out.println();
                System.out.println();
                printResult();
            }
        }
        iterations = 0;
    }

    private coordinate take_next_point(){
        if( old_points == null ) {
            old_points = new Stack<>();
        }
        old_points.push(start_coordinate);
        for(coordinate s: points){
            if(!old_points.contains(s))
                return s;
        }
        return null;
    }

    public void printResult(){
        if(print) System.out.println("Итерация " + iterations);
        if(print) System.out.println("Результат:");
        for(int i=0; i<result.length; i++){
            for(int j=0; j<result[0].length; j++)
                System.out.print(result[i][j] + " \t");
            System.out.println();
        }
        System.out.println();
    }

    public boolean CheckResAndNeeds () {
        double sum1 = 0, sum2 = 0;
        for (int i = 0; i < m; i++) {
            sum1 += res[i];
        }
        for (int i = 0; i < n; i++) {
            sum2 += needs[i];
        }
        if (sum1 == sum2) return true;
        else return false;
    }
}
