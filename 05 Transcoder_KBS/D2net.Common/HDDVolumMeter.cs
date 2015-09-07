using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// HDDVolumMeter�� ���� ��� �����Դϴ�.
	/// </summary>
    public class HDDVolumMeter : System.Windows.Forms.Label
    {
        /// <summary> 
        /// �ʼ� �����̳� �����Դϴ�.
        /// </summary>
        private System.ComponentModel.Container components = null;
        private Color _LineColor = Color.Black;
        private Color _FreeColor = Color.White;
        private Color _FillColor = Color.Magenta;
        private Brush _BackBrush = null;
        private Brush _LineBrush = null;
        private Brush _FreeBrush = null;
        private Brush _FillBrush = null;
        private Brush _TextBrush = null;
        private StringFormat _DrawFormat = null;
        private int _Rate = 0;

        public HDDVolumMeter()
        {
            // �� ȣ���� Windows.Forms Form �����̳ʿ� �ʿ��մϴ�.
            InitializeComponent();

            _BackBrush = new SolidBrush(BackColor);
            _LineBrush = new SolidBrush(_LineColor);
            _FreeBrush = new SolidBrush(_FreeColor);
            _FillBrush = new SolidBrush(_FillColor);
            _TextBrush = new SolidBrush(ForeColor);

            _DrawFormat = new StringFormat();
            _DrawFormat.FormatFlags = StringFormatFlags.LineLimit;
            _DrawFormat.Alignment = StringAlignment.Center;
        }

        /// <summary> 
        /// ��� ���� ��� ���ҽ��� �����մϴ�.
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

        public override Color BackColor
        {
            get { return base.BackColor; }
            set 
            {
                base.BackColor = value;
                lock (this) { _BackBrush = new SolidBrush(base.BackColor); }
                Invalidate();
            }
        }

        public Color LineColor
        {
            get { return _LineColor; }
            set 
            {
                _LineColor = value;
                lock (this) { _LineBrush = new SolidBrush(_LineColor); }
                Invalidate();
            }
        }

        public Color FreeColor
        {
            get { return _FreeColor; }
            set 
            {
                _FreeColor = value;
                lock (this) { _FreeBrush = new SolidBrush(_FreeColor); }
                Invalidate();
            }
        }

        public Color FillColor
        {
            get { return _FillColor; }
            set 
            {
                _FillColor = value;
                lock (this) { _FillBrush = new SolidBrush(_FillColor); }
                Invalidate();
            }
        }

        public override Color ForeColor
        {
            get
            {
                return base.ForeColor;
            }
            set
            {
                base.ForeColor = value;
                lock (this) { _TextBrush = new SolidBrush(base.ForeColor); }
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
                lock (this) { base.Font = value; }
                Invalidate();
            }
        }

        public override string Text
        {
            get
            {
                return base.Text;
            }
            set
            {
                lock (this) { base.Text = value; }
                Invalidate();
            }
        }


        public int Rate
        {
            get { return _Rate; }
            set
            { 
                if (value < 0 ||
                    value > 100)
                    throw new Exception("���丮�� ä�� ������ 0%���� 100%�����Դϴ�.");
                lock (this) { _Rate = value; }
                Invalidate();
            }
        }

		#region ���� ��� �����̳ʿ��� ������ �ڵ�
		/// <summary> 
		/// �����̳� ������ �ʿ��� �޼����Դϴ�. 
		/// �� �޼����� ������ �ڵ� ������� �������� ���ʽÿ�.
		/// </summary>
		private void InitializeComponent()
		{
            // 
            // HDDVolumMeter
            // 
            this.BackColor = System.Drawing.SystemColors.Control;
            this.Resize += new System.EventHandler(this.HDDVolumMeter_Resize);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.HDDVolumMeter_Paint);

        }
		#endregion

        private void HDDVolumMeter_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
        {
            string rate;

            lock (this)
            {
                e.Graphics.FillRectangle(_BackBrush, 0, 0, Bounds.Width, Bounds.Height);
                e.Graphics.FillEllipse(_LineBrush, 0, 0, Bounds.Width, Bounds.Height);
                e.Graphics.FillEllipse(_FreeBrush, 1, 1, Bounds.Width - 2, Bounds.Height - 2);
                if (_Rate != 0)
                    e.Graphics.FillPie(_FillBrush, 1, 1, Bounds.Width - 2, Bounds.Height - 2, -90, _Rate * 360 / 100);

                rate = _Rate.ToString() + "%";
                
                _DrawFormat.LineAlignment = StringAlignment.Far;
                e.Graphics.DrawString(Text, Font, _TextBrush, 
                    new RectangleF(0, 0, Bounds.Width, Bounds.Height / 2), 
                    _DrawFormat);

                _DrawFormat.LineAlignment = StringAlignment.Near;
                e.Graphics.DrawString(rate, Font, _TextBrush, 
                    new RectangleF(0, Bounds.Height / 2, Bounds.Width, Bounds.Height - Bounds.Height / 2), 
                    _DrawFormat);
            }
        }

        private void HDDVolumMeter_Resize(object sender, System.EventArgs e)
        {
            Invalidate();
        }
	}
}
