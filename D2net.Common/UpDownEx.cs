using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// UpDownEx에 대한 요약 설명입니다.
	/// </summary>
	public class UpDownEx : System.Windows.Forms.UserControl
	{
        private System.Windows.Forms.TextBox txtEdit;
        private System.Windows.Forms.Label lblUpDownButton;
        private BorderStyle _BorderStyle = BorderStyle.FixedSingle;
        private int _BorderSize = 2;

        private Brush ArrowBrush = new SolidBrush(SystemColors.ControlText);
        private Brush DropButtonBrush = new SolidBrush(SystemColors.Control);
        private Color _BackColor = SystemColors.Window;
        private Color _ButtonBackColor = SystemColors.Control;
        private Pen _ButtonBorderLight1 = null;
        private Pen _ButtonBorderLight2 = null;
        private Pen _ButtonBorderDark1 = null;
        private Pen _ButtonBorderDark2 = null;
        private Pen _CtrlBorderLight1 = null;
        private Pen _CtrlBorderLight2 = null;
        private Pen _CtrlBorderDark1 = null;
        private Pen _CtrlBorderDark2 = null;

        private Rectangle _ButtonArea = new Rectangle(0, 0, 0, 0);
        private Rectangle[] _UpDownButtonArea = new Rectangle[2];
        private Bitmap[] _ButtonImages = new Bitmap[3];
        private Bitmap _DrawingPanel = null;

        private UpDownMode _UpDownMode = UpDownMode.Numeric;

        private decimal _Min = 0;
        private decimal _Max = 100;
        private decimal _Value = 0;
        private decimal _Increment = 1;

        public event EventHandler ValueChanged = null;

		/// <summary> 
		/// 필수 디자이너 변수입니다.
		/// </summary>
		private System.ComponentModel.Container components = null;

        #region Properties
        [DefaultValue(BorderStyle.FixedSingle)]
        public BorderStyle BorderStyle
        {
            get { return _BorderStyle; }
            set
            {
                _BorderStyle = value;

                switch (_BorderStyle)
                {
                    case BorderStyle.FixedSingle:   _BorderSize = 1; break;
                    case BorderStyle.None:          _BorderSize = 0; break;
                    default:                        _BorderSize = 2; break;
                }

                RecalcBounds();
                Invalidate();
            }
        }

        public override Color BackColor
        {
            get { return _BackColor; }
            set
            {
                _BackColor = value;
                RecalcBorderColor();
                txtEdit.BackColor = value;
                base.BackColor = value;
            }
        }

        [DefaultValue(typeof(Color), "SystemColors.WindowText")]
        public override Color ForeColor
        {
            get { return txtEdit.ForeColor; }
            set { txtEdit.ForeColor = value; }
        }

        [DefaultValue(typeof(Color), "SystemColors.Control")]
        public Color ButtonBackColor
        {
            get { return _ButtonBackColor; }
            set
            {
                _ButtonBackColor = value;
                DropButtonBrush = new SolidBrush(_ButtonBackColor);
                ArrowBrush = new SolidBrush(Color.FromArgb(
                    255 - _ButtonBackColor.R,
                    255 - _ButtonBackColor.G,
                    255 - _ButtonBackColor.B));
                RecalcBorderColor();
                RemakeButtonImages();
                Invalidate();
            }    
        }

        [DefaultValue(HorizontalAlignment.Center)]
        public HorizontalAlignment TextAlign
        {
            get { return txtEdit.TextAlign; }
            set { txtEdit.TextAlign = value; }
        }

        [DefaultValue(UpDownMode.Numeric)]
        public UpDownMode UpDownMode
        {
            get { return _UpDownMode; }
            set
            {
                if (value == UpDownMode.Numeric)
                    txtEdit.Text = "0";
                else
                    txtEdit.Text = "";
                _UpDownMode = value;
            }
        }

        [DefaultValue(0)]
        public decimal Minimum
        {
            get { return _Min; }
            set
            {
                if (value > _Max)
                    throw new Exception("최소값이 최대값보다 큽니다.");
                if (Value < value)
                    Value = value;
                _Min = value;
            }
        }

        [DefaultValue(100)]
        public decimal Maximum
        {
            get { return _Max; }
            set
            {
                if (value < _Min)
                    throw new Exception("최대값이 최소값보다 작습니다.");
                if (Value > value)
                    Value = value;
                _Max = value;
            }
        }

        [DefaultValue(0)]
        public decimal Value
        {
            get { return _Value; }
            set
            {
                if (value < _Min || 
                    value > _Max)
                    throw new Exception("설정된 값은 최소/최대 값을 벗어났습니다.");
                _Value = value;
                txtEdit.Text = _Value.ToString();
                if (ValueChanged != null)
                    ValueChanged(this, new EventArgs());
            }
        }

        [DefaultValue(1)]
        public decimal Increment
        {
            get { return _Increment; }
            set { _Increment = value; }
        }
        #endregion

		public UpDownEx()
		{
			// 이 호출은 Windows.Forms Form 디자이너에 필요합니다.
			InitializeComponent();

            RecalcBorderColor();
            RecalcBounds();

            lblUpDownButton.MouseDown += new MouseEventHandler(lblUpDownButton_MouseDown);
            lblUpDownButton.MouseUp += new MouseEventHandler(lblUpDownButton_MouseUp);

            txtEdit.KeyPress += new KeyPressEventHandler(txtEdit_KeyPress);
            txtEdit.Enter += new EventHandler(txtEdit_Enter);
            txtEdit.Leave += new EventHandler(txtEdit_Leave);
            txtEdit.TextChanged += new EventHandler(txtEdit_TextChanged);

            if (_UpDownMode == UpDownMode.Numeric)
                txtEdit.Text = _Value.ToString();
            else
                txtEdit.Text = "";
		}

		/// <summary> 
		/// 사용 중인 모든 리소스를 정리합니다.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

        private void RecalcBorderColor()
        {
            int Gain = 0;

            switch (_BorderStyle)
            {
                case BorderStyle.FixedSingle:   Gain = 50; break;
                case BorderStyle.None:          Gain = 0; break;
                default:                        Gain = 30; break;
            }

            if (_ButtonBorderLight1 != null)
                _ButtonBorderLight1.Dispose();
            ResetBorderPen(ref _ButtonBorderLight1, _ButtonBackColor, Gain);
            if (_ButtonBorderLight2 != null)
                _ButtonBorderLight2.Dispose();
            ResetBorderPen(ref _ButtonBorderLight2, _ButtonBackColor, 2 * Gain);
            if (_ButtonBorderDark1 != null)
                _ButtonBorderDark1.Dispose();
            ResetBorderPen(ref _ButtonBorderDark1, _ButtonBackColor, -Gain);
            if (_ButtonBorderDark2 != null)
                _ButtonBorderDark2.Dispose();
            ResetBorderPen(ref _ButtonBorderDark2, _ButtonBackColor, -2 * Gain);

            if (_CtrlBorderLight1 != null)
                _CtrlBorderLight1.Dispose();
            ResetBorderPen(ref _CtrlBorderLight1, _BackColor, Gain);
            if (_CtrlBorderLight2 != null)
                _CtrlBorderLight2.Dispose();
            ResetBorderPen(ref _CtrlBorderLight2, _BackColor, 2 * Gain);
            if (_CtrlBorderDark1 != null)
                _CtrlBorderDark1.Dispose();
            ResetBorderPen(ref _CtrlBorderDark1, _BackColor, -Gain);
            if (_CtrlBorderDark2 != null)
                _CtrlBorderDark2.Dispose();
            ResetBorderPen(ref _CtrlBorderDark2, _BackColor, -2 * Gain);
        }

        private void ResetBorderPen(ref Pen pen, Color col, int gain)
        {
            int r, g, b;
            
            r = col.R + gain;
            g = col.G + gain;
            b = col.B + gain;

            r = r < 0 ? 0 : r;
            r = r > 255 ? 255 : r;
            g = g < 0 ? 0 : g;
            g = g > 255 ? 255 : g;
            b = b < 0 ? 0 : b;
            b = b > 255 ? 255 : b;

            pen = new Pen(Color.FromArgb(r, g, b));
        }

        private void RecalcBounds()
        {
            Rectangle rt = ClientRectangle;
            rt.Inflate(-_BorderSize, -_BorderSize);
            txtEdit.Width = rt.Width - 15 - 2 * _BorderSize;
            txtEdit.Location = new Point(rt.Left + _BorderSize, (Height - txtEdit.Bounds.Height) / 2);
            _ButtonArea = new Rectangle(rt.Right - 15, _BorderSize, 15, rt.Height);
            _UpDownButtonArea[0] = new Rectangle(0, 0, 15, _ButtonArea.Height / 2);
            _UpDownButtonArea[1] = new Rectangle(0, _ButtonArea.Height / 2, 15, _ButtonArea.Height / 2);
            lblUpDownButton.Bounds = _ButtonArea;
            RemakeButtonImages();

            // _ButtonArea = new Rectangle(Width - 15, 3, 12, Height - 6);
            Bitmap bmp = _DrawingPanel;
            _DrawingPanel = new Bitmap(Width, Height);
            if (bmp != null)
                bmp.Dispose();
        }

        private void RemakeButtonImages()
        {
            lblUpDownButton.Image = null;

            CreateButtonImage(ref _ButtonImages[0], false, false);
            CreateButtonImage(ref _ButtonImages[1], true, false);
            CreateButtonImage(ref _ButtonImages[2], false, true);

            lblUpDownButton.Image = _ButtonImages[0];
        }

        private void CreateButtonImage(ref Bitmap bmp, bool UpClick, bool DownClick)
        {
            if (bmp != null)
                bmp.Dispose();
            bmp = null;
            bmp = new Bitmap(_ButtonArea.Width, _ButtonArea.Height);
            Graphics g = Graphics.FromImage(bmp);
            g.Clear(BackColor);
            DrawButtonImage(g, _UpDownButtonArea[0], true, UpClick);
            DrawButtonImage(g, _UpDownButtonArea[1], false, DownClick);
        }

        private void DrawButtonImage(Graphics g, Rectangle r, bool UpButton, bool Click)
        {
            g.FillRectangle(DropButtonBrush, r);
            DrawComboButtonLine(g, r, UpButton, Click);
        }

        private void DrawCtrl(Graphics g)
        {
            g.Clear(_BackColor);
            DrawComboTextLine(this.ClientRectangle, g);
        }

        protected void DrawComboTextLine (Rectangle rect, Graphics g)
        {
            switch (_BorderStyle)
            {
                case BorderStyle.Fixed3D:
                    g.DrawLine(_CtrlBorderLight2, 1, rect.Height - 2, rect.Width - 2, rect.Height - 2); 
                    g.DrawLine(_CtrlBorderLight2, rect.Width - 2, 1, rect.Width - 2, rect.Height - 2);
                    g.DrawLine(_CtrlBorderDark2, 1, 1, rect.Width - 2, 1); 
                    g.DrawLine(_CtrlBorderDark2, 1, 1, 1, rect.Height - 2);
                    g.DrawLine(_CtrlBorderLight1, 0, rect.Height - 1, rect.Width, rect.Height - 1); 
                    g.DrawLine(_CtrlBorderLight1, rect.Width - 1, 0, rect.Width - 1, rect.Height);
                    g.DrawLine(_CtrlBorderDark1, 0, 0, rect.Width, 0); 
                    g.DrawLine(_CtrlBorderDark1, 0, 0, 0, rect.Height);
                    break;
                case BorderStyle.FixedSingle:
                    g.DrawLine(_CtrlBorderLight1, 0, rect.Height - 1, rect.Width, rect.Height - 1); 
                    g.DrawLine(_CtrlBorderLight1, rect.Width - 1, 0, rect.Width - 1, rect.Height);
                    g.DrawLine(_CtrlBorderDark1, 0, 0, rect.Width, 0); 
                    g.DrawLine(_CtrlBorderDark1, 0, 0, 0, rect.Height);
                    break;
                default:
                    break;
            }
        }

        private void DrawComboButtonLine(Graphics g, Rectangle rect, bool UpButton, bool Click)
        {
            Pen bp11 = null;
            Pen bp12 = null;
            Pen bp21 = null;
            Pen bp22 = null;
            PointF TopLeft;
            PointF TopRight;
            PointF Bottom;

            if (Click)
            {               
                bp12 = _ButtonBorderDark1;
                bp11 = _ButtonBorderLight1;
                bp22 = _ButtonBorderDark2;
                bp21 = _ButtonBorderLight2;
            }
            else 
            {
                bp12 = _ButtonBorderLight1;
                bp11 = _ButtonBorderDark1;
                bp22 = _ButtonBorderLight2;
                bp21 = _ButtonBorderDark2;
            }

            switch (_BorderStyle)
            {
                case BorderStyle.Fixed3D:
                    g.DrawLine(bp21, 1, rect.Bottom - 2, rect.Width - 2, rect.Bottom - 2); 
                    g.DrawLine(bp21, rect.Width - 2, rect.Top + 1, rect.Width - 2, rect.Bottom - 2);
                    g.DrawLine(bp22, 1, rect.Top + 1, rect.Width - 2, rect.Top + 1); 
                    g.DrawLine(bp22, 1, rect.Top + 1, 1, rect.Bottom - 2);
                    g.DrawLine(bp11, 0, rect.Bottom - 1, rect.Width, rect.Bottom - 1); 
                    g.DrawLine(bp11, rect.Width - 1, rect.Top, rect.Width - 1, rect.Bottom);
                    g.DrawLine(bp12, 0, rect.Top, rect.Width, rect.Top); 
                    g.DrawLine(bp12, 0, rect.Top, 0, rect.Bottom);
                    break;
                case BorderStyle.FixedSingle:
                    g.DrawLine(bp11, 0, rect.Bottom - 1, rect.Width, rect.Bottom - 1); 
                    g.DrawLine(bp11, rect.Width - 1, rect.Top, rect.Width - 1, rect.Bottom);
                    g.DrawLine(bp12, 0, rect.Top, rect.Width, rect.Top); 
                    g.DrawLine(bp12, 0, rect.Top, 0, rect.Bottom);
                    break;
                default:
                    break;
            }

            GraphicsPath pth = new GraphicsPath();

            if (UpButton)
            {
                TopLeft = new PointF(rect.Width / 2 - 2, rect.Top + rect.Height / 2 + 1);
                TopRight = new PointF(rect.Width / 2 + 4, rect.Top + rect.Height / 2 + 1);
                Bottom = new PointF(rect.Width / 2 + 1, rect.Top + rect.Height / 2 - 2);
            }
            else
            {
                TopLeft = new PointF(rect.Width / 2 - 2, rect.Top + rect.Height / 2 - 2);
                TopRight = new PointF(rect.Width / 2 + 4, rect.Top + rect.Height / 2 - 2);
                Bottom = new PointF(rect.Width / 2 + 1, rect.Top + rect.Height / 2 + 1);
            }

            pth.AddLine(TopLeft, TopRight);
            pth.AddLine(TopRight, Bottom);
            g.SmoothingMode = SmoothingMode.HighQuality;
                    
            g.FillPath(ArrowBrush, pth);
        }

        #region 구성 요소 디자이너에서 생성한 코드
		/// <summary> 
		/// 디자이너 지원에 필요한 메서드입니다. 
		/// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
		/// </summary>
		private void InitializeComponent()
		{
            this.txtEdit = new System.Windows.Forms.TextBox();
            this.lblUpDownButton = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // txtEdit
            // 
            this.txtEdit.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.txtEdit.Location = new System.Drawing.Point(8, 8);
            this.txtEdit.Name = "txtEdit";
            this.txtEdit.TabIndex = 0;
            this.txtEdit.Text = "";
            // 
            // lblUpDownButton
            // 
            this.lblUpDownButton.BackColor = System.Drawing.SystemColors.Control;
            this.lblUpDownButton.Location = new System.Drawing.Point(208, 8);
            this.lblUpDownButton.Name = "lblUpDownButton";
            this.lblUpDownButton.Size = new System.Drawing.Size(16, 23);
            this.lblUpDownButton.TabIndex = 1;
            // 
            // UpDownEx
            // 
            this.Controls.Add(this.lblUpDownButton);
            this.Controls.Add(this.txtEdit);
            this.Name = "UpDownEx";
            this.Size = new System.Drawing.Size(232, 24);
            this.ResumeLayout(false);

        }
		#endregion

        protected override void WndProc(ref Message m)
        {
            base.WndProc (ref m);                      

            switch (m.Msg)
            {
                case 0xF: // WM_PAINT
                {
                    Graphics g, gimg;

                    g  = this.CreateGraphics(); 
                    gimg = Graphics.FromImage(_DrawingPanel);
                    DrawCtrl(gimg);

                    g.DrawImage(_DrawingPanel, 0, 0);
                }
                    break;
            }
        }

        protected override void OnSizeChanged(EventArgs e)
        {
            base.OnSizeChanged (e);

            RecalcBorderColor();
            RecalcBounds();
        }

        protected void lblUpDownButton_MouseDown(object sender, MouseEventArgs e)
        {
            lblUpDownButton.Capture = true;
            decimal val = 0;

            Point pt = new Point(e.X, e.Y);
            val = _Value;
            if (_UpDownButtonArea[0].Contains(pt))
            {
                lblUpDownButton.Image = _ButtonImages[1];
                val += _Increment;
            }
            else
            {
                lblUpDownButton.Image = _ButtonImages[2];
                val -= _Increment;
            }

            if (val < _Min || 
                val > _Max)
                return;
            Value = val;
            if (ValueChanged != null)
                ValueChanged(this, new EventArgs());
        }

        protected void lblUpDownButton_MouseUp(object sender, MouseEventArgs e)
        {
            lblUpDownButton.Image = _ButtonImages[0];
            lblUpDownButton.Capture = false;
        }

        protected void txtEdit_KeyPress(object sender, KeyPressEventArgs e)
        {
            int s = 0, end = 0;
            string newVal = "";
            int newValInt = 0;

            if (_UpDownMode == UpDownMode.Domain)
                return;

            if (e.KeyChar == (char)13 /* Enter Key */)
            {
                if (txtEdit.Text == "")
                {
                    if (_Min > 0)
                        _Value = _Min;
                    else
                        _Value = 0;
                    txtEdit.Text = _Value.ToString();
                }
                else
                {
                    try
                    {
                        _Value = Convert.ToInt32(txtEdit.Text);
                    }
                    catch (Exception ex1)
                    {
                        if (_Min > 0)
                            _Value = _Min;
                        else
                            _Value = 0;
                        txtEdit.Text = _Value.ToString();
                    }
                }

                if (_Value < _Min)
                {
                    _Value = _Min;
                    txtEdit.Text = _Value.ToString();
                }
                else if (_Value > _Max)
                {
                    _Value = _Max;
                    txtEdit.Text = _Value.ToString();
                }

                if (ValueChanged != null)
                    ValueChanged(this, new EventArgs());
                return;
            }

            // [2006-09-21] -> Back Space 값이 적용되지 않는 버그 - FIX
            if ((e.KeyChar < '0' || 
                e.KeyChar > '9') &&
                e.KeyChar != '-' &&
                e.KeyChar != (char)8 /* Back Space */ && 
                e.KeyChar != (char)16)
            {
                e.Handled = true;
                return;
            }

            /*
            s = txtEdit.SelectionStart;
            end = s + txtEdit.SelectionLength;

            newVal = txtEdit.Text;
            newVal = newVal.Substring(0, s) + e.KeyChar.ToString() + newVal.Substring(end, newVal.Length - end);
            
            if (newVal == "-")
                return;
            
            try
            {
                newValInt = Convert.ToInt32(newVal);

                if (newValInt < this._Min || 
                    newValInt > this._Max)
                {
                    e.Handled = true;
                    return;
                }
            }
            catch (Exception ex)
            {
                e.Handled = true;
                return;
            }
            */
        }

        protected void txtEdit_Enter(object sender, EventArgs e)
        {
        }

        protected void txtEdit_Leave(object sender, EventArgs e)
        {
            try
			{
				string val = txtEdit.Text;
				_Value = val == "" ? 0 : Convert.ToInt32(val);
			}
			catch (Exception ex)
			{
				_Value = 0;
			}

			if (ValueChanged != null)
				ValueChanged(this, new EventArgs());
        }

        // [2006-09-21] -> 텍스트 박스 값을 변경하여도 내부 값이 변경되지 않는 버그 - FIX
        protected void txtEdit_TextChanged(object sender, EventArgs e)
        {
            if (this._UpDownMode != UpDownMode.Domain)
                return;

            string val = txtEdit.Text;
            if (val == "")
                _Value = 0;
            else
                _Value = Convert.ToInt32(txtEdit.Text);
            if (ValueChanged != null)
                ValueChanged(this, new EventArgs());
        }
	}

    public enum UpDownMode : int
    {
        Domain,
        Numeric,
    }
}
