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
public class LabelRank {
    
    private int labelIndex;
    private int rank;
    private double sb_value;

    //Constructor
    LabelRank(int labelIndex, double sbValue, int rank){
        this.labelIndex = labelIndex;
        this.sb_value = sbValue;
        this.rank = rank;
    }

    /**
     * @return the labelIndex
     */
    public int getLabelIndex() {
        return labelIndex;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @return the sb_value
     */
    public double getSb_value() {
        return sb_value;
    }

    /**
     * @param labelIndex the labelIndex to set
     */
    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * @param sb_value the sb_value to set
     */
    public void setSb_value(double sb_value) {
        this.sb_value = sb_value;
    }

    
    
    
}
