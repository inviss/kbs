using System.Reflection;
using System.Runtime.CompilerServices;

//
// ������� ���� �Ϲ� ������ ���� Ư�� ������ ���� ����˴ϴ�. 
// ������� ���õ� ������ �����Ϸ���
// �� Ư�� ���� �����Ͻʽÿ�.
//
[assembly: AssemblyTitle("")]
[assembly: AssemblyDescription("")]
[assembly: AssemblyConfiguration("")]
[assembly: AssemblyCompany("")]
[assembly: AssemblyProduct("")]
[assembly: AssemblyCopyright("")]
[assembly: AssemblyTrademark("")]
[assembly: AssemblyCulture("")]		

//
// ������� ���� ������ ���� �� ���� ������ �����˴ϴ�.
//
//      �� ����
//      �� ���� 
//      ���� ��ȣ
//      ���� ��ȣ
//
// ��� ���� �����ϰų�
// �Ʒ��� ���� '*'�� ����Ͽ� ���� ��ȣ �� ���� ��ȣ�� �ڵ����� �����ǵ��� �� �� �ֽ��ϴ�.

/*
 * version      : 1.1.0.1
 * date         : 2005-12-01
 * update by    : kang chang goog
 * description
 *      - AssemblyListCtrl(���α׷����� �����ϴ� dll ����Ʈ ���·� ������) Control Class �߰�
 */

/*
 * version      : 1.1.0.3
 * date         : 2005-12-12
 * update by    : kang chang goog
 * description
 *      - ���� Assembly List�� ǥ���ϱ⸦ ���ϴ� Assembly ��ϸ� ǥ�õǰ� ������
 *      - Sorting
 */

/*
 * version      : 1.1.0.4
 * date         : 2006-01-25
 * update by    : kang chang goog
 * description
 *      - ���ε� Assembly���� �źεǾ����� Assembly List�� ������
 */

/*
 * version      : 1.2.0.5
 * date         : 2006-01-27
 * update by    : kang chang goog
 * description
 *      - MonthCalendarEx Control �߰�
 *      - DateTimePickerEx Control �߰�
 *      - ImageButton�� Focus�ɶ� Focus Line(����)�� �������� ���θ� �Ӽ����� �����ϰ� ��.
 */

/*
 * version      : 1.3.0.1
 * date         : 2006-05-15
 * update by    : kang chang goog
 * description
 *      - FTP Module�� �ѱ��� �����ǰ� ��
 *      - �̹��� ��ư�� ��ư �̹����� �����Ҷ� null�� �Է��ϸ� ���� �߻���
 *      - �̹��� ��ư�� �ؽ�Ʈ ��½� ������ �°� ǥ����
 */

/*
 * version      : 1.3.0.2
 * date         : 2006-06-16
 * update by    : kang chang goog
 * description
 *      - Entry In ���� 00:00:00:00���� �����ϰ� �Ǹ� Preroll ���� ����� �߸��Ǿ� ������ �߻���
 */

/*
 * version      : 1.3.0.6
 * date         : 2006-06-16
 * update by    : kang chang goog
 * description
 *      - User32 Class �߰�
 *      - APIDatatypes class �߰�
 *      - Window Message ����
 *      - ��Ÿ ��� ����
 */

/*
 * version      : 1.3.0.7
 * date         : 2006-07-15
 * update by    : kang chang goog
 * description
 *      - 00:00:00:00 ���� �������� ���� 23:--:--:-- �̷� �������� ó���Ǿ� ����� Preroll�� ��� ���� ���ϴ� BUG - FIX
 */

/*
 * version      : 1.3.0.8
 * date         : 2006-07-26
 * update by    : kang chang goog
 * description
 *      - �α� �Է½� ������ ������ ���� �Է���
 */

/*
 * version      : 1.4.0.15
 * date         : 2006-08-22
 * update by    : kang chang goog
 * description
 *      - Back Color�� ������ �� �ִ� �޺� �ڽ� �߰�
 *      - Back Color�� ������ �� �ִ� DateTimePicker�� ����
 *      - Ȯ��� Color class �߰�
 *      - üũ �ڽ��� �̹����� ������ �� �ִ� üũ �ڽ� ��Ʈ�� �߰�
 *      - ������ ������ �� �ִ� �ؽ�Ʈ �ڽ� ��Ʈ�� �߰�
 *      - Ÿ���ڵ� �����͸� TextBoxEx���� ��� �޵��� �θ� Ŭ������ ������
 *      - DropList Mode���� �ؽ�Ʈ�� ������ �ʴ� ���� FIX
 */

/*
 * version      : 1.4.4.18
 * date         : 2006-08-31
 * update by    : kang chang goog
 * description
 *      - ComboBoxEx���� Selected Item�� ���� �ؽ�Ʈ ��½� ���õ� �������� ǥ����.
 *      - DateTimePickerEx���� �޷� Popup�� DropDown Event �߻��ϰ� ����
 *      - ResourceManager���� �ʱ�ȭ�� Resource Table�� �ʱ�ȭ��
 *      - UpDownEx Class �߰�
 *      - FlatTabControl Class �߰�
 *      - ImageRadioButton Class �߰�
 *      - SkinManager Class �߰�
 */

/*
 * version      : 1.4.4.19
 * date         : 2006-09-01
 * update by    : kang chang goog
 * description
 *      - AssemblyListCtrl ��Ʈ���� ���̳ʸ��� ���� �������� ǥ����
 */

/*
 * version      : 1.4.4.23
 * date         : 2006-09-21
 * update by    : kang chang goog
 * description
 *      - ���� ������ �ٲܼ� �ִ� �� ��Ʈ�� �߰�
 *      - OutLineWidth �Ӽ��� ���������ϵ���  public���� ������
 *      - Timecode ���� ��ƾ���� 10�� ������ ���� ������ ������
 *      - Ű�� ���������� �߻��ϴ� �̺�Ʈ���� ���� �߻��� �����Ѵ�.
 */

/*
 * version      : 1.4.4.25
 * date         : 2006-09-21
 * update by    : kang chang goog
 * description
 *      - UpDownEx class���� Back Space ���� ������� �ʴ� ���� - FIX
 *      - UpDownEx class���� �ؽ�Ʈ �ڽ� ���� �����Ͽ��� ���� ���� ������� �ʴ� ���� - FIX
 */

/*
 * version      : 1.4.4.26
 * date         : 2006-09-27
 * update by    : kang chang goog
 * description
 *      - Timecode Class�� �ð�, ��, ��, ������ �Ӽ��� �߰�
 */

/*
 * version      : 1.4.4.27
 * date         : 2006-10-21
 * update by    : kang chang goog
 * description
 *      - UpDownEx Control���� ����â���� ������ ����� ���� ���� ���ϰ� ����
 */

/*
 * version      : 1.4.4.29
 * date         : 2006-11-28
 * update by    : kang chang goog
 * description
 *      - UpDownEx Control���� �Է¶��� Back Sapce�� �Է��Ҽ� �ְ� ������
 *      - Shell32 Calss �߰�
 */

[assembly: AssemblyVersion("1.4.4.29")]

//
// ������� �����Ϸ��� ����� Ű�� �����ؾ� �մϴ�. ����� ���� ���� �ڼ��� ������ 
// Microsoft .NET Framework ������ �����Ͻʽÿ�.
//
// �����ϴ� �� ����� Ű�� �����Ϸ��� �Ʒ� Ư���� ����մϴ�. 
//
// ����: 
//   (*) Ű�� �������� ������ ������� ������ �� �����ϴ�.
//   (*) KeyName��
//       ����� ��ǻ���� CSP(��ȣȭ ���� ������)��
//        ��ġ�Ǿ� �ִ� Ű�� �����ϰ� KeyFile�� Ű�� ���Ե� ������
//        �����մϴ�.
//   (*) KeyFile�� KeyName ���� ��� �����ϸ� 
//       ������ ���� ���μ����� �߻��մϴ�.
//       (1) CSP�� KeyName�� ������ �ش� Ű�� ���˴ϴ�.
//       (2) KeyName�� ����, KeyFile�� ������ 
//           KeyFile�� Ű�� CSP�� ��ġ�Ǿ� ���˴ϴ�.
//   (*) sn.exe(������ �̸� ��ƿ��Ƽ)�� ����ϸ� KeyFile�� ���� �� �ֽ��ϴ�.
//        KeyFile�� �����ϴ� ���
//       KeyFile�� ��ġ�� %Project Directory%\obj\<configuration>�� ������Ʈ ��� ���͸� ��ġ�� �������� �ϴ� ��� ��ġ�̾�� �մϴ�.
//       ���� ���, KeyFile�� ������Ʈ ���͸��� �ִ� ���
//       AssemblyKeyFile Ư���� 
//       [assembly: AssemblyKeyFile("..\\..\\mykey.snk")]�� �����մϴ�.
//   (*) ���� ����� ��� �ɼ��Դϴ�.
//       �� �ɼǿ� ���� �ڼ��� ������ Microsoft .NET Framework ������ �����Ͻʽÿ�.
//
[assembly: AssemblyDelaySign(false)]
[assembly: AssemblyKeyFile("")]
[assembly: AssemblyKeyName("")]
