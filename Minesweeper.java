import java.util.Scanner;

public class Minesweeper{
  
  public static void main(String[] args){
    //Fields
    int rows;
    int cols;
    int numBombs;
    int gameState = 0; //0 = running; 1 = win; -1 = lose
    
    //Start of Program
    Scanner scan = new Scanner(System.in);
    System.out.println("Select a mode: Mode 0: Custom  Mode 1: Easy (10x10) Mode 2: Medium (10*20)Mode 3: Hard (20*20)Mode 4: Lunatic (40*40)");
    int m = scan.nextInt(); 
    while(m < 0 || m > 4){
      m = scan.nextInt(); 
    }
    
    //Sets fields and instantiates object
    Modes mode = new Modes(m);
    rows = mode.getRows();
    cols = mode.getColumns();
    numBombs = (int)(rows*cols*.25);
      
    //Sets grid with values and grid that shows if values should be hidden or shown
    int[][] grid = new int[rows][cols];
    Boolean[][] hiddenGrid = new Boolean[rows][cols];
    
    //Sets the grid and prints initial grid
    mode.setGrids(grid, hiddenGrid, numBombs);
    mode.drawGrid(grid, hiddenGrid);
    int r = rows;
    int c = cols;
    
    //game loop after each turn until win or lose
    while(gameState == 0){
      //asks for player to input rows and columns
      System.out.println("Insert rows 1-" + r);
      rows = scan.nextInt() - 1;
      System.out.println("Insert columns 1-" + c);
      cols = scan.nextInt() - 1;
      //updates grid
      mode.updateGrid(grid, hiddenGrid, rows, cols);
      //win or continue-loop condition
      gameState = mode.checkWin();
      //lose condition
      if(grid[rows][cols] == -1){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("You stepped on a mine. Game Over");
        gameState = -1;
      }
     
    }
      
  }
  
  
}
  

