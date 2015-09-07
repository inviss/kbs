using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace D2net.Common
{
    public partial class GradiantProgressBar : UserControl
    {

#region Variables
        private Color _ProgressBarMainColor;
        private Color _ProgressBarMainGradianColor;
        private Color _ProgressBarBoderColor;
        private int _ProgressBarHeight;
        private int _Percent;
        private string _DisplayText;
        private bool _DisplayTextEnable;
#endregion
#region Constructor
        public GradiantProgressBar()
        {
            InitializeComponent();
        }
#endregion
#region Property
        [Browsable(true)]
        public Color ProgressBarMainColor
        {
            get { return _ProgressBarMainColor; }
            set { _ProgressBarMainColor = value; }
        }
        [Browsable(true)]
        public Color ProgressBarMainGradianColor
        {
            get { return _ProgressBarMainGradianColor; }
            set { _ProgressBarMainGradianColor = value; }
        }
        [Browsable(true)]
        public Color ProgressBarBoderColor
        {
            get { return _ProgressBarBoderColor; }
            set { _ProgressBarBoderColor = value; }
        }
        [Browsable(true)]
        public int ProgressBarHeight
        {
            get { return _ProgressBarHeight; }
            set { _ProgressBarHeight = value; }
        }
        [Browsable(true)]
        public string DisplayText
        {
            get { return _DisplayText; }
            set { _DisplayText = value; }
        }
        [Browsable(true)]
        public bool DisplayTextEnable
        {
            get { return _DisplayTextEnable; }
            set 
            {
                _DisplayTextEnable = value;
                Invalidate();
            }
        }

        public int Percent
         {
             get { return _Percent; }
             set 
             {
                 _Percent = value;
                 Invalidate();
             }
         }
        public override Font Font
         {
             get
             {
                 return base.Font;
             }
             set
             {
                 base.Font = value;
             }
         }
        public override Color BackColor
        {
            get
            {
                return base.BackColor;
            }
            set
            {
                base.BackColor = value;
            }
        }
#endregion
#region Event
        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);

            SizeF FontSize = e.Graphics.MeasureString(string.Format("{0}{1}",Percent, _DisplayText), Font);
            int ProgressWidth = (int)(e.ClipRectangle.Width - FontSize.Width - 2);
            int ProgressHeight = (e.ClipRectangle.Height >= _ProgressBarHeight)? _ProgressBarHeight : e.ClipRectangle.Height;
            int ProgressCenterY = 0;
            int posX = (int)(e.ClipRectangle.Width - FontSize.Width);
            int posY = (int)((e.ClipRectangle.Height / 2) - FontSize.Height / 2);

            float percentage = ((float)Percent / 100.0f);
            if (e.ClipRectangle.Height >= _ProgressBarHeight)
                ProgressCenterY = (e.ClipRectangle.Height / 2) - (_ProgressBarHeight / 2);

            //e.Graphics.FillRectangle(new SolidBrush(BackColor), e.ClipRectangle);
            e.Graphics.DrawRectangle(new Pen(_ProgressBarBoderColor), 0, ProgressCenterY, ProgressWidth, ProgressHeight);
            e.Graphics.FillRectangle(new SolidBrush(_ProgressBarMainColor), 0, ProgressCenterY, Convert.ToInt32((percentage * ProgressWidth)), ProgressHeight / 2);
            e.Graphics.FillRectangle(new SolidBrush(_ProgressBarMainGradianColor), 0, ProgressCenterY + ProgressHeight / 2, Convert.ToInt32((percentage * ProgressWidth)), ProgressHeight / 2);

            if(_DisplayTextEnable)
                e.Graphics.DrawString(string.Format("{0}{1}",Percent, _DisplayText), Font, new SolidBrush(ForeColor), posX, posY);
            else
                e.Graphics.DrawString(string.Format("{0}{1}", Percent, _DisplayText), Font, new SolidBrush(Color.DimGray), posX, posY);
        }
#endregion
    }
}
