using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// UserControl1�� ���� ��� �����Դϴ�.
	/// </summary>
	public class Storagedisplay : System.Windows.Forms.Panel
	{
		/// <summary>
		/// �ʼ� �����̳� �����Դϴ�.
		/// </summary>
		private System.ComponentModel.Container components = null;
		private ArrayList _hdd = new ArrayList();
		private Color _GraphDarkColor = Color.Cyan;
		private Color _GraphLightColor = Color.White;
		private Color _TextColor = Color.DarkGreen;
		private bool _Is3D = true;
		private Brush _GraphBrush;

		public sealed class _storage
		{
			public string storageName = "";
			public int Percentage = 0;
			public Color LightColor = Color.LightYellow;
			public Color DarkColor = Color.DarkOrange;
		}
		
		public Storagedisplay()
		{
			InitializeComponent();
			SetStyle(ControlStyles.DoubleBuffer | ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint , true);
			SetStyle(ControlStyles.UserMouse, true);
		}

		/// <summary>
		/// ��� ���� ��� ���ҽ��� �����մϴ�.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if( components != null )
					components.Dispose();
			}
			base.Dispose( disposing );
		}

		#region ���� ��� �����̳ʿ��� ������ �ڵ�
		/// <summary>
		/// �����̳� ������ �ʿ��� �޼����Դϴ�. 
		/// �� �޼����� ������ �ڵ� ������� �������� ���ʽÿ�.
		/// </summary>
		private void InitializeComponent()
		{
			// 
			// Storagedisplay
			// 
			this.Name = "Storagedisplay";
			this.Size = new System.Drawing.Size(184, 104);
			this.Paint += new System.Windows.Forms.PaintEventHandler(this.Storagedisplay_Paint);

		}
		#endregion

		private void Storagedisplay_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			try
			{
				e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
				e.Graphics.FillRectangle(new SolidBrush(this.BackColor),0,0,this.Width,this.Height);			
				if(_hdd.Count != 0)
				{
					int rgn = this.Width/_hdd.Count;
					int center = (int)rgn/2;
					for(int i = 0; i < _hdd.Count; i++ )
					{
						e.Graphics.FillEllipse(new SolidBrush(Color.White),((int)(rgn/10)+(rgn*i)),((int)this.Height/10),((int)rgn*8/10),((int)this.Height*8/10));
						if(_Is3D)
						{
							e.Graphics.FillPie(new SolidBrush(Color.Gray),((float)(rgn/8)+(rgn*i)),((float)this.Height/8),((float)rgn*6/8),((float)this.Height*6/8),(float)0.0,(float)(360*((_storage)_hdd[i]).Percentage/100));
							_GraphBrush = new System.Drawing.Drawing2D.LinearGradientBrush(
								new Rectangle(((int)(rgn/8)+(rgn*i)-(rgn/30)),((int)this.Height/8-(rgn/30)),((int)rgn*6/8),((int)this.Height*6/8)),
								((_storage)_hdd[i]).LightColor,
								((_storage)_hdd[i]).DarkColor,
								60,
								true);

							e.Graphics.FillPie(
                                _GraphBrush,
								((float)(rgn/8)+(rgn*i)-(rgn/30)),((float)this.Height/8-(rgn/30)),((float)rgn*6/8),((float)this.Height*6/8),(float)0.0,(float)(360*((_storage)_hdd[i]).Percentage/100));
						}
						else
						{
							_GraphBrush = new System.Drawing.Drawing2D.LinearGradientBrush(
								new Rectangle(((int)(rgn/8)+(rgn*i)-(rgn/30)),((int)this.Height/8-(rgn/30)),((int)rgn*6/8),((int)this.Height*6/8)),
								((_storage)_hdd[i]).LightColor,
								((_storage)_hdd[i]).DarkColor,
								60,
								true);
							e.Graphics.FillPie(_GraphBrush,((float)(rgn/8)+(rgn*i)),((float)this.Height/8),((float)rgn*6/8),((float)this.Height*6/8),(float)0.0,(float)(360*((_storage)_hdd[i]).Percentage/100));

						}
						e.Graphics.DrawString(((_storage)_hdd[i]).storageName+" / "+((_storage)_hdd[i]).Percentage+"%",this.Font,
							new SolidBrush(_TextColor),new Rectangle(rgn/3+(rgn*i),this.Height/2,rgn/2,Height/5));
					}
				}
			}
			catch(Exception err)
			{
				throw err;
			}
		}

		private void CreateBrush()
		{

		}

		private void SetGraphColor()
		{
			if(_hdd.Count >0)
			{
				for(int i = 0; i < _hdd.Count;i++)
				{
					((_storage)_hdd[i]).DarkColor = _GraphDarkColor;
					((_storage)_hdd[i]).LightColor = _GraphLightColor;				
				}
			}
			Invalidate();
		}

		/// <summary>
		/// �׷����� ��ο� ��(��1)�� ����
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("�׷����� ��ο� ��(��1)�� ����"),
		System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
		]
		public Color GraphDarkColor
		{
			set
			{
				_GraphDarkColor = value;
				SetGraphColor();
			}
			get
			{
				return _GraphDarkColor;
			}
		}

		/// <summary>
		/// �׷����� ������(��2)�� ����
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("�׷����� ������(��2)�� ����"),
		System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
		]
		public Color GraphLightColor
		{
			set
			{
				_GraphLightColor = value;
				SetGraphColor();
			}
			get
			{
				return _GraphLightColor;
			}
		}

		/// <summary>
		/// �ؽ�Ʈ�� ������ ����
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("�ؽ�Ʈ�� ������ ����"),
		System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
		]
		public Color TextColor
		{
			set 
			{
				_TextColor = value; 
			}
			get
			{
				return _TextColor;
			}
		}

		/// <summary>
		/// �׷��� �߰�
		/// </summary>
		/// <param name="name">�̸�</param>
		/// <param name="free">���� �뷮</param>
		public void AddValue(string name, int free)
		{
			try
			{
				_storage stg = new _storage();
				stg.storageName = name;
				stg.Percentage = free;
				stg.DarkColor = _GraphDarkColor;
				stg.LightColor = _GraphLightColor;
				_hdd.Add((object)stg);
				Invalidate();
			}
			catch(Exception err)
			{
				throw err;				
			}
		}

		public int StorageCount
		{
			get { return _hdd.Count; }
		}

		/// <summary>
		/// �̹� ������ �׷����� ���� ��ȯ
		/// </summary>
		/// <param name="index">���° �׷���</param>
		/// <param name="name">ǥ�� �ؽ�Ʈ</param>
		/// <param name="free">���� �뷮</param>
		public void SetValue(int index, string name, int free)
		{
			try
			{
				if(index >= _hdd.Count)
				{
					return;
				}
				((_storage)_hdd[index]).storageName = name;
				((_storage)_hdd[index]).Percentage = free;
				Invalidate();
			}
			catch(Exception err)
			{
				throw err;
			}
		}

		/// <summary>
		/// �̹� ������ �׷����� ������ ��ȯ
		/// </summary>
		/// <param name="index">���° �׷���</param>
		/// <param name="DarkColor">��ο� ����(��1)</param>
		/// <param name="LightColor">���� ����(��2)</param>
		public void SetColor(int index, Color DarkColor, Color LightColor)
		{
			try
			{
				if(index >= _hdd.Count)
				{
					return;
				}
				((_storage)_hdd[index]).DarkColor = DarkColor;
				((_storage)_hdd[index]).LightColor = LightColor;
				Invalidate();
			}
			catch(Exception err)
			{
				throw err;
			}
		}

		/// <summary>
		/// ȭ�鿡 ǥ�õ� �׷����� ��� ����
		/// </summary>
		public void ClearGraph()
		{
			_hdd.Clear();
			Invalidate();
		}

		/// <summary>
		/// ��ü���� ǥ������ ����
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("��ü���� ǥ������ ����"),
		System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
		]
		public bool Is3D
		{
			set 
			{
				_Is3D = value;
				Invalidate();
			}
			get
			{
                return _Is3D;
			}
		}

		
	}
}
