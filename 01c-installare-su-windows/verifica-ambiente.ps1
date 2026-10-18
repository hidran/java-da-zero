# Java environment check for the course "Java da zero" (Windows, PowerShell).
# Run it in a NEW PowerShell window, after installing Eclipse Temurin 25:
#   powershell -ExecutionPolicy Bypass -File .\verifica-ambiente.ps1
# It only reads: it changes nothing on the computer.

Write-Host '--- java -version (expected: openjdk version "25...)'
java -version

Write-Host '--- javac -version (expected: javac 25...)'
javac -version

Write-Host '--- where.exe java (the first path is the java that runs)'
where.exe java

Write-Host "--- JAVA_HOME = $env:JAVA_HOME"
if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME 'bin\javac.exe'))) {
    Write-Host 'JAVA_HOME points to a JDK: ok'
} else {
    Write-Host 'JAVA_HOME is missing or does not point to a JDK: set it in the Temurin installer'
}
