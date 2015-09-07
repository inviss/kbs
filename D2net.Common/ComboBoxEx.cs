using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// ComboBoxEx에 대한 요약 설명입니다.
	/// </summary>
	public class ComboBoxEx : ComboBox
	{        
        private Brush BorderBrush = new SolidBrush(SystemColors.Control);        
        private Brush ArrowBrush = new SolidBrush(SystemColors.ControlText);
        private Brush DropButtonBrush = new SolidBrush(SystemColors.Control);
        private Color _BackColor = Color.White;
        private Color _ButtonBackColor = SystemColors.Control;
        private Pen _ButtonBorderLight = null;
        private Pen _ButtonBorderDark = null;
        private Pen _CtrlBorderLight = null;
        private Pen _CtrlBorderDark = null;

        private bool bUp = false;

        private Rectangle _ButtonArea = new Rectangle(0, 0, 0, 0);
        private RectangleF _TextArea = new RectangleF(0, 0, 0, 0);
        private StringFormat _DateTimeDisFormat = new StringFormat();
        private Bitmap _DrawingPanel = null; 

        public ComboBoxEx()
		{
             RecalcBorderColor();

            _DateTimeDisFormat.Alignment = StringAlignment.Near;
            _DateTimeDisFormat.LineAlignment = StringAlignment.Near;
		}

        public new Color BackColor
        {
            get { return _BackColor; }
            set
            {                
                _BackColor = value;
                BorderBrush = new SolidBrush(_BackColor); 
                RecalcBorderColor();
                base.BackColor = _BackColor;
            }
        }

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
                Invalidate();
            }    
        }
        
        #region Drawing
        private void RecalcBounds()
        {
            _ButtonArea = new Rectangle(Width - 15, 3, 12, Height - 6);
            _TextArea = new Rectangle(3, 3, Width - 6, Height - 6);
            Bitmap bmp = _DrawingPanel;
            _DrawingPanel = new Bitmap(Width, Height);
            if (bmp != null)
                bmp.Dispose();
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

        private void RecalcBorderColor()
        {
            if (_ButtonBorderLight != null)
                _ButtonBorderLight.Dispose();
            ResetBorderPen(ref _ButtonBorderLight, _ButtonBackColor, 50);
            if (_ButtonBorderDark != null)
                _ButtonBorderDark.Dispose();
            ResetBorderPen(ref _ButtonBorderDark, _ButtonBackColor, -50);

            if (_CtrlBorderLight != null)
                _CtrlBorderLight.Dispose();
            ResetBorderPen(ref _CtrlBorderLight, _BackColor, 50);
            if (_CtrlBorderDark != null)
                _CtrlBorderDark.Dispose();
            ResetBorderPen(ref _CtrlBorderDark, _BackColor, -50);
        }

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

        private void DrawCtrl(Graphics g)
        {
            g.Clear(BackColor);
            DrawComboTextLine(this.ClientRectangle, g);
            g.FillRectangle(DropButtonBrush, 
                _ButtonArea.X - 2 , 
                _ButtonArea.Y - 2, 
                _ButtonArea.Width + 3, 
                _ButtonArea.Height + 3);

            DrawComboButtonLine (_ButtonArea, g);                    
            GraphicsPath pth = new GraphicsPath();                    
            PointF TopLeft = new PointF(this.Width - 13, (this.Height - 5) / 2);
            PointF TopRight = new PointF(this.Width - 6, (this.Height - 5) / 2);
            PointF Bottom = new PointF(this.Width - 9, (this.Height + 2) / 2);
            pth.AddLine(TopLeft, TopRight);
            pth.AddLine(TopRight, Bottom);
            g.SmoothingMode = SmoothingMode.HighQuality;
                    
            g.FillPath(ArrowBrush, pth);

            // [2006-08-31] -> Selected Item에 대한 텍스트 출력시 선택된 아이템을 표시함.
            if (DropDownStyle == ComboBoxStyle.DropDownList &&
                this.SelectedItem != null)
            {
                g.DrawString(this.SelectedItem.ToString(), 
                    this.Font, 
                    new SolidBrush(ForeColor), 
                    _TextArea, 
                    _DateTimeDisFormat);
            }
        }

        protected void DrawComboTextLine (Rectangle rect, Graphics g)
        {
            g.DrawLine(_CtrlBorderLight, 0, rect.Height - 1, rect.Width, rect.Height - 1); 
            g.DrawLine(_CtrlBorderLight, rect.Width - 1, 0, rect.Width - 1, rect.Height);

            g.DrawLine(_CtrlBorderDark, 0, 0, rect.Width, 0); 
            g.DrawLine(_CtrlBorderDark, 0, 0, 0, rect.Height);
        }

        protected void DrawComboButtonLine (Rectangle rect, Graphics g)
        {
            Pen bp = null;
            Pen bp1 = null;

            if (bUp == false)
            {               
                bp1 = _ButtonBorderDark;
                bp = _ButtonBorderLight;
            }
            else 
            {
                bp1 = _ButtonBorderLight;
                bp = _ButtonBorderDark;
            }

            // top
            g.DrawLine(bp, rect.X - 2 , rect.Y - 2, 
                (rect.X - 2  + rect.Width + 3), rect.Y - 2); 
            // left
            g.DrawLine(bp, rect.X - 2, rect.Y - 2, 
                rect.X - 2, rect.Y - 2 + rect.Height + 3);
            // bottom
            g.DrawLine(bp1, rect.X - 2, rect.Y - 2 + rect.Height + 3,
                (rect.X + 1 + rect.Width), rect.Y - 2 + rect.Height + 3);                    
            // right
            g.DrawLine(bp1, (rect.X + 1 + rect.Width), rect.Y - 2,
                (rect.X + 1 + rect.Width), rect.Y - 2 + rect.Height + 3);
        }

        protected override void OnSizeChanged(EventArgs e)
        {
            base.OnSizeChanged (e);
            RecalcBounds();
            Invalidate();
        }

        protected override void OnMouseUp(MouseEventArgs e)
        {            
            base.OnMouseUp (e); 
            bUp = false;          
            this.Invalidate();
        }

        protected override void OnDropDown(EventArgs e)
        {
            base.OnDropDown (e);
            bUp = true;
            this.Invalidate();
        }
        #endregion
	}
}
