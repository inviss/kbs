using System;
using System.Text;
using System.Collections;
using System.Runtime.InteropServices;

namespace D2net.Common.API
{
    /// <summary>
	/// 시스템의 로지컬 드라이브의 타입을 나타내는 열거형.
    /// </summary>
    public enum DriveType : uint
    {
        /// <summary>
	    /// 알수 없는 타입
        /// </summary>
        DRIVE_UNKNOWN = 0,

        /// <summary>
	    /// 경로가 유효하지 않은 디렉토리
        /// </summary>
        DRIVE_NO_ROOT_DIR,

        /// <summary>
	    /// 제거할수 있는 드라이브
        /// </summary>
        DRIVE_REMOVABLE,

        /// <summary>
	    /// 지울수 없는 드라이브
        /// </summary>
        DRIVE_FIXED,

        /// <summary>
	    /// 리모트(네트웍) 드라이브
        /// </summary>
        DRIVE_REMOTE,

        /// <summary>
	    /// CD-ROM 드라이브
        /// </summary>
        DRIVE_CDROM,

        /// <summary>
	    /// RAM 디스크
        /// </summary>
        DRIVE_RAMDISK
    }

	/// <summary>
	/// Kernel32 Api의 호출을 위한 클래스 타입 
	/// </summary>
	public sealed class Kernel32
	{
        /// <summary>
	    /// 경로 길이의 최대값
        /// </summary>
        public const int MAX_PATH = 256;
        private static string _ComputerName = "";

        /// <summary>
	    /// 기본 생성자
        /// </summary>
        private Kernel32()
		{
		}

        [DllImport("kernel32.dll")]
        public static extern bool GetComputerName([Out] StringBuilder lpBuffer, ref uint lpnSize);

        public static string ComputerName
        {
            get
            {
                uint len = 128;
                StringBuilder sb = new StringBuilder(128);

                if (_ComputerName != "")
                    return _ComputerName;

                if (!GetComputerName(sb, ref len))
                    return null;

                _ComputerName = sb.ToString().Trim();
                return _ComputerName;
            }
        }

        /// <summary>
	    /// 디스크상의 비어있는 공간 정보를 반환한다.
        /// </summary>
        /// <returns>성공하면 true, 실패하면 false.</returns>
        /// <param name="path">
        /// 정보를 반환 받을 경로명. 
        /// 이 값이 null이면 현재 선택된 경로의 디스크의 정보를 반환한다. 
        /// 이 값이 UNC 이름이면, 반드시 '\\'를 포함하여야 한다(예, \\MyServer\MyShare).
        /// 경로는 '\'를 가지고 있어야 한다(예, C:\).
        /// </param>
        /// <param name="sec1">클러스터당 색터 수를 반환 받을 변수의 참조</param>
        /// <param name="sec2">색터당 바이트 수를 반환 받을 변수의 참조</param>
        /// <param name="sec3">디스크의 사용 가능한 클러스터 수를 반환 받을 변수의 참조</param>
        /// <param name="sec4">디스크의 전체 클러스터 수를 반환 받을 변수의 참조</param>
        [DllImport("kernel32.dll")]
        public static extern bool GetDiskFreeSpace(string path, ref uint sec1, ref uint sec2, ref uint sec3, ref uint sec4);

        /// <summary>
	    /// 디스크상의 비어있는 공간 정보를 반환한다.
        /// </summary>
        /// <returns>성공하면 true, 실패하면 false.</returns>
        /// <param name="path">
        /// 정보를 반환 받을 경로명. 
        /// 이 값이 null이면 현재 선택된 경로의 디스크의 정보를 반환한다. 
        /// 이 값이 UNC 이름이면, 반드시 '\\'를 포함하여야 한다(예, \\MyServer\MyShare).
        /// 경로는 '\'를 가지고 있어야 한다(예, C:\).
        /// </param>
        /// <param name="FreeBytesAvailable">디스크의 현재 사용자에게 사용 가능한 바이트 수를 반환 받을 변수의 참조.</param>
        /// <param name="TotalNumberOfBytes">디스크의 전체 바이트 수를 반환 받을 변수의 참조</param>
        /// <param name="TotalNumberOfFreeBytes">디스크의 사용 가능한 바이트 수를 반환 받을 변수의 참조.</param>
        [DllImport("kernel32.dll")]
        public static extern bool GetDiskFreeSpaceEx(string path, ref ulong FreeBytesAvailable , ref ulong TotalNumberOfBytes , ref ulong TotalNumberOfFreeBytes);

        /// <summary>
	    /// 호출되는 스레드의 마지막 시스템 에러코드를 반환한다.
        /// </summary>
        /// <returns>마지막 에러코드.</returns>
        [DllImport("kernel32.dll")]
        public static extern uint GetLastError();

        /// <summary>
	    /// 시스템에서 유효한 드라이브를 문자열로 버퍼에 채운다.
        /// </summary>
        /// <returns>성공하면 문자열 길이를 반환한다. 실패하면 0 값을 반환한다.</returns>
        /// <param name="buffLen">드라이브 이름들을 반환 받을 버퍼의 길이.</param>
        /// <param name="buff">드라이브 이름들을 반환 받을 버퍼.
        /// 드라이브 이름은 null로 구별하고, 전체 드라이브 이름 정보의 끝은 null이 두번 나오게 된다.
        /// c:\&lt;null&gt;d:\&lt;null&gt;&lt;null&gt;
        /// </param>
        [DllImport("kernel32.dll")]
        public static extern uint GetLogicalDriveStrings(uint buffLen, sbyte[] buff);

        /// <summary>
	    /// 입력한 경로에 해당하는 드라이브 타입을 결정한다.
        /// </summary>
        /// <returns>드라이브 타입(<c>DriveType</c>)</returns>
        /// <param name="path">
        /// 타입을 반환 받을 경로명. '\'를 가지고 있어야 한다(예, C:\).
        /// 이 값이 null이면 현재 선택된 디렉토리에 대한 타입을 반환한다.
        /// </param>
        /// <seealso cref="DriveType"/>
        [DllImport("kernel32.dll")]
        public static extern DriveType GetDriveType(string path);

        /// <summary>
	    /// 입력한 타입에 해당하는 드라이브 이름을 문자열의 배열로 반환한다.
        /// </summary>
        /// <returns>드라이브 이름 문자열의 배열.</returns>
        /// <param name="types">
        /// 반환 받을 드라이브의 타입을 입력한다.
        /// </param>
        /// <seealso cref="DriveType"/>
        /// <example> 드라이브 이름 반환
        /// <code>
        /// string[] drvs = Kernel32.GetDrives(DriveType.DRIVE_FIXED, DriveType.DRIVE_REMOTE);
        /// </code>
        /// 위의 실행 결과는 고정된 하드 디스크와 네트웍 드라이브의 이름을 반환 받는다.
        /// </example>
        public static string[] GetDrives(params DriveType[] types)
        {
            try
            {
                uint count, i, j;
                DriveType type;
                sbyte[] buff = new sbyte[(int)MAX_PATH];
                string drive = "";
                ArrayList result = new ArrayList();
                
                count = Kernel32.GetLogicalDriveStrings(MAX_PATH, buff) / 4;
                for (i = 0; i < count; i++)
                {
                    drive = new string((char)buff[4 * i], 1) + ":\\";
                    type = Kernel32.GetDriveType(drive);

                    for (j = 0; j < types.Length; j++)
                    {
                        if (type == types[j])
                            result.Add(drive);
                    }
                }

                return (string[])result.ToArray(typeof(string));
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
	}
}
