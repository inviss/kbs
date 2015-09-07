using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace Transcoder_KBS
{
    public partial class ConfigForm : Form
    {
#region Variables
        private string _ConfigPath = string.Empty;
        private ConfigInfo _Config = null;
#endregion
#region Property
        public ConfigInfo Config
        {
            get { return _Config; }
        }
#endregion
#region Constructor
        public ConfigForm(string path)
        {
            InitializeComponent();
            _ConfigPath = path;
        }
#endregion
#region Method
        public ConfigInfo LoadConfig()
        {
            if (_Config == null)
                _Config = new ConfigInfo();
            if (!ReadConfig())
                return null;
            
            return _Config;
        }

        private void Initialize()
        {
            LoadConfig();
            txt_EqId.Text = _Config.Equip_ID;
            ntxt_ThreadCount.Value = _Config.Thread_Count;
            cbox_CopyMode.Checked = _Config.CopyMode;
            txt_LocalFolder.Text = _Config.LocalFolder;
            ntxt_MinFileSize.Text = _Config.MinimumFileSize.ToString();
            ntxt_MinPercent.Text = _Config.MinimumPercent.ToString();
            ntxt_DeleteThreadOccurrenceCycle.Text = Config.DeleteThreadOccurrenceCycle.ToString();
            ntxt_DeleteCycleForErrorFiles.Text = Config.DeleteCycleForErroredFiles.ToString();


            txt_Wsdl.Text = _Config.WebServiceURL;
            ntxt_JobRequestCycle.Value = _Config.JobRequestCycle;
            cbox_CopyMode_CheckedChanged(null, null);
        }
        private bool ReadConfig()
        {
            if (!Directory.Exists(Path.GetDirectoryName(_ConfigPath)))
                Directory.CreateDirectory(Path.GetDirectoryName(_ConfigPath));
            if (!File.Exists(_ConfigPath))
            {
                CreateDefaultConfig();
                return false;
            }

            IniFile ini = new IniFile(_ConfigPath);
            _Config.Equip_ID = ini.IniReadValue("Transcoder", "EquipID" );
            _Config.Thread_Count = ini.IniReadValueToInt("Transcoder", "ThreadCount");

            if (ini.IniReadValue("Transcoder", "CopyMode").Trim() == string.Empty)
                _Config.CopyMode = true;
            else
                _Config.CopyMode = Convert.ToBoolean(ini.IniReadValue("Transcoder", "CopyMode"));

            _Config.LocalFolder = ini.IniReadValue("Transcoder", "LocalFolder");
            _Config.MinimumFileSize = ini.IniReadValueToInt("Transcoder", "MinimumFileSize");
            _Config.MinimumPercent = ini.IniReadValueToInt("Transcoder", "MinimumPercent");
            _Config.DeleteThreadOccurrenceCycle = ini.IniReadValueToInt("Transcoder", "DeleteThreadOccurrenceCycle");
            _Config.DeleteCycleForErroredFiles = ini.IniReadValueToInt("Transcoder", "DeleteCycleForErroredFiles");

            _Config.WebServiceURL = ini.IniReadValue("Communication", "WebServiceURL");
            _Config.JobRequestCycle = ini.IniReadValueToInt("Communication", "JobRequestCycle");

            return true;
        }
        private void WriteConig()
        {
            IniFile ini = new IniFile(_ConfigPath);
            ini.IniWriteValue("Transcoder", "EquipID", txt_EqId.Text.Trim());
            ini.IniWriteValue("Transcoder", "ThreadCount", ntxt_ThreadCount.Value.ToString());
            ini.IniWriteValue("Transcoder", "CopyMode", cbox_CopyMode.Checked.ToString());
            ini.IniWriteValue("Transcoder", "LocalFolder", txt_LocalFolder.Text);
            ini.IniWriteValue("Transcoder", "MinimumFileSize", ntxt_MinFileSize.Value.ToString());
            ini.IniWriteValue("Transcoder", "MinimumPercent", ntxt_MinPercent.Value.ToString());
            ini.IniWriteValue("Transcoder", "DeleteThreadOccurrenceCycle", ntxt_DeleteThreadOccurrenceCycle.Value.ToString());
            ini.IniWriteValue("Transcoder", "DeleteCycleForErroredFiles", ntxt_DeleteCycleForErrorFiles.Value.ToString());

            ini.IniWriteValue("Communication", "WebServiceURL", txt_Wsdl.Text.Trim());
            ini.IniWriteValue("Communication", "JobRequestCycle", ntxt_JobRequestCycle.Value.ToString());
        }
        private void CreateDefaultConfig()
        {
            IniFile ini = new IniFile(_ConfigPath);
            ini.IniWriteValue("Transcoder", "EquipID", "TC_00");
            ini.IniWriteValue("Transcoder", "ThreadCount", "12");
            ini.IniWriteValue("Transcoder", "CopyMode", "True");
            ini.IniWriteValue("Transcoder", "LocalFolder", "C:\\TranscoderTempWorking");
            ini.IniWriteValue("Transcoder", "MinimumFileSize", "1");
            ini.IniWriteValue("Transcoder", "MinimumPercent", "90");
            ini.IniWriteValue("Transcoder", "DeleteThreadOccurrenceCycle", "1");
            ini.IniWriteValue("Transcoder", "DeleteCycleForErroredFiles", "1");

            ini.IniWriteValue("Communication", "WebServiceURL", "http://192.168.10.40/services/ServiceNavigator?wsdl");
            ini.IniWriteValue("Communication", "JobRequestCycle", "10");
        }
#endregion
#region Event
        private void btnOk_Click(object sender, EventArgs e)
        {
            if (txt_EqId.Text.Trim().EndsWith("00"))
            {
                MessageBox.Show(this, string.Format("Equip ID를 확인해 주십시오."));
                return;
            }
            else if (cbox_CopyMode.Checked && txt_LocalFolder.Text.Trim() == "C:\\TranscoderTempWorking")
            {
                MessageBox.Show(this, string.Format("로컬 폴더를 재설정 해주시기 바랍니다."));
                return;
            }

            WriteConig();
            DialogResult = DialogResult.OK;
        }
        private void cbox_CopyMode_CheckedChanged(object sender, EventArgs e)
        {
            lbl_LocalFolder.Enabled = cbox_CopyMode.Checked;
            txt_LocalFolder.Enabled = cbox_CopyMode.Checked;
        }
        private void ConfigForm_Shown(object sender, EventArgs e)
        {
            Initialize();
        }
#endregion
    }
}
