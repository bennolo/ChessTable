/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import java.util.List;



/**
 *
 * @author benno
 */
public interface MyInterface {
    
    static int test  (List<String> l){
        int start = 21;
        long count = l.stream()
                .filter(i -> i.isEmpty())
                .count();
        
        return start - (int)count;
    }
    
}
