/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author benno
 */
public class ResultInput {
    
    private static String result;
    
    public static String resultBox(String player1, String player2, int one, int two, double X, double Y){
        
        
        String title = "Input";  
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setX(X-30);
        window.setY(Y-30);
        
        
        
        Button winBtn = new Button();
        winBtn.setText("  Win  ");
        winBtn.setOnAction(e -> {
            result = "win";
            window.close();
        });
        
        Button remisBtn = new Button();
        remisBtn.setText("Remis");
        remisBtn.setOnAction(e -> {
            result = "remis";
            window.close();
        });
        
        Button lossBtn = new Button();
        lossBtn.setText(" Loss ");
        lossBtn.setOnAction(e -> {
            result = "loss";
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            result = "";
        });
        
        Button closeBtn = new Button(" Close ");
        closeBtn.setOnAction(e -> {
            result = "";
            window.close();
        });
        
        String vs = "    " +one + " vs. " + two + "     ("+player1+ " : "  + player2 +")";  
        Label lbl = new Label(" ");
        Label vs_lbl = new Label(vs);
        GridPane grid = new GridPane();
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(33);
        RowConstraints row0 = new RowConstraints(40);
        RowConstraints row1 = new RowConstraints(40);
        RowConstraints row2 = new RowConstraints(60);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(34);
        grid.getColumnConstraints().addAll(col0, col1, col2);
        grid.getRowConstraints().addAll(row0, row1, row2);
        
        grid.addRow(0, winBtn, remisBtn, lossBtn);
        grid.addRow(1, lbl, closeBtn);
        grid.addRow(2, new Label(""), vs_lbl);
       
        
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
        
        return result;
        
        
    }
    
}
