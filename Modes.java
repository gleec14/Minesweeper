/*Built in Minesweeper modes
 * Mode 0: Custom
 * Mode 1: Easy (10x10)
 * Mode 2: Medium (10*20)
 * Mode 3: Hard (20*20)
 * Mode 4: Lunatic (40*40)
*/
import java.util.Scanner;

public class Modes {
  //fields
  private int rows;
  private int columns;
  private int bombs;
  private static int nonbombs;
  
  //Sets rows and columns for default grid sizes and custom size
  public Modes(int mode){
    switch(mode){
      case 0:
        custom();
        break;
      case 1:
        rows = 10;
        columns = 10;
        break;
      case 2:
        rows = 10;
        columns = 20;
        break;
      case 3:
        rows = 20;
        columns = 20;
        break;
      case 4:
        rows = 40;
        columns = 40;
        break;
      default:
        System.out.println("Not a mode");
        break;
    }
    bombs = 0;
  }
  
  //Gets Rows
  public int getRows(){
   return rows; 
  }
  //Gets Columns
  public int getColumns(){
   return columns; 
  }
  
  //Sets custom grid size
  public void custom(){
      Scanner scan = new Scanner(System.in);
      System.out.println("Input number of rows");
      int r = scan.nextInt();
      System.out.println("Input number of columns");
      int c = scan.nextInt();
      rows = r;
      columns = c;
  }
  
  public void setGrids(int[][] grid, Boolean[][]hiddenGrid, int totalBombs){
    //Sets bombs into the grid
    int bombsLeft = totalBombs;
    
    for(int k = 0; k < grid.length; k++){
      for(int i = 0; i < grid[k].length; i++){
        int rand = (int)(Math.random() * 5);
        if(rand == 1 && bombsLeft > 0 && grid[k][i] != -1){
          grid[k][i] = -1;
          bombsLeft--;
        }
        if(k == grid.length-1 && i == grid[k].length-1 && bombsLeft != 0){
          k = 0;
          i = 0;
        }
      }
    }
    bombs = totalBombs - bombsLeft;
    nonbombs = rows*columns - bombs;
    System.out.println("There are " + bombs + " bombs");
    
   //Detect number of bombs around squares and creates hints and bombs
    for(int k = 0; k < grid.length; k++){
      for(int i = 0; i < grid[k].length; i++){
        int numBombsDetected = 0;
        if(grid[k][i] != -1){
          if(k-1 >= 0 && i-1 >= 0 && grid[k-1][i-1] == -1)
            numBombsDetected++;
          if(k-1 >= 0 && grid[k-1][i] == -1)
            numBombsDetected++;
          if(k-1 >= 0 && i+1 < grid[k].length && grid[k-1][i+1] == -1)
            numBombsDetected++;
          if(i-1 >= 0 && grid[k][i-1] == -1)
            numBombsDetected++;
          if(i+1 < grid[k].length && grid[k][i+1] == -1)
            numBombsDetected++;
          if(k+1 < grid.length && i-1 > 0 && grid[k+1][i-1] == -1)
            numBombsDetected++;
          if(k+1 < grid.length && grid[k+1][i] == -1)
            numBombsDetected++;
          if(k+1 < grid.length && i+1 < grid[k].length && grid[k+1][i+1] == -1)
            numBombsDetected++;
          grid[k][i] = numBombsDetected;
        }
        else
          grid[k][i] = -1;
        
      }
    }
    
    //Set Grid to false meaning everything is hidden
    for(int k = 0; k < grid.length; k++){
      for(int i = 0; i < grid[k].length; i++){
        hiddenGrid[k][i] = false;
      }
    }
    
 }
  
  //Draws Grid
  public void drawGrid(int [][] grid, Boolean [][] hiddenGrid){
   for(int k = 0; k < grid.length; k++){
      for(int i = 0; i < grid[k].length; i++){
        if (i == 0)
          System.out.println(""); 
        System.out.print("| ");
        if (hiddenGrid[k][i] == false)
          System.out.print("-"); 
        else
          System.out.print(grid[k][i]);
      }
      System.out.print("|");
    } 
  }
  
  //Updates grid per move
  public void updateGrid(int[][] grid, Boolean[][] hiddenGrid, int row, int col ){
     printZeroes(grid, hiddenGrid, row, col);   
     drawGrid(grid, hiddenGrid);
     }
  
  //sets player chosen coordinate to be visible
  //if player chosen coordinate equals 0, sets surrounding hints to visible
  //decreases nonbombs to show that the player is one step closer to winning
  private static void printZeroes(int[][] grid, Boolean [][] hiddenGrid, int row, int col){
     for (int k = 0; k < grid.length; k++){
       for (int i = 0; i < grid[k].length; i++){
         if(k == row && i == col && hiddenGrid[k][i] == false){
            hiddenGrid[k][i] = true;
            nonbombs--;
         }
         
         //checks the surroundings of each point to see if there is an adjacent zero 
         //point should be set visible if there is an adjacent zero
         // zeroes = 1 so that the loop can be initiated
         // zeroes = 0 so that the loop can end if all hints adjacent to zeroes are shown
         // zeroes++ because we dont know if adjacent point is a zero or a number hint
          int zeroes = 1;
         
          while(zeroes > 0){
            zeroes = 0;
            for (int r = 0; r < grid.length; r++){
              for (int c = 0; c < grid[r].length; c++){
                if(hiddenGrid[r][c] == false && grid[r][c] != -1 &&(
                   (r-1 >= 0 && c-1 >= 0 && hiddenGrid[r-1][c-1] == true && grid[r-1][c-1] == 0) || 
                   (r-1 >= 0 && hiddenGrid[r-1][c] == true && grid[r-1][c] == 0) ||
                   (r-1 >= 0 && c+1 < grid[r].length && hiddenGrid[r-1][c+1] == true && grid[r-1][c+1] == 0) ||
                   (c-1 >= 0 && hiddenGrid[r][c-1] == true && grid[r][c-1] == 0) ||
                   (c+1 < grid[r].length && hiddenGrid[r][c+1] == true && grid[r][c+1] == 0) ||
                   (r+1 < grid.length && c-1 > 0 && hiddenGrid[r+1][c-1] == true && grid[r+1][c-1] == 0) ||
                   (r+1 < grid.length && hiddenGrid[r+1][c] == true && grid[r+1][c] == 0) ||
                   (r+1 < grid.length && c+1 < grid[r].length && hiddenGrid[r+1][c+1] == true && grid[r+1][c+1] == 0)
                )){
                  hiddenGrid[r][c] = true;
                  nonbombs--;
                  zeroes++;
                }
              }
            }
          }
        }
      }
    }
  //checks if nonbombs is zero
  //if nonbomns is zero, player has uncovered all hints and no bombs
  //this means that the player has won
  //if nonbombs > 0, player is still playing. return 0 to keep gamestate loop running
  public int checkWin(){
      if(nonbombs == 0){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Congratulations. You won");
        return 1;     
      }
      return 0;
  }
}


