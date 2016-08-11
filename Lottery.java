/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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




public class Lottery {

    
    private static final boolean DEBUG = true;
    private final Button[][] buttonList = new Button[21][21];
    //private static int evaluationList[][];
    private final int round = 1;
    //private static LookUpTable Tournament_V2,look;
    private int[] players;
    private int[][] pairsList;
    private VBox vb1;
    private VBox vb2;
    private HBox hb_bottom;
    private List<Integer> nonEmpty = new ArrayList<>();
    
    
    
    Lottery(){
        
        
    }
    
    
    
    public void initLottery(){
        
        //init ButtonList
        
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
        
        //init pairsList
        pairsList = new int[21][3];
        for (int i = 1; i <= 20; i++){            
            for (int j = 0; j < 3; j++){
                pairsList[i][j] =  0;
            }
        }
      
        
        
        
        String title = "put results in";  
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(600);
        
        Tournament_V2.look = new LookUpTable(Tournament_V2.rr_list.size());
            if (DEBUG){
                Tournament_V2.look.printLookUp();
                System.out.println("Tournament_V2,lookUpPosition: 1:0 " + Tournament_V2.look.isPossibleLookUpPosition(1, 0));
                System.out.println("Tournament_V2,lookUpPosition: 1:3 " + Tournament_V2.look.isPossibleLookUpPosition(1, 3));
                System.out.println("Tournament_V2,lookUpPosition: 1:4 " + Tournament_V2.look.isPossibleLookUpPosition(1, 4));
                System.out.println("Tournament_V2,lookUpPosition: 4:1 " + Tournament_V2.look.isPossibleLookUpPosition(4, 1));
               
                
            }
      
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

    public void SetPlayerToEvaluationList(){
        //evaluationList = new int[40][21];   //max 19 points = 38 - max 20 Players
        //init evaluationList
        for (int i = 1; i < 39; i++){
            for (int j = 1; j <=20; j++){
                Tournament_V2.evaluationList[i][j] = 0; 
            }
        }
        try{
        for (int i = 0; i < Tournament_V2.rr_list.size(); i++) {
            //System.out.println(list.get(i).getPointsRow());
            int index; //points * 2 is position
            index = (int) (Tournament_V2.rr_list.get(i).getPointsRow()*2);
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
            getLevelIsNotEmpty();
            lottoRoundOne();
        }
    
    
    private List<Integer> getLevelIsNotEmpty(){
            nonEmpty.clear();
            for (int j = 0; j < 21; j++){
                if (Tournament_V2.evaluationList[0][j] != 0){   
                    nonEmpty.add(Tournament_V2.evaluationList[0][j]);
                }
            }
            System.out.println("getLevelIsNotEmpty: "  + nonEmpty.toString());
        
        return nonEmpty;    
    }    
    
    public void lottoRoundOne(){
        int gamesCounter = 1;
        nonEmpty = getLevelIsNotEmpty();
        Collections.shuffle(nonEmpty);
        //int counter = nonEmpty.size()/2;
        while ( !nonEmpty.isEmpty()){
            
            players = new int[2];               
            players[0] = nonEmpty.get(0);
            players[1] = nonEmpty.get(1);
            
            pairsList[gamesCounter] = players;
            setPlayersLabelsAndButtons(nonEmpty.get(0),nonEmpty.get(1));
            //Test
            
            


        //set the white/black to result row (rr_list)
//            ResultRow rr =  tournament_v2.Tournament_V2.rr_list.get(players[0]);
//            rr.setWhite();
//            rr =  tournament_v2.Tournament_V2.rr_list.get(players[1]);
//            rr.setBlack();
//        
            
            
            
            //correction the Tournament_V2,lookUpTable
            Tournament_V2.look.setLookUpPositionZero(nonEmpty.get(0), nonEmpty.get(1));
            
            //correction the evaluationList
            Tournament_V2.evaluationList[0][nonEmpty.get(0)] = 0;
            Tournament_V2.evaluationList[0][nonEmpty.get(1)] = 0;
            System.out.println("gezogen: " + Arrays.toString(pairsList[gamesCounter]));
            
            //temp clean up from Players at Index 0 and 1
            nonEmpty.remove(0);
            nonEmpty.remove(0); // after first remove next is all so at index 0
            System.out.print("   " + nonEmpty.toString());
            gamesCounter++;
            
            System.out.println("");
            System.out.println("pairsList: " + Arrays.deepToString(pairsList));
        }
        
    
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
    
    
    
    
}
        
   
    

