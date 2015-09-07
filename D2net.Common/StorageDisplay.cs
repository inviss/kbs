using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// UserControl1에 대한 요약 설명입니다.
	/// </summary>
	public class Storagedisplay : System.Windows.Forms.Panel
	{
		/// <summary>
		/// 필수 디자이너 변수입니다.
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
		/// 사용 중인 모든 리소스를 정리합니다.
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

		#region 구성 요소 디자이너에서 생성한 코드
		/// <summary>
		/// 디자이너 지원에 필요한 메서드입니다. 
		/// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
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
		/// 그래프의 어두운 색(색1)을 설정
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("그래프의 어두운 색(색1)을 설정"),
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
		/// 그래프의 밝은색(색2)을 설정
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("그래프의 밝은색(색2)을 설정"),
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
		/// 텍스트의 색깔을 설정
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("텍스트의 색깔을 설정"),
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
		/// 그래프 추가
		/// </summary>
		/// <param name="name">이름</param>
		/// <param name="free">남은 용량</param>
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
		/// 이미 생성된 그래프의 값을 변환
		/// </summary>
		/// <param name="index">몇번째 그래프</param>
		/// <param name="name">표시 텍스트</param>
		/// <param name="free">남은 용량</param>
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
		/// 이미 생성된 그래프의 색상을 변환
		/// </summary>
		/// <param name="index">몇번째 그래프</param>
		/// <param name="DarkColor">어두운 색상(색1)</param>
		/// <param name="LightColor">밝은 색상(색2)</param>
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
		/// 화면에 표시된 그래프를 모두 제거
		/// </summary>
		public void ClearGraph()
		{
			_hdd.Clear();
			Invalidate();
		}

		/// <summary>
		/// 입체감을 표시할지 여부
		/// </summary>
		[Browsable(true),
		CategoryAttribute("StorageDisplay Setting"),
		Description("입체감을 표시할지 여부"),
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
