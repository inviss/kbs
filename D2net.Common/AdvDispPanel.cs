using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace D2net.Common.UI
{
    public partial class AdvDispPanel : UserControl
    {
        //private TimeCode _VcrTC = null;
        //private TimeCode _RecTC = null;
        //private TimeCode _RemTC = null;
        //private double TotX = 0;
        //private double TotY = 0;
        //private Font _Font;
        //private Font _LabelFont;
        //private ResourceManager _ResMgr = null;
        //private bool _IsRecoder = true;

        //#region CreateEvents
        //public delegate void FireEvent(bool IsRecoder);
        //public event FireEvent ModeChange;
        //#endregion

        //public AdvDispPanel()
        //{
        //    InitializeComponent();

        //    _VcrTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    _RecTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    _RemTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    TotX = (double)this.Size.Width;
        //    TotY = (double)this.Size.Height;
        //    _Font = new Font("»ﬁ∏’µ’±Ÿ«ÏµÂ∂Û¿Œ", (float)(TotY * 0.200), FontStyle.Bold);
        //    _LabelFont = new Font("»ﬁ∏’µ’±Ÿ«ÏµÂ∂Û¿Œ", (float)(TotY * 0.110), FontStyle.Bold);
        //    _IsRecoder = true;
            
        //}

        //public AdvDispPanel(ResourceManager input)
        //{
        //    InitializeComponent();

        //    _VcrTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    _RecTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    _RemTC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, 0);
        //    TotX = (double)this.Size.Width;
        //    TotY = (double)this.Size.Height;
        //    _Font = new Font("»ﬁ∏’µ’±Ÿ«ÏµÂ∂Û¿Œ", (float)(TotY * 0.200), FontStyle.Bold);
        //    _LabelFont = new Font("»ﬁ∏’µ’±Ÿ«ÏµÂ∂Û¿Œ", (float)(TotY * 0.110), FontStyle.Bold);
        //    if (input != null)
        //    {
        //        _ResMgr = input;
        //        this.btnType.ButtonImage = input["Control/Status_Record"];
        //    }
        //    this.btnType.Size = new Size(btnType.ButtonImage.Size.Width, btnType.ButtonImage.Size.Height / 4);


        //}

        //public TimeCode Time_Code
        //{
        //    get { return _VcrTC; }
        //    set {
        //        if (_VcrTC != null)
        //        {
        //            lock (_VcrTC)
        //            {
        //                _VcrTC = value;
        //            }
        //        }
        //        if (_RecTC != null)
        //        {
        //            lock (_RecTC)
        //            {
        //                _RecTC = value;
        //            }
        //        }
        //        if (_RemTC != null)
        //        {
        //            lock (_RemTC)
        //            {
        //                _RemTC = value;
        //            }
        //        }
        //    }
 
        //}

        //public string VCRTC
        //{
        //    get 
        //    {
        //        return _VcrTC.TC; 
        //    }
        //    set 
        //    {
        //        if (_VcrTC != null)
        //        {
        //            lock (_VcrTC)
        //            {
        //                _VcrTC.TC = value;
        //            }
        //            lock (lblVCRTC)
        //            {
        //                this.lblVCRTC.Text = _VcrTC.TC;
        //            }
        //        }
        //    }
        //}

        //public Int64 VCRFrame
        //{
        //    get { return _VcrTC.Frame; }
        //    set 
        //    {
        //        if (_VcrTC != null)
        //        {
        //            lock (_VcrTC)
        //            {
        //                _VcrTC.Frame = value;
        //            }
        //            lock (lblVCRTC)
        //            {
        //                this.lblVCRTC.Text = _VcrTC.TC;
        //            }
        //        }
        //    }
        //}

        //public string RecTC
        //{
        //    get { return _RecTC.TC; }
        //    set 
        //    {
        //        if (_RecTC != null)
        //        {
        //            lock (_RecTC)
        //            {
        //                _RecTC.TC = value;
        //            }
        //            lock (lblRecTC)
        //            {
        //                this.lblRecTC.Text = _RecTC.TC;
        //            }
        //        }
        //    }
        //}

        //public Int64 RecFrame
        //{
        //    get { return _RecTC.Frame; }
        //    set 
        //    {
        //        if (_RecTC != null)
        //        {
        //            lock (_RecTC)
        //            {
        //                _RecTC.Frame = value;
        //            }
        //            lock (lblRecTC)
        //            {
        //                this.lblRecTC.Text = _RecTC.TC;
        //            }
        //        }
        //    }
        //}

        //public string RemTC
        //{
        //    get { return _RemTC.TC; }
        //    set {
        //        if (_RemTC != null)
        //        {
        //            lock (_RemTC)
        //            {
        //                _RemTC.TC = value;
        //            }
        //            lock (lblRemTC)
        //            {
        //                this.lblRemTC.Text = _RemTC.TC;
        //            }
        //        }
        //    }
        //}

        //public Int64 RemFrame
        //{
        //    get { return _RemTC.Frame; }
        //    set {
        //        if (_RemTC != null)
        //        {
        //            lock (_RemTC)
        //            {
        //                _RemTC.Frame = value;
        //            }
                
        //        lock (lblRemTC)
        //        {
        //            this.lblRemTC.Text = _RemTC.TC;
        //        }
        //        }
        //    }
        //}

        //public ResourceManager ResMgr
        //{
        //    set {
        //            if (value != null)
        //            {
        //                _ResMgr = value;
        //                this.btnType.ButtonImage = _ResMgr["Control/Status_Record"];
        //                this.btnType.Size = new Size(btnType.ButtonImage.Size.Width, btnType.ButtonImage.Size.Height / 4);
        //                this.btnType.Location = new Point(this.Width - this.btnType.Size.Width - 8, 8);
        //            }
        //        }
        //}

        //public Font FontVal
        //{
        //    get { return _Font; }
        //    set 
        //    {
        //        TotY = (double)this.Size.Height;
        //        _Font = new Font(((Font)value).FontFamily, (float)(TotY * 0.200), FontStyle.Bold);
        //        this.Invalidate();
        //    }
        //}

        //public Font LabelFontVal
        //{
        //    get { return _LabelFont; }
        //    set
        //    {
        //        TotY = (double)this.Size.Height;
        //        _LabelFont = new Font(((Font)value).FontFamily, (float)(TotY * 0.200), FontStyle.Bold);
        //        this.Invalidate();
        //    }
        //}

        //public TCMode TimeCodeMode
        //{
        //    set
        //    {
        //        if (_VcrTC != null)
        //        {
        //            lock (_VcrTC)
        //            {
        //                _VcrTC.TimeCodeMode = value;
        //            }
        //        }
        //        if (_RecTC != null)
        //        {
        //            lock (_RecTC)
        //            {
        //                _RecTC.TimeCodeMode = value;
        //            }
        //        }
        //        if (_RemTC != null)
        //        {
        //            lock (_RemTC)
        //            {
        //                _RemTC.TimeCodeMode = value;
        //            }
        //        }
        //    }
        //    get
        //    {
        //        return _VcrTC.TimeCodeMode;
        //    }
        //}

        //private bool _ShowCurType = false;

        //public bool ShowCurType
        //{
        //    get { return _ShowCurType; }
        //    set 
        //    {
        //        _ShowCurType = value;
        //        if (_ShowCurType)
        //        {
        //            this.btnType.Show();
        //        }
        //        else
        //        {
        //            this.btnType.Hide();
        //        }
        //    }
        //}

        //private Size _Size(double X, double Y)
        //{
        //    return new Size((int)X, (int)Y);
        //}

        //private Point _Point(double X, double Y)
        //{
        //    return new Point((int)X, (int)Y);
        //}

        //private void AdvDispPanel_Resize(object sender, EventArgs e)
        //{
        //    TotX = (double)this.Size.Width;
        //    TotY = (double)this.Size.Height;

        //    this.lblVCR.Size = _Size(TotX * 0.230, TotY * 0.250);
        //    this.lblVCR.Location = _Point(TotX * 0.03, TotY * 0.120);
        //    this.lblRec.Size = _Size(TotX * 0.230, TotY * 0.250);
        //    this.lblRec.Location = _Point(TotX * 0.03, TotY * 0.430);
        //    this.lblRem.Size = _Size(TotX * 0.230, TotY * 0.250);
        //    this.lblRem.Location = _Point(TotX * 0.03, TotY * 0.740);

        //    if (_ShowCurType)
        //    //if (true)
        //    {
        //        this.lblVCRTC.Size = _Size(TotX * 0.500, TotY * 0.250);
        //        this.lblVCRTC.Location = _Point(TotX * 0.230, TotY * 0.060);
                
        //        this.lblRecTC.Size = _Size(TotX * 0.500, TotY * 0.250);
        //        this.lblRecTC.Location = _Point(TotX * 0.230, TotY * 0.370);
                
        //        this.lblRemTC.Size = _Size(TotX * 0.500, TotY * 0.250);
        //        this.lblRemTC.Location = _Point(TotX * 0.230, TotY * 0.680);
        //        _Font = new Font(_Font.FontFamily, (float)(TotX * 0.045), FontStyle.Bold);
        //    }
        //    else
        //    {
        //        this.lblVCRTC.Size = _Size(TotX * 0.750, TotY * 0.250);
        //        this.lblVCRTC.Location = _Point(TotX * 0.230, TotY * 0.070);

        //        this.lblRecTC.Size = _Size(TotX * 0.750, TotY * 0.250);
        //        this.lblRecTC.Location = _Point(TotX * 0.230, TotY * 0.390);

        //        this.lblRemTC.Size = _Size(TotX * 0.750, TotY * 0.250);
        //        this.lblRemTC.Location = _Point(TotX * 0.230, TotY * 0.700);

        //        _Font = new Font(_Font.FontFamily, (float)(TotX * 0.080), FontStyle.Bold);
        //    }

            
        //    _LabelFont = new Font(_LabelFont.FontFamily, (float)(TotY * 0.060), FontStyle.Bold);

        //    this.lblVCR.Font = _LabelFont;
        //    this.lblVCRTC.Font = _Font;
        //    this.lblRec.Font = _LabelFont;
        //    this.lblRecTC.Font = _Font;
        //    this.lblRem.Font = _LabelFont;
        //    this.lblRemTC.Font = _Font;

        //    this.btnType.Location = new Point(this.Width - this.btnType.Size.Width - 8, 8);
           
        //}

        //private void AdvDispPanel_Load(object sender, EventArgs e)
        //{
        //    Initialize();
            
        //}

        //private void Initialize()
        //{
        //    this.lblVCR.BackColor = Color.Transparent;
        //    this.lblVCRTC.BackColor = Color.Transparent;
        //    this.lblRec.BackColor = Color.Transparent;
        //    this.lblRecTC.BackColor = Color.Transparent;
        //    this.lblRem.BackColor = Color.Transparent;
        //    this.lblRemTC.BackColor = Color.Transparent;

        //    this.lblVCR.Font = _LabelFont;
        //    this.lblVCRTC.Font = _Font;
        //    this.lblRec.Font = _LabelFont;
        //    this.lblRecTC.Font = _Font;
        //    this.lblRem.Font = _LabelFont;
        //    this.lblRemTC.Font = _Font;
        //}

        //private void AdvDispPanel_SizeChanged(object sender, EventArgs e)
        //{
            

        //}

        //private void btnType_Click(object sender, EventArgs e)
        //{
        //    _IsRecoder = !_IsRecoder;
        //    if (_IsRecoder)
        //    {
        //        this.btnType.ButtonImage = _ResMgr["Control/Status_Record"];
        //    }
        //    else
        //    {
        //        this.btnType.ButtonImage = _ResMgr["Control/Status_Play"];
        //    }
        //    ModeChange(_IsRecoder);
        //}

        //public bool IsRecoding
        //{
        //    set
        //    {
        //        if (value)
        //        {
        //            _IsRecoder = true;
        //            this.btnType.ButtonImage = _ResMgr["Control/Status_Record"];
        //        }
        //        else
        //        {
        //            _IsRecoder = false;
        //            this.btnType.ButtonImage = _ResMgr["Control/Status_Play"];
        //        }
        //    }
        //}

        //public void ResetTimeCode()
        //{
        //    if (_VcrTC.TimeCodeMode == TCMode.TimeCode)
        //    {
        //        this.lblRecTC.Text = "00:00:00:00";
        //        this.lblRemTC.Text = "00:00:00:00";
        //    }
        //    else
        //    {
        //        this.lblRecTC.Text = "+0:00:00:00";
        //        this.lblRemTC.Text = "+0:00:00:00";
        //    }
        //}
    }
}
