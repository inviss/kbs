using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Threading;
using System.Diagnostics;
using Microsoft.Win32;

namespace D2net.Common.UI
{
	/// <summary>
	/// Summary description for SplashScreen.
	/// </summary>
	public class SplashWindowForm : System.Windows.Forms.Form
	{
		// Threading
		static SplashWindowForm ms_frmSplash = null;
		static Thread ms_oThread = null;

		// Fade in and out.
		private double m_dblOpacityIncrement = .05;
		private double m_dblOpacityDecrement = .08;
		private const int TIMER_INTERVAL = 50;

		// Status and progress bar
		static string ms_sStatus;
		private double m_dblCompletionFraction = 0;
		private Rectangle m_rProgress;
		public static Color SColor = Color.LightGreen;
		public static Color EColor = Color.DarkGreen;

		// Progress smoothing
		private double m_dblLastCompletionFraction = 0.0;
		private double m_dblPBIncrementPerTimerInterval = .015;

		// Self-calibration support
		private bool m_bFirstLaunch = false;
		private DateTime m_dtStart;
		private bool m_bDTSet = false;
		private int m_iIndex = 1;
		private int m_iActualTicks = 0;
		private ArrayList m_alPreviousCompletionFraction;
		private ArrayList m_alActualTimes = new ArrayList();
		private const string REG_KEY_INITIALIZATION = "Initialization";
		private const string REGVALUE_PB_MILISECOND_INCREMENT = "Increment";
		private const string REGVALUE_PB_PERCENTS = "Percents";

		private System.Windows.Forms.Label lblStatus;
		private System.Windows.Forms.Label lblTimeRemaining;
		private System.Windows.Forms.Timer timer1;
		private System.Windows.Forms.Panel pnlStatus;
		private System.ComponentModel.IContainer components;
		private static System.Drawing.Image BKImage = null;
		private static System.Windows.Forms.BorderStyle BorderStyle = System.Windows.Forms.BorderStyle.None;
		private static Font _TextFont = new Font("����",10);
		private static Color _TextBackgroundColor = Color.Transparent;
		private static Color _TextColor = Color.Black;
		private static System.Windows.Forms.BorderStyle _TextLabelStyle = System.Windows.Forms.BorderStyle.None;
		
		/*
		[Browsable(true),
		CategoryAttribute("Temp"),
		Description("�ִ밪 ����"),
		System.ComponentModel.RefreshProperties(RefreshProperties.Repaint)
		]
		public Color BeginningColor
		{
			set
			{
				SColor = value;
			}
			get
			{
				return SColor;
			}
		}

		public Color EndColor
		{
			set
			{
				EColor = value;
			}
			get
			{
				return EColor;
			}
		}
		*/

		/// <summary>
		/// Constructor
		/// </summary>
		public SplashWindowForm(System.Drawing.Image img,
			Color scolor,
			Color ecolor,
			System.Windows.Forms.BorderStyle borderStyle,
			Font tFont,
			Color tColor,
			Color tBKColor,
			System.Windows.Forms.BorderStyle tlStyle)
		{
			///_BKImage,LColor,RColor,BorderStyle,_TextFont,_TextBackgroundColor,_TextLabelStyle
			InitializeComponent();
			this.Opacity = .00;
			timer1.Interval = TIMER_INTERVAL;
			timer1.Start();

			this.BackgroundImage = img;
			if(BackgroundImage != null)
			{
				this.ClientSize = this.BackgroundImage.Size;				
			}
			
			BKImage = img;
			SColor = scolor;
			EColor = ecolor;
			BorderStyle = borderStyle;
			_TextFont = tFont;
			_TextColor = tColor;
			_TextBackgroundColor = tBKColor;
			_TextLabelStyle = tlStyle;

			this.lblStatus.Font = tFont;
			this.lblStatus.ForeColor = tColor;
			this.lblStatus.BackColor = tBKColor;
			this.lblStatus.BorderStyle = tlStyle;
			this.lblStatus.SetBounds(
				this.Size.Width/8,this.Size.Height*10/14,this.Size.Width*3/4,this.Size.Height/14);
			this.pnlStatus.BorderStyle = borderStyle;
			this.pnlStatus.SetBounds(
				this.Size.Width/8,this.Size.Height*11/14,this.Size.Width*3/4,this.Size.Height/14);
			this.lblTimeRemaining.Font = tFont;
			this.lblTimeRemaining.ForeColor = tColor;
			this.lblTimeRemaining.BackColor = tBKColor;
			this.lblTimeRemaining.BorderStyle = tlStyle;
			this.lblTimeRemaining.SetBounds(
				this.Size.Width/8,this.Size.Height*12/14,this.Size.Width*3/4,this.Size.Height/14);
			
			//this.BackgroundImage = BKImage;
			//Invalidate();
		}

		/// <summary>
		/// Clean up any resources being used.
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

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			this.lblStatus = new System.Windows.Forms.Label();
			this.pnlStatus = new System.Windows.Forms.Panel();
			this.lblTimeRemaining = new System.Windows.Forms.Label();
			this.timer1 = new System.Windows.Forms.Timer(this.components);
			this.SuspendLayout();
			// 
			// lblStatus
			// 
			this.lblStatus.BackColor = System.Drawing.Color.Transparent;
			this.lblStatus.Location = new System.Drawing.Point(118, 212);
			this.lblStatus.Name = "lblStatus";
			this.lblStatus.Size = new System.Drawing.Size(335, 15);
			this.lblStatus.TabIndex = 0;
			this.lblStatus.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
			this.lblStatus.DoubleClick += new System.EventHandler(this.SplashScreen_DoubleClick);
			// 
			// pnlStatus
			// 
			this.pnlStatus.BackColor = System.Drawing.Color.Transparent;
			this.pnlStatus.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.pnlStatus.Location = new System.Drawing.Point(117, 236);
			this.pnlStatus.Name = "pnlStatus";
			this.pnlStatus.Size = new System.Drawing.Size(335, 25);
			this.pnlStatus.TabIndex = 1;
			this.pnlStatus.Paint += new System.Windows.Forms.PaintEventHandler(this.pnlStatus_Paint);
			this.pnlStatus.DoubleClick += new System.EventHandler(this.SplashScreen_DoubleClick);
			// 
			// lblTimeRemaining
			// 
			this.lblTimeRemaining.BackColor = System.Drawing.Color.Transparent;
			this.lblTimeRemaining.Location = new System.Drawing.Point(117, 269);
			this.lblTimeRemaining.Name = "lblTimeRemaining";
			this.lblTimeRemaining.Size = new System.Drawing.Size(335, 17);
			this.lblTimeRemaining.TabIndex = 2;
			this.lblTimeRemaining.Text = "Time remaining";
			this.lblTimeRemaining.DoubleClick += new System.EventHandler(this.SplashScreen_DoubleClick);
			// 
			// timer1
			// 
			this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
			// 
			// SplashWindowForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
			this.BackColor = System.Drawing.Color.LightGray;
			this.ClientSize = new System.Drawing.Size(471, 336);
			this.Controls.Add(this.lblTimeRemaining);
			this.Controls.Add(this.pnlStatus);
			this.Controls.Add(this.lblStatus);
			this.ForeColor = System.Drawing.Color.Black;
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
			this.Name = "SplashWindowForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "SplashScreen";
			this.TopMost = true;
			this.DoubleClick += new System.EventHandler(this.SplashScreen_DoubleClick);
			this.ResumeLayout(false);

		}
		#endregion

		// ************* Static Methods *************** //

		// A static method to create the thread and 
		// launch the SplashScreen.
		public void ShowSplashScreen(System.Drawing.Image img)
		{
			// Make sure it's only launched once.
			if( ms_frmSplash != null )
				return;
			ms_oThread = new Thread( new ThreadStart(SplashWindowForm.ShowForm));
			ms_oThread.IsBackground = true;
			ms_oThread.ApartmentState = ApartmentState.STA;
			ms_oThread.Start();
		}

		// A property returning the splash screen instance
		static public SplashWindowForm SplashForm 
		{
			get
			{
				return ms_frmSplash;
			} 
		}

		// A private entry point for the thread.
		static private void ShowForm()
		{
			ms_frmSplash = new SplashWindowForm(BKImage, SColor, EColor,BorderStyle,_TextFont,_TextColor,_TextBackgroundColor,_TextLabelStyle);
			Application.Run(ms_frmSplash);
		}

		// A static method to close the SplashScreen
		public void CloseForm()
		{
			if( ms_frmSplash != null && ms_frmSplash.IsDisposed == false )
			{
				// Make it start going away.
				ms_frmSplash.m_dblOpacityIncrement = - ms_frmSplash.m_dblOpacityDecrement;
			}
			ms_oThread = null;	// we don't need these any more.
			ms_frmSplash = null;
		}

		// A static method to set the status and update the reference.
		public void SetStatus(string newStatus)
		{
			SetStatus(newStatus, true);
		}

		// A static method to set the status and optionally update the reference.
		// This is useful if you are in a section of code that has a variable
		// set of status string updates.  In that case, don't set the reference.
		public void SetStatus(string newStatus, bool setReference)
		{
			ms_sStatus = newStatus;
			if( ms_frmSplash == null )
				return;
			if( setReference )
				ms_frmSplash.SetReferenceInternal();
		}

		// Static method called from the initializing application to 
		// give the splash screen reference points.  Not needed if
		// you are using a lot of status strings.
		static public void SetReferencePoint()
		{
			if( ms_frmSplash == null )
				return;
			ms_frmSplash.SetReferenceInternal();

		}

		// ************ Private methods ************

		// Internal method for setting reference points.
		private void SetReferenceInternal()
		{
			if( m_bDTSet == false )
			{
				m_bDTSet = true;
				m_dtStart = DateTime.Now;
				ReadIncrements();
			}
			double dblMilliseconds = ElapsedMilliSeconds();
			m_alActualTimes.Add(dblMilliseconds);
			m_dblLastCompletionFraction = m_dblCompletionFraction;
			if( m_alPreviousCompletionFraction != null && m_iIndex < m_alPreviousCompletionFraction.Count )
				m_dblCompletionFraction = (double)m_alPreviousCompletionFraction[m_iIndex++];
			else
				m_dblCompletionFraction = ( m_iIndex > 0 )? 1: 0;
		}

		// Utility function to return elapsed Milliseconds since the 
		// SplashScreen was launched.
		private double ElapsedMilliSeconds()
		{
			TimeSpan ts = DateTime.Now - m_dtStart;
			return ts.TotalMilliseconds;
		}

		// Function to read the checkpoint intervals from the previous invocation of the
		// splashscreen from the registry.
		private void ReadIncrements()
		{
			string sPBIncrementPerTimerInterval = RegistryAccess.GetStringRegistryValue( REGVALUE_PB_MILISECOND_INCREMENT, "0.0015");
			double dblResult;

			if( Double.TryParse(sPBIncrementPerTimerInterval, System.Globalization.NumberStyles.Float, System.Globalization.NumberFormatInfo.InvariantInfo, out dblResult) == true )
				m_dblPBIncrementPerTimerInterval = dblResult;
			else
				m_dblPBIncrementPerTimerInterval = .0015;

			string sPBPreviousPctComplete = RegistryAccess.GetStringRegistryValue( REGVALUE_PB_PERCENTS, "" );

			if( sPBPreviousPctComplete != "" )
			{
				string [] aTimes = sPBPreviousPctComplete.Split(null);
				m_alPreviousCompletionFraction = new ArrayList();

				for(int i = 0; i < aTimes.Length; i++ )
				{
					double dblVal;
					if( Double.TryParse(aTimes[i], System.Globalization.NumberStyles.Float, System.Globalization.NumberFormatInfo.InvariantInfo, out dblVal) )
						m_alPreviousCompletionFraction.Add(dblVal);
					else
						m_alPreviousCompletionFraction.Add(1.0);
				}
			}
			else
			{
				m_bFirstLaunch = true;
				lblTimeRemaining.Text = "";
			}
		}

		// Method to store the intervals (in percent complete) from the current invocation of
		// the splash screen to the registry.
		private void StoreIncrements()
		{
			string sPercent = "";
			double dblElapsedMilliseconds = ElapsedMilliSeconds();
			for( int i = 0; i < m_alActualTimes.Count; i++ )
				sPercent += ((double)m_alActualTimes[i]/dblElapsedMilliseconds).ToString("0.####", System.Globalization.NumberFormatInfo.InvariantInfo) + " ";

			RegistryAccess.SetStringRegistryValue( REGVALUE_PB_PERCENTS, sPercent );

			m_dblPBIncrementPerTimerInterval = 1.0/(double)m_iActualTicks;
			RegistryAccess.SetStringRegistryValue( REGVALUE_PB_MILISECOND_INCREMENT, m_dblPBIncrementPerTimerInterval.ToString("#.000000", System.Globalization.NumberFormatInfo.InvariantInfo));
		}

		//********* Event Handlers ************

		// Tick Event handler for the Timer control.  Handle fade in and fade out.  Also
		// handle the smoothed progress bar.
		private void timer1_Tick(object sender, System.EventArgs e)
		{
			
			lblStatus.Text = ms_sStatus;

			if( m_dblOpacityIncrement > 0 )
			{
				m_iActualTicks++;
				if( this.Opacity < 1 )
					this.Opacity += m_dblOpacityIncrement;
			}
			else
			{
				if( this.Opacity > 0 )
					this.Opacity += m_dblOpacityIncrement;
				else
				{
					StoreIncrements();
					this.Close();
					Debug.WriteLine("Called this.Close()");
				}
			}
			if( m_bFirstLaunch == false && m_dblLastCompletionFraction < m_dblCompletionFraction )
			{
				m_dblLastCompletionFraction += m_dblPBIncrementPerTimerInterval;
				int width = (int)Math.Floor(pnlStatus.ClientRectangle.Width * m_dblLastCompletionFraction);
				int height = pnlStatus.ClientRectangle.Height;
				int x = pnlStatus.ClientRectangle.X;
				int y = pnlStatus.ClientRectangle.Y;
				if( width > 0 && height > 0 )
				{
					m_rProgress = new Rectangle( x, y, width, height);
					pnlStatus.Invalidate(m_rProgress);
					int iSecondsLeft = 1 + (int)(TIMER_INTERVAL * ((1.0 - m_dblLastCompletionFraction)/m_dblPBIncrementPerTimerInterval)) / 1000;
					if( iSecondsLeft == 1 )
						lblTimeRemaining.Text = string.Format( "1 second remaining");
					else
						lblTimeRemaining.Text = string.Format( "{0} seconds remaining", iSecondsLeft);

				}
			}
		}

		// Paint the portion of the panel invalidated during the tick event.
		private void pnlStatus_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			
			if( m_bFirstLaunch == false && e.ClipRectangle.Width > 0 && m_iActualTicks > 1 )
			{
				LinearGradientBrush brBackground = new LinearGradientBrush(m_rProgress, SColor, EColor, LinearGradientMode.Horizontal);
				e.Graphics.FillRectangle(brBackground, m_rProgress);
			}
		}

		// Close the form if they double click on it.
		private void SplashScreen_DoubleClick(object sender, System.EventArgs e)
		{
			CloseForm();
		}

		
	}

	/// <summary>
	/// A class for managing registry access.
	/// </summary>
	public class RegistryAccess
	{
		private const string SOFTWARE_KEY = "Software";
		private const string COMPANY_NAME = "D2Net";
		private const string APPLICATION_NAME = "CM Express";

		// Method for retrieving a Registry Value.
		static public string GetStringRegistryValue(string key, string defaultValue)
		{
			RegistryKey rkCompany;
			RegistryKey rkApplication;

			rkCompany = Registry.CurrentUser.OpenSubKey(SOFTWARE_KEY, false).OpenSubKey(COMPANY_NAME, false);
			if( rkCompany != null )
			{
				rkApplication = rkCompany.OpenSubKey(APPLICATION_NAME, true);
				if( rkApplication != null )
				{
					foreach(string sKey in rkApplication.GetValueNames())
					{
						if( sKey == key )
						{
							return (string)rkApplication.GetValue(sKey);
						}
					}
				}
			}
			return defaultValue;
		}

		// Method for storing a Registry Value.
		static public void SetStringRegistryValue(string key, string stringValue)
		{
			RegistryKey rkSoftware;
			RegistryKey rkCompany;
			RegistryKey rkApplication;

			rkSoftware = Registry.CurrentUser.OpenSubKey(SOFTWARE_KEY, true);
			rkCompany = rkSoftware.CreateSubKey(COMPANY_NAME);
			if( rkCompany != null )
			{
				rkApplication = rkCompany.CreateSubKey(APPLICATION_NAME);
				if( rkApplication != null )
				{
					rkApplication.SetValue(key, stringValue);
				}
			}
		}

		
	}
}
