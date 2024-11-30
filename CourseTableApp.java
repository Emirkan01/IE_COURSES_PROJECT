package com.example.ie_courses;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CourseTableApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Industrial Engineering Courses");

        TableView<Course> tableView = new TableView<>();

        // Set column resize policy to CONSTRAINED_RESIZE_POLICY
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Program Curriculum Columns

        TableColumn<Course, String> courseCodeColumn = new TableColumn<>("Course Code");
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCodeColumn.setPrefWidth(120);

        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseNameColumn.setPrefWidth(300); // Increase the preferred width to ensure longer course names fit
        courseNameColumn.setCellFactory(tc -> {
            TableCell<Course, String> cell = new TableCell<>() {
                private final Text text = new Text();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        text.setText(item);
                        text.wrappingWidthProperty().bind(tc.widthProperty().subtract(10)); // Set wrapping width to column width
                        setGraphic(text);
                    }
                }
            };
            cell.setPrefHeight(60); // Adjust height as needed
            return cell;
        });


        TableColumn<Course, Integer> creditsColumn = new TableColumn<>("Credits");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        creditsColumn.setPrefWidth(80);

        TableColumn<Course, Double> ectsColumn = new TableColumn<>("ECTS");
        ectsColumn.setCellValueFactory(new PropertyValueFactory<>("ects"));
        ectsColumn.setPrefWidth(80);

        TableColumn<Course, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setPrefWidth(150);

        // Course History Columns
        TableColumn<Course, String> statusColumn = new TableColumn<>("Course Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setPrefWidth(250); // Increase the preferred width to ensure longer statuses fit
        statusColumn.setCellFactory(tc -> {
            TableCell<Course, String> cell = new TableCell<>() {
                private final Text text = new Text();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        text.setText(item);
                        text.wrappingWidthProperty().bind(tc.widthProperty().subtract(10)); // Set wrapping width to column width
                        setGraphic(text);
                    }
                }
            };
            cell.setPrefHeight(60); // Adjust height as needed
            return cell;
        });

        TableColumn<Course, String> gradeColumn = new TableColumn<>("Letter Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        gradeColumn.setPrefWidth(100);

        TableColumn<Course, String> termTakenColumn = new TableColumn<>("Term Taken");
        termTakenColumn.setCellValueFactory(new PropertyValueFactory<>("termTaken"));
        termTakenColumn.setPrefWidth(150);

        TableColumn<Course, String> courseUrlColumn = new TableColumn<>("Course URL");
        courseUrlColumn.setCellValueFactory(new PropertyValueFactory<>("courseUrl"));
        courseUrlColumn.setCellFactory(createHyperlinkCell());
        courseUrlColumn.setPrefWidth(200);

        // Add columns to the table (excluding gradContributionColumn)
        tableView.getColumns().addAll(courseCodeColumn, courseNameColumn, creditsColumn, ectsColumn,
                categoryColumn, statusColumn, gradeColumn, termTakenColumn, courseUrlColumn);

        // Set Data
        tableView.setItems(getCourseList());

        // Apply Row Colors Based on Status
        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (course != null) {
                    switch (course.getStatus()) {
                        case "Not Registered": setStyle("-fx-background-color: #add8e6;"); break; // Open Blue
                        case "Registered + Not Finalized": setStyle("-fx-background-color: yellow;"); break;
                        case "Registered + Finalized + Successful": setStyle("-fx-background-color: #90ee90;"); break; // Lighter Green
                        case "Exempt": setStyle("-fx-background-color: #dda0dd;"); break; // Lighter Purple
                        case "Registered + Finalized + Failed": setStyle("-fx-background-color: red;"); break;
                        case "Additional Curriculum + Registered + Not Finalized": setStyle("-fx-background-color: gray;"); break;
                        case "Additional Curriculum + Registered + Finalized + Successful": setStyle("-fx-background-color: brown;"); break;
                        case "Additional Curriculum + Registered + Finalized + Failed": setStyle("-fx-background-color: black;"); break;
                        default: setStyle(""); break;
                    }
                } else {
                    setStyle("");
                }
            }
        });

        // Adjust height based on the number of rows
        tableView.setFixedCellSize(35); // Adjust the height of each row if needed
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));
        tableView.minHeightProperty().bind(tableView.prefHeightProperty());
        tableView.maxHeightProperty().bind(tableView.prefHeightProperty());

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Callback<TableColumn<Course, String>, TableCell<Course, String>> createHyperlinkCell() {
        return column -> new TableCell<>() {
            private final Hyperlink hyperlink = new Hyperlink();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    hyperlink.setText("Course Link");
                    hyperlink.setOnAction(event -> getHostServices().showDocument(item));
                    setGraphic(hyperlink);
                }
            }
        };
    }

    private ObservableList<Course> getCourseList() {
        return FXCollections.observableArrayList(
                new Course("1", "IE201", "Probability and Statistics I", 3, 7.0, "Mandatory",
                        "Exempt", "CB", "Exempt Term", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9454/detail"),
                new Course("1", "IE202", "Probability and Statistics II", 3, 6.5, "Mandatory",
                        "Registered + Finalized + Successful", "CC", "2022-2023 Spring", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9460/detail"),
                new Course("1", "IE222", "Operations Research I", 4, 7.5, "Mandatory",
                        "Registered + Finalized + Successful", "DC", "2022-2023 Spring", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9462/detail"),
                new Course("1", "IE222", "Operations Research I", 4, 7.5, "Mandatory",
                        "Registered + Finalized + Successful", "CC", "2023-2024 Spring", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9462/detail"),
                new Course("1", "IE304", "Information Management", 3, 6.0, "Mandatory",
                        "Not Registered", "", "N/A", false, "https://www.atilim.edu.tr/en/ects/site-courses/208/9474/detail"),
                new Course("1", "IE307", "Production Planning and Control", 3, 5.0, "Mandatory",
                        "Registered + Finalized + Successful", "BA", "2023-2024 Fall", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9466/detail"),
                new Course("1", "IE323", "Operations Research II", 4, 8.0, "Mandatory",
                        "Registered + Not Finalized", "", "2024-2025 Fall", false, "https://www.atilim.edu.tr/en/ects/site-courses/208/9468/detail"),
                new Course("1", "IE326", "Quality Control and Assurance", 3, 7.5, "Mandatory",
                        "Registered + Finalized + Successful", "DC", "2023-2024 Spring", true, "https://www.atilim.edu.tr/en/ects/site-courses/208/9477/detail"),
                new Course("1", "MATH275", "Linear Algebra", 4, 6.0, "Mandatory",
                        "Exempt", "BA", "2023-2024 Fall", true, "https://www.atilim.edu.tr/en/ects/site-courses/12/640/detail")
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
