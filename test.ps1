﻿Get-Process | Where-Object {$_.cpu -gt 50} > C:\Users\natha\Documents\get-process_usage_cpu.csv
C:\Users\natha\Documents\get-process_usage_cpu.csv
get-process | Group-Object -Property ProcessName | Format-Table Name, @{n='Mem (KB)';e={'{0:N0}' -f (($_.Group|Measure-Object WorkingSet -Sum).Sum / 1KB)};a='right'} -AutoSize  > C:\Users\natha\Documents\get-process_usage_ram.csv
C:\Users\natha\Documents\get-process_usage_ram.csv
