using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace D2net.Common.UI
{
    /// <summary>
    /// TextBoxEx에 대한 요약 설명입니다.
    /// </summary>
    public class TextBoxEx : TextBox
    {
        private Brush BorderBrush = new SolidBrush(SystemColors.Window);
        private Brush ArrowBrush = new SolidBrush(SystemColors.ControlText);
        private Brush DropButtonBrush = new SolidBrush(SystemColors.Control);
        private Color _BackColor = SystemColors.Control;        
        private Pen _CtrlBorderLight = null;
        private Pen _CtrlBorderDark = null;

        public TextBoxEx()
        {
            RecalcBorderColor();
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
                case 0xF:
                    Graphics g = this.CreateGraphics();
                    
                    g.DrawLine(_CtrlBorderLight, 0, ClientRectangle.Height - 1, ClientRectangle.Width, ClientRectangle.Height - 1); 
                    g.DrawLine(_CtrlBorderLight, ClientRectangle.Width - 1, 0, ClientRectangle.Width - 1, ClientRectangle.Height);
                    g.DrawLine(_CtrlBorderDark, 0, 0, ClientRectangle.Width, 0); 
                    g.DrawLine(_CtrlBorderDark, 0, 0, 0, ClientRectangle.Height);

                    // Create the path for the arrow                    
                    g.SmoothingMode = SmoothingMode.HighQuality;
                    break;
            }                 
        }
    }
}
