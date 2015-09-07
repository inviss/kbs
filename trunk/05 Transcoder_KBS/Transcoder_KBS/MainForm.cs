using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Threading;

using System.IO.Pipes;
using System.ServiceModel.Description;
using Transcoder_KBS.SoapObj;
using System.Diagnostics;
using System.IO;
using D2net.Common;
namespace Transcoder_KBS
{
    public partial class MainForm : Form
    {
#region DllImport
        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;
        public const int HT_CLOSE = 0x14;
        public const int HT_MIN = 0x08;
        public const int HT_MAX = 0x09;
        public const int HT_LEFT = 0x0A;
        public const int HT_RIGHT = 0x0B;
        public const int HT_TOP = 0x0C;
        public const int HT_TOPLEFT = 0x0D;
        public const int HT_TOPRIGHT = 0x0E;
        public const int HT_BOTTOM = 0x0F;
        public const int HT_BOTTOMLEFT = 0x10;
        public const int HT_BOTTOMRIGHT = 0x11;

        [System.Runtime.InteropServices.DllImport("user32.dll")]
        static extern uint DefWindowProc(IntPtr hWnd, uint uMsg, IntPtr wParam, IntPtr lParam);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern bool ReleaseCapture();
#endregion
#region Variables
        private List<Transcoder> _listTCs = null;
        public ServiceNavigatorService tc_Interface = null;
        private WorkAgent _wa = null;
        private Thread _MainThread = null;
        private Thread _TempErrorFileDelThread = null;
        /************************************************************************/
        string _ConfigPath = string.Format(@"{0}\Config\Config.ini", Application.StartupPath);
        ConfigForm _ConfigForm = null;
        ConfigInfo _Config = null;
        /************************************************************************/
        private ContextMenu _Menu = null;
        private MenuItem _Menu_Open = null;
        /************************************************************************/
        private bool _MouseLBDwon = false; 
        /************************************************************************/
#endregion
#region Consturctor
        public MainForm()
        {
            InitializeComponent();
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            // 지우기(erase phase) 단계 제거를 위한 AllPaintingInWmPaint 컨트롤 스타일 적용[5/22/2012 LJY]
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            // 더블 버퍼링되는 컨트롤을 위해 필요한 과정 [5/22/2012 LJY]
            this.SetStyle(ControlStyles.UserPaint, true);
            // 크기가 재조정될 때 컨트롤을 다시 그린다. [5/22/2012 LJY]
            this.SetStyle(ControlStyles.ResizeRedraw, true);
            CheckForIllegalCrossThreadCalls = false;
            
            lbl_Version.Text = string.Format("v_{0}", Application.ProductVersion);
        }
#endregion
#region Method
        private void SetUI()
        {
            try
            {
                string ImageDir = string.Format(@"{0}\skin", Application.StartupPath);
                string Image = string.Format(@"{0}\{1}", ImageDir, "setting.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    btn_Option.SetImage(bMap);
                }
                Image = string.Format(@"{0}\{1}", ImageDir, "logo.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    pic_Title.BackgroundImage = bMap;
                }
                Image = string.Format(@"{0}\{1}", ImageDir, "green.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    btn_Min.SetImage(bMap);
                }
                Image = string.Format(@"{0}\{1}", ImageDir, "red.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    btn_Close.SetImage(bMap);
                }
                Image = string.Format(@"{0}\{1}", ImageDir, "bg_list.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    pnl_Info.BackgroundImage = bMap;
                    pnl_Info.BackgroundImageLayout = ImageLayout.Stretch;
                }
                Image = string.Format(@"{0}\{1}", ImageDir, "ling_bg.png");
                if (File.Exists(Image))
                {
                    Bitmap bMap = new Bitmap(Image);
                    pnl_Info_Detail.BackgroundImage = bMap;
                    pnl_Info_Detail.BackgroundImageLayout = ImageLayout.Stretch;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Skin 파일이 비정상 입니다.", "UI 구성 중 오류 발생");
            }
        }
        private void Make_RoundingEdge()
        {
            Rectangle rectangle = this.DisplayRectangle;
            System.Drawing.Drawing2D.GraphicsPath path = new System.Drawing.Drawing2D.GraphicsPath();
            float arcRadius = 10F;
            int space = 1;

            path.AddArc(rectangle.X + space, rectangle.Y + space, arcRadius, arcRadius, 180, 90);
            path.AddArc(rectangle.X + rectangle.Width - arcRadius - space, rectangle.Y + space, arcRadius, arcRadius, 270, 90);
            path.AddArc(rectangle.X + rectangle.Width - arcRadius - space, rectangle.Y + rectangle.Height - arcRadius - space, arcRadius, arcRadius, 0, 90);
            path.AddArc(rectangle.X + space, rectangle.Y + rectangle.Height - arcRadius - space, arcRadius, arcRadius, 90, 90);

            path.CloseFigure();

            this.Region = new Region(path);
        }
        private void ShowDetailInfo(Job job)
        {
            try
            {
                lbl_Extention.Text = string.Format(".{0}", job.ext.ToUpper());
                lbl_VideoCodec.Text = (job.vdo_codec != null)? job.vdo_codec.ToUpper() : "";
                lbl_VideoBitrate.Text = string.Format("{0} kb",job.vdo_bit_rate);
                lbl_VideoFramerate.Text = (job.vdo_f_s != null)? job.vdo_f_s.ToUpper() : "";
                lbl_VideoResolution.Text = string.Format("{0} X {1}", job.vdo_hori, job.vdo_vert);
                lbl_AudioCodec.Text = (job.aud_codec != null)? job.aud_codec.ToUpper() : "";
                lbl_AudioBitrate.Text = string.Format("{0} kb", job.aud_bit_rate);
                lbl_AudioSampling.Text = string.Format("{0} kb", job.aud_s_rate);
                lbl_AudioChannel.Text = job.aud_chan.ToString();
            }
            catch (System.Exception ex)
            {

            }
        }
#endregion
#region Event 
        void _wa_OnGotNewJob(workflow wf)
        {
            try
            {
                txt_SrcPath.Text = string.Format("{0}{1}", wf.source_path, wf.source_file);
                txt_DesPath.Text = string.Format("{0}", wf.target_path);
                lbl_CTIID.Text = wf.cti_Id.ToString();
                if (wf.audio_mode == "02")
                    lbl_AudioMode.Text = "스테레오";
                else if (wf.audio_mode == "04")
                    lbl_AudioMode.Text = "화면해설";
                else
                    lbl_AudioMode.Text = "All Mixing";

                lbl_Extention.Text = string.Format(".{0}",wf.job[0].ext.ToUpper());
                lbl_VideoCodec.Text = (wf.job[0].vdo_codec != null)? wf.job[0].vdo_codec.ToUpper() : "";
                lbl_VideoBitrate.Text = string.Format("{0} kb", wf.job[0].vdo_bit_rate);
                lbl_VideoFramerate.Text = (wf.job[0].vdo_f_s != null)? wf.job[0].vdo_f_s.ToUpper() : "";
                lbl_VideoResolution.Text = string.Format("{0} X {1}", wf.job[0].vdo_hori, wf.job[0].vdo_vert);
                lbl_AudioCodec.Text = (wf.job[0].aud_codec !=null)? wf.job[0].aud_codec.ToUpper() : "";
                lbl_AudioBitrate.Text = string.Format("{0} kb", wf.job[0].aud_bit_rate);
                lbl_AudioSampling.Text = string.Format("{0} kb", wf.job[0].aud_s_rate);
                lbl_AudioChannel.Text = wf.job[0].aud_chan.ToString();
            }
            catch (System.Exception ex)
            {
            	
            }
        }
        void _wa_OnLocalCopyPercent(int percent)
        {
            if (_Config.CopyMode)
                if (percent <= 100)
                    ProgressBarCopy.Percent = percent;
        }
        
        private void dtGridView_JobQ_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            if (e.Context == DataGridViewDataErrorContexts.Commit)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[JOB_Q_GRID_VIEW Commit error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}",e.Exception.Message,e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.CurrentCellChange)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[JOB_Q_GRID_VIEW Cell change] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.Parsing)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[JOB_Q_GRID_VIEW parsing error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.LeaveControl)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[JOB_Q_GRID_VIEW leave control error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            else
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[JOB_Q_GRID_VIEW ETC] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }

            if ((e.Exception) is ConstraintException)
            {
                DataGridView view = (DataGridView)sender;
                view.Rows[e.RowIndex].ErrorText = "an error";
                view.Rows[e.RowIndex].Cells[e.ColumnIndex].ErrorText = "an error";

                e.ThrowException = false;
                e.Cancel = true;
                if (_wa != null)
                    _wa.WriteLog("Error Event Canceled");
            }
        }
        private void dtGridView_TC_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            Console.WriteLine("Error happened " + e.Context.ToString() + " on TC_INFO_GRID_VIEW");

            if (e.Context == DataGridViewDataErrorContexts.Commit)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[TC_INFO_GRID_VIEW Commit error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.CurrentCellChange)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[TC_INFO_GRID_VIEW Cell change] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.Parsing)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[TC_INFO_GRID_VIEW parsing error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            if (e.Context == DataGridViewDataErrorContexts.LeaveControl)
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[TC_INFO_GRID_VIEW leave control error] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }
            else
            {
                if (_wa != null)
                    _wa.WriteLog(string.Format("[TC_INFO_GRID_VIEW ETC] ExceptionMsg : {0}\r\nExceptionstackTrace : {1}", e.Exception.Message, e.Exception.StackTrace));
            }

            if ((e.Exception) is ConstraintException)
            {
                DataGridView view = (DataGridView)sender;
                view.Rows[e.RowIndex].ErrorText = "an error";
                view.Rows[e.RowIndex].Cells[e.ColumnIndex].ErrorText = "an error";

                e.ThrowException = false;
                e.Cancel = true;
                if (_wa != null)
                    _wa.WriteLog("Error Event Canceled");
            }
        }

        private void MainProc()
        {
            while (true)
            {
                try
                {
                    if (_wa.GetJobListCount() < 1)
                    {
                        Thread.Sleep(1000);
                        bool bRunning = false;
                        foreach (Transcoder tc in _listTCs)
                        {
                            bRunning = tc._IsRunning;
                            if (bRunning)
                                break;
                        }

                        if (!bRunning)
                            _wa.QueryGetJobs(_Config.Equip_ID);
                    }
                    Thread.Sleep(_Config.JobRequestCycle * 1000);
                }
                catch (ThreadAbortException ex)
                {
                    throw ex;
                }
                catch (Exception ex)
                {
                    Thread.Sleep(_Config.JobRequestCycle * 1000);
                }
            }
        }
        private void TempErrorFileDele()
        {
            string ErrorFileFolder = string.Format(@"{0}\Error",_Config.LocalFolder);
            if(_Config.DeleteCycleForErroredFiles <1) _Config.DeleteCycleForErroredFiles =1;
            if(_Config.DeleteCycleForErroredFiles<1) _Config.DeleteCycleForErroredFiles=1;
            while (true)
            {
                try
                {
                    if (_wa != null)
                        _wa.WriteLog("==============[Start Delete For Errored Files]=================");
                    if (!Directory.Exists(ErrorFileFolder))
                    {
                        Thread.Sleep(1000 * 60 * 60 * _Config.DeleteCycleForErroredFiles);
                        continue;
                    }
                    

                    string[] Files = Directory.GetFiles(ErrorFileFolder);
                    foreach (string file in Files)
                    {
                        TimeSpan ts = DateTime.Now - File.GetLastAccessTime(file);
                        if (ts.Days > _Config.DeleteThreadOccurrenceCycle)
                        {
                            if (_wa != null)
                                _wa.WriteLog(string.Format("[Start Delete For Errored Files] File : {0} , LastAccessTme : {1}", file, File.GetLastAccessTime(file)));
                            File.Delete(file);
                            if (_wa != null)
                                _wa.WriteLog(string.Format("[Start Delete For Errored Files] Deleted : {0}", file));
                        }
                    }
                }
                catch(ThreadAbortException ex)
                {
                    throw ex;
                }
                catch (Exception ex)
                {
                    if (_wa != null)
                        _wa.WriteLog(ex.Message + "\r\n" + ex.StackTrace);
                }
                Thread.Sleep(1000 * 60  * 60 * _Config.DeleteCycleForErroredFiles);
            }
        }
        private void MainForm_Load(object sender, EventArgs e)
        {
            try
            {
                _ConfigForm = new ConfigForm(_ConfigPath);
                _Config = _ConfigForm.LoadConfig();
                if (_Config == null)
                {
                    _ConfigForm.ShowDialog(this);
                    _Config = _ConfigForm.LoadConfig();
                }
                if (_Config.CopyMode)
                {
                    pic_Copymode.Image = Properties.Resources.on;
                    ProgressBarCopy.DisplayTextEnable = true;
                }
                else
                {
                    pic_Copymode.Image = Properties.Resources.off;
                    ProgressBarCopy.DisplayTextEnable = false;
                }
                SetUI();
                /************************************************************************/
                _Menu_Open = new MenuItem();
                _Menu_Open.Text = "로그 폴더 열기";
                _Menu_Open.Click += new EventHandler(_Menu_Open_Click);

                _Menu = new ContextMenu();
                _Menu.Name = "Menu";
                _Menu.MenuItems.Add(_Menu_Open);
                /************************************************************************/
                tc_Interface = new ServiceNavigatorService();
                tc_Interface.Url = _Config.WebServiceURL;

                _wa = new WorkAgent(dtGridView_JobQ, tc_Interface, _Config);
                _wa.OnLocalCopyPercent += new WorkAgent.LocalCopyPercent(_wa_OnLocalCopyPercent);
                _wa.OnGotNewJob += new WorkAgent.GotNewJob(_wa_OnGotNewJob);
                /************************************************************************/
                dtGridView_JobQ.AllowUserToAddRows = false;
                dtGridView_JobQ.DataSource = _wa.BndSrcJob;
                /************************************************************************/
                _listTCs = new List<Transcoder>();
                BindingList<Transcoder> _bind_TC_List = new BindingList<Transcoder>(_listTCs);
                dtGridView_TC.AllowUserToAddRows = false;
                dtGridView_TC.DataSource = new BindingSource(_bind_TC_List, null);
                /************************************************************************/
                _listTCs.Clear();
                _listTCs.Add(new Transcoder(_wa, dtGridView_TC, _Config, "01")); Thread.Sleep(100);
                _listTCs.Add(new Transcoder(_wa, dtGridView_TC, _Config, "02")); Thread.Sleep(100);
                _listTCs.Add(new Transcoder(_wa, dtGridView_TC, _Config, "03")); Thread.Sleep(100);
                _listTCs.Add(new Transcoder(_wa, dtGridView_TC, _Config, "04")); Thread.Sleep(100);
                _listTCs.Add(new Transcoder(_wa, dtGridView_TC, _Config, "05")); Thread.Sleep(100);
                //lbTcCnt.Text = string.Format("TC_COUNT : {0}", _listTCs.Count);
                ((BindingSource)dtGridView_TC.DataSource).ResetBindings(false);
                dtGridView_TC.ClearSelection();
                dtGridView_TC.Refresh();
                /************************************************************************/
                _MainThread = new Thread(new ThreadStart(MainProc));
                _MainThread.Start();
                //_TempErrorFileDelThread = new Thread(new ThreadStart(TempErrorFileDele));
                //_TempErrorFileDelThread.Start();

                MainForm_Resize(null, null);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message + "\r\n" + ex.StackTrace);
            }
        }

        private void MainForm_Resize(object sender, EventArgs e)
        {
            Make_RoundingEdge();

            pnl_Title.Size = new System.Drawing.Size(this.Width - 10, pnl_Title.Height);
            pnl_Title.Location = new Point(5, 5);

            split_Main.Size = new Size(this.Width - 10, this.Height - 10 - pnl_Title.Height);
            split_Main.Location = new Point(5, pnl_Title.Bottom);

            btn_Close.Location = new Point(pnl_Title.Width - 2 - btn_Close.Width, btn_Close.Top);
            btn_Min.Location = new Point(btn_Close.Left - 2 - btn_Min.Width, btn_Min.Top);
            btn_Option.Location = new Point(btn_Min.Left - 2 - btn_Option.Width, btn_Option.Top);
        }
        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {
                if (_listTCs.Count > 0)
                {
                    foreach (Transcoder t in _listTCs)
                    {
                        t.ReleaseTC();
                    }
                }
                _listTCs.Clear();
                /************************************************************************/
                Process[] ffmpegPrs = Process.GetProcessesByName("ffmpeg");
                foreach (Process prs in ffmpegPrs)
                {
                    prs.Kill();
                }
                /************************************************************************/
                foreach (DataGridViewRow row in dtGridView_JobQ.Rows)
                {
                    _wa.UpdateJobErrorSuccessed((Transcoder_KBS.Job)row.DataBoundItem, StatusErrorCode.ETC_ERROR, string.Format("{0}_01", _Config.Equip_ID));
                }
                /************************************************************************/
                if (_MainThread != null && _MainThread.IsAlive)
                    _MainThread.Abort();
                _MainThread = null;
                if (_TempErrorFileDelThread != null && _TempErrorFileDelThread.IsAlive)
                    _TempErrorFileDelThread.Abort();
                _TempErrorFileDelThread = null;
                /************************************************************************/
                ((BindingSource)dtGridView_TC.DataSource).ResetBindings(false);
            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }

        private void btn_Option_Click(object sender, EventArgs e)
        {
            if (_ConfigForm.ShowDialog(this) != DialogResult.OK)
                return;
            _Config = _ConfigForm.LoadConfig();
            if (_Config.CopyMode)
            {
                pic_Copymode.Image = Properties.Resources.on;
                ProgressBarCopy.DisplayTextEnable = true;
            }
            else
            {
                pic_Copymode.Image = Properties.Resources.off;
                ProgressBarCopy.DisplayTextEnable = false;
            }
        }
        private void btn_Option_MouseDown(object sender, MouseEventArgs e)
        {
            try
            {
                if (e.Button != MouseButtons.Right)
                    return;

                _Menu.Show(btn_Option, new Point(0, 0));
            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message + "\r\n" + ex.StackTrace);
            }
        }
        private void _Menu_Open_Click(object sender, EventArgs e)
        {
            try
            {
                string LogPath = string.Format(@"{0}\Log", Application.StartupPath);
                if (Directory.Exists(LogPath))
                {
                    System.Diagnostics.Process.Start("explorer.exe", LogPath);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message + "\r\n" + ex.StackTrace);
            }
        }

        private void dtGridView_JobQ_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            try
            {
                string jobid = dtGridView_JobQ[0, e.RowIndex].Value.ToString();
                Job job = _wa.GetSelJobInfo(jobid);
                ShowDetailInfo(job);
            }
            catch (Exception ex)
            {
                if (_wa != null)
                    _wa.WriteLog(ex.Message +"\r\n"+ex.StackTrace);
            }
        }
        private void dtGridView_JobQ_SelectionChanged(object sender, EventArgs e)
        {
            dtGridView_JobQ.ClearSelection();
        }
        private void dtGridView_TC_SelectionChanged(object sender, EventArgs e)
        {
            dtGridView_TC.ClearSelection();
        }
        private void split_Main_Panel1_Resize(object sender, EventArgs e)
        {
            pnl_Info.Size = new Size(pnl_Info.Width, split_Main.Panel1.Height);
            pnl_Info.Location = new Point(0, 0);
            dtGridView_JobQ.Size = new Size(split_Main.Panel1.Width - pnl_Info.Width-1, split_Main.Panel1.Height-10);
            dtGridView_JobQ.Location = new Point(pnl_Info.Right+3, 4);
        }

        private void pic_Title_MouseDown(object sender, MouseEventArgs e)
        {
            pnl_Title_MouseDown(null, e);
        }
        private void pic_Title_MouseMove(object sender, MouseEventArgs e)
        {
            pnl_Title_MouseMove(null, e);
        }

        private void lbl_Version_MouseDown(object sender, MouseEventArgs e)
        {
            pnl_Title_MouseDown(null, e);
        }
        private void lbl_Version_MouseMove(object sender, MouseEventArgs e)
        {
            pnl_Title_MouseMove(null, e);
        }

        private void pnl_Title_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                _MouseLBDwon = true;
            }
        }
        private void pnl_Title_MouseMove(object sender, MouseEventArgs e)
        {
            if (_MouseLBDwon)
            {
                ReleaseCapture();
                SendMessage(this.Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
                _MouseLBDwon = false;
            }
        }

        private void btn_Min_Click(object sender, EventArgs e)
        {
            if (this.WindowState != FormWindowState.Minimized)
            {
                this.WindowState = FormWindowState.Minimized;
            }
        }
        private void btn_Close_Click(object sender, EventArgs e)
        {
            DialogResult dResult = MessageBoxEx.Show("종료하시겠습니까?", "종료", MessageBoxButtons.OKCancel, MessageBoxIcon.Warning);
            if (dResult != DialogResult.OK)
                return;
            this.Close();
        }

        protected override void WndProc(ref Message m)
        {
            try
            {
                if (m.Msg == 0x84)
                {
                    if (this.WindowState == FormWindowState.Maximized)
                    {
                        return;
                    }
                    Point pos = new Point(m.LParam.ToInt32() & 0xffff, m.LParam.ToInt32() >> 16);
                    pos = this.PointToClient(pos);

                    if (pos.X <= 5 && pos.Y <= 5)
                    {
                        m.Result = (IntPtr)HT_TOPLEFT;
                        return;
                    }
                    else if (pos.X >= ClientSize.Width - 5 && pos.Y <= 5)
                    {
                        m.Result = (IntPtr)HT_TOPRIGHT;
                        return;
                    }
                    else if (pos.X <= 5 && pos.Y >= ClientSize.Height - 5)
                    {
                        m.Result = (IntPtr)HT_BOTTOMLEFT;
                        return;
                    }
                    else if (pos.X >= this.ClientSize.Width - 5 && pos.Y >= this.ClientSize.Height - 5)
                    {
                        m.Result = (IntPtr)HT_BOTTOMRIGHT;
                        return;
                    }
                    else if (pos.Y <= 5)
                    {
                        m.Result = (IntPtr)HT_TOP;
                        return;
                    }
                    else if (pos.Y >= ClientSize.Height - 5)
                    {
                        m.Result = (IntPtr)HT_BOTTOM;
                        return;
                    }
                    else if (pos.X <= 5)
                    {
                        m.Result = (IntPtr)HT_LEFT;
                        return;
                    }
                    else if (pos.X >= ClientSize.Width - 5)
                    {
                        m.Result = (IntPtr)HT_RIGHT;
                        return;
                    }
                }
                base.WndProc(ref m);
            }
            catch (Exception ex)
            {

            }
        }
#endregion 
    }
}
