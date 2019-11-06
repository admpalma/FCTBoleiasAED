@echo off
for /l %%a in (1,1,9) do (
  fc "%~dp0T0%%a\output%%a.txt" "%~dp0T0%%a\out.txt" > nul
  if errorlevel 1 (
    echo Test 0%%a doesn't match.
  ) else (
    echo Test 0%%a passed.
  )
)
for /l %%a in (10,1,17) do (
  fc "%~dp0T%%a\output%%a.txt" "%~dp0T%%a\out.txt" > nul
  if errorlevel 1 (
    echo Test %%a doesn't match.
  ) else (
    echo Test %%a passed.
  )
)
pause
