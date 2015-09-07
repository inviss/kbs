using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using D2net.Common;

namespace D2net.Common.UI
{
	/// <summary>
	/// TimeCodeEditor에 대한 요약 설명입니다.
	/// </summary>
    public class TCEditor : D2net.Common.UI.TextBoxEx
    {
        private TCMode _TCMode = TCMode.TimeCode;
        private TCFormat _TCFormat = TCFormat.NTSC_Drop;
        private TimeCode _Value = null;
		private bool _ReadOnly = false;
		private Color _ForeColor = Color.Black;

        public TCEditor()
        {
            InitializeComponent();

            _Value = TimeCode.CreateTimeCode(_TCFormat, _TCMode, 0);
            base.Text = _TCMode == TCMode.CTL ? "+0:00:00:00" : "00:00:00:00";
            TextAlign = HorizontalAlignment.Center;
        }

		public TCEditor(TCMode _TCMode, TCFormat _TCFormat)
		{
			InitializeComponent();

			this._TCMode = _TCMode;
			this._TCFormat = _TCFormat;
			_Value = TimeCode.CreateTimeCode(_TCFormat, _TCMode, 0);
			SetWindowText(Handle, _TCMode == TCMode.CTL ? "+0:00:00:00" : "00:00:00:00");
			TextAlign = HorizontalAlignment.Center;
		}

        private void InitializeComponent()
        {
            // 
            // TCEditor
            // 
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.TCEditor_KeyDown);
            this.Resize += new EventHandler(TCEditor_Resize);
        }

        void TCEditor_Resize(object sender, EventArgs e)
        {
            //this.Font = new Font(this.Font.FontFamily, 21);
            //System.Diagnostics.Debug.WriteLine(this.Size.Height.ToString());
            //if (this.Size.Height > 30 || this.Size.Height <= 0)
            //{
            //    this.Size = new Size(this.Size.Width, 30);
            //}
            //this.Font = new Font(this.Font.FontFamily, this.Size.Height-2);
            
            //System.Diagnostics.Debug.WriteLine(this.Size.Height.ToString());
        }

		public new bool ReadOnly
		{
			get { return _ReadOnly; }
			set
			{
				_ReadOnly = value;
				base.ForeColor = _ReadOnly ? SystemColors.ControlDarkDark : _ForeColor;
			}
		}

		public new Color ForeColor
		{
			get { return base.ForeColor; }
			set
			{
				_ForeColor = value;
				base.ForeColor = value;
			}
		}

        public TCMode TimecodeMode
        {
            get { return _TCMode; }
            set
            {
                _TCMode = value;
                _Value = TimeCode.CreateTimeCode(_TCFormat, _TCMode, 0);
                SetWindowText(Handle, _Value.TC);
            }
        }

        public TCFormat TimecodeFormat
        {
            get{ return _TCFormat; }
            set
            {
                _TCFormat = value;
                _Value = TimeCode.CreateTimeCode(_TCFormat, _TCMode, 0);
                SetWindowText(Handle, _Value.TC);
            }
        }

        [Browsable(false)]
        public TimeCode Value
        {
            get { return _Value; }
            set
            {
                if (_Value.TimeCodeFormat != value.TimeCodeFormat ||
                    _Value.TimeCodeMode != value.TimeCodeMode)
                    throw new Exception("타임코드 포멧이나 모드가 맞지 않습니다");
                _Value = value;
                SetWindowText(Handle, _Value.TC);
            }
        }

        [Browsable(false)]
        public Int64 Frame
        {
            get { return _Value.Frame; }
            set
            {
                _Value.Frame = value;
                SetWindowText(Handle, _Value.TC);
            }
        }

        [Browsable(false)]
        public override string Text
        {
            get { return _Value.TC; }
            set
            {
                _Value.TC = value;
                SetWindowText(Handle, _Value.TC);
            }
        }
        
        protected override void WndProc(ref Message m)
        {
            switch (m.Msg)
            {
                case 0x0102:
                    if (TCEditor_KeyPress(ref m))
                        return;
                    break;

                case 0x0201: /* LButtonDown */
                    {
                        base.WndProc(ref m);
                        int nStartChar = SelectionStart;
                        if (nStartChar == 2 || 
                            nStartChar == 5 ||
                            nStartChar == 8)
                            Select(nStartChar + 1, 0);
                    }
                    return;
                case 0x0204: /* RButtonDown */
                    return;
            }

            base.WndProc (ref m);
        }

        private void TCEditor_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
        {
			int nStartChar;

            switch (e.KeyCode)
            {
                case Keys.Left :
                case Keys.Up :
                    nStartChar = this.SelectionStart;
                    if (nStartChar < 1)
                        return;
                    if (nStartChar == 3 || 
                        nStartChar == 6 || 
                        nStartChar == 9)
                        Select(nStartChar - 2, 0);
                    else
                        Select(nStartChar - 1, 0);
                    e.Handled = true;
                    break;
                case Keys.Right :
                case Keys.Down :
                    nStartChar = SelectionStart;
                    if (nStartChar > Text.Length - 1)
                        return;
                    if (nStartChar == 1 || 
                        nStartChar == 4 || 
                        nStartChar == 7)
                        Select(nStartChar + 2, 0);
                    else 
                        Select(nStartChar + 1, 0);
                    e.Handled = true;
                    break;
            }
        }

        private bool TCEditor_KeyPress(ref Message m)
        {
            Point pt = new Point(0, 0);
            int lpt = 0;
            int nCurPos = 0;
            int nChar = (int)m.WParam;
            char[] datas = null;
            string text;
            TimeCode tc = null;

			if (_ReadOnly)
				return true;

            try
            {
                GetCaretPos(ref pt);
                lpt = pt.Y << 16 | pt.X;
                nCurPos = (int)SendMessage(m.HWnd, 0x00D7, 0, lpt);
                // System.Diagnostics.Debug.WriteLine(pt.ToString() + "(" + Convert.ToString(lpt, 16) + ") -> " + nCurPos.ToString());
    
                if (nCurPos == 2 ||
                    nCurPos == 5 ||
                    nCurPos == 8)
                    return true;
    
                // 유효한 문자만 받아들임.
                if (nCurPos == 0)
                {
                    if ((nChar != '+' &&
                        nChar != '-') &&
                        (nChar < '0' ||
                        nChar > '9'))
                        return true;
                }
                else
                {
                    if (nChar < '0' ||
                        nChar > '9')
                        return true;
                }
    
                if (nCurPos >= Text.Length)
                    return true;
    
                datas = Text.ToCharArray();
                datas[nCurPos] = (char)nChar;
                text = new string(datas);            
    
                try
                {
                    tc = TimeCode.CreateTimeCode(TimecodeFormat, TimecodeMode, text);
                    tc.CheckTimecode();
    				_Value = tc;
                    SetWindowText(m.HWnd, tc.TC);
    
                    if ((nCurPos + 2 < text.Length) &&
                        (text[nCurPos + 2] < '0' ||
                        text[nCurPos + 2] < '9'))
                        Select(nCurPos + 2, 0);
                    else
                        Select(nCurPos + 1, 0);
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine(ex.Message);
                }
            }
            catch (Exception exo)
            {
                System.Diagnostics.Debug.WriteLine(exo.Message);
            }

            return true;
        }

        [DllImport("User32.Dll")]
        public static extern void SetWindowText(IntPtr hWnd, String s);

        [DllImport("User32.Dll")]
        private static extern bool GetCaretPos(ref Point pos);

        [DllImport("User32.Dll")]
        private static extern uint SendMessage(IntPtr hWnd, uint Msg, uint wParam, int lParam);
        
	}
}
