package com.str2txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button btnLoadFile;
    @FXML
    private Button btnProcessTextAreaContent;
    @FXML
    private Image btnImage;
    @FXML
    private TextArea txtOnlyText;
    @FXML
    private TextArea txtSRTContent;

    private File fileSRT;

    private ArrayList<String> transcripciones;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.setTitle("Cargar archivo");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Subtitles Files", "*.srt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        fileSRT = fc.showOpenDialog(btnLoadFile.getScene().getWindow());
        if (fileSRT != null) {
            printSubtitles(fileSRT);
            label.setText("Archivo cargado: " + fileSRT.getAbsolutePath());
        }
    }

    private void printSubtitles(File file) {
        BufferedReader reader = null;        
        try {            
            reader = new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));                                            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Línea: " + line);
                txtSRTContent.appendText(line + "\n");
            }
        } catch (IOException e) {
            label.setText(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                label.setText(ex.getMessage());
            }
        }
    }

    @FXML
    private void convertSTR2Text(ActionEvent event) {
        BufferedReader reader = null;

        if (fileSRT == null) {
            label.setText("Antes de procesar, cargue un archivo por favor!");
        } else {
            try {
                reader = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileSRT), "UTF-8"));

                processSubtitles(reader);
            } catch (IOException e) {
                label.setText("IOException: " + e.getMessage());
            } finally {
                try {
                    label.setText("Archivo '" + fileSRT.getName() + "' procesado correctamente!");
                    reader.close();
                } catch (Exception ex) {
                    label.setText("Exception: " + ex.getMessage());
                }
            }
        }
    }

    private void processSubtitles(BufferedReader reader) {
        transcripciones = new ArrayList<>();
        String str;
        String pattern = "([0-9][0-9]:[0-9][0-9]:[0-9][0-9],\\d+)? --> ([0-9][0-9]:[0-9][0-9]:[0-9][0-9],\\d+)?";
        boolean isNumber = false;
        boolean isTimeInterval = false;

        try {
            while ((str = reader.readLine()) != null) {
                isNumber = false;
                isTimeInterval = false;
                try {
                    int numberOfSubtitle = Integer.parseInt(str.trim()); // Garantizo que se convierta a un entero
                    if (numberOfSubtitle > 0) {
                        isNumber = true;
                        label.setText("Procesando un entero: " + str);
                    }
                } catch (NumberFormatException ex) {
                    label.setText(ex.getMessage());
                }
                // Si no es un número entero
                if (!isNumber) {
                    try {

                        // Create a Pattern object
                        Pattern r = Pattern.compile(pattern);
                        // Now create matcher object.
                        Matcher matcher = r.matcher(str.trim());
                        if (matcher.matches()) {
                            isTimeInterval = true;
                            System.out.println(str);
                            label.setText("Procesando un intervalo de tiempo: " + str);
                        }
                    } catch (NumberFormatException ex) {
                        label.setText(ex.getMessage());
                    }
                }

                // Si no es un número entero ni un intervalo de tiempo entonces se añade a la lista de texto
                if (!isNumber && !isTimeInterval) {
                    transcripciones.add(str);
                    txtOnlyText.appendText(str + "\n");
                }
            }
        } catch (IOException ex) {
            label.setText(ex.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
