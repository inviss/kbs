using System;
using System.IO;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace D2net.Common.API
{
    public enum SHFileOPFunc : uint
    {
        Move    = 1,
        Copy    = 2,
        Delete  = 3,
        Rename  = 4,
    }

    public enum SHFileOPFlags : ushort
    {
        None                = 0x0000,
        MultiDestFiles      = 0x0001,
        ConfirmMouse        = 0x0002,
        Silent              = 0x0004,  // don't create progress/report
        RenameOnCollision   = 0x0008,
        NoConfirmation      = 0x0010,  // Don't prompt the user.
        WantMappingHandle   = 0x0020,  // Fill in SHFILEOPSTRUCT.hNameMappings
        AllowUndo           = 0x0040,
        FilesOnly           = 0x0080,  // on *.*, do only files
        SimpleProgress      = 0x0100,  // means don't show names of files
        NoConfirmMKDir      = 0x0200,  // don't confirm making any needed dirs
        NoErrorUI           = 0x0400,  // don't put up error UI
        NoCopySecurityAttribs = 0x0800,  // dont copy NT file Security Attributes
        NoRecursion         = 0x1000,  // don't recurse into directories.
        NoConnectedEelement = 0x2000,  // don't operate on connected elements.
        WantNukeWarning     = 0x4000,  // during delete operation, warn if nuking instead of recycling (partially overrides FOF_NOCONFIRMATION)
        NoRecurseReparse    = 0x8000,  // treat reparse points as objects, not containers
    }

	/// <summary>
	/// Shell32에 대한 요약 설명입니다.
	/// </summary>
	public class Shell32
	{
        [StructLayout(LayoutKind.Sequential)]
        internal unsafe struct SHFileOPStruct
        {
            public IntPtr hwnd;
            public uint wFunc;
            public IntPtr pFrom;
            public IntPtr pTo;
            public ushort fFlags;
            [MarshalAs(UnmanagedType.Bool)]
            public bool fAnyOperationsAborted;
            public IntPtr hNameMappings;
            public IntPtr lpszProgressTitle;
        }

        private static Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        private Shell32()
		{
		}

        public static int FileCopy(Control ctrl, 
            string[] from, 
            string to)
        {
            return SHFileOperation(ctrl, 
                SHFileOPFunc.Copy, 
                from, 
                to, 
                SHFileOPFlags.None,
                false,
                IntPtr.Zero,
                "");
        }

        public static int FileMove(Control ctrl, 
            string[] from, 
            string to)
        {
            return SHFileOperation(ctrl, 
                SHFileOPFunc.Move, 
                from, 
                to, 
                SHFileOPFlags.None,
                false,
                IntPtr.Zero,
                "");
        }

        public static int FileDelete(Control ctrl, 
            string[] from)
        {
            return SHFileOperation(ctrl, 
                SHFileOPFunc.Delete, 
                from, 
                "", 
                SHFileOPFlags.None,
                false,
                IntPtr.Zero,
                "");
        }

        public static int FileRename(Control ctrl, 
            string from, 
            string to)
        {
            string[] f = new string[1];

            f[0] = from;
            return SHFileOperation(ctrl, 
                SHFileOPFunc.Rename, 
                f, 
                to, 
                SHFileOPFlags.None,
                false,
                IntPtr.Zero,
                "");
        }

        public static int SHFileOperation(Control ctrl, 
            SHFileOPFunc func, 
            string[] from, 
            string to, 
            SHFileOPFlags flags,
            bool anyOpAborted,
            IntPtr nameMappings,
            string pgsTitle)
        {
            SHFileOPStruct op;
            StringWriter sw = null;
            byte[] data = null;

            op.lpszProgressTitle = IntPtr.Zero;
            op.pTo = IntPtr.Zero;
            op.pFrom = IntPtr.Zero;

            try
            {
                op.hwnd = ctrl.Handle;
                op.wFunc = (uint)func;
                op.fFlags = (ushort)flags;
                op.fAnyOperationsAborted = anyOpAborted;
                op.hNameMappings = nameMappings;
                
                sw = new StringWriter();
                foreach (string s in from)
                    sw.Write(s + "|");
                sw.Write("|");

                data = _KSC5601.GetBytes(sw.ToString());
                for (int i = 0; i < data.Length; i++)
                {
                    if (data[i] == (byte)'|')
                        data[i] = (byte)0;
                }
                op.pFrom = Marshal.AllocCoTaskMem(data.Length);
                Marshal.Copy(data, 0, op.pFrom, data.Length);
                op.pTo = Marshal.StringToCoTaskMemAnsi(to);
                op.lpszProgressTitle = Marshal.StringToCoTaskMemAnsi(pgsTitle);

                return SHFileOperation(ref op);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.StackTrace);
                throw ex;
            }
            finally
            {
                if (op.lpszProgressTitle != IntPtr.Zero)
                    Marshal.FreeCoTaskMem(op.lpszProgressTitle);
                if (op.pTo != IntPtr.Zero)
                    Marshal.FreeCoTaskMem(op.pTo);
                if (op.pFrom != IntPtr.Zero)
                    Marshal.FreeCoTaskMem(op.pFrom);
            }
        }

        [DllImport("shell32.Dll")]
        private static extern int SHFileOperation(ref SHFileOPStruct lpFileOp);
	}
}
