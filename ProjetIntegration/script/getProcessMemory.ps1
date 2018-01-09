#Get-Counter -ComputerName localhost '\Process(*)\% Processor Time' | Select-Object -ExpandProperty countersamples | Select-Object -Property instancename, cookedvalue| Sort-Object -Property cookedvalue -Descending| Select-Object -First 20 | Export-Csv log/get-process_usage_cpu.csv
#Get-Process | Where-Object {$_.cpu -gt 50} | Export-Csv log/get-process_usage_cpu.csv
Get-Process | Export-Csv -Delimiter ';' -Path "log/processMemory.csv"
#Get-process | Group-Object -Property ProcessName | Format-Table Name, @{n='Mem (KB)';e={'{0:N0}' -f (($_.Group|Measure-Object WorkingSet -Sum).Sum / 1KB)};a='right'} -AutoSize |  Export-Csv C:\Users\natha\Documents\get-process_usage_ram.csv


