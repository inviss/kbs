using System;
using System.Text;
using System.Collections;
using System.Runtime.InteropServices;

namespace D2net.Common.API
{
    /// <summary>
	/// �ý����� ������ ����̺��� Ÿ���� ��Ÿ���� ������.
    /// </summary>
    public enum DriveType : uint
    {
        /// <summary>
	    /// �˼� ���� Ÿ��
        /// </summary>
        DRIVE_UNKNOWN = 0,

        /// <summary>
	    /// ��ΰ� ��ȿ���� ���� ���丮
        /// </summary>
        DRIVE_NO_ROOT_DIR,

        /// <summary>
	    /// �����Ҽ� �ִ� ����̺�
        /// </summary>
        DRIVE_REMOVABLE,

        /// <summary>
	    /// ����� ���� ����̺�
        /// </summary>
        DRIVE_FIXED,

        /// <summary>
	    /// ����Ʈ(��Ʈ��) ����̺�
        /// </summary>
        DRIVE_REMOTE,

        /// <summary>
	    /// CD-ROM ����̺�
        /// </summary>
        DRIVE_CDROM,

        /// <summary>
	    /// RAM ��ũ
        /// </summary>
        DRIVE_RAMDISK
    }

	/// <summary>
	/// Kernel32 Api�� ȣ���� ���� Ŭ���� Ÿ�� 
	/// </summary>
	public sealed class Kernel32
	{
        /// <summary>
	    /// ��� ������ �ִ밪
        /// </summary>
        public const int MAX_PATH = 256;
        private static string _ComputerName = "";

        /// <summary>
	    /// �⺻ ������
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
	    /// ��ũ���� ����ִ� ���� ������ ��ȯ�Ѵ�.
        /// </summary>
        /// <returns>�����ϸ� true, �����ϸ� false.</returns>
        /// <param name="path">
        /// ������ ��ȯ ���� ��θ�. 
        /// �� ���� null�̸� ���� ���õ� ����� ��ũ�� ������ ��ȯ�Ѵ�. 
        /// �� ���� UNC �̸��̸�, �ݵ�� '\\'�� �����Ͽ��� �Ѵ�(��, \\MyServer\MyShare).
        /// ��δ� '\'�� ������ �־�� �Ѵ�(��, C:\).
        /// </param>
        /// <param name="sec1">Ŭ�����ʹ� ���� ���� ��ȯ ���� ������ ����</param>
        /// <param name="sec2">���ʹ� ����Ʈ ���� ��ȯ ���� ������ ����</param>
        /// <param name="sec3">��ũ�� ��� ������ Ŭ������ ���� ��ȯ ���� ������ ����</param>
        /// <param name="sec4">��ũ�� ��ü Ŭ������ ���� ��ȯ ���� ������ ����</param>
        [DllImport("kernel32.dll")]
        public static extern bool GetDiskFreeSpace(string path, ref uint sec1, ref uint sec2, ref uint sec3, ref uint sec4);

        /// <summary>
	    /// ��ũ���� ����ִ� ���� ������ ��ȯ�Ѵ�.
        /// </summary>
        /// <returns>�����ϸ� true, �����ϸ� false.</returns>
        /// <param name="path">
        /// ������ ��ȯ ���� ��θ�. 
        /// �� ���� null�̸� ���� ���õ� ����� ��ũ�� ������ ��ȯ�Ѵ�. 
        /// �� ���� UNC �̸��̸�, �ݵ�� '\\'�� �����Ͽ��� �Ѵ�(��, \\MyServer\MyShare).
        /// ��δ� '\'�� ������ �־�� �Ѵ�(��, C:\).
        /// </param>
        /// <param name="FreeBytesAvailable">��ũ�� ���� ����ڿ��� ��� ������ ����Ʈ ���� ��ȯ ���� ������ ����.</param>
        /// <param name="TotalNumberOfBytes">��ũ�� ��ü ����Ʈ ���� ��ȯ ���� ������ ����</param>
        /// <param name="TotalNumberOfFreeBytes">��ũ�� ��� ������ ����Ʈ ���� ��ȯ ���� ������ ����.</param>
        [DllImport("kernel32.dll")]
        public static extern bool GetDiskFreeSpaceEx(string path, ref ulong FreeBytesAvailable , ref ulong TotalNumberOfBytes , ref ulong TotalNumberOfFreeBytes);

        /// <summary>
	    /// ȣ��Ǵ� �������� ������ �ý��� �����ڵ带 ��ȯ�Ѵ�.
        /// </summary>
        /// <returns>������ �����ڵ�.</returns>
        [DllImport("kernel32.dll")]
        public static extern uint GetLastError();

        /// <summary>
	    /// �ý��ۿ��� ��ȿ�� ����̺긦 ���ڿ��� ���ۿ� ä���.
        /// </summary>
        /// <returns>�����ϸ� ���ڿ� ���̸� ��ȯ�Ѵ�. �����ϸ� 0 ���� ��ȯ�Ѵ�.</returns>
        /// <param name="buffLen">����̺� �̸����� ��ȯ ���� ������ ����.</param>
        /// <param name="buff">����̺� �̸����� ��ȯ ���� ����.
        /// ����̺� �̸��� null�� �����ϰ�, ��ü ����̺� �̸� ������ ���� null�� �ι� ������ �ȴ�.
        /// c:\&lt;null&gt;d:\&lt;null&gt;&lt;null&gt;
        /// </param>
        [DllImport("kernel32.dll")]
        public static extern uint GetLogicalDriveStrings(uint buffLen, sbyte[] buff);

        /// <summary>
	    /// �Է��� ��ο� �ش��ϴ� ����̺� Ÿ���� �����Ѵ�.
        /// </summary>
        /// <returns>����̺� Ÿ��(<c>DriveType</c>)</returns>
        /// <param name="path">
        /// Ÿ���� ��ȯ ���� ��θ�. '\'�� ������ �־�� �Ѵ�(��, C:\).
        /// �� ���� null�̸� ���� ���õ� ���丮�� ���� Ÿ���� ��ȯ�Ѵ�.
        /// </param>
        /// <seealso cref="DriveType"/>
        [DllImport("kernel32.dll")]
        public static extern DriveType GetDriveType(string path);

        /// <summary>
	    /// �Է��� Ÿ�Կ� �ش��ϴ� ����̺� �̸��� ���ڿ��� �迭�� ��ȯ�Ѵ�.
        /// </summary>
        /// <returns>����̺� �̸� ���ڿ��� �迭.</returns>
        /// <param name="types">
        /// ��ȯ ���� ����̺��� Ÿ���� �Է��Ѵ�.
        /// </param>
        /// <seealso cref="DriveType"/>
        /// <example> ����̺� �̸� ��ȯ
        /// <code>
        /// string[] drvs = Kernel32.GetDrives(DriveType.DRIVE_FIXED, DriveType.DRIVE_REMOTE);
        /// </code>
        /// ���� ���� ����� ������ �ϵ� ��ũ�� ��Ʈ�� ����̺��� �̸��� ��ȯ �޴´�.
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
