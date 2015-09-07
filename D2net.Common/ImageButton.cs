using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;

namespace D2net.Common.UI
{
	/// <summary>
	/// 이미지 버튼을 정의한다. 이미지는 {[기본],[클릭],[HOT],[비활성화]}의 순으로 가로로 한장의 이미지로 작성한다.
    /// </summary>
    /// <example> 이미지 설정
    /// <code>
    /// btnTest = ImageButton();
    /// btnTest.ButtonImage = new Image("*.jpeg");
    /// </code>
    /// </example>
	public class ImageButton : System.Windows.Forms.Button
	{
        /// <summary>
	    /// 버튼의 상태를 정의한 열거형
        /// </summary>
        protected enum BtnState : int
        {
            /// <summary>
	        /// 기본
            /// </summary>
            DEFAULT,

            /// <summary>
	        /// 클릭
            /// </summary>
            CLICK,
            
            /// <summary>
	        /// HOT(마우스가 버튼위에 올라왔을때)
            /// </summary>
            ROLLOVER,

            /// <summary>
	        /// 비활성화
            /// </summary>
            DISABLED
        }

        /// <summary>
	    /// 전체 이미지 - {[기본],[클릭],[HOT],[비활성화]} 의 순으로 가로로 구성된 이미지
        /// </summary>
        private Image _ButtonImage = null;

        /// <summary>
	    /// {[기본],[클릭],[HOT],[비활성화]} 순의 이미지 배열(Croma Key 있는것)
        /// </summary>
        private Bitmap[] _ButtonImages = null;

        /// <summary>
        /// {[기본],[클릭],[HOT],[비활성화]} 순의 이미지 배열(Croma Key 없는것)
        /// </summary>
        private Image[] _OrginalImages = null;

        /// <summary>
	    /// 버튼의 상태 변화를 나타내는 플래그
        /// </summary>
        private BtnState _BtnState = BtnState.DEFAULT;

        /// <summary>
	    /// 버튼에 텍스트를 보여줄지 여부를 설정하는 플래그
        /// </summary>
        private bool _ViewText = false;

        /// <summary>
	    /// 버튼위로 마우스가 올라왔을때의 버튼 텍스트의 색깔
        /// </summary>
        private Color _HotTextForeColor = Color.Blue;

        /// <summary>
	    /// 비활성화시에 버튼 텍스트의 색깔
        /// </summary>
        private Color _DisabledTextForeColor = Color.Gray;

        /// <summary>
	    /// 비활성화시에 버튼 텍스트 그림자의 색깔
        /// </summary>
        private Color _DisabledTextShadowColor = Color.White;

        /// <summary>
	    /// 버튼이 눌렸을때 밀리는 픽셀을 정의한다.
        /// </summary>
        private int _PushDownPixel = 1;

        /// <summary>
        /// 버튼의 Croma key를 적용할지 여부를 판단한다.
        /// </summary>
        private bool _Transparent = true;

        /// <summary>
        /// 버튼의 Croma key를 색깔을 지정한다.
        /// </summary>
        private Color _TransparentColor = Color.FromArgb(236, 0, 140);

        /// <summary>
        /// 버튼의 경계를 출력하기 위한 폭
        /// </summary>
        private int _OutLineWidth = 4;
        private Rectangle[] _RtMap = new Rectangle[9];
        private Bitmap _BufferImage = null;
        private Pen _FocusedPen = new Pen(Color.Black);
        private bool _ShowFocusLine = true;

        /// <summary>
	    /// 버튼 기본 생성자
        /// </summary>
		public ImageButton()
		{
            // _BufferImage = new Bitmap(ClientSize.Width, ClientSize.Height, PixelFormat.Format32bppPArgb);
            // RecalcRectangles();
            FlatStyle = FlatStyle.Flat;
            _FocusedPen.DashStyle = DashStyle.Dot;
		}

        /// <summary>
	    /// 버튼을 활성화 비활성화를 정의한다. 버튼의 Enabled 속성을 설정하면 버튼이 다시그려진다.
        /// </summary>
        public new bool Enabled
        {
            get { return base.Enabled; }
            set
            {
                base.Enabled = value;
                Invalidate();
            }
        }

        /// <summary>
	    /// 버튼의 FlatStyle 속성을 정의한다. 기본적으로 FlatStyle.Flat로 설정되며, 변경되지 않는다.
        /// </summary>
        [BrowsableAttribute(false)]
        public new FlatStyle FlatStyle
        {
            get { return base.FlatStyle; }
            set { }
        }

        /// <summary>
        /// 버튼의 Croma key를 적용할지 여부를 판단한다.
        /// </summary>
        public bool Transparent
        {
            get { return _Transparent; }
            set { _Transparent = value; this.Invalidate(); }
        }

        /// <summary>
        /// 버튼의 Croma key를 색깔을 지정한다.
        /// </summary>
        public Color TransparentColor
        {
            get { return _TransparentColor; }
            set
            {
                _TransparentColor = value;

                if (_ButtonImages != null)
                {
                    foreach (Bitmap bmp in _ButtonImages)
                        bmp.MakeTransparent(_TransparentColor);
                }
                this.Invalidate();
            }
        }

        /// <summary>
	    /// 전체 이미지 - {[기본],[클릭],[HOT],[비활성화]} 의 순으로 가로로 구성된 이미지
        /// </summary>
        public Image ButtonImage
        {
            get { return _ButtonImage; }
            set
            {
                if (value == null)
                {
                    // [2006-05-13] -> Button Image를 null로 설정해도 변경이 않됨
                    _ButtonImage = null;
                    Invalidate();
                    return;
                }

                FlatStyle = FlatStyle.Flat;
                _ButtonImage = value;
                _OrginalImages = ImageSplitter.SplitImage(value, 4, 1);
                int len = _OrginalImages.Length;
                _ButtonImages = new Bitmap[len];

                for (int i = 0; i < len; i++)
                {
                    _ButtonImages[i] = new Bitmap(_OrginalImages[i]);
                    _ButtonImages[i].MakeTransparent(_TransparentColor);
                    //_ButtonImages[i].MakeTransparent();
                }

                Invalidate();
            }
        }
        
        /// <summary>
	    /// 버튼에 텍스트를 보여줄지 여부를 설정하는 플래그
        /// </summary>
        public bool ViewText
        {
            get { return _ViewText; }
            set { _ViewText = value; }
        }

        /// <summary>
	    /// 버튼 기본 텍스트의 색깔. 단, 설정이 되지 않음. 설정시에는 <c>ImageButton.EnabledTextForeColor</c>를 사용
        /// </summary>
        /// <seealso cref="EnabledTextForeColor"/>
        [BrowsableAttribute(false)]
        public override Color ForeColor
        {
            get { return base.ForeColor; }
            set { base.ForeColor = value; }
        }

        /// <summary>
	    /// 버튼 기본 텍스트의 색깔. <c>ImageButton.ForeColor</c>와 동일하다.
        /// </summary>
        /// <seealso cref="ForeColor"/>
        public Color EnabledTextForeColor
        {
            get { return base.ForeColor; }
            set { base.ForeColor = value; }
        }
        
        /// <summary>
	    /// 버튼위로 마우스가 올라왔을때의 버튼 텍스트의 색깔
        /// </summary>
        public Color HotTextForeColor
        {
            get { return _HotTextForeColor; }
            set { _HotTextForeColor = value; }
        }

        /// <summary>
	    /// 비활성화시에 버튼 텍스트의 색깔
        /// </summary>
        public Color DisabledTextForeColor
        {
            get { return _DisabledTextForeColor; }
            set { _DisabledTextForeColor = value; }
        }

        /// <summary>
	    /// 비활성화시에 버튼 텍스트 그림자의 색깔
        /// </summary>
        public Color DisabledTextShadowColor
        {
            get { return _DisabledTextShadowColor; }
            set { _DisabledTextShadowColor = value; }
        }

        /// <summary>
	    /// 버튼이 눌렸을때 밀리는 픽셀을 정의한다.
        /// </summary>
        public int PushDownPixel
        {
            get { return _PushDownPixel; }
            set { _PushDownPixel = value; }
        }

        public bool ShowFocusLine
        {
            get { return _ShowFocusLine; }
            set { _ShowFocusLine = value; }
        }

        // [2006-02-10] OutLine Width를 왜부에서 설정할수 있게 private에서 public로 설정하였다
        public int OutLineWidth
        {
            get { return _OutLineWidth; }
            set { _OutLineWidth = value; Invalidate(); }
        }

        private Color ReCalcBackColor()
        {
            Color _BackColor = BackColor;
            Control ctrl = this;

            while (ctrl != null)
            {
                if (ctrl.BackColor != Color.Transparent)
                {
                    _BackColor = ctrl.BackColor;
                    break;
                }

                ctrl = ctrl.Parent;
            }

            return _BackColor;
        }

        private void RecalcRectangles()
        {
            int dest_width = Width;
            int dest_height = Height;

            _RtMap[0] = new Rectangle(0, 0, _OutLineWidth, _OutLineWidth);
            _RtMap[1] = new Rectangle(dest_width - _OutLineWidth, 0, _OutLineWidth, _OutLineWidth);
            _RtMap[2] = new Rectangle(0, dest_height - _OutLineWidth, _OutLineWidth, _OutLineWidth);
            _RtMap[3] = new Rectangle(dest_width - _OutLineWidth, dest_height - _OutLineWidth, _OutLineWidth, _OutLineWidth);

            _RtMap[4] = new Rectangle(_OutLineWidth, 0, dest_width - 2 * _OutLineWidth, _OutLineWidth);
            _RtMap[5] = new Rectangle(_OutLineWidth, dest_height - _OutLineWidth, dest_width - 2 * _OutLineWidth, _OutLineWidth);
            _RtMap[6] = new Rectangle(0, _OutLineWidth, _OutLineWidth, dest_height - 2 * _OutLineWidth);
            _RtMap[7] = new Rectangle(dest_width - _OutLineWidth, _OutLineWidth, _OutLineWidth, dest_height - 2 * _OutLineWidth);

            _RtMap[8] = new Rectangle(_OutLineWidth, _OutLineWidth, dest_width - 2 * _OutLineWidth, dest_height - 2 * _OutLineWidth);
        }

        /// <summary>
	    /// 버튼을 그리기 위한 재정의 함수
        /// </summary>
        protected override void OnPaint(PaintEventArgs e)
        {
            Graphics g = null;

            if (_ButtonImage == null)
            {
                base.OnPaint (e);
                return;
            }

            if (Enabled == false)
                _BtnState = BtnState.DISABLED;
            else if (ClientRectangle.Contains(PointToClient(MousePosition)))
            {
                if (Capture)
                    _BtnState = BtnState.CLICK;
                else
                    _BtnState = BtnState.ROLLOVER;
            }
            else
                _BtnState = BtnState.DEFAULT;

//            lock (this)
//            {
//                // g = Graphics.FromImage(_BufferImage);
//            }

            // DrawButton(g, _BtnState);
            // e.Graphics.DrawImage(_BufferImage, new Rectangle(0, 0, Width, Height), 0, 0, Width, Height, GraphicsUnit.Pixel);
            RecalcRectangles();
            _BufferImage = new Bitmap(Width, Height);
            g = Graphics.FromImage(_BufferImage);            
            DrawButton(g, _BtnState);
            e.Graphics.DrawImage(_BufferImage, 0, 0);
        }

        /// <summary>
	    /// 마우스 포인터가 버튼에 들어갔을때 이벤트 헨들러
        /// </summary>
        protected override void OnMouseEnter(EventArgs e)
        {
            _BtnState = BtnState.ROLLOVER;
            Invalidate();
        }

        /// <summary>
	    /// 마우스 포인터가 버튼으로 들어갔을때 이벤트 헨들러
        /// </summary>
        protected override void OnMouseLeave(EventArgs e)
        {
            _BtnState = BtnState.DEFAULT;
            Invalidate();
        }

        /// <summary>
	    /// 마우스 포인터가 버튼에서 나왔을때 이벤트 헨들러
        /// </summary>
        protected override void OnMouseUp(MouseEventArgs e)
        {
            if(ClientRectangle.Contains(PointToClient(MousePosition)))
			{
				if(e.Button == MouseButtons.Left)
					PerformClick();

                _BtnState = BtnState.ROLLOVER;
            }
            else
                _BtnState = BtnState.DEFAULT;
            Invalidate();
        }

        /// <summary>
	    /// 실제 버튼을 그린다.
        /// </summary>
        protected void DrawButton(Graphics g, BtnState BtnStat)
        {
            int dest_width = ClientSize.Width;
            int dest_height = ClientSize.Height;
            int src_width = 0;
            int src_height = 0;
            Image img = null;

            g.Clear(ReCalcBackColor());
            // System.Diagnostics.Debug.WriteLine("Back color = " + Parent.BackColor.ToString());
            // g.FillRectangle(new SolidBrush(Parent.BackColor), 0, 0, dest_width, dest_height);

            /*
            if (_Transparent)
                g.DrawImage(_ButtonImages[(int)BtnStat], 0, 0, dest_width, dest_height);
            else
                g.DrawImage(_OrginalImages[(int)BtnStat], 0, 0, dest_width, dest_height);
            */

            if (_Transparent)
                img = _ButtonImages[(int)BtnStat];
            else
                img = _OrginalImages[(int)BtnStat];

            if (img != null)
            {
                src_width = img.Width;
                src_height = img.Height;

                g.DrawImage(img, _RtMap[0], 
                    0, 0, _OutLineWidth, _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[1], 
                    src_width - _OutLineWidth, 0, 
                    _OutLineWidth, _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[2], 
                    0, src_height - _OutLineWidth, _OutLineWidth, 
                    _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[3], 
                    src_width - _OutLineWidth, src_height - _OutLineWidth, 
                    _OutLineWidth, _OutLineWidth, GraphicsUnit.Pixel);

                g.DrawImage(img, _RtMap[4], 
                    _OutLineWidth, 0, src_width - 2 * _OutLineWidth, _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[5], 
                    _OutLineWidth, src_height - _OutLineWidth, src_width - 2 * _OutLineWidth, _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[6], 
                    0, _OutLineWidth, _OutLineWidth, src_height - 2 * _OutLineWidth, GraphicsUnit.Pixel);
                g.DrawImage(img, _RtMap[7], 
                    src_width - _OutLineWidth, _OutLineWidth, _OutLineWidth, src_height - 2 * _OutLineWidth, GraphicsUnit.Pixel);

                g.DrawImage(img, _RtMap[8], 
                    _OutLineWidth, _OutLineWidth, src_width - 2 * _OutLineWidth, src_height - 2 * _OutLineWidth, GraphicsUnit.Pixel);
            }

            if (_ShowFocusLine && 
                Focused)
                g.DrawRectangle(_FocusedPen, 3, 3, dest_width - 7, dest_height - 7);
            
            if (!_ViewText)
                return;

            StringFormat strf = new StringFormat();
			strf.Alignment = StringAlignment.Center;
			strf.LineAlignment = StringAlignment.Center;

            switch (BtnStat)
            {
                case BtnState.DEFAULT:
                    g.DrawString(Text, Font, new SolidBrush(ForeColor), 
                        new RectangleF(0, 0, dest_width, dest_height), 
                        strf);
                    break;
                case BtnState.ROLLOVER:
                    g.DrawString(Text, Font, new SolidBrush(_HotTextForeColor), 
                        new RectangleF(0, 0, dest_width, dest_height), 
                        strf);
                    break;
                case BtnState.CLICK:
                    g.DrawString(Text, Font, new SolidBrush(ForeColor), 
                        new RectangleF(_PushDownPixel, _PushDownPixel, dest_width, dest_height), 
                        strf);
                    break;
                case BtnState.DISABLED:
                    g.DrawString(Text, Font, new SolidBrush(_DisabledTextShadowColor), 
                        new RectangleF(1, 1, dest_width, dest_height), 
                        strf);
                    g.DrawString(Text, Font, new SolidBrush(_DisabledTextForeColor), 
                        new RectangleF(0, 0, dest_width, dest_height), 
                        strf);
                    break;
            }
        }

        private void InitializeComponent()
        {
            // 
            // ImageButton
            // 
            this.Resize += new System.EventHandler(this.ImageButton_Resize);

        }

        private void ImageButton_Resize(object sender, System.EventArgs e)
        {
            lock (this)
            {
                // _BufferImage = new Bitmap(ClientSize.Width, ClientSize.Height, PixelFormat.Format32bppPArgb);
                // RecalcRectangles();
            }
        }
	}
}
