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
    /// UserControl1�� ���� ��� �����Դϴ�.
    /// </summary>
    public sealed class AdvProgressBar : System.Windows.Forms.Panel
    {
        /// <summary>
        /// �ʼ� �����̳� �����Դϴ�.
        /// </summary>
        private System.ComponentModel.Container components = null;
        private int Range = 0;
        private int Value = 0;
        private int _Min = 0;
        private int _Max = 100;
        private System.Drawing.Drawing2D.LinearGradientBrush Color;
        private Rectangle Rect;
        private System.Drawing.Color _BackColor = System.Drawing.Color.Black;
        private System.Drawing.Color _SColor = System.Drawing.Color.Cyan;
        private System.Drawing.Color _EColor = System.Drawing.Color.White;
        private System.Drawing.Color _TextColor = System.Drawing.Color.Black;
        private System.Drawing.Color _TextShadowColor = System.Drawing.Color.Gray;
        private int _FontSize = 10;
        private string _Font = "����";
        private Font _TextFont = null;// = new Font(Font,FontSize);
        private FontStyle _FS = FontStyle.Bold;
        private bool _SetCenter = false;
        private string _DisplayingText = "";
        private bool _DrawShadow = false;
        private bool _NegativeText = false;

        public AdvProgressBar()
        {
            InitializeComponent();
            SetStyle(ControlStyles.DoubleBuffer | ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint, true);
            _TextFont = new Font(_Font, _FontSize, _FS);
        }

        /// <summary>
        /// ��� ���� ��� ���ҽ��� �����մϴ�.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                    components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region ���� ��� �����̳ʿ��� ������ �ڵ�
        /// <summary>
        /// �����̳� ������ �ʿ��� �޼����Դϴ�. 
        /// �� �޼����� ������ �ڵ� ������� �������� ���ʽÿ�.
        /// </summary>
        private void InitializeComponent()
        {
            // 
            // AdvProgressBar
            // 
            this.Name = "AdvProgressBar";
            this.Size = new System.Drawing.Size(248, 56);
            this.Resize += new System.EventHandler(this.AdvProgressBar_Resize);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.ProgressBar_Paint);

        }
        #endregion

        private void ProgressBar_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
        {
            try
            {
                if (this.Created)
                {
                    e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
                    e.Graphics.FillRectangle(new SolidBrush(_BackColor), new Rectangle(0, 0, this.Width, this.Height));
                    // if(Value > 0)
                    {
                        Brush br = SetBrush();
                        Brush Txtbr = new SolidBrush(TextColor);
                        StringFormat strf = new StringFormat();
                        strf.Alignment = StringAlignment.Center;
                        strf.LineAlignment = StringAlignment.Center;

                        if (Value != 0)
                        {
                            Rect = new Rectangle(0, 0, (Width * (Value - MinValue)) / (MaxValue - MinValue), Height);
                            e.Graphics.FillRectangle(Color, Rect);
                        }
                    
                        if (_DrawShadow)
                        {
                            e.Graphics.DrawString(_DisplayingText, _TextFont, new SolidBrush(_TextShadowColor), Width / 2 + 1, Height / 2 + 1, strf);
                        }
                        else
                        { }
                        if (_NegativeText)
                        {
                            e.Graphics.DrawString(_DisplayingText, _TextFont, br, Width / 2, Height / 2, strf);
                        }
                        else
                        {
                            e.Graphics.DrawString(_DisplayingText, _TextFont, Txtbr, Width / 2, Height / 2, strf);
                        }
                        /*
						int t = (int)((float)this.Width*((float)Value/(float)Range/2))-(_DisplayingText.Length*_FontSize/2);
						if(t < 0)
						{
							t = 0;
						}
						if(!_SetCenter)
						{
							// e.Graphics.DrawString(_DisplayingText,_TextFont,new SolidBrush(_TextShadowColor),new Rectangle(t,this.Height/2-_FontSize/2,this.Width,this.Height));
							e.Graphics.DrawString(_DisplayingText,_TextFont,br,new Rectangle(t-1,this.Height/2-_FontSize/2-1,this.Width,this.Height));
						}
						else
						{
							// e.Graphics.DrawString(_DisplayingText,_TextFont,new SolidBrush(_TextShadowColor),new Rectangle(this.Width/2-(_DisplayingText.Length*_FontSize/2),this.Height/2-_FontSize/2,this.Width,this.Height));
							e.Graphics.DrawString(_DisplayingText,_TextFont,br,new Rectangle(this.Width/2-1-(_DisplayingText.Length*_FontSize/2),this.Height/2-_FontSize/2-1,this.Width,this.Height));

						}
                        */
                    }
                }
            }
            catch (Exception err)
            {
                throw err;
            }

        }

        private Brush SetBrush()
        {
            try
            {
                if (Value == 0)
                {
                    Rect = new Rectangle(0, 0, 1, this.Height);
                }
                else
                {
                    Rect = new Rectangle(0, 0, (int)((float)this.Width * ((float)Value / (float)Range)), this.Height);
                    //Rect = new Rectangle(0,0,this.Width,this.Height);
                }
                Color = new System.Drawing.Drawing2D.LinearGradientBrush(
                    Rect,
                    _SColor,
                    _EColor,
                    LinearGradientMode.Horizontal);
                int scR, scG, scB, ecR, ecG, ecB;
                System.Drawing.Color sc, ec;
                scR = Math.Abs((int)_SColor.R - 255);
                scG = Math.Abs((int)_SColor.G - 255);
                scB = Math.Abs((int)_SColor.B - 255);
                ecR = Math.Abs((int)_EColor.R - 255);
                ecG = Math.Abs((int)_EColor.G - 255);
                ecB = Math.Abs((int)_EColor.B - 255);

                sc = System.Drawing.Color.FromArgb(scR, scG, scB);
                ec = System.Drawing.Color.FromArgb(ecR, ecG, ecB);
                return new System.Drawing.Drawing2D.LinearGradientBrush(
                    Rect,
                    sc,
                    ec,
                    LinearGradientMode.Horizontal);
            }
            catch (Exception err)
            {
                throw err;
            }
        }

        private void AdvProgressBar_Resize(object sender, System.EventArgs e)
        {
            SetBrush();
        }

        #region Property
        /// <summary>
        /// �ּҰ� ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Value"),
        Description("�ּҰ� ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public int MinValue
        {
            set
            {
                _Min = value;
                Range = _Max - _Min;
                Invalidate();

            }
            get
            {
                return _Min;
            }

        }

        /// <summary>
        /// �ִ밪 ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Value"),
        Description("�ִ밪 ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public int MaxValue
        {
            set
            {
                _Max = value;
                Range = _Max - _Min;
                Invalidate();
            }
            get
            {
                return _Max;
            }

        }

        /// <summary>
        /// ���簪 ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Value"),
        Description("���簪 ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public int currentValue
        {
            set
            {
                
                if (Value < _Min)
                    Value = _Min;
                else if (Value > _Max)
                    Value = _Max;
                else
                    Value = value;

                Invalidate();
            }
            get
            {
                return Value;
            }

        }

        /// <summary>
        /// ������ ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("������ ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color SetBackgroundColor
        {
            set
            {
                _BackColor = value;
                Invalidate();
            }
            get
            {
                return _BackColor;
            }
        }

        /// <summary>
        /// ��Ʈ�� ������ ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("������ ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color ControlBackgroundColor
        {
            set
            {
                this.BackColor = value;
                Invalidate();
            }
            get
            {
                return this.BackColor;
            }
        }

        /// <summary>
        /// ǥ�ø�����
        /// �������� ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ�ø����� �������� ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color StartingColor
        {
            set
            {
                _SColor = value;
                Invalidate();
            }
            get
            {
                return _SColor;
            }
        }

        /// <summary>
        /// Border Style
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("�ܰ��� ��Ÿ��"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public BorderStyle APBBorderStyle
        {
            set
            {
                this.BorderStyle = value;
                Invalidate();
            }
            get
            {
                return this.BorderStyle;
            }
        }

        /// <summary>
        /// ǥ�ø�����
        /// ������ ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ�ø����� ������ ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color EndColor
        {
            set
            {
                _EColor = value;
                Invalidate();
            }
            get
            {
                return _EColor;
            }
        }

        /// <summary>
        /// ������ ��
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("������ ��"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color TextColor
        {
            set
            {
                _TextColor = value;
                Invalidate();
            }
            get
            {
                return _TextColor;
            }
        }

        /// <summary>
        /// ������ �׸����� ��
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("������ �׸����� ��"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public System.Drawing.Color TextShadowColor
        {
            set
            {
                _TextShadowColor = value;
                Invalidate();
            }
            get
            {
                return _TextShadowColor;
            }
        }

        /// <summary>
        /// ��Ʈ�� ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("��Ʈ�� ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public Font TextFont
        {
            set
            {
                _TextFont = value;
            }
            get
            {
                return _TextFont;
            }
        }

        /// <summary>
        /// ǥ�õǴ� ���ڸ� ������ ���� �̵���ų�� ����
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ�õǴ� ���ڸ� ������ ���� �̵���ų�� ����"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public bool SetCenter
        {
            set
            {
                _SetCenter = value;
                Invalidate();
            }
            get
            {
                return _SetCenter;
            }
        }

        /// <summary>
        /// ǥ���� �ؽ�Ʈ
        /// </summary>
        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ���� �ؽ�Ʈ"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public string DisplayingText
        {
            set
            {
                _DisplayingText = value;
                Invalidate();
            }
            get
            {
                return _DisplayingText;
            }
        }

        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ���� �ؽ�Ʈ"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public bool DrawShadow
        {
            set
            {
                _DrawShadow = value;
                Invalidate();
            }
            get
            {
                return _DrawShadow;
            }
        }

        [Browsable(true),
        CategoryAttribute("ProgressBar Layout"),
        Description("ǥ���� �ؽ�Ʈ"),
        System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
        ]
        public bool NegativeText
        {
            set
            {
                _NegativeText = value;
                Invalidate();
            }
            get
            {
                return _NegativeText;
            }
        }

        #endregion
    }
}
