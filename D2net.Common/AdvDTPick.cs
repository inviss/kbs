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
	public class AdvDTPick : System.Windows.Forms.UserControl
	{
		/// <summary>
		/// 필수 디자이너 변수입니다.
		/// </summary>
		private System.ComponentModel.Container components = null;
		private System.Windows.Forms.TextBox DisplayDate;
		private System.Windows.Forms.Button btnDropCalender;
		private CalenderForm cForm = null;
		private DateTime _Value;

		#region CreateEvents
		public delegate void SendDate(object sender, EventArgs e);
		public event SendDate SelectedDay;
		public event SendDate ChangeDay;
		public event SendDate DisplayCalender;
		#endregion

		public AdvDTPick()
		{
			// 이 호출은 Windows.Forms Form 디자이너에 필요합니다.
			InitializeComponent();

			// TODO: InitComponent를 호출한 다음 초기화 작업을 추가합니다.
			cForm = new CalenderForm();
			this.cForm.SelectedDay += new D2net.Common.UI.CalenderForm.SendDate(cForm_SelectedDay);
			this.cForm.ChangeDay += new D2net.Common.UI.CalenderForm.SendDate(cForm_ChangeDay);
			cForm.Hide();
			this.Height = this.DisplayDate.Height;
			this.DisplayDate.SendToBack();
			this.btnDropCalender.BringToFront();
			
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
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(AdvDTPick));
			this.DisplayDate = new System.Windows.Forms.TextBox();
			this.btnDropCalender = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// DisplayDate
			// 
			this.DisplayDate.Dock = System.Windows.Forms.DockStyle.Fill;
			this.DisplayDate.Location = new System.Drawing.Point(0, 0);
			this.DisplayDate.Name = "DisplayDate";
			this.DisplayDate.Size = new System.Drawing.Size(150, 21);
			this.DisplayDate.TabIndex = 2;
			this.DisplayDate.Text = "textBox1";
			// 
			// btnDropCalender
			// 
			this.btnDropCalender.Image = ((System.Drawing.Image)(resources.GetObject("btnDropCalender.Image")));
			this.btnDropCalender.Location = new System.Drawing.Point(128, 0);
			this.btnDropCalender.Name = "btnDropCalender";
			this.btnDropCalender.Size = new System.Drawing.Size(16, 17);
			this.btnDropCalender.TabIndex = 1;
			this.btnDropCalender.Click += new System.EventHandler(this.btnDropCalender_Click);
			this.btnDropCalender.Leave += new System.EventHandler(this.btnDropCalender_Leave);
			// 
			// AdvDTPick
			// 
			this.Controls.Add(this.btnDropCalender);
			this.Controls.Add(this.DisplayDate);
			this.Name = "AdvDTPick";
			this.Size = new System.Drawing.Size(150, 21);
			this.Resize += new System.EventHandler(this.AdvDTPick_Resize);
			this.Layout += new System.Windows.Forms.LayoutEventHandler(this.AdvDTPick_Layout);
			this.ResumeLayout(false);

		}
		#endregion

		private void AdvDTPick_Layout(object sender, System.Windows.Forms.LayoutEventArgs e)
		{
			this.Height = this.DisplayDate.Height;
			this.DisplayDate.SendToBack();
			this.btnDropCalender.BringToFront();
			_Value = DateTime.Today;
			this.DisplayDate.Text = _Value.Year.ToString()+"년 "+
				_Value.Month.ToString()+"월 "+
				_Value.Day.ToString()+"일 "+
				((DayOfWeek)_Value.DayOfWeek).ToString();
		}

		private void AdvDTPick_Resize(object sender, System.EventArgs e)
		{
			this.Height = this.DisplayDate.Height;
			this.btnDropCalender.SetBounds(this.Width - 18, 2,16,this.Height-4);
		}

		private void btnDropCalender_Click(object sender, System.EventArgs e)
		{
			Point p = this.PointToScreen(new Point(0,this.Height));
			cForm.SetDesktopLocation(p.X,p.Y);
			cForm.SetPoint = p;
			cForm.Show();
			EventArgs ea = new EventArgs();
			DisplayCalender(sender,ea);
			//cForm.TestFunc();

		}

		private void btnDropCalender_Leave(object sender, System.EventArgs e)
		{
			cForm.Hide();		
		}

		public DateTime[] BoldDates
		{
			set
			{
				this.cForm.SetBoldDates = null;
				this.cForm.SetBoldDates = value;
			}
		}

		public DateTime Value
		{
			get
			{
				return _Value;
			}
			set
			{
				_Value = value;
				cForm.Value = _Value;
			}
		}

		private void cForm_SelectedDay(object sender, System.EventArgs e)
		{
			EventArgs ea = new EventArgs();
			_Value = (DateTime)sender;
			this.DisplayDate.Text = _Value.Year.ToString()+"년 "+
				_Value.Month.ToString()+"월 "+
				_Value.Day.ToString()+"일 "+
				((DayOfWeek)_Value.DayOfWeek).ToString();
			SelectedDay(sender,ea);
		}

		public System.Windows.Forms.BorderStyle CalenderBorderStyle
		{
			get
			{
				return this.cForm.CalenderBorderStyle;
			}
			set
			{
				this.cForm.CalenderBorderStyle = value;
			}
		}


		enum DayOfWeek
		{
			일요일 = 0,
			월요일,
			화요일,
			수요일,
			목요일,
			금요일,
			토요일,
			
		};

		private void cForm_ChangeDay(object sender, EventArgs e)
		{
			EventArgs ea = new EventArgs();
			ChangeDay(sender,ea);
		}

		public void TestFunc()
		{
			this.cForm.TestFunc();
		}

		public void SetBoldDays(bool[] bDates)
		{
			this.cForm.SetBoldays(bDates);

		}
	}
}
