/*Attempt to rebuild Java file */



class Input {
      public static void main(String[] args) {
      String[] arr = new String[]{"Swap Row 0 2", "Reverse Row 2", "Rotate 90 CounterClockwise", "Swap Columns 0 1", "Rotate 90 Clockwise", "Reverse Col 1"};
      int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
      CommandReader.getCommand(arr, matrix);
   }
}
