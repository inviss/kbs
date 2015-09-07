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
	/// �̹��� ��ư�� �����Ѵ�. �̹����� {[�⺻],[Ŭ��],[HOT],[��Ȱ��ȭ]}�� ������ ���η� ������ �̹����� �ۼ��Ѵ�.
    /// </summary>
    /// <example> �̹��� ����
    /// <code>
    /// btnTest = ImageButton();
    /// btnTest.ButtonImage = new Image("*.jpeg");
    /// </code>
    /// </example>
	public class ImageButton : System.Windows.Forms.Button
	{
        /// <summary>
	    /// ��ư�� ���¸� ������ ������
        /// </summary>
        protected enum BtnState : int
        {
            /// <summary>
	        /// �⺻
            /// </summary>
            DEFAULT,

            /// <summary>
	        /// Ŭ��
            /// </summary>
            CLICK,
            
            /// <summary>
	        /// HOT(���콺�� ��ư���� �ö������)
            /// </summary>
            ROLLOVER,

            /// <summary>
	        /// ��Ȱ��ȭ
            /// </summary>
            DISABLED
        }

        /// <summary>
	    /// ��ü �̹��� - {[�⺻],[Ŭ��],[HOT],[��Ȱ��ȭ]} �� ������ ���η� ������ �̹���
        /// </summary>
        private Image _ButtonImage = null;

        /// <summary>
	    /// {[�⺻],[Ŭ��],[HOT],[��Ȱ��ȭ]} ���� �̹��� �迭(Croma Key �ִ°�)
        /// </summary>
        private Bitmap[] _ButtonImages = null;

        /// <summary>
        /// {[�⺻],[Ŭ��],[HOT],[��Ȱ��ȭ]} ���� �̹��� �迭(Croma Key ���°�)
        /// </summary>
        private Image[] _OrginalImages = null;

        /// <summary>
	    /// ��ư�� ���� ��ȭ�� ��Ÿ���� �÷���
        /// </summary>
        private BtnState _BtnState = BtnState.DEFAULT;

        /// <summary>
	    /// ��ư�� �ؽ�Ʈ�� �������� ���θ� �����ϴ� �÷���
        /// </summary>
        private bool _ViewText = false;

        /// <summary>
	    /// ��ư���� ���콺�� �ö�������� ��ư �ؽ�Ʈ�� ����
        /// </summary>
        private Color _HotTextForeColor = Color.Blue;

        /// <summary>
	    /// ��Ȱ��ȭ�ÿ� ��ư �ؽ�Ʈ�� ����
        /// </summary>
        private Color _DisabledTextForeColor = Color.Gray;

        /// <summary>
	    /// ��Ȱ��ȭ�ÿ� ��ư �ؽ�Ʈ �׸����� ����
        /// </summary>
        private Color _DisabledTextShadowColor = Color.White;

        /// <summary>
	    /// ��ư�� �������� �и��� �ȼ��� �����Ѵ�.
        /// </summary>
        private int _PushDownPixel = 1;

        /// <summary>
        /// ��ư�� Croma key�� �������� ���θ� �Ǵ��Ѵ�.
        /// </summary>
        private bool _Transparent = true;

        /// <summary>
        /// ��ư�� Croma key�� ������ �����Ѵ�.
        /// </summary>
        private Color _TransparentColor = Color.FromArgb(236, 0, 140);

        /// <summary>
        /// ��ư�� ��踦 ����ϱ� ���� ��
        /// </summary>
        private int _OutLineWidth = 4;
        private Rectangle[] _RtMap = new Rectangle[9];
        private Bitmap _BufferImage = null;
        private Pen _FocusedPen = new Pen(Color.Black);
        private bool _ShowFocusLine = true;

        /// <summary>
	    /// ��ư �⺻ ������
        /// </summary>
		public ImageButton()
		{
            // _BufferImage = new Bitmap(ClientSize.Width, ClientSize.Height, PixelFormat.Format32bppPArgb);
            // RecalcRectangles();
            FlatStyle = FlatStyle.Flat;
            _FocusedPen.DashStyle = DashStyle.Dot;
		}

        /// <summary>
	    /// ��ư�� Ȱ��ȭ ��Ȱ��ȭ�� �����Ѵ�. ��ư�� Enabled �Ӽ��� �����ϸ� ��ư�� �ٽñ׷�����.
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
	    /// ��ư�� FlatStyle �Ӽ��� �����Ѵ�. �⺻������ FlatStyle.Flat�� �����Ǹ�, ������� �ʴ´�.
        /// </summary>
        [BrowsableAttribute(false)]
        public new FlatStyle FlatStyle
        {
            get { return base.FlatStyle; }
            set { }
        }

        /// <summary>
        /// ��ư�� Croma key�� �������� ���θ� �Ǵ��Ѵ�.
        /// </summary>
        public bool Transparent
        {
            get { return _Transparent; }
            set { _Transparent = value; this.Invalidate(); }
        }

        /// <summary>
        /// ��ư�� Croma key�� ������ �����Ѵ�.
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
	    /// ��ü �̹��� - {[�⺻],[Ŭ��],[HOT],[��Ȱ��ȭ]} �� ������ ���η� ������ �̹���
        /// </summary>
        public Image ButtonImage
        {
            get { return _ButtonImage; }
            set
            {
                if (value == null)
                {
                    // [2006-05-13] -> Button Image�� null�� �����ص� ������ �ʵ�
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
	    /// ��ư�� �ؽ�Ʈ�� �������� ���θ� �����ϴ� �÷���
        /// </summary>
        public bool ViewText
        {
            get { return _ViewText; }
            set { _ViewText = value; }
        }

        /// <summary>
	    /// ��ư �⺻ �ؽ�Ʈ�� ����. ��, ������ ���� ����. �����ÿ��� <c>ImageButton.EnabledTextForeColor</c>�� ���
        /// </summary>
        /// <seealso cref="EnabledTextForeColor"/>
        [BrowsableAttribute(false)]
        public override Color ForeColor
        {
            get { return base.ForeColor; }
            set { base.ForeColor = value; }
        }

        /// <summary>
	    /// ��ư �⺻ �ؽ�Ʈ�� ����. <c>ImageButton.ForeColor</c>�� �����ϴ�.
        /// </summary>
        /// <seealso cref="ForeColor"/>
        public Color EnabledTextForeColor
        {
            get { return base.ForeColor; }
            set { base.ForeColor = value; }
        }
        
        /// <summary>
	    /// ��ư���� ���콺�� �ö�������� ��ư �ؽ�Ʈ�� ����
        /// </summary>
        public Color HotTextForeColor
        {
            get { return _HotTextForeColor; }
            set { _HotTextForeColor = value; }
        }

        /// <summary>
	    /// ��Ȱ��ȭ�ÿ� ��ư �ؽ�Ʈ�� ����
        /// </summary>
        public Color DisabledTextForeColor
        {
            get { return _DisabledTextForeColor; }
            set { _DisabledTextForeColor = value; }
        }

        /// <summary>
	    /// ��Ȱ��ȭ�ÿ� ��ư �ؽ�Ʈ �׸����� ����
        /// </summary>
        public Color DisabledTextShadowColor
        {
            get { return _DisabledTextShadowColor; }
            set { _DisabledTextShadowColor = value; }
        }

        /// <summary>
	    /// ��ư�� �������� �и��� �ȼ��� �����Ѵ�.
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

        // [2006-02-10] OutLine Width�� �ֺο��� �����Ҽ� �ְ� private���� public�� �����Ͽ���
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
	    /// ��ư�� �׸��� ���� ������ �Լ�
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
	    /// ���콺 �����Ͱ� ��ư�� ������ �̺�Ʈ ��鷯
        /// </summary>
        protected override void OnMouseEnter(EventArgs e)
        {
            _BtnState = BtnState.ROLLOVER;
            Invalidate();
        }

        /// <summary>
	    /// ���콺 �����Ͱ� ��ư���� ������ �̺�Ʈ ��鷯
        /// </summary>
        protected override void OnMouseLeave(EventArgs e)
        {
            _BtnState = BtnState.DEFAULT;
            Invalidate();
        }

        /// <summary>
	    /// ���콺 �����Ͱ� ��ư���� �������� �̺�Ʈ ��鷯
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
	    /// ���� ��ư�� �׸���.
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
