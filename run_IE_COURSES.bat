@echo off
setlocal

rem Set JavaFX SDK path (assuming it's set via an environment variable named JAVA_FX)
rem Replace %JAVA_FX% with your JavaFX lib path if not using an environment variable
java --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml -jar "IE_COURSES.jar"

endlocal
