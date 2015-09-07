@echo off
copy D:\Source\Project\CommonLib\D2net.Common\bin\Debug\\*.dll D:\Source\Project\CommonLib\CommonLib
if errorlevel 1 goto CSharpReportError
goto CSharpEnd
:CSharpReportError
echo Project error: 도구에서 오류 코드를 반환했습니다. 위치: 빌드 이벤트
exit 1
:CSharpEnd