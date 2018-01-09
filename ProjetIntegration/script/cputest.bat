
REM *********************************
REM *
REM * Set environnement variable
REM *
REM *********************************


set CLASSPATH=C:\test\lib

echo %CLASSPATH%

set JAVA_OPTIONS=-Xms1024m -Xmx1024m 

REM Launch App

java %JAVA_OPTIONS% -cp %CLASSPATH% -Djava.ext.dirs=%CLASSPATH% fr.cfi.views.ChartExemple %*
