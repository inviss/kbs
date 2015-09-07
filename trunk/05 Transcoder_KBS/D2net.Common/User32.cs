using System;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace D2net.Common.API
{
	/// <summary>
	/// User32에 대한 요약 설명입니다.
	/// </summary>
	public class User32
	{
		private User32()
		{
		}

        // [2006-03-29] -> 2회 이상 미리보기시 Event로 Tab의 선택된 index를 지정시 Deadlock이 걸림 - FIX
        //                 SendMessage로 처리
        // [2006-05-21] -> 관리를 위해 이동
        [DllImport("User32.Dll")]
        public static extern uint SendMessage(IntPtr hWnd, uint Msg, uint wParam, int lParam);

        [DllImport("user32.dll", SetLastError = true)] 
        public static extern int GetSystemMenu(IntPtr hWnd, int revert); 

        [DllImport("user32.dll", SetLastError = true)] 
        public static extern int EnableMenuItem(int menu, int ideEnableItem, int enable); 

        public static void SysCloseBtnEnabled(Form form, bool Enabled) 
        { 
            IntPtr hWnd = form.Handle; 
            int SystemMenu = GetSystemMenu(hWnd, 0); 
            int PreviousState = EnableMenuItem(SystemMenu, 
                (int)SystemMenuCommand.Close, 
                (int)MenuFlags.ByCommand | 
                (Enabled ? (int)MenuFlags.Enabled : ((int)MenuFlags.Disabled | (int)MenuFlags.Grayed))); 
            if (PreviousState == -1) 
                throw new Exception("The close menu does not exist"); 			
        } 
	}
}
