/*
 * Copyright ?2005,  Patrik Bohman
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *    - Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 * 
 *    - Redistributions in binary form must reproduce the above copyright notice, 
 *      this list of conditions and the following disclaimer in the documentation 
 *      and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */

using System;
using System.Runtime.InteropServices;

namespace D2net.Common.UI
{
	/// <summary>
	/// Summary description for NativeMethods.
	/// </summary>
	internal class NativeMethods
	{
		private NativeMethods()
		{

		}

		#region Structs
		
		[StructLayout(LayoutKind.Sequential)]
		public struct DLLVERSIONINFO
		{
			public int cbSize;
			public int dwMajorVersion;
			public int dwMinorVersion;
			public int dwBuildNumber;
			public int dwPlatformID;
		}

		[StructLayout(LayoutKind.Sequential)]
		public class KeyboardHookStruct
		{
			public int vkCode;	//Specifies a virtual-key code. The code must be a value in the range 1 to 254. 
			public int scanCode; // Specifies a hardware scan code for the key. 
			public int flags;  // Specifies the extended-key flag, event-injected flag, context code, and transition-state flag.
			public int time; // Specifies the time stamp for this message.
			public int dwExtraInfo; // Specifies extra information associated with the message. 
		}

		#endregion

		#region Constants
		
		public const int WM_THEMECHANGED = 0x031A;	
		public const int WM_KEYDOWN =  0x100;
		public const int WM_KEYUP = 0x101;
		public const int WM_SYSKEYDOWN	= 0x104;
		public const int WM_SYSKEYUP = 0x105;
		public const int WH_KEYBOARD_LL = 13;	//keyboard hook constant

		#endregion

		#region Win32 API
    
		// Declare functions used in uxTheme.dll and ComCtl32.dll

		[DllImport("uxTheme.dll", EntryPoint="GetThemeColor", ExactSpelling=true, PreserveSig=false, CharSet=CharSet.Unicode )]
		public extern static void GetThemeColor (System.IntPtr hTheme,
			int partID,
			int stateID,
			int propID,
			out int color);

		[DllImport( "uxtheme.dll", CharSet=CharSet.Unicode )]
		public static extern IntPtr OpenThemeData( IntPtr hwnd, string classes );
		
		[DllImport( "uxtheme.dll", EntryPoint="CloseThemeData", ExactSpelling=true, PreserveSig=false, CharSet=CharSet.Unicode) ]
		public static extern int CloseThemeData( IntPtr hwnd );

		[DllImport("uxtheme.dll", EntryPoint="GetWindowTheme", ExactSpelling=true,PreserveSig=false, CharSet=CharSet.Unicode)]
		public static extern int GetWindowTheme(IntPtr hWnd);
		
		[DllImport("uxtheme.dll", EntryPoint="IsThemeActive", ExactSpelling=true,PreserveSig=false, CharSet=CharSet.Unicode)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool IsThemeActive();
		
		[DllImport("Comctl32.dll", EntryPoint="DllGetVersion", ExactSpelling=true,PreserveSig=false, CharSet=CharSet.Unicode)]
		public static extern int DllGetVersion(ref DLLVERSIONINFO s);
		
		// calls needed for globalhook..

		[DllImport("user32.dll",CharSet=CharSet.Auto, CallingConvention=CallingConvention.StdCall)]
		public static extern int SetWindowsHookEx(int idHook, HookProc lpfn,IntPtr hInstance, int threadId);
		
		[DllImport("user32.dll",CharSet=CharSet.Auto, CallingConvention=CallingConvention.StdCall)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool UnhookWindowsHookEx(int idHook);
		
		[DllImport("user32.dll",CharSet=CharSet.Auto, CallingConvention=CallingConvention.StdCall)]
		public static extern int CallNextHookEx(int idHook, int nCode,Int32 wParam, IntPtr lParam);  
		
		[DllImport("user32.dll")]
		public static extern int GetKeyboardState(byte [] lpKeyState);
		
		[DllImport("user32")] 
		public static extern int ToAscii(int uVirtKey,int uScanCode,byte[] lpbKeyState,byte[] lpwTransKey,int fuState);  

		
		#endregion

	}
}
