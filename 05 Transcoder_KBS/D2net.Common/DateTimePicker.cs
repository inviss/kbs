using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace D2net.Common.UI
{
    /// <summary>
	/// MyDateTimePicker에 대한 요약 설명입니다.
	/// </summary>
	public class DateTimePicker : System.Windows.Forms.DateTimePicker
	{
        [StructLayout(LayoutKind.Sequential)]
        internal struct SystemTime 
        {
            internal ushort wYear;
            internal ushort wMonth;
            internal ushort wDayOfWeek;
            internal ushort wDay;
            internal ushort wHour;
            internal ushort wMinute;
            internal ushort wSecond;
            internal ushort wMilliseconds;
        }

        public event DropDownPreChangeState OnDropDownPreChangeState;

        private const int MCM_FIRST         = 0x1000;

        private const int MCM_GETMONTHRANGE = (MCM_FIRST + 0x7);
        private const int MCM_SETDAYSTATE   = (MCM_FIRST + 0x8);

        private const int DTM_GETMONTHCAL   = 0x1008;

        internal const int GMR_VISIBLE       = 0;
        internal const int GMR_DAYSTATE      = 1;

        private IntPtr _PTimes = IntPtr.Zero;

		public DateTimePicker()
		{
            _PTimes = Marshal.AllocCoTaskMem(Marshal.SizeOf(typeof(SystemTime)) * 2);
		}

        protected override void Dispose(bool disposing)
        {
            if (_PTimes != IntPtr.Zero)
                Marshal.FreeCoTaskMem(_PTimes);
            base.Dispose (disposing);
        }

        protected IntPtr MonthCalendarHandle
        {
            get
            {
                try
                {
                    return new IntPtr(SendMessage(Handle, DTM_GETMONTHCAL, 0, 0));
                }
                catch (Exception ex)
                {
                    throw ex;
                }
            }
        }

        protected override void OnDropDown(EventArgs eventargs)
        {
            DropDownArgs args = new DropDownArgs();
            args._MonthCalendarHandle = MonthCalendarHandle;
            DropDownEx(this, args);
            base.OnDropDown(eventargs);
        }

        private unsafe void DropDownEx(object sender, DropDownArgs args)
        {
            IntPtr hCalendar = args.MonthCalendarHandle;

            try
            {
                if (OnDropDownPreChangeState != null)
                {
                    DateTime s = DateTime.Now, e = DateTime.Now;
                    int buffsize = GetMonthRange(hCalendar, ref s, ref e, GMR_DAYSTATE);
                    DropDownChangeStateArgs sargs = new DropDownChangeStateArgs(buffsize, s.Day == 1 ? 0 : 1);
                    sargs._Min = s;
                    sargs._Max = e;
                    OnDropDownPreChangeState(this, sargs);
                    if (!SetDayState(hCalendar, buffsize, new IntPtr((void*)sargs._Buffer)))
                    {
                        System.Diagnostics.Debug.WriteLine("SetDayState Error");
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        internal int GetMonthRange(IntPtr pHandle, ref DateTime Min, ref DateTime Max, int flag)
        {
            try
            {   
                int retval = SendMessage(pHandle, MCM_GETMONTHRANGE, (uint)flag, _PTimes.ToInt32());
                SystemTime sMin = (SystemTime)Marshal.PtrToStructure(_PTimes, typeof(SystemTime));
                SystemTime sMax = (SystemTime)Marshal.PtrToStructure(
                    new IntPtr(_PTimes.ToInt32() + Marshal.SizeOf(typeof(SystemTime))), typeof(SystemTime));
                Min = ConvertDateTime(ref sMin);
                Max = ConvertDateTime(ref sMax);
                return retval;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        internal bool SetDayState(IntPtr pHandle, int Months, IntPtr pState)
        {
            try
            {
                return SendMessage(pHandle, MCM_SETDAYSTATE, (uint)Months, pState.ToInt32()) == 0 ? false : true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private DateTime ConvertDateTime(ref SystemTime st)
        {
            return new DateTime(st.wYear, st.wMonth, st.wDay, st.wHour, 
                st.wMinute, st.wSecond, st.wMilliseconds);
        }

        [DllImport("User32.Dll")]
        private static extern int SendMessage(IntPtr hWnd, uint Msg, uint wParam, int lParam);
	}

    public delegate void DropDownPreChangeState(object sender, DropDownChangeStateArgs args);

    public class DropDownArgs
    {
        internal IntPtr _MonthCalendarHandle;

        public IntPtr MonthCalendarHandle
        {
            get { return _MonthCalendarHandle; }
        }
    }

    public unsafe class DropDownChangeStateArgs
    {
        internal uint* _Buffer = null;
        internal DateTime _Min;
        internal DateTime _Max;
        private int _Offset = 0;

        private DropDownChangeStateArgs() {}
        internal unsafe DropDownChangeStateArgs(int buffsize, int offset)
        {
            _Buffer = (uint*)Marshal.AllocCoTaskMem(Marshal.SizeOf(typeof(int)) * buffsize).ToPointer();
            _Offset = offset;
            memset(new IntPtr(_Buffer), 0, (uint)(Marshal.SizeOf(typeof(uint)) * buffsize));
        }

        ~DropDownChangeStateArgs()
        {
            Marshal.FreeCoTaskMem(new IntPtr(_Buffer));
        }

        public DateTime Min
        {
            get { return _Min; }
        }

        public DateTime Max
        {
            get { return _Max; }
        }

        public unsafe bool this [int day]
        {
            get
            {
                if (day < 1 || day > 31)
                    return false;
                day--;
                return (_Buffer[_Offset] & (0x1 << (day % 32))) == 1 ? true : false;
            }

            set
            {
                if (day < 1 || day > 31)
                    return;
                day--;
                uint setValue = (uint)(0x1 << day);
                if (value)
                    _Buffer[_Offset] |= setValue;
                else
                {
                    if ((_Buffer[_Offset] & setValue) < 0)
                        return;
                        
                    _Buffer[_Offset] ^= setValue;
                }
            }
        }

        [DllImport("MSVCRT.Dll")]
        private static extern IntPtr memset(IntPtr ptr, int c, uint count);
    }
}
