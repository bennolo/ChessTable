/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournament_v2;

import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.Comparator;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author benno
 */
public class Tournament_V2 extends Application {
    
    private final boolean DEBUG = true;
    private TextField tf_club;
    private final List<String> textList = FXCollections.observableArrayList();
    private final static double[][] resultList = new double[21][21];
    private final static double[] pointsList = new double[21];
    private final static Label[] labelPointsList = new Label[21];
    private final static double[] sbList = new double[21];
    private final static Label[] labelSbList = new Label[21];
    private final static Label[] labelRank = new Label[21];   // for the labels Rank
    private final static LabelRank[] labelRankList = new LabelRank[21];
    public static List<ResultRow> rr_list = FXCollections.observableArrayList();
    //
    protected static final int evaluationList[][] = new int[40][21];   //max 19 points = 38 - max 20 Players
    protected static LookUpTable look;
    //
    private Button sbButton;
    private DatePicker datepicker;
    private TextField tf;
    private int[] winRemisLoss;
    static Button showTableBtn;
    public static ChoiceBox<Integer> roundsToPlay;
    static Button[][] Btn;   //two dimensional button field
    private Button lotteryBtn;
    private Label statusLabel;
    private Button lotteryRecBtn;
   

    @Override
    public void start(Stage primaryStage) {
        
        showTableBtn = new Button();
        showTableBtn.setText("show table");
        fadeOut(showTableBtn);
        showTableBtn.setOnAction((ActionEvent event) -> {
            setResultRow();
            
            LocalDate date = datepicker.getValue();
            String tourn;
            try{
                tourn = tf.getText();
            }catch (Exception e){
                tourn = "none";
            }
            String club;
            try{
                club = tf_club.getText();
            }catch (Exception e){
                club = " ";
            }
            PrintSave ps = new PrintSave();
            ps.printSaveBox(rr_list, date, tourn, club);
        });


//        Button showTableBtnResultList = new Button();
//        showTableBtnResultList.setText("ResultList");
//        showTableBtnResultList.setOnAction((ActionEvent event) -> {
//            System.out.print("ResultList: ");
//            for (int i = 1; i <=20; i++){
//            printResultList();
//            }
//        });


    //initalize textList
    for (int i = 0; i <=20; i++){
        textList.add("");
    // init resultList
        for (int j = 0; j <= 20; j++ ){
            resultList[i][j] = 0.0;
        }
    // init pointsList
        pointsList[i] = 0.0;
    // init LabelPointsList
        labelPointsList[i] = new Label("");
    // init sbList
        sbList[i] = 0.0;
    // init LabelSbList (Sonneborn-Berger)
        labelSbList[i] = new Label("");
    // init labelRank the labels
        labelRank[i] = new Label("");
    // init labelRankList
        labelRankList[i] = new LabelRank(i, 0.0, 0);
    }

        //new Buttons
        Btn = new Button[21][21];
        for (int i = 1; i <= 20; i++){
            for (int j = 1; j <=20; j++){
                Btn[i][j] = new Button(i + "|"+ j);
                Btn[i][j].setId(i + ":" + j);
                Btn[i][j].setMaxHeight(20);
                Btn[i][j].setMinWidth(20);
                Btn[i][j].setTextFill(Color.WHITE);
                Btn[i][j].setOnAction((ActionEvent event) -> {
                    String srcTxt = event.getSource().toString();
                    Button bt = (Button) event.getSource();
                    String id = bt.getId();
                    //System.out.println("ID:"+id);
                    int colon_index = id.indexOf(":");
                    int one = Integer.parseInt(id.substring(0, colon_index));
                    int two = Integer.parseInt(id.substring(colon_index+1));
                    String player1 = textList.get(one);
                    String player2 = textList.get(two);
                    Bounds boundsInScreen = Btn[one][two].localToScreen(Btn[one][two].getBoundsInLocal());
                    double boundX = boundsInScreen.getMinX();
                    double boundY = boundsInScreen.getMinY();
                    String result = ResultInput.resultBox( player1, player2, one, two , boundX, boundY);
                    //System.out.println("result =: " + result);
                    Image imageOne = new Image(getClass().getResourceAsStream("one.png"));
                    Image imageZero = new Image(getClass().getResourceAsStream("zero.png"));
                    Image imageHalf = new Image(getClass().getResourceAsStream("image3092.png"));
                    switch (result) {

                        case "win": Btn[one][two].setGraphic(new ImageView(imageOne));
                            resultList[one][two] = 1;
                            pointsList[one] = getSumPointsRows(one);
                            Btn[two][one].setGraphic(new ImageView(imageZero));
                            resultList[two][one] = 0;
                            pointsList[two] = getSumPointsRows(two);
                            clearUpLabelPointsList(two, one);
                            //printResultList();
                            break;
                        // remis
                        case "remis": Btn[one][two].setGraphic(new ImageView(imageHalf));
                            resultList[one][two] = 0.5;
                            pointsList[one] = getSumPointsRows(one);
                            Btn[two][one].setGraphic(new ImageView(imageHalf));
                            resultList[two][one] = 0.5;
                            pointsList[two] = getSumPointsRows(two);
                            clearUpLabelPointsList(two, one);
                            //printResultList();
                            break;
                        // loss
                        case "loss" : Btn[one][two].setGraphic(new ImageView(imageZero));
                            resultList[one][two] = 0;
                            pointsList[one] = getSumPointsRows(one);
                            clearUpLabelPointsList(two, one);
                            resultList[two][one] = 1;
                            pointsList[two] = getSumPointsRows(two);
                            clearUpLabelPointsList(two, one);
                            Btn[two][one].setGraphic(new ImageView(imageOne));
                            clearUpLabelPointsList(two, one);
                            getSumPointsRows(one);
                            getSumPointsRows(two);
                            break;
                    }





                    //System.out.println("ID: " + Btn[one][two].getId());

//                        SVGPath svg = new SVGPath();
//                        svg.setContent("m 399.5,157.625 -7.96875,1.6875 -0.5625,1.40625 6.625,-1.34375 -4.5625,11.5625 -5.34375,-0.375 -0.46875,1.15625 17.875,1.1875 0.46875,-1.15625 -5.34375,-0.34375 L 405.5,158 l -6,-0.375 z m -18.75,17.75 -0.0312,1.53125 30,1.96875 0.0312,-1.53125 -30,-1.96875 z m 15.28125,7.125 c -0.82841,-0.0141 -1.66166,0.005 -2.5,0.0312 -1.67663,0.0459 -3.38303,0.16317 -5.125,0.375 l -1.25,3.03125 c 1.88417,-0.38579 3.5642,-0.63836 5.0625,-0.78125 1.49834,-0.14941 2.87881,-0.17439 4.09375,-0.0937 1.29164,0.0857 2.28626,0.2829 3,0.625 0.7137,0.34213 1.06694,0.77574 1.0625,1.3125 -0.004,0.50406 -0.35469,1.00992 -1.0625,1.5 -0.69507,0.49094 -1.72814,0.97087 -3.0625,1.40625 l -12.21875,3.875 -1.09375,2.71875 20.8125,1.40625 1.0625,-2.71875 -12.4375,-0.84375 7.15625,-2.25 c 3.48959,-1.10448 5.7516,-2.03238 6.78125,-2.75 1.02959,-0.71761 1.55453,-1.54426 1.5625,-2.5 0.009,-1.11282 -0.83003,-2.05975 -2.5,-2.8125 -1.66995,-0.75926 -3.95919,-1.21268 -6.875,-1.40625 -0.81848,-0.0543 -1.64034,-0.11094 -2.46875,-0.125 z");
//                        Btn[one][two].setGraphic(svg);
//                        svg.scaleXProperty().bind(Btn[one][two].widthProperty().divide(10));
//                        svg.scaleYProperty().bind(Btn[one][two].heightProperty().divide(20));


//String source = event.getSource().toString();
//setPosition(Btn[one][two]);
//                    Btn[one][two].setText("1");
//                    Btn[one][two].setTextFill(Color.BLACK);
//                    Btn[two][one].setText(null);
//                    Btn[two][one].setText("0");
//                    Btn[two][one].setTextFill(Color.BLACK);
                });
            }

        }





        //"m 479.86188,249.57912 7.21017,0 -0.61103,3.14404 -12.04288,0 0.62214,-3.16626 7.05464,-5.32153 c 0.77026,-0.5925 1.35537,-1.19242 1.75533,-1.79976 0.40734,-0.60732 0.61102,-1.19613 0.61103,-1.76644 -10e-6,-0.60731 -0.20739,-1.07762 -0.62214,-1.41093 -0.41477,-0.33327 -0.99618,-0.49992 -1.74422,-0.49993 -0.70362,1e-5 -1.4887,0.14074 -2.35525,0.42217 -0.86656,0.27405 -1.84421,0.69992 -2.93295,1.27761 l 0.71102,-3.52177 c 1.00727,-0.3703 1.99603,-0.64434 2.96628,-0.82211 0.97024,-0.18515 1.92937,-0.27773 2.8774,-0.27774 1.68866,1e-5 3.01812,0.36663 3.98837,1.09985 0.97023,0.72585 1.45535,1.71831 1.45537,2.97739 -2e-5,1.08136 -0.29627,2.06641 -0.88877,2.95518 -0.59253,0.88878 -1.89607,2.08862 -3.91061,3.59953 l -4.1439,3.1107"
       // String source = event.getSource().toString();



        //Set black buttons
        Integer[][] intList = new Integer[21][21];
        int k = 1;
        int l = 1;
        while (k <= 20 || l <= 20){
            Btn[k][l].setStyle("-fx-background-color: black;");
            Btn[k][l].setTextFill(Color.BLACK);
            Btn[k][l].setDisable(true);
            k++;
            l++;
        }


        TextField[] textField = new TextField[21];
        for (int i=1; i<=20 ; i++) {
        textField[i] = new TextField("");
        textField[i].setPromptText("Player");
        textField[i].setId(""+i);
        textField[i].setOnAction(e -> {
            TextField source = (TextField) e.getSource();
            String id = source.getId();
            int int_id = Integer.parseInt(id);
            String playerText = textField[int_id].getText();
            textField[int_id].setStyle("-fx-background-color: palegreen");
            textList.set(int_id, playerText);
            if (!"".equals(textField[1].getText()) &&
                !"".equals(textField[2].getText()) &&
                !"".equals(textField[3].getText()) &&
                !"".equals(textField[4].getText())
                ){
                fadeIn(lotteryBtn);
                fadeIn(showTableBtn);
            }
            int textFieldCounter = 0;
            for (int j = 1; j <= 20; j++){
                if (!"".equals(textField[j].getText())){
                    textFieldCounter += 1;
                }
            }
            if (textFieldCounter > 4 && textFieldCounter % 2 == 1){
                statusLabel.setText("even number of players required");
            } 
            if (textFieldCounter < 4 ){
                statusLabel.setText("more players required");
            }
            if (textFieldCounter >= 4 && textFieldCounter % 2 == 0){
                statusLabel.setText("OK");
            }
            if (int_id < 20){
                textField[int_id+1].requestFocus();
            }
        });
        }

        GridPane grid = new GridPane();

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(18);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(2);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(2);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(2);

        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(2);

        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(2);

        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(2);

        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(2);

        ColumnConstraints col9 = new ColumnConstraints();
        col9.setPercentWidth(2);

        ColumnConstraints col10 = new ColumnConstraints();
        col10.setPercentWidth(2);

        ColumnConstraints col11 = new ColumnConstraints();
        col11.setPercentWidth(2);

        ColumnConstraints col12 = new ColumnConstraints();
        col12.setPercentWidth(2);

        ColumnConstraints col13 = new ColumnConstraints();
        col13.setPercentWidth(2);

        ColumnConstraints col14 = new ColumnConstraints();
        col14.setPercentWidth(2);

        ColumnConstraints col15 = new ColumnConstraints();
        col15.setPercentWidth(2);

        ColumnConstraints col16 = new ColumnConstraints();
        col16.setPercentWidth(2);

        ColumnConstraints col17 = new ColumnConstraints();
        col17.setPercentWidth(2);

        ColumnConstraints col18 = new ColumnConstraints();
        col18.setPercentWidth(2);

        ColumnConstraints col19 = new ColumnConstraints();
        col19.setPercentWidth(2);

        ColumnConstraints col20 = new ColumnConstraints();
        col20.setPercentWidth(2);

        ColumnConstraints col21 = new ColumnConstraints();
        col21.setPercentWidth(2);

        ColumnConstraints col22 = new ColumnConstraints();
        col22.setPercentWidth(6);

        ColumnConstraints col23 = new ColumnConstraints();
        col23.setPercentWidth(6);

        ColumnConstraints col24 = new ColumnConstraints();
        col23.setPercentWidth(6);

        ColumnConstraints col25 = new ColumnConstraints();
        col23.setPercentWidth(6);
   
        grid.getColumnConstraints().addAll(col0,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17,col18,col19,col20,col21,col22,col23,col24,col25);
        
        Tooltip tooltip = new Tooltip();
        tooltip.setText(
            "Sonneborn-Berger Values");
        
        sbButton = new Button("   SB   ");
        sbButton.setTooltip(tooltip);
        sbButton.setOnAction( e -> {
            fadeIn(lotteryBtn);   // fade the button in
            
            sonnebornBerger();
            for (int i = 1; i <= 20; i++){
                double sb = sbList[i];
                double rp = getSumPointsRows(i);
                labelRankList[i].setSb_value(sb);
                int rank = getRankPoints(rp, sb);
                labelRankList[i].setRank(rank);
            }prepareList();
            
            
        });


        grid.addRow(0, new Label("  Player"), new Button(" "), new Label(" 1"), new Label(" 2"), new Label(" 3"), new Label(" 4"), new Label(" 5"), new Label(" 6"), new Label(" 7"), new Label(" 8"), new Label(" 9"), new Label("10"), new Label("11"), new Label("12"), new Label("13"), new Label("14"), new Label("15"), new Label("16"), new Label("17"), new Label("18"), new Label("19"), new Label("20"), new Label("  Points"), sbButton, new Label(" Rank"));
        grid.addRow(1, textField[1], new Label("  1"), Btn[1][1], Btn[1][2], Btn[1][3], Btn[1][4], Btn[1][5], Btn[1][6], Btn[1][7], Btn[1][8], Btn[1][9], Btn[1][10], Btn[1][11], Btn[1][12], Btn[1][13], Btn[1][14], Btn[1][15], Btn[1][16], Btn[1][17], Btn[1][18], Btn[1][19], Btn[1][20], labelPointsList[1], labelSbList[1], labelRank[1]);
        grid.addRow(2, textField[2], new Label("  2"), Btn[2][1], Btn[2][2], Btn[2][3], Btn[2][4], Btn[2][5], Btn[2][6], Btn[2][7], Btn[2][8], Btn[2][9], Btn[2][10], Btn[2][11], Btn[2][12], Btn[2][13], Btn[2][14], Btn[2][15], Btn[2][16], Btn[2][17], Btn[2][18], Btn[2][19], Btn[2][20], labelPointsList[2], labelSbList[2], labelRank[2]);
        grid.addRow(3, textField[3], new Label("  3"), Btn[3][1], Btn[3][2], Btn[3][3], Btn[3][4], Btn[3][5], Btn[3][6], Btn[3][7], Btn[3][8], Btn[3][9], Btn[3][10], Btn[3][11], Btn[3][12], Btn[3][13], Btn[3][14], Btn[3][15], Btn[3][16], Btn[3][17], Btn[3][18], Btn[3][19], Btn[3][20], labelPointsList[3], labelSbList[3], labelRank[3]);
        grid.addRow(4, textField[4], new Label("  4"), Btn[4][1], Btn[4][2], Btn[4][3], Btn[4][4], Btn[4][5], Btn[4][6], Btn[4][7], Btn[4][8], Btn[4][9], Btn[4][10], Btn[4][11], Btn[4][12], Btn[4][13], Btn[4][14], Btn[4][15], Btn[4][16], Btn[4][17], Btn[4][18], Btn[4][19], Btn[4][20], labelPointsList[4], labelSbList[4], labelRank[4]);
        grid.addRow(5, textField[5], new Label("  5"), Btn[5][1], Btn[5][2], Btn[5][3], Btn[5][4], Btn[5][5], Btn[5][6], Btn[5][7], Btn[5][8], Btn[5][9], Btn[5][10], Btn[5][11], Btn[5][12], Btn[5][13], Btn[5][14], Btn[5][15], Btn[5][16], Btn[5][17], Btn[5][18], Btn[5][19], Btn[5][20], labelPointsList[5], labelSbList[5], labelRank[5]);
        grid.addRow(6, textField[6], new Label("  6"), Btn[6][1], Btn[6][2], Btn[6][3], Btn[6][4], Btn[6][5], Btn[6][6], Btn[6][7], Btn[6][8], Btn[6][9], Btn[6][10], Btn[6][11], Btn[6][12], Btn[6][13], Btn[6][14], Btn[6][15], Btn[6][16], Btn[6][17], Btn[6][18], Btn[6][19], Btn[6][20], labelPointsList[6], labelSbList[6], labelRank[6]);
        grid.addRow(7, textField[7], new Label("  7"), Btn[7][1], Btn[7][2], Btn[7][3], Btn[7][4], Btn[7][5], Btn[7][6], Btn[7][7], Btn[7][8], Btn[7][9], Btn[7][10], Btn[7][11], Btn[7][12], Btn[7][13], Btn[7][14], Btn[7][15], Btn[7][16], Btn[7][17], Btn[7][18], Btn[7][19], Btn[7][20], labelPointsList[7], labelSbList[7], labelRank[7]);
        grid.addRow(8, textField[8], new Label("  8"), Btn[8][1], Btn[8][2], Btn[8][3], Btn[8][4], Btn[8][5], Btn[8][6], Btn[8][7], Btn[8][8], Btn[8][9], Btn[8][10], Btn[8][11], Btn[8][12], Btn[8][13], Btn[8][14], Btn[8][15], Btn[8][16], Btn[8][17], Btn[8][18], Btn[8][19], Btn[8][20], labelPointsList[8], labelSbList[8], labelRank[8]);
        grid.addRow(9, textField[9], new Label("  9"), Btn[9][1], Btn[9][2], Btn[9][3], Btn[9][4], Btn[9][5], Btn[9][6], Btn[9][7], Btn[9][8], Btn[9][9], Btn[9][10], Btn[9][11], Btn[9][12], Btn[9][13], Btn[9][14], Btn[9][15], Btn[9][16], Btn[9][17], Btn[9][18], Btn[9][19], Btn[9][20], labelPointsList[9], labelSbList[9], labelRank[9]);
        grid.addRow(10, textField[10], new Label(" 10"), Btn[10][1], Btn[10][2], Btn[10][3], Btn[10][4], Btn[10][5], Btn[10][6], Btn[10][7], Btn[10][8], Btn[10][9], Btn[10][10], Btn[10][11], Btn[10][12], Btn[10][13], Btn[10][14], Btn[10][15], Btn[10][16], Btn[10][17], Btn[10][18], Btn[10][19], Btn[10][20], labelPointsList[10], labelSbList[10], labelRank[10]);
        grid.addRow(11, textField[11], new Label(" 11"), Btn[11][1], Btn[11][2], Btn[11][3], Btn[11][4], Btn[11][5], Btn[11][6], Btn[11][7], Btn[11][8], Btn[11][9], Btn[11][10], Btn[11][11], Btn[11][12], Btn[11][13], Btn[11][14], Btn[11][15], Btn[11][16], Btn[11][17], Btn[11][18], Btn[11][19], Btn[11][20], labelPointsList[11], labelSbList[11], labelRank[11]);
        grid.addRow(12, textField[12], new Label(" 12"), Btn[12][1], Btn[12][2], Btn[12][3], Btn[12][4], Btn[12][5], Btn[12][6], Btn[12][7], Btn[12][8], Btn[12][9], Btn[12][10], Btn[12][11], Btn[12][12], Btn[12][13], Btn[12][14], Btn[12][15], Btn[12][16], Btn[12][17], Btn[12][18], Btn[12][19], Btn[12][20], labelPointsList[12], labelSbList[12], labelRank[12]);
        grid.addRow(13, textField[13], new Label(" 13"), Btn[13][1], Btn[13][2], Btn[13][3], Btn[13][4], Btn[13][5], Btn[13][6], Btn[13][7], Btn[13][8], Btn[13][9], Btn[13][10], Btn[13][11], Btn[13][12], Btn[13][13], Btn[13][14], Btn[13][15], Btn[13][16], Btn[13][17], Btn[13][18], Btn[13][19], Btn[13][20], labelPointsList[13], labelSbList[13], labelRank[13]);
        grid.addRow(14, textField[14], new Label(" 14"), Btn[14][1], Btn[14][2], Btn[14][3], Btn[14][4], Btn[14][5], Btn[14][6], Btn[14][7], Btn[14][8], Btn[14][9], Btn[14][10], Btn[14][11], Btn[14][12], Btn[14][13], Btn[14][14], Btn[14][15], Btn[14][16], Btn[14][17], Btn[14][18], Btn[14][19], Btn[14][20], labelPointsList[14], labelSbList[14], labelRank[14]);
        grid.addRow(15, textField[15], new Label(" 15"), Btn[15][1], Btn[15][2], Btn[15][3], Btn[15][4], Btn[15][5], Btn[15][6], Btn[15][7], Btn[15][8], Btn[15][9], Btn[15][10], Btn[15][11], Btn[15][12], Btn[15][13], Btn[15][14], Btn[15][15], Btn[15][16], Btn[15][17], Btn[15][18], Btn[15][19], Btn[15][20], labelPointsList[15], labelSbList[15], labelRank[15]);
        grid.addRow(16, textField[16], new Label(" 16"), Btn[16][1], Btn[16][2], Btn[16][3], Btn[16][4], Btn[16][5], Btn[16][6], Btn[16][7], Btn[16][8], Btn[16][9], Btn[16][10], Btn[16][11], Btn[16][12], Btn[16][13], Btn[16][14], Btn[16][15], Btn[16][16], Btn[16][17], Btn[16][18], Btn[16][19], Btn[16][20], labelPointsList[16], labelSbList[16], labelRank[16]);
        grid.addRow(17, textField[17], new Label(" 17"), Btn[17][1], Btn[17][2], Btn[17][3], Btn[17][4], Btn[17][5], Btn[17][6], Btn[17][7], Btn[17][8], Btn[17][9], Btn[17][10], Btn[17][11], Btn[17][12], Btn[17][13], Btn[17][14], Btn[17][15], Btn[17][16], Btn[17][17], Btn[17][18], Btn[17][19], Btn[17][20], labelPointsList[17], labelSbList[17], labelRank[17]);
        grid.addRow(18, textField[18], new Label(" 18"), Btn[18][1], Btn[18][2], Btn[18][3], Btn[18][4], Btn[18][5], Btn[18][6], Btn[18][7], Btn[18][8], Btn[18][9], Btn[18][10], Btn[18][11], Btn[18][12], Btn[18][13], Btn[18][14], Btn[18][15], Btn[18][16], Btn[18][17], Btn[18][18], Btn[18][19], Btn[18][20], labelPointsList[18], labelSbList[18], labelRank[18]);
        grid.addRow(19, textField[19], new Label(" 19"), Btn[19][1], Btn[19][2], Btn[19][3], Btn[19][4], Btn[19][5], Btn[19][6], Btn[19][7], Btn[19][8], Btn[19][9], Btn[19][10], Btn[19][11], Btn[19][12], Btn[19][13], Btn[19][14], Btn[19][15], Btn[19][16], Btn[19][17], Btn[19][18], Btn[19][19], Btn[19][20], labelPointsList[19], labelSbList[19], labelRank[19]);
        grid.addRow(20, textField[20], new Label(" 20"), Btn[20][1], Btn[20][2], Btn[20][3], Btn[20][4], Btn[20][5], Btn[20][6], Btn[20][7], Btn[20][8], Btn[20][9], Btn[20][10], Btn[20][11], Btn[20][12], Btn[20][13], Btn[20][14], Btn[20][15], Btn[20][16], Btn[20][17], Btn[20][18], Btn[20][19], Btn[20][20], labelPointsList[20], labelSbList[20], labelRank[20]);
        
        grid.getChildren().stream().filter((n) -> (n instanceof Label)).map((n) -> (Label) n).map((label) -> {
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            return label;
        }).forEach((label) -> {
            label.setStyle("-fx-background-color: lightgrey; -fx-alignment: center;");
        });
         
        
        grid.getStyleClass().add("grid");
        grid.setStyle("-fx-background-color: white; -fx-padding: 1; -fx-hgap: 1; -fx-vgap: 1;");
        grid.setSnapToPixel(false);
        datepicker = new DatePicker();
        datepicker.setValue(LocalDate.now());
        tf = new TextField();
        tf.setMaxSize(250, 20);
        tf.setPromptText("Tournament");
        tf.setStyle("-fx-text-inner-color: darkgrey; -fx-font-size: 14; -fx-font-weight: bold");
        tf.setOnAction(e -> {
            tf_club.requestFocus();
        });
        tf_club = new TextField();
        tf_club.setMaxSize(250, 20);
        tf_club.setPromptText("Chess Club");
        tf_club.setStyle("-fx-text-inner-color: darkgrey; -fx-font-size: 14; -fx-font-weight: bold");
        tf_club.setOnAction(e -> {
            datepicker.requestFocus();
        });
        
        roundsToPlay = new ChoiceBox<>();
        roundsToPlay.getItems().addAll(4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19);
        roundsToPlay.setValue(10);
        
        statusLabel = new Label();
        statusLabel.setText("Status: insert min. 4 Players");
        statusLabel.setId("statusLabel");
        
        GridPane topGrid = new GridPane();
        ColumnConstraints top0 = new ColumnConstraints();
        top0.setPercentWidth(25);
        ColumnConstraints top1 = new ColumnConstraints();
        top1.setPercentWidth(25);
        ColumnConstraints top2 = new ColumnConstraints();
        top2.setPercentWidth(34);
        ColumnConstraints top3 = new ColumnConstraints();
        top3.setPercentWidth(16);
        RowConstraints topRow1 = new RowConstraints();
        topRow1.setMinHeight(15);
        RowConstraints topRow2 = new RowConstraints();
        topRow2.setMinHeight(40);
        
        
        topGrid.getColumnConstraints().addAll(top0, top1, top2, top3);
        topGrid.getRowConstraints().addAll(topRow1, topRow2);
        topGrid.addRow(0, new Label(""),new Label(),new Label("Date:"));
        topGrid.addRow(1, tf, tf_club, datepicker);
        topGrid.addRow(2, new Label("number of rounds: "), roundsToPlay, statusLabel);
         
        lotteryBtn = new Button();
//        System.out.println("rr_list.length: " + rr_list.size());
//        if (rr_list.size() > 0){
//            String name = rr_list.get(1).getPlayerName();
//            System.out.println("name= " + name);
//        }
        lotteryBtn.setText("Lottery");
        fadeOut(lotteryBtn);
        lotteryBtn.setOnAction(e -> {
            setResultRow();
            Lottery lottery = new Lottery();
            look = new LookUpTable(rr_list.size());
            lottery.initButtonList();
            lottery.initLottery();
            lottery.SetPlayerToEvaluationList();
            
        });
        
        lotteryRecBtn = new Button();
        lotteryRecBtn.setText("next Round");
        lotteryRecBtn.setOnAction(e -> {
            setResultRow();
            LotteryRecursive lr = new LotteryRecursive();
            lr.initLotteryRecursive();
            lr.initButtonList();
            lr.SetPlayerToEvaluationList();
        });
        
        HBox hbBottom = new HBox();
        hbBottom.setPadding(new Insets(10,10,10,10));
        hbBottom.setSpacing(40);
        hbBottom.getChildren().addAll(showTableBtn, lotteryBtn, lotteryRecBtn);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        root.setTop(topGrid);
        root.setCenter(grid);
        root.setBottom(hbBottom);


        Scene scene = new Scene(root, 860, 700);
        scene.getStylesheets().add("/tournament_v2/styles.css");
        primaryStage.setTitle("Chess Tournament");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void printResultList(){
        for (int i = 1; i <= 20; i++){
            for (int j = 1; j <= 20; j++){
            System.out.print(resultList[i][j] + " ");
            if(j % 20 == 0){
                System.out.print("\n");}
            }
        }
    }

    //Set Points to Labels in Col. Points
    public void getSumPoints(){
        double sum = 0.0;
        for (int i = 1; i <= 20; i++){
            for (int j = 1; j <=20; j++){
                sum = sum + resultList[i][j];
                if (sum > 0.0){
                    String strsum = Double.toString(sum);
                    labelPointsList[j].setText(strsum);
                }
                if (sum == 0.0){
                    labelPointsList[j].setText("");
                }
            }
        }
        // change the other value
        sum = 0.0;
        for (int i = 1; i <= 20; i++){
            for (int j = 1; j <=20; j++){
                sum = sum + resultList[i][j];
                if (sum > 0.0){
                    String strsum = Double.toString(sum);
                    labelPointsList[i].setText(strsum);
                }
                if (sum == 0.0){
                    labelPointsList[i].setText("");
                }
            }
        }
    }

    public double getSumPointsRows(int row){
        double sum = 0.0;
            for (int j = 1; j <=20; j++){
                sum = sum + resultList[row][j];
                if (sum != 0.0){
                    String strsum = Double.toString(sum);
                    if (sum > 9.5){
                    labelPointsList[row].setText("   " +strsum);
                    } else {
                        labelPointsList[row].setText("     " +strsum);
                    }
                }
            }
        return sum;
    }
    
    public int[] getWinRemisLoss(int row){
        int win = 0;
        int remis = 0;
        int loss = 0;
        int[] result = new int[3];
        
        for (int i = 1; i <=20; i ++){
            if (resultList[row][i] == 1.0){
                win += 1;
            }
            if (resultList[row][i] == 0.5){
                remis += 1;
            }
            if (resultList[row][i] == 0.0){
                loss += 1;
            }
            
        }
        result[0] = win;
        result[1] = remis;
        result[2] = loss-1;
        return result;
    }

    public void sonnebornBerger(){
        for (int i = 1; i <= 20; i++){
            sbList[i] = 0.0;
        }
        for (int player = 1; player <=20; player ++){
         double value;
            for ( int row = 1; row <=20; row ++){
                 double sb_pl = resultList[player][row];
                 double lblTxtValue = pointsList[row];
                 double sb_val = sb_pl * lblTxtValue;
                 sbList[player] += sb_val;
            }
            if (sbList[player] != 0){
                labelSbList[player].setText("   " + sbList[player]);
            }else {
                labelSbList[player].setText("");
            }
        }
    }

    public void clearUpLabelPointsList(int row, int col){
        labelPointsList[row].setText("    "+ pointsList[row]);
        labelPointsList[col].setText("    "+ pointsList[col]);
    }

    public int getRankPoints(double row_points, double sb_value){
        int rankPoints;
        int p = (int) (row_points * 10000);
        int sb = (int) (sb_value * 100);
        rankPoints = p + sb;
        return rankPoints;
    }

    //Comparator for int, by Number
    Comparator<? super LabelRank> comparatorLR_byNumber = (LabelRank o1, LabelRank o2) -> o1.getRank()- o2.getRank();
    
    Comparator<? super ResultRow> comparatorRR_byNumber = (ResultRow o1, ResultRow o2) -> o1.getPlayerRank()- o2.getPlayerRank();

    List<LabelRank> list;
    List<LabelRank> descendingList;
    
    private void prepareList(){
        list = new ArrayList<>();
        for (int j = 1; j <=20; j++ ){
        list.add(labelRankList[j]);
        // 1. clear all labelRank labels
        labelRank[j].setText("");
        }
        list.sort(comparatorLR_byNumber);
        descendingList = new ArrayList<>();
        for (int k = list.size()-1; k > 1; k--){
            descendingList.add(list.get(k));   
        }
            int rank_counter = 0;
            int set_counter = 0;
            int firstValue;
            int oldValue = 0;
        for (int x = 0; x < descendingList.size(); x++){
            LabelRank LR = descendingList.get(x);
            firstValue = LR.getRank();
            
            int label_index = LR.getLabelIndex();
            boolean setValue = false;
            
            if (x == 0){
                rank_counter += 1;
                labelRank[label_index].setText(" "+rank_counter);
                oldValue = firstValue;
                System.out.println("oldValue: "+oldValue +"| firstV: " + firstValue);
                
            }
            if (firstValue != 0 && oldValue != 0){
                if (x > 0 && firstValue == oldValue && setValue == false){
                    System.out.println("oldValue: "+oldValue +"| firstV: " + firstValue);
                    labelRank[label_index].setText(" "+ rank_counter);
                    setValue = true;
                    oldValue = firstValue;
                    set_counter +=1;
                }
                if (firstValue > oldValue && setValue == false){
                    rank_counter += 1 + set_counter;
                    set_counter = 0;
                    labelRank[label_index].setText(" "+ rank_counter);
                    oldValue = firstValue;
                } 
                  if (firstValue < oldValue && setValue == false){
                    rank_counter += 1 + set_counter;
                    set_counter = 0;
                    labelRank[label_index].setText(" "+ rank_counter);
                    oldValue = firstValue;
                } 
            }
        }
    }

    public int numberOfPlayers(){
        int nOfP = 20;
        for (int i = 1; i <= 20; i++){
            if (textList.get(i).isEmpty()){
                nOfP -= 1;
            }
        }
        return nOfP;
    }

    public void fadeIn(Button button){
        FadeTransition ft = new FadeTransition(Duration.millis(2000), button);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();   
    }

    public static void fadeOut(Button button){
        FadeTransition ft = new FadeTransition(Duration.millis(2000), button);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
    
    public void setResultRow(){
        rr_list.clear();
        
        for (int i = 1; i <=20; i++){
                ResultRow rr = new ResultRow();
                winRemisLoss = getWinRemisLoss(i);
                rr.setPlayerIndex(i);
                rr.setPlayerName(textList.get(i));
                try{
                rr.setPlayerRank((int) parseInt(labelRank[i].getText().trim()));
                } catch (Exception e){
                    rr.setPlayerRank(numberOfPlayers());
                }
                rr.setR1(winRemisLoss[0]);
                rr.setR2(winRemisLoss[1]);
                rr.setR3(numberOfPlayers()-1 - winRemisLoss[0] - winRemisLoss[1]);
                rr.setPointsRow(getSumPointsRows(i));
                rr.setSonnebornBerger(labelRankList[i].getSb_value());
                if (!"".equals(textList.get(i))){  
                    rr_list.add(rr);
                }
                rr_list.sort(comparatorRR_byNumber);
            }
    }

}
