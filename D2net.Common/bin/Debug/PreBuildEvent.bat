@echo off
copy D:\Source\Project\CommonLib\D2net.Common\bin\Debug\\*.dll D:\Source\Project\CommonLib\CommonLib
if errorlevel 1 goto CSharpReportError
goto CSharpEnd
:CSharpReportError
echo Project error: �������� ���� �ڵ带 ��ȯ�߽��ϴ�. ��ġ: ���� �̺�Ʈ
exit 1
:CSharpEnd