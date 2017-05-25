/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static tournament_v2.Tournament_V2.Btn;

/**
 *
 * @author benno
 */
public class LotteryRecursive {
    
    private final boolean DEBUG = true;
    private final Button[][] buttonList = new Button[21][21];
    private static int round;
    private int[] players;
    private int[][] pairsList;
    private VBox vb1;
    private VBox vb2;
    private HBox hb_bottom;
    private final List<Integer> nonEmpty = new ArrayList<>();
    private final List<Integer> fromTop = new ArrayList<>();

    public LotteryRecursive() {
    }
    
    public void initLotteryRecursive(){
        
    
    String title = "put results in";  
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(600);
        Label lblWhiteBlack = new Label("White        vs       Black");
        
        vb1 = new VBox();
        vb1.setSpacing(10);
        vb1.setPadding(new Insets(10,10,10,10));
        vb1.getChildren().addAll(lblWhiteBlack);
        
        vb2 = new VBox();
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10,10,10,10));
        vb2.getChildren().addAll(new Label("  "));
        
        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setPadding(new Insets(10,10,10,10));
        hb.getChildren().addAll(vb1, vb2);
        
        Button closeBtn = new Button("close");
        closeBtn.setPrefWidth(60);
        closeBtn.setOnAction(e -> {
            window.close();
        });
        
        hb_bottom = new HBox();
        hb_bottom.setSpacing(10);
        hb_bottom.setPadding(new Insets(10,10,10,10));
        hb_bottom.getChildren().addAll(new Label(" "), new Label(" "), closeBtn); 
        
        
            
        SetPlayerToEvaluationList();
        
        BorderPane bp = new BorderPane();
        //bp.setTop(hb);
        bp.setCenter(hb);
        bp.setBottom(hb_bottom);
        Scene scene = new Scene(bp);
        scene.getStylesheets().add("/tournament_v2/styles.css");
        window.setScene(scene);
        window.showAndWait();
    }
    
    private void setPlayersLabelsAndButtons(int playerWhite, int playerBlack){
        Label tmpLabel = new Label();
        tmpLabel.setPrefHeight(30);
        String labelText = "";
        tmpLabel.setText(playerWhite + "         :           " + playerBlack);
        vb1.getChildren().add(tmpLabel);
        vb2.getChildren().add(buttonList[playerWhite][playerBlack]);
        
    }
    
    public void initButtonList(){
        
        
        for (int i = 1; i < 21; i++){
            for (int j = 1; j < 21; j++){
                buttonList[i][j] = new Button();
                buttonList[i][j].setPrefHeight(30);
            }
        }
        
        
        //init setOnAction for ButtonList
        for (int i = 1; i < 21; i++){
            for (int j = 1; j < 21; j++){
                int row = i;
                int col = j;
                buttonList[i][j].setOnAction(e -> {  
                    Btn[row][col].fire();                        
                });
            }
        }
    }
    
    public void SetPlayerToEvaluationList(){
        
        int row = 0;
        //evaluationList = new int[40][21];   //max 19 points = 38 - max 20 Players
        //init evaluationList
        //first clear Array
        for (int i = 1; i < 39; i++){
            row = i;
            for (int j = 1; j <=20; j++){
                Tournament_V2.evaluationList[i][j] = 0; 
            }
        }
        try{
        for (int i = 0; i < Tournament_V2.rr_list.size(); i++) {
            
            //System.out.println(list.get(i).getPointsRow());
            int index; //points * 2 is position
            
            index = (int) (Tournament_V2.rr_list.get(i).getPointsRow()*2);
            row = index;
            //System.out.println("tournament_v2.Lottery.SetPlayerToEvaluationList() Index: "+index);
            int player = Tournament_V2.rr_list.get(i).getPlayerIndex();
            //System.out.println("tournament_v2.Lottery.SetPlayerToEvaluationList() Player: "+ player);
            Tournament_V2.evaluationList[index][player] = player;
            //System.out.println("evaluationList=:" + index + " " + evaluationList[i][player]);
        }
        }catch (Exception e){
            System.out.println("tournament_v2.Lottery.SetPlayerToEvaluationList()" + "Error");
        }
        
        if (DEBUG){ 
                for (int i = Tournament_V2.evaluationList.length-1; i >= 0; i--){
                    System.out.println("evalList: " + i + "  " + Arrays.toString(Tournament_V2.evaluationList[i]));
//                    for (int j = 1; j < 21; j++){
//                        if (evaluationList[i][j] != 0){
//                            System.out.println("evaluationList=:" + i + " "+j + " value: "+evaluationList[i][j]);
//                        }
//                    }
                }
                System.out.println("------------DEBUG");
            } 
            getLevelIsNotEmpty(row);
            System.out.println("getLevelIsNotEmpty: " + row + "  " + nonEmpty.toString());
        }
    
    private List<Integer> getLevelIsNotEmpty(int row){
            nonEmpty.clear();
            for (int j = 0; j < 21; j++){
                if (Tournament_V2.evaluationList[row][j] != 0){   
                    nonEmpty.add(Tournament_V2.evaluationList[row][j]); //
                }
            }
                   
        return nonEmpty;    
    }

    
}
