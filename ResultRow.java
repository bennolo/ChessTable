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
public class ResultRow {
    
    private int playerIndex;
    private String playerName;
    private int r1, r2, r3;
    private double pointsRow;
    private double sonnebornBerger;
    private int playerRank;
    private int blackWhite;
    
    
    //Constructor
    public ResultRow(){
        
    }

    public ResultRow(int playerIndex, int playerRank, String playerName, int r1, int r2, int r3, double pointsRow, double sb) {
        this.playerIndex = playerIndex;
        this.playerRank = playerRank;
        this.playerName = playerName;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.pointsRow = pointsRow;
        this.sonnebornBerger = sb;
        this.blackWhite = 0;
    }

     
    public int getPlayerIndex(){
        return playerIndex;
    }
    
    public void setPlayerIndex(int playerIndex){
        this.playerIndex = playerIndex;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return the pointsRow
     */
    public double getPointsRow() {
        return pointsRow;
    }

    /**
     * @return the sonnenbornBerger
     */
    public double getSonnebornBerger() {
        return sonnebornBerger;
    }

    /**
     * @return the playerRank
     */
    public int getPlayerRank() {
        return playerRank;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @param pointsRow the pointsRow to set
     */
    public void setPointsRow(double pointsRow) {
        this.pointsRow = pointsRow;
    }

    /**
     * @param sb the sonnebornBerger to set
     */
    public void setSonnebornBerger(double sb) {
        this.sonnebornBerger = sb;
    }

    /**
     * @param playerRank the playerRank to set
     */
    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }
    

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }
    
    public int getBlackWhite(){
        return blackWhite;
    }
    
    public void setBlack(){
        blackWhite -= 1;
    }
    
    public void setWhite(){
        blackWhite += 1;
    }
    
    
}
