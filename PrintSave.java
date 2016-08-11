/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Reflection;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static tournament_v2.Tournament_V2.showTableBtn;

/**
 *
 * @author benno
 */

public class PrintSave {
    
    private List<ResultRow> list;
    private final ObservableList<ResultRow> data = FXCollections.observableArrayList();
    private LocalDate date;
    private String tf;
    private String tf_club;
    private BorderPane bp;
    
    public void printSaveBox(List<ResultRow> givenList,LocalDate date, String tf, String tf_club){
        
        this.list = givenList;
        this.date = date;
        this.tf = tf;
        this.tf_club = tf_club;
        
        //System.out.println("givenList.size(): " + givenList.size());
    
    String title = "final result";  
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMaxWidth(650);
        window.setMinHeight(700);
    
        TableView table = new TableView();
        TableColumn<ResultRow, Integer> col1 = new TableColumn<>("Rank");
        col1.setMaxWidth(70);
        col1.setStyle( "-fx-alignment: CENTER; -fx-font-weight: bold");
        col1.setCellValueFactory(new PropertyValueFactory<>("playerRank"));
        
        TableColumn<ResultRow, String> col2 = new TableColumn<>("Player");
        col2.setMinWidth(200);
        col2.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        
        TableColumn<ResultRow, Integer> col3 = new TableColumn<>("Win");
        col3.setMaxWidth(60);
        col3.setStyle( "-fx-alignment: CENTER;");
        col3.setCellValueFactory(new PropertyValueFactory<>("r1"));
        
        TableColumn<ResultRow, Integer> col4 = new TableColumn<>("Remis");
        col4.setMaxWidth(60);
        col4.setStyle( "-fx-alignment: CENTER;");
        col4.setCellValueFactory(new PropertyValueFactory<>("r2"));
        
        TableColumn<ResultRow, Integer> col5 = new TableColumn<>("Loss");
        col5.setMaxWidth(60);
        col5.setStyle( "-fx-alignment: CENTER;");
        col5.setCellValueFactory(new PropertyValueFactory<>("r3"));
        
        TableColumn<ResultRow, Double> col6 = new TableColumn<>(" ");
        col6.setMaxWidth(10);
        
        TableColumn<ResultRow, Double> col7 = new TableColumn<>("Points");
        col7.setPrefWidth(70);
        col7.setStyle( "-fx-alignment: CENTER;");
        col7.setCellValueFactory(new PropertyValueFactory<>("pointsRow"));
        
        TableColumn<ResultRow, Double> col8 = new TableColumn<>("SB");
        col8.setPrefWidth(70);
        col8.setStyle( "-fx-alignment: CENTER;");
        col8.setCellValueFactory(new PropertyValueFactory<>("sonnebornBerger"));
        neuBerechnung();
        table.getItems().addAll(data);
         
       
        table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
        table.setMinHeight(550);
        Label headlineRound = new Label();
        headlineRound.setText(createHeadLineText());
        headlineRound.setStyle("-fx-font-size: 18;");
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(headlineRound, table);
              
        Reflection refl = new Reflection();
        refl.setFraction(0.8);
        refl.setTopOffset(-0.3);
        
        Label lblClub = new Label();
        lblClub.setMinSize(180,20);
        lblClub.setText(tf);
        lblClub.setId("lblClub");
        lblClub.setEffect(refl);
        
        Label lblTournament = new Label();
        lblTournament.setMinSize(180,20);
        lblTournament.setText(tf_club);
        lblTournament.setId("lblTournament");
        lblTournament.setEffect(refl);
        
        Label lblDate = new Label();
        lblDate.setMaxSize(100,20);
        lblDate.setText(date.toString());
        lblDate.setStyle("-fx-text-inner-color: blue; -fx-font-size: 12;");
        lblDate.setEffect(refl);
        
        HBox hbTop = new HBox();
        hbTop.setPadding(new Insets(10,10,10,10));
        hbTop.setSpacing(60);
        hbTop.getChildren().addAll(lblClub, lblTournament, lblDate);
        
        Button closeBtn = new Button(" Close ");
        closeBtn.setOnAction(e -> { 
            window.close();
        });
        
        TextField tfFilePath = new TextField();
        tfFilePath.setMinWidth(300);
                
        Button snapShotBtn = new Button("Take a Snapshot");
        snapShotBtn.setOnAction((ActionEvent t) -> {
            try {
                SnapshotParameters parameters = new SnapshotParameters();
                WritableImage wi = new WritableImage(650, 650);
                WritableImage snapshot = bp.snapshot(new SnapshotParameters(), wi);

                File output = new File("snapshot" + new Date().getTime() + ".png");
                tfFilePath.setText("path: "+ output);  //not visible
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
            } catch (IOException ex) {
                Logger.getLogger(Tournament_V2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        HBox hb = new HBox();
        hb.setPadding( new Insets(10,10,10,10));
        hb.setSpacing(50);
        hb.getChildren().addAll(closeBtn, snapShotBtn, tfFilePath);
        
        bp = new BorderPane();
        
        bp.setTop(hbTop);
        bp.setCenter(vbox);
        bp.setBottom(hb);
        
        Scene scene = new Scene(bp);
        scene.getStylesheets().add("/tournament_v2/styles.css");
        window.setScene(scene);
        window.showAndWait();
        
    }

    private void neuBerechnung() {
        data.clear();
        for (int i = 0; i < list.size(); i++){
            ResultRow rr = list.get(i);
            if (rr.getPlayerRank() == 0){
                rr.setPlayerRank(list.size());
                data.add(rr);
            } else {
                data.add(rr);
            }
        }
    }
    
    private String createHeadLineText(){
        double points = 0;
        String headline = "";
        int numPlayers = list.size();
        
        for (int i = 0; i < numPlayers; i ++){
            int round;
            double rr1 = list.get(i).getR1();        //Win
            double rr2 = list.get(i).getR2()*0.5;    //Remis
            points += rr1 += rr2;
            round = (int) (points*2) / numPlayers;
            int next = 0;
            if (points*2 % numPlayers != 0){
                next = 1;
            }
            if (round == numPlayers-1){
                headline = "final Result: round  (" + round + ")";
            } else {
                headline = "round: " + (round+next);
            }
        }
        
        return headline;
        
    }    
}
