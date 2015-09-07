using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace D2net.Common.UI
{
	/// <summary>
	/// Form1에 대한 요약 설명입니다.
	/// </summary>
	public class CalenderForm : System.Windows.Forms.Form
	{
		/// <summary>
		/// 필수 디자이너 변수입니다.
		/// </summary>
		private System.ComponentModel.Container components = null;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.MonthCalendar monthCalendar1;
		private Point _SetPoint = new Point();
		private DateTime TempDate;
		private DateTime ValueDate;
		private D2net.Common.UI.DateTimePicker dateTimePicker;

		#region CreateEvents
		public delegate void SendDate(object sender, EventArgs e);
		public event SendDate SelectedDay;
		public event SendDate ChangeDay;
		#endregion

		public CalenderForm()
		{
			//
			// Windows Form 디자이너 지원에 필요합니다.
			//
			InitializeComponent();

			//
			// TODO: InitializeComponent를 호출한 다음 생성자 코드를 추가합니다.
			//
			this.TempDate = this.monthCalendar1.TodayDate;
			this.ValueDate = this.monthCalendar1.TodayDate;

			#region dateTimePicker1
			this.dateTimePicker = new D2net.Common.UI.DateTimePicker();
			// 
			// dateTimePicker1
			// 
			this.dateTimePicker.Location = new System.Drawing.Point(72, 80);
			this.dateTimePicker.Name = "dateTimePicker1";
			this.dateTimePicker.TabIndex = 0;
			this.dateTimePicker.OnDropDownPreChangeState += new DropDownPreChangeState(dateTimePicker_OnDropDownChangeState);
			this.Controls.Add(this.dateTimePicker);
			#endregion
			

		}

		/// <summary>
		/// 사용 중인 모든 리소스를 정리합니다.
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

		#region Windows Form 디자이너에서 생성한 코드
		/// <summary>
		/// 디자이너 지원에 필요한 메서드입니다.
		/// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
		/// </summary>
		private void InitializeComponent()
		{
			this.panel1 = new System.Windows.Forms.Panel();
			this.monthCalendar1 = new System.Windows.Forms.MonthCalendar();
			this.panel1.SuspendLayout();
			this.SuspendLayout();
			// 
			// panel1
			// 
			this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel1.Controls.Add(this.monthCalendar1);
			this.panel1.Location = new System.Drawing.Point(0, 0);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(160, 168);
			this.panel1.TabIndex = 1;
			// 
			// monthCalendar1
			// 
			this.monthCalendar1.Location = new System.Drawing.Point(0, 0);
			this.monthCalendar1.MaxSelectionCount = 1;
			this.monthCalendar1.Name = "monthCalendar1";
			this.monthCalendar1.TabIndex = 0;
			this.monthCalendar1.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.monthCalendar1_DateSelected_1);
			this.monthCalendar1.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.monthCalendar1_DateChanged);
			// 
			// CalenderForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
			this.ClientSize = new System.Drawing.Size(184, 200);
			this.ControlBox = false;
			this.Controls.Add(this.panel1);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "CalenderForm";
			this.ShowInTaskbar = false;
			this.Text = "Form1";
			this.TopMost = true;
			this.Resize += new System.EventHandler(this.CalenderForm_Resize);
			this.Layout += new System.Windows.Forms.LayoutEventHandler(this.CalenderForm_Layout);
			this.VisibleChanged += new System.EventHandler(this.CalenderForm_VisibleChanged);
			this.Leave += new System.EventHandler(this.CalenderForm_Leave);
			this.Deactivate += new System.EventHandler(this.CalenderForm_Deactivate);
			this.panel1.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion

		private void CalenderForm_Layout(object sender, System.Windows.Forms.LayoutEventArgs e)
		{
			
			this.panel1.Size = this.monthCalendar1.Size;
			this.Size = this.panel1.Size;		
		
		}

		private void CalenderForm_Resize(object sender, System.EventArgs e)
		{
			this.panel1.Size = this.monthCalendar1.Size;
			this.Size = this.panel1.Size;		
		}

		private void CalenderForm_Leave(object sender, System.EventArgs e)
		{
			this.Hide();
		}

		private void CalenderForm_Deactivate(object sender, System.EventArgs e)
		{
			this.Hide();		
		}

		private void CalenderForm_VisibleChanged(object sender, System.EventArgs e)
		{
			this.SetDesktopLocation(_SetPoint.X, _SetPoint.Y);
		
		}

		private void monthCalendar1_DateSelected_1(object sender, System.Windows.Forms.DateRangeEventArgs e)
		{
			if(TempDate == e.Start)
			{
				System.Diagnostics.Debug.WriteLine("DateSelected : "+e.Start.Date.ToString());
				EventArgs ea = new EventArgs();
				this.ValueDate = e.Start;
				SelectedDay((object)ValueDate, ea);
				this.Hide();
			}
		}

		private void monthCalendar1_DateChanged(object sender, System.Windows.Forms.DateRangeEventArgs e)
		{
			if(TempDate == e.Start)
			{
				System.Diagnostics.Debug.WriteLine("DateChangeed : "+e.Start.Date.ToString());
				EventArgs ea = new EventArgs();
				ChangeDay((object)e.Start, ea);
			}
			else
			{
				TempDate = e.Start;
			}
		}

		public System.Windows.Forms.BorderStyle CalenderBorderStyle
		{
			get
			{
				return this.panel1.BorderStyle;
			}
			set
			{
				this.panel1.BorderStyle = value;
				Invalidate();
			}
		}

		public Point SetPoint
		{
			get
			{
				return _SetPoint;
			}
			set
			{
				_SetPoint = value;
			}
		}

		public DateTime[] SetBoldDates
		{
			set
			{
				//this.monthCalendar1.AnnuallyBoldedDates = null;
				this.monthCalendar1.AnnuallyBoldedDates = value;
			}
		}

		public DateTime Value
		{
			get
			{
				return ValueDate;
			}
			set
			{
				ValueDate = value;
			}
		}

		public void dateTimePicker_OnDropDownChangeState(object sender, DropDownChangeStateArgs args)
		{
			args[6] = true;
			System.Diagnostics.Debug.WriteLine("Min = " + args.Min);
			System.Diagnostics.Debug.WriteLine("Max = " + args.Max);
		}


		public void TestFunc()
		{
			DropDownChangeStateArgs args = new DropDownChangeStateArgs(3,1);
			for(int i = 13; i < 20; i++)
			{
				args[i] = true;
			}

			//int months = this.dateTimePicker.GetMonthRange(this.monthCalendar1.Handle, ref s, ref end, 
			//	D2net.Common.UI.DateTimePicker.GMR_DAYSTATE);

			
			unsafe
			{
				this.dateTimePicker.SetDayState(this.monthCalendar1.Handle, 3, new IntPtr(args._Buffer));
			}
			//this.dateTimePicker.SetDayState(this.monthCalendar1.Handle,month,new IntPtr(arg
		}

		public void SetBoldays(bool[] BoldDates)
		{
			DropDownChangeStateArgs args = new DropDownChangeStateArgs(3,1);
			for(int i = 0; i < 31; i++)
			{
				if(BoldDates[i])
					args[i] = true;
			}
			unsafe
			{
				this.dateTimePicker.SetDayState(this.monthCalendar1.Handle, 3, new IntPtr(args._Buffer));
			}
		}
	}
}
