/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import java.util.List;
import javafx.scene.paint.Color;
import static tournament_v2.Tournament_V2.Btn;


public interface My_I {
    
    static int numberOfPlayers  (List<String> l){
        int start = 21;
        long count = l.stream()
                .filter(i -> i.isEmpty())
                .count();
        
        return start - (int)count;
    }
    
    default void fillButtons() {
        int k = 1;
        int l = 1;
        while (k <= 20 || l <= 20){
            Btn[k][l].setStyle("-fx-background-color: black;");
            Btn[k][l].setTextFill(Color.BLACK);
            Btn[k][l].setDisable(true);
            k++;
            l++;
        }
    }
    
}
