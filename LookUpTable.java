/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

/**
 *
 * @author benno
 */
public class LookUpTable {
    
    private static int[][] lookUp;
    private final int numOfPlayers;
    private final boolean DEBUG = true;
    
        LookUpTable(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        LookUpTable.lookUp = new int[numOfPlayers+1][numOfPlayers+1];
        
        for (int i = 1; i <= numOfPlayers; i++){
            for (int j = numOfPlayers; j >= i; j--){
                int play;
                if (i == j){
                    play = 0;
                } else {
                    play = 1;
                }
                lookUp[i][j] = play;
            }
        }
        
        for (int i = numOfPlayers; i >= 1; i--){
            for (int j = 1; j <= numOfPlayers; j++){
                int play;
                if (i == j){
                    play = 0;
                } else {
                    play = 1;
                }
                lookUp[i][j] = play;
            }
        }        
        
    }
       
    public boolean isPossibleLookUpPosition(int row, int col) {
      //true = game possible
        int poss = lookUp[row][col];
        if (poss == 1){
            return true;
        } else {
            return false;
        }
    }
    
    public void setLookUpPositionZero(int row, int col){
        lookUp[row][col] = 0;
        lookUp[col][row] = 0;
        if (DEBUG){
            System.out.println();
            printLookUp();
        }
    }
    
    public void printLookUp(){
        for (int i = 1; i <= numOfPlayers; i++){
            System.out.print("row i: " +i + "  ");
            for (int j = 1; j <= numOfPlayers; j++){
                System.out.print("\t" + lookUp[i][j]);
                if ( j == numOfPlayers ){
                    System.out.println();
                }
            }
        }
    }
    
    
    
}
