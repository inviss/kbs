using System.Reflection;
using System.Runtime.CompilerServices;

//
// 어셈블리에 대한 일반 정보는 다음 특성 집합을 통해 제어됩니다. 
// 어셈블리와 관련된 정보를 수정하려면
// 이 특성 값을 변경하십시오.
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
// 어셈블리의 버전 정보는 다음 네 가지 값으로 구성됩니다.
//
//      주 버전
//      부 버전 
//      빌드 번호
//      수정 번호
//
// 모든 값을 지정하거나
// 아래와 같이 '*'를 사용하여 수정 번호 및 빌드 번호가 자동으로 지정되도록 할 수 있습니다.

/*
 * version      : 1.1.0.1
 * date         : 2005-12-01
 * update by    : kang chang goog
 * description
 *      - AssemblyListCtrl(프로그램에서 참조하는 dll 리스트 형태로 보여줌) Control Class 추가
 */

/*
 * version      : 1.1.0.3
 * date         : 2005-12-12
 * update by    : kang chang goog
 * description
 *      - 참조 Assembly List의 표시하기를 원하는 Assembly 목록만 표시되게 수정함
 *      - Sorting
 */

/*
 * version      : 1.1.0.4
 * date         : 2006-01-25
 * update by    : kang chang goog
 * description
 *      - 승인된 Assembly에서 거부되어지는 Assembly List로 변경함
 */

/*
 * version      : 1.2.0.5
 * date         : 2006-01-27
 * update by    : kang chang goog
 * description
 *      - MonthCalendarEx Control 추가
 *      - DateTimePickerEx Control 추가
 *      - ImageButton이 Focus될때 Focus Line(점선)을 보여줄지 여부를 속성으로 설정하게 함.
 */

/*
 * version      : 1.3.0.1
 * date         : 2006-05-15
 * update by    : kang chang goog
 * description
 *      - FTP Module에 한국어 지원되게 함
 *      - 이미지 버튼의 버튼 이미지를 설정할때 null을 입력하면 에러 발생함
 *      - 이미지 버튼의 텍스트 출력시 공간에 맞게 표시함
 */

/*
 * version      : 1.3.0.2
 * date         : 2006-06-16
 * update by    : kang chang goog
 * description
 *      - Entry In 점을 00:00:00:00으로 설정하게 되면 Preroll 점의 계산이 잘못되어 에러가 발생함
 */

/*
 * version      : 1.3.0.6
 * date         : 2006-06-16
 * update by    : kang chang goog
 * description
 *      - User32 Class 추가
 *      - APIDatatypes class 추가
 *      - Window Message 정의
 *      - 기타 상수 정의
 */

/*
 * version      : 1.3.0.7
 * date         : 2006-07-15
 * update by    : kang chang goog
 * description
 *      - 00:00:00:00 에서 이전으로 가면 23:--:--:-- 이런 형식으로 처리되어 제대로 Preroll을 계산 하지 못하는 BUG - FIX
 */

/*
 * version      : 1.3.0.8
 * date         : 2006-07-26
 * update by    : kang chang goog
 * description
 *      - 로그 입력시 아이콘 정보도 같이 입력함
 */

/*
 * version      : 1.4.0.15
 * date         : 2006-08-22
 * update by    : kang chang goog
 * description
 *      - Back Color를 설정할 수 있는 콤보 박스 추가
 *      - Back Color를 설정할 수 있는 DateTimePicker로 수정
 *      - 확장된 Color class 추가
 *      - 체크 박스의 이미지를 설정할 수 있는 체크 박스 컨트롤 추가
 *      - 배경색을 설정할 수 있는 텍스트 박스 컨트롤 추가
 *      - 타임코드 에디터를 TextBoxEx에서 상속 받도록 부모 클래스를 수정함
 *      - DropList Mode에서 텍스트가 보이지 않는 버그 FIX
 */

/*
 * version      : 1.4.4.18
 * date         : 2006-08-31
 * update by    : kang chang goog
 * description
 *      - ComboBoxEx에서 Selected Item에 대한 텍스트 출력시 선택된 아이템을 표시함.
 *      - DateTimePickerEx에서 달력 Popup시 DropDown Event 발생하게 해줌
 *      - ResourceManager에서 초기화시 Resource Table을 초기화함
 *      - UpDownEx Class 추가
 *      - FlatTabControl Class 추가
 *      - ImageRadioButton Class 추가
 *      - SkinManager Class 추가
 */

/*
 * version      : 1.4.4.19
 * date         : 2006-09-01
 * update by    : kang chang goog
 * description
 *      - AssemblyListCtrl 컨트롤의 바이너리의 최종 수정일을 표시함
 */

/*
 * version      : 1.4.4.23
 * date         : 2006-09-21
 * update by    : kang chang goog
 * description
 *      - 텝의 순서를 바꿀수 있는 텝 컨트롤 추가
 *      - OutLineWidth 속성을 수정가능하도록  public으로 설정함
 *      - Timecode 설정 루틴에서 10이 넘을때 음의 값으로 설정함
 *      - 키가 눌려졌을때 발생하는 이벤트에서 에러 발생시 무시한다.
 */

/*
 * version      : 1.4.4.25
 * date         : 2006-09-21
 * update by    : kang chang goog
 * description
 *      - UpDownEx class에서 Back Space 값이 적용되지 않는 버그 - FIX
 *      - UpDownEx class에서 텍스트 박스 값을 변경하여도 내부 값이 변경되지 않는 버그 - FIX
 */

/*
 * version      : 1.4.4.26
 * date         : 2006-09-27
 * update by    : kang chang goog
 * description
 *      - Timecode Class에 시간, 분, 초, 프레임 속성을 추가
 */

/*
 * version      : 1.4.4.27
 * date         : 2006-10-21
 * update by    : kang chang goog
 * description
 *      - UpDownEx Control에서 편집창에서 범위에 벗어나는 값은 수정 못하게 막음
 */

/*
 * version      : 1.4.4.29
 * date         : 2006-11-28
 * update by    : kang chang goog
 * description
 *      - UpDownEx Control에서 입력란에 Back Sapce를 입력할수 있게 수정함
 *      - Shell32 Calss 추가
 */

[assembly: AssemblyVersion("1.4.4.29")]

//
// 어셈블리에 서명하려면 사용할 키를 지정해야 합니다. 어셈블리 서명에 대한 자세한 내용은 
// Microsoft .NET Framework 설명서를 참조하십시오.
//
// 서명하는 데 사용할 키를 제어하려면 아래 특성을 사용합니다. 
//
// 참고: 
//   (*) 키를 지정하지 않으면 어셈블리에 서명할 수 없습니다.
//   (*) KeyName은
//       사용자 컴퓨터의 CSP(암호화 서비스 공급자)에
//        설치되어 있는 키를 참조하고 KeyFile은 키가 포함된 파일을
//        참조합니다.
//   (*) KeyFile과 KeyName 값을 모두 지정하면 
//       다음과 같은 프로세스가 발생합니다.
//       (1) CSP에 KeyName이 있으면 해당 키가 사용됩니다.
//       (2) KeyName은 없고, KeyFile이 있으면 
//           KeyFile의 키가 CSP에 설치되어 사용됩니다.
//   (*) sn.exe(강력한 이름 유틸리티)를 사용하면 KeyFile을 만들 수 있습니다.
//        KeyFile을 지정하는 경우
//       KeyFile의 위치는 %Project Directory%\obj\<configuration>의 프로젝트 출력 디렉터리 위치를 기준으로 하는 상대 위치이어야 합니다.
//       예를 들어, KeyFile이 프로젝트 디렉터리에 있는 경우
//       AssemblyKeyFile 특성을 
//       [assembly: AssemblyKeyFile("..\\..\\mykey.snk")]로 지정합니다.
//   (*) 서명 연기는 고급 옵션입니다.
//       이 옵션에 대한 자세한 내용은 Microsoft .NET Framework 설명서를 참조하십시오.
//
[assembly: AssemblyDelaySign(false)]
[assembly: AssemblyKeyFile("")]
[assembly: AssemblyKeyName("")]
