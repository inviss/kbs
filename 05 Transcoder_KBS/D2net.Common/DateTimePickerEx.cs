using System;
using System.IO;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Data;
using System.Reflection;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Globalization;
using System.Drawing.Design;

namespace D2net.Common.UI
{
	/// <summary>
	/// DateTimePickerEx에 대한 요약 설명입니다.
	/// </summary>
	public class DateTimePickerEx : System.Windows.Forms.Control
	{
        private System.Windows.Forms.Form _CalForm = null;
        private D2net.Common.UI.MonthCalendarEx _Cal = null;
        private DateTime _CurDate;
        
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
        private RectangleF _DateTimeArea = new RectangleF(0, 0, 0, 0);
        private StringFormat _DateTimeDisFormat = new StringFormat();
        private Bitmap _DrawingPanel = null; 

		public DateTimePickerEx()
		{
			// 이 호출은 Windows.Forms Form 디자이너에 필요합니다.
			// InitializeComponent();

            // this.btnPopup.Text = "";

            // RecalcBounds();
            RecalcBorderColor();
            _DateTimeDisFormat.Alignment = StringAlignment.Near;
            _DateTimeDisFormat.LineAlignment = StringAlignment.Center;

            _CalForm = new Form();
            _CalForm.SuspendLayout();

            _CalForm.Size = new System.Drawing.Size(176, 184);
            _CalForm.FormBorderStyle = FormBorderStyle.None;
            _CalForm.TopMost = true;
            _CalForm.ShowInTaskbar = false;
            
            _Cal = new D2net.Common.UI.MonthCalendarEx();
            _CurDate = DateTime.Now;
            _Cal.ActiveMonth.Year = _CurDate.Year;
            _Cal.ActiveMonth.Month = _CurDate.Month;
            _Cal.Dock = DockStyle.Fill;
            _Cal.BorderStyle = ButtonBorderStyle.Solid;
            _Cal.MaxDate = new System.DateTime(_CurDate.Year + 10, 
                _CurDate.Month, 
                _CurDate.Day, 
                _CurDate.Hour, 
                _CurDate.Minute, 
                _CurDate.Second, 
                _CurDate.Millisecond);
            _Cal.MinDate = new System.DateTime(_CurDate.Year - 10, 
                _CurDate.Month, 
                _CurDate.Day, 
                _CurDate.Hour, 
                _CurDate.Minute, 
                _CurDate.Second, 
                _CurDate.Millisecond);
            _Cal.BorderColor = System.Drawing.SystemColors.ActiveBorder;
            _Cal.SelectionMode = mcSelectionMode.One;
            _Cal.SelectTrailingDates = true;

            _Cal.MouseDown += new MouseEventHandler(_Cal_MouseDown);
            _Cal.MouseUp += new MouseEventHandler(_Cal_MouseUp);
            _Cal.LostFocus += new EventHandler(_Cal_LostFocus);
            _Cal.DayClick += new DayClickEventHandler(_Cal_DayClick);
            // [2006-02-14] -> 달이 바뀔때도 MonthChanged Event 발생
            _Cal.MonthChanged += new MonthChangedEventHandler(_Cal_MonthChanged);

            _CalForm.Controls.Add(_Cal);
            _CalForm.ResumeLayout(false);

            // Assembly myAssembly = System.Reflection.Assembly.GetExecutingAssembly();
            // Stream myStream = myAssembly.GetManifestResourceStream("D2net.Common.Images.DropButton.bmp");
            // btnPopup.ButtonImage = new Bitmap(myStream);
		}

		/// <summary> 
		/// 사용 중인 모든 리소스를 정리합니다.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			base.Dispose( disposing );
		}

        #region Static Properties
        public static DateTime MaxDateTime
        {
            get { return DateTimePicker.MaxDateTime; }
        }

        public static DateTime MinDateTime
        {
            get { return DateTimePicker.MinDateTime; }
        }
        #endregion

        [Browsable(false)]
        public DateTime Value
        {
            get { return _CurDate; }
            set
            {
                _CurDate = value;
                _Cal.ActiveMonth.Year = value.Year;
                _Cal.ActiveMonth.Month = value.Month;
                _Cal.SelectDate(value);
            }
        }

        [Browsable(false)]
        public D2net.Common.UI.MonthCalendarEx Calendar
        {
            get { return _Cal; }
        }

        #region Popup Properties
        public Size PopupSize
        {
            get { return _CalForm.Size; }
            set { _CalForm.Size = value; }
        }

        [Description("First day of week.")]
        [RefreshProperties(RefreshProperties.All)] 
        [Category("Behavior")]
        [DefaultValue(0)]
        [TypeConverter(typeof(FirstDayOfWeekConverter))]
        public int FirstDayOfWeek
        {
            get { return _Cal.FirstDayOfWeek; }
            set { _Cal.FirstDayOfWeek = value; }
        }
	

        [Description("Indicates wether the trailing dates should be drawn.")]
        [Category("Behavior")]
        [DefaultValue(true)]
        public bool ShowTrailingDates
        {
            get { return _Cal.ShowTrailingDates; }
            set { _Cal.ShowTrailingDates = value; }
        }
				
        [Category("Behavior")]
        [RefreshProperties(RefreshProperties.All)]
        [Description("Culture to use for calendar.")]
        public CultureInfo Culture
        {
            get { return _Cal.Culture; }
            set { _Cal.Culture = value; }
        }

        [Category("Behavior")]
        [DefaultValue(null)]
        [Description("Collection with formatted dates.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)] 
        [Editor(typeof(DateItemCollectionEditor), typeof(UITypeEditor))]
        public DateItemCollection Dates
        {
            get { return _Cal.Dates; }
        }
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("The color used to mark todays date.")]
        [DefaultValue(typeof(Color),"Red")]
        public Color TodayColor
        {
            get { return _Cal.TodayColor; }
            set { _Cal.TodayColor = value; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [Description("Indicates wether todays date should be marked.")]
        [DefaultValue(true)]
        public bool ShowToday
        {
            get { return _Cal.ShowToday; }
            set { _Cal.ShowToday = value; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [Description("Indicates wether the focus should be displayed.")]
        [DefaultValue(true)]
        public bool ShowFocus
        {
            get { return _Cal.ShowFocus; }
            set { _Cal.ShowFocus = value; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [Description("Indicates wether its possible to select trailing dates.")]
        [DefaultValue(true)]
        public bool SelectTrailingDates
        {
            get { return _Cal.SelectTrailingDates; }
            set { _Cal.SelectTrailingDates = value; }
        }
		
        [Browsable(true)]
        [Category("Behavior")]
        [Description("ImageList thats contains the images used in the calendar.")]
        public ImageList ImageList
        {
            get { return _Cal.ImageList; }
            set { _Cal.ImageList = value; }
        }
		
        [Browsable(true)]
        [Category("Behavior")]
        [Description("The mouse button used for selections.")]
        [DefaultValue(typeof(mcSelectButton),"Left")]
        public mcSelectButton SelectButton
        {
            get { return _Cal.SelectButton; }
            set { _Cal.SelectButton = value; }
        }
	
        [Browsable(true)]
        [Category("Behavior")]
        [Description("The minimum date that can be selected.")]
        [TypeConverter(typeof(DateTimeTypeConverter))]
        public DateTime MinDate
        {
            get { return _Cal.MinDate; }
            set { _Cal.MinDate = value; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [Description("The maximum date that can be selected.")]
        [TypeConverter(typeof(DateTimeTypeConverter))]
        public DateTime MaxDate
        {
            get { return _Cal.MaxDate; }
            set { _Cal.MaxDate = value; }
        }
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates wether the calendar should display week numbers.")]
        [DefaultValue(false)]
        public bool ShowWeeknumbers
        {
            get { return _Cal.ShowWeeknumbers; }
            set { _Cal.ShowWeeknumbers = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Properties for header.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Header Header
        {
            get { return _Cal.Header; }
        }
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("Properties for weekdays.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Weekday Weekdays
        {
            get { return _Cal.Weekdays; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        [Description("")]
        public ActiveMonth ActiveMonth
        {
            get { return _Cal.ActiveMonth; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Properties for week numbers.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Weeknumber Weeknumbers
        {
            get { return _Cal.Weeknumbers; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Properties for month.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Month Month
        {
            get { return _Cal.Month; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Properties for footer.")]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Footer Footer
        {
            get { return _Cal.Footer; }
        }

        /*
        [Browsable(true)]
        [Category("Appearance")]
        [Description("The borderstyle used for the picker.")]
        [DefaultValue(typeof(BorderStyle),"Solid")]
        public BorderStyle BorderStyle
        {
            get { return pnlBorder.BorderStyle; }
            set { pnlBorder.BorderStyle = value; }
        }
        */
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("The borderstyle used for the calendar.")]
        [DefaultValue(typeof(ButtonBorderStyle),"Solid")]
        public ButtonBorderStyle PopupBorderStyle
        {
            get { return _Cal.BorderStyle; }
            set { _Cal.BorderStyle = value; }
        }
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("The color used for the border.")]
        [DefaultValue(typeof(Color),"Black")]
        public Color PopupBorderColor
        {
            get { return _Cal.BorderColor; }
            set { _Cal.BorderColor = value; }
        }	
		
        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates wether the calendar should display the footer.")]
        [DefaultValue(true)]
        public bool ShowFooter
        {
            get { return _Cal.ShowFooter; }
            set { _Cal.ShowFooter = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates wether the calendar should display the header.")]
        [DefaultValue(true)]
        public bool ShowHeader
        {
            get { return _Cal.ShowHeader; }
            set { _Cal.ShowHeader = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates wether the calendar should display weekdays.")]
        [DefaultValue(true)]
        public bool ShowWeekdays
        {
            get { return _Cal.ShowWeekdays; }
            set { _Cal.ShowWeekdays = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates backcolor.")]
        [DefaultValue(true)]
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

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates button backcolor.")]
        [DefaultValue(true)]
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

        /*
        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates Calendar backcolor.")]
        public Color CalendarBackColor
        {
            get { return _Cal.BackColor; }
            set { _Cal.BackColor = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates Calendar forecolor.")]
        public Color CalendarForeColor
        {
            get { return _Cal.ForeColor; }
            set { _Cal.ForeColor = value; }
        }
/*
        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates forecolor.")]
        [DefaultValue(true)]
        public new Color ForeColor
        {
            get { return lblDateTime.ForeColor; }
            set { lblDateTime.ForeColor = value; }
        }

        [Browsable(true)]
        [Category("Appearance")]
        [Description("Indicates TextAlign.")]
        [DefaultValue(true)]
        public new ContentAlignment TextAlign
        {
            get { return lblDateTime.TextAlign; }
            set { lblDateTime.TextAlign = value; }
        }
        */
        #endregion

        #region Event Setting
        public event EventHandler CloseUp;
        public event EventHandler DropDown;
        public event EventHandler ValueChanged;
        // [2006-02-14] -> 달이 바뀔때도 MonthChanged Event 발생
        public event EventHandler MonthChanged;
        #endregion

        /*
         * #define HWND_TOP        ((HWND)0)
         * #define HWND_BOTTOM     ((HWND)1)
         * #define HWND_TOPMOST    ((HWND)-1)
         * #define HWND_NOTOPMOST  ((HWND)-2)
         */

        private static readonly IntPtr HwndTop = new IntPtr(0);
        private static readonly IntPtr HwndBottom = new IntPtr(1);
        private static readonly IntPtr HwndTopMost = new IntPtr(-1);
        private static readonly IntPtr HwndNoTopMost = new IntPtr(-2);

        /*
         * SetWindowPos Flags
         *
         * #define SWP_NOSIZE          0x0001
         * #define SWP_NOMOVE          0x0002
         * #define SWP_NOZORDER        0x0004
         * #define SWP_NOREDRAW        0x0008
         * #define SWP_NOACTIVATE      0x0010
         * #define SWP_FRAMECHANGED    0x0020
         * #define SWP_SHOWWINDOW      0x0040
         * #define SWP_HIDEWINDOW      0x0080
         * #define SWP_NOCOPYBITS      0x0100
         * #define SWP_NOOWNERZORDER   0x0200
         * #define SWP_NOSENDCHANGING  0x0400
         */

        [DllImport("User32")]
        private static extern bool SetWindowPos(IntPtr hWnd, 
            IntPtr hWndInsertAfter,
            int X,
            int Y,
            int cx,
            int cy,
            uint uFlags);

        [DllImport("User32")]
        private static extern IntPtr SetCapture(IntPtr hWnd);
        
        [DllImport("User32")]
        private static extern bool ReleaseCapture();

        [DllImport("User32")]
        private static extern IntPtr SetFocus(IntPtr hWnd);

        private void Popup()
        {
            Point pt = new Point(0, this.Height);
            pt = PointToScreen(pt);
            SetWindowPos(_CalForm.Handle, HwndTopMost, pt.X, pt.Y, _CalForm.Width, _CalForm.Height, 0x0080);
            _Cal.SelectDate(Value);
            _Cal.ActiveMonth.Year = Value.Year;
            _Cal.ActiveMonth.Month = Value.Month;
            _CalForm.Show();
            SetCapture(_Cal.Handle);
        }

        private void PopupHide()
        {
            ReleaseCapture();
            _CalForm.Hide();
        }

        private void DisplayValue()
        {
            // lblDateTime.Text = _CurDate.ToLongDateString();
        }

        private void DateTimePickerEx_Load(object sender, System.EventArgs e)
        {
            // DisplayValue();
        }

        private void btnPopup_Click(object sender, System.EventArgs e)
        {
            _Cal.SelectDate(_CurDate);
            if (DropDown != null)
                DropDown(this, new EventArgs());
            Popup();
        }

        private void _Cal_MouseDown(object sender, MouseEventArgs e)
        {
            Rectangle rt = _Cal.Bounds;
            if (!rt.Contains(new Point(e.X, e.Y)))
            {
                PopupHide();
                return;
            }
        }

        private void _Cal_MouseUp(object sender, MouseEventArgs e)
        {
            if (!bUp)
                return;
            bUp = false;
            this.Invalidate();
        }

        private void _Cal_LostFocus(object sender, System.EventArgs e)
        {
            PopupHide();
        }

        private void _Cal_DayClick(object sender, DayClickEventArgs e)
        {
            Value = DateTime.Parse(e.Date);
            if (ValueChanged != null)
                ValueChanged(this, new EventArgs());
            DisplayValue();
            PopupHide();
            if (CloseUp != null)
                CloseUp(this, new EventArgs());
            Invalidate();
        }

        // [2006-02-14] -> 달이 바뀔때도 MonthChanged Event 발생
        private void _Cal_MonthChanged(object sender, MonthChangedEventArgs e)
        {
            if (MonthChanged != null)
                MonthChanged(this, new EventArgs());
        }

        #region Drawing
        private void RecalcBounds()
        {
            _ButtonArea = new Rectangle(Width - 15, 3, 12, Height - 6);
            _DateTimeArea = new Rectangle(3, 3, Width - 6, Height - 6);
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
            g.DrawString(_CurDate.ToLongDateString(), 
                this.Font, 
                new SolidBrush(ForeColor),
                _DateTimeArea,
                _DateTimeDisFormat);
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

        protected override void OnMouseDown(MouseEventArgs e)
        {
            base.OnMouseDown (e);
            if (!_ButtonArea.Contains(e.X, e.Y))
                return;
            bUp = true;
            this.Invalidate();
            // [2006-08-31] -> 달력 Popup시 DropDown Event 발생하게 해줌
            _Cal.SelectDate(_CurDate);
            if (DropDown != null)
                DropDown(this, new EventArgs());
            Popup();
        }
        #endregion
	}
}
