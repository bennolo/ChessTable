/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package tournament_v2

/**
 *
 * @author benno
 */
class LabelRank//Constructor
internal constructor(labelIndex: Int, sbValue: Double, rank: Int) {
    /**
     * @return the labelIndex
     */
    /**
     * @param labelIndex the labelIndex to set
     */
    var labelIndex: Int = 0
    /**
     * @return the rank
     */
    /**
     * @param rank the rank to set
     */
    var rank: Int = 0
    /**
     * @return the sb_value
     */
    /**
     * @param sb_value the sb_value to set
     */
    var sb_value: Double = 0.toDouble()

    init {
        this.labelIndex = labelIndex
        this.sb_value = sbValue
        this.rank = rank
    }
}