/* Ver 1.0 completed 2024.10.28
Roa Locklear
Written for Java openJDK 22.0.1 in IntelliJ
-------------------------------------------------------
[PROMPT]
Need a Scanner to be able to read specific commands.
Swap Row X Y  //X & Y represent integers such as 0, 1 to represent where in a 2D array to execute the swap
Swap Col X Y
Reverse Row X
Reverse Col Y
Rotate 90 Clockwise // Rotate the entire matrix/2D Array
Rotate 90 Counter  //paraphrasing from the original

It will be presented as a String Array such that:
{"Swap Row 0 1", "Reverse Col 2", "Rotate 90 Counter"}

This is in response to an evaluation prompt which asked the evaluatee
to write a program that could perform these operations on any square matrix
However, they could not use any reference materials of any sort. Time limit was 45 minutes

The real challenge for the evaluation was how to parse out the commands
with regex with no documentation. Code is not optimized.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;


class CommandReader {

    public static void swapRow(ArrayList<Integer> arr, int[][] matrix ){
         int[] findVal = new int[arr.size()];
         for(int i =0; i<arr.size(); i++){
            findVal[i]=arr.get(i);
         }
         System.out.println("Rows to swap as: ");
         for(int val: findVal)
            System.out.print(" "+val);
        System.out.println();
        /*
        Add code to perform the swap, in-place would be best but if this is on an evaluation
        then naive brute-force approach will probably be the fastest to implement.
        */

        int k, row1, row2 =0; //we'll use this to target the rows to swap
        row1 = findVal[0];
        row2 = findVal[1];
        k = matrix.length;

        ArrayList<Integer> rowA =new ArrayList<>();
        ArrayList<Integer> rowB = new ArrayList<>();

        for(int j = 0; j<k ; j++){ //this only works for square matrix
            rowA.add(matrix[row1][j]);
            rowB.add(matrix[row2][j]);
        }
        for(int j = 0; j<k ; j++){ //this only works for square matrix
            matrix[row1][j] =rowB.get(j);
            matrix[row2][j] = rowA.get(j);
        }
        printNewMatrix(matrix);//we haven't transformed the given matrix, yet.
    }//end swapRow
    public static void swapCol(ArrayList<Integer> arr, int[][] matrix ){
        int[] findVal = new int[arr.size()];
        for(int i =0; i<arr.size(); i++){
            findVal[i]=arr.get(i);
        }
        System.out.println("Columns to swap as: ");
        for(int val: findVal)
            System.out.print(" "+val);

        System.out.println();//probably a better way to format this, need to look that up.
        int k, col1, col2 =0;
        col1 = findVal[0];
        col2 = findVal[1];
        k = matrix.length;

        ArrayList<Integer> colA =new ArrayList<>();
        ArrayList<Integer> colB = new ArrayList<>();

        for(int i = 0; i<k ; i++){ //this only works for square matrix
            colA.add(matrix[i][col1]);
            colB.add(matrix[i][col2]);
        }//fill the arraylists for each column
        for(int i = 0; i<k ; i++){
            matrix[i][col1] = colB.get(i);
            matrix[i][col2] = colA.get(i);
        }//place new element sequences into given matrix
        printNewMatrix(matrix);
    }//end swapCol

    public static void reverseRow(ArrayList<Integer> arr, int[][] matrix){
        int[] findVal = new int[arr.size()]; //overkill since there should be only one element
        for(int i =0; i<arr.size(); i++){
            findVal[i]=arr.get(i);
        }

        System.out.println("Row to reverse: ");
        for(int val: findVal)
            System.out.print(" "+val);
        System.out.println();

        ArrayList<Integer> rowReverse =new ArrayList<>();//reversing an arrayList is trivial :(
        ArrayList<Integer> reversedRow = new ArrayList<>();
        int k = matrix.length;
        int n = k;
        int hold = findVal[0]; //we want to stay on the correct row
        int temp = 0;
        int i =0; //we'll use this to control the while loop
        for(int j =0; j<k; j++){
            rowReverse.add(matrix[hold][j]);
        }
        /*
        Arraylist can leverage the standard Collections to be reversed.
        e.g. Collections.reverse(rowReverse);
        However, lets try to do it ourselves instead.
         */
        while(n !=0){
            temp =  rowReverse.get(n-1);//needs an offset
            reversedRow.add(temp);
            n--;

        }
        for(int g= 0; g<k; g++){//variables got a little out of hand
            matrix[hold][g] = reversedRow.get(g);
        }
        printNewMatrix(matrix);
    } //end reverseRow

    public static void reverseCol(ArrayList<Integer> arr, int[][] matrix){
        int[] findVal = new int[arr.size()]; //overkill since there should be only one element
        for(int i =0; i<arr.size(); i++){
            findVal[i]=arr.get(i);
        }

        System.out.println("Column to reverse: ");
        for(int val: findVal)
            System.out.print(" "+val);
        System.out.println();

        ArrayList<Integer> colReverse =new ArrayList<>();
        ArrayList<Integer> reversedCol = new ArrayList<>();
        int k = matrix.length;
        int n = k;
        int hold = findVal[0]; //we want to stay on the correct row
        int temp = 0;
        int j =0; //we'll use this to control the while loop
        for(int i =0; i<k; i++){
            colReverse.add(matrix[i][hold]);
        }

        while(n !=0){
            temp =  colReverse.get(n-1);//needs an offset
            reversedCol.add(temp);
            n--;

        }
        for(int g= 0; g<k; g++){
            matrix[g][hold] = reversedCol.get(g);
        }
        printNewMatrix(matrix);
    }

    public static void rotateClockwise(int[][] matrix){ //should be able to take the transpose and then swap the columns
        //printNewMatrix(matrix); //diagnostic to help with visualization
        //create the transpose
        int n = matrix.length; //the number of rows and columns will be the same
        for(int i =0; i< matrix.length; i++){
            for(int j=i; j< matrix[i].length; j++){
                if(i!=j){
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
        //printNewMatrix(matrix); //diagnostic, for transpose step

       /*
       Should be able to just swap the columns since we have the transpose
       would have liked to call the swapCol method, but it won't work due to its signature and assumptions :(
       */
       for(int i=0; i<n; i++){
           int bottom =0, top = n-1;//needs an offset
          /*
          idea is to reverse the elements of each row,
          since we have elements in the rows we desire
          just not in the target order
           */
           while (bottom<top){//potential to strike an edge case?
               int temp = matrix[i][bottom];
               matrix[i][bottom] = matrix[i][top];
               matrix[i][top] = temp;
               bottom++;
               top--;
           }
       }
       printNewMatrix(matrix);
    }

    public static void rotateCounterClockwise(int[][] matrix){
        /*
        Since the methodology for Clockwise worked,
        we should only have to reverse the elements in
        each column in order to rotate a matrix counter-clockwise
         */
        //printNewMatrix(matrix); //diagnostic to help with visualization
        //create the transpose
        int n = matrix.length;
        for(int i =0; i< matrix.length; i++){
            for(int j=i; j< matrix[i].length; j++){
                if(i!=j){
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
        //printNewMatrix(matrix); //diagnostic

        for(int i=0; i<n; i++){
            int bottom =0, top = n-1;
          /*
          idea is to reverse the elements of each column,
          since we have elements in the columns we desire
          just not in the target order
           */
            while (bottom<top){
                int temp = matrix[bottom][i];
                matrix[bottom][i] = matrix[top][i];
                matrix[top][i] = temp;
                bottom++;
                top--;
            }
        }
        printNewMatrix(matrix);
    }


    public static void printPass(ArrayList<Integer> arr){
        for(int num: arr)
            System.out.println("Print Pass Coordinate: "+num);
    }

    public static void printNewMatrix(int[][] matrix){
        System.out.println("The new matrix is: ");
        for(int[] row : matrix){
            for(int col : row){
                System.out.print(" "+col+" ");
            }//end inner loop
            System.out.println();
        }//end outer loop
    }// end printNewMatrix



    public static void getCommand (String[] arr, int[][] matrix){

        for(int i =0; i<arr.length; i++) {
            String current = arr[i];
            ArrayList<Integer> passIt =new ArrayList<>();
            Scanner s = new Scanner(current);
            //now we need to parse current, so we are looking for Swap Row, Swap Col, Reverse Row, Reverse Col, etc.

            Pattern swapRow = Pattern.compile("Swap Row");
            Pattern swapCol = Pattern.compile("Swap Col");
            Pattern reverseRow = Pattern.compile("Reverse Row");
            Pattern reverseCol = Pattern.compile("Reverse Col");
            Pattern clockWise = Pattern.compile(".*\\bClockwise");
            Pattern counterClock =Pattern.compile(".*CounterClockwise");
            int temp =0;
            switch(current) {
                case String r when swapRow.matcher(r).lookingAt():
                    //do something with the numbers call a sub-method to do an action.
                    System.out.println("Operation to perform: ");
                     //Could probably take this out of the switch structure
                    while(s.hasNext()){//we know we need 2 int for a coordinate
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next()); //best practice, it won't advance the token pointer
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                            }
                        }
                    //printPass(passIt); //diagnostic
                    System.out.println();
                    swapRow(passIt, matrix);
                    break;
                case String r when swapCol.matcher(r).lookingAt():
                    System.out.println("Operation to perform: ");
                    while(s.hasNext()){
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next());
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                        }
                    }
                    System.out.println();
                    swapCol(passIt, matrix);
                    break;
                case String r when reverseRow.matcher(r).lookingAt():
                    System.out.println("Operation to perform: ");
                    while(s.hasNext()){
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next());
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                        }
                    }
                    System.out.println();
                    reverseRow(passIt, matrix);
                    break;
                case String r when reverseCol.matcher(r).lookingAt():
                    System.out.println("Operation to perform: ");
                    while(s.hasNext()){
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next());
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                        }
                    }
                    System.out.println();
                    reverseCol(passIt, matrix);

                    break;
                case String r when clockWise.matcher(r).lookingAt():
                    System.out.println("Operation to perform: ");
                    while(s.hasNext()){
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next());
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                        }
                    }
                    System.out.println();
                    rotateClockwise(matrix);
                    break; //redundant, khong?
                case String r when counterClock.matcher(r).lookingAt():
                    System.out.println("Operation to perform: ");
                    while(s.hasNext()){
                        if (s.hasNextInt()) {
                            temp = Integer.parseInt(s.next());
                            passIt.add(temp);
                        }else if(s.hasNext()){
                            System.out.print(" "+s.next());
                        }else {
                            System.out.println("Input failed.");
                        }
                    }
                    System.out.println();
                    rotateCounterClockwise(matrix);
                    break;
                case null: //Ideally, should never hit this case, should add exception handling.
                    System.out.println("NULL DETECTED!!!");
                default:

                    System.out.println("Command was not recognized. "+current);
            }//end of switch
        }//end of for loop

    }//end of getCommand

}// End of CommandReader

class Input{//would like to update this to accept input from a text file or pull from an API as a future improvement
    public static void main(String[] args){
        String[] arr = {"Swap Row 0 2","Reverse Row 2", "Rotate 90 CounterClockwise",
                "Swap Columns 0 1", "Rotate 90 Clockwise","Reverse Col 1"};
     int[][]  matrix = {{1,2,3,4},{5,6,7,8}, {9,10,11,12}, {13,14,15,16}}; //Easy to work with can be any square matrix

     CommandReader.getCommand(arr, matrix);
    }//end of main

}//end of StringInput

//EOF