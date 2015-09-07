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
	public class AdvDTPick : System.Windows.Forms.UserControl
	{
		/// <summary>
		/// �ʼ� �����̳� �����Դϴ�.
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
			// �� ȣ���� Windows.Forms Form �����̳ʿ� �ʿ��մϴ�.
			InitializeComponent();

			// TODO: InitComponent�� ȣ���� ���� �ʱ�ȭ �۾��� �߰��մϴ�.
			cForm = new CalenderForm();
			this.cForm.SelectedDay += new D2net.Common.UI.CalenderForm.SendDate(cForm_SelectedDay);
			this.cForm.ChangeDay += new D2net.Common.UI.CalenderForm.SendDate(cForm_ChangeDay);
			cForm.Hide();
			this.Height = this.DisplayDate.Height;
			this.DisplayDate.SendToBack();
			this.btnDropCalender.BringToFront();
			
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
			this.DisplayDate.Text = _Value.Year.ToString()+"�� "+
				_Value.Month.ToString()+"�� "+
				_Value.Day.ToString()+"�� "+
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
			this.DisplayDate.Text = _Value.Year.ToString()+"�� "+
				_Value.Month.ToString()+"�� "+
				_Value.Day.ToString()+"�� "+
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
			�Ͽ��� = 0,
			������,
			ȭ����,
			������,
			�����,
			�ݿ���,
			�����,
			
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
