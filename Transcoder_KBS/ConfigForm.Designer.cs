namespace Transcoder_KBS
{
    partial class ConfigForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label9 = new System.Windows.Forms.Label();
            this.ntxt_MinPercent = new System.Windows.Forms.NumericUpDown();
            this.label10 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.ntxt_MinFileSize = new System.Windows.Forms.NumericUpDown();
            this.label7 = new System.Windows.Forms.Label();
            this.lbl_LocalFolder = new System.Windows.Forms.Label();
            this.txt_LocalFolder = new System.Windows.Forms.TextBox();
            this.cbox_CopyMode = new System.Windows.Forms.CheckBox();
            this.label6 = new System.Windows.Forms.Label();
            this.ntxt_ThreadCount = new System.Windows.Forms.NumericUpDown();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txt_EqId = new System.Windows.Forms.TextBox();
            this.txt_Wsdl = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.ntxt_JobRequestCycle = new System.Windows.Forms.NumericUpDown();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.btnOk = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.ntxt_DeleteCycleForErrorFiles = new System.Windows.Forms.NumericUpDown();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.ntxt_DeleteThreadOccurrenceCycle = new System.Windows.Forms.NumericUpDown();
            this.label14 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_MinPercent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_MinFileSize)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_ThreadCount)).BeginInit();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_JobRequestCycle)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_DeleteCycleForErrorFiles)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_DeleteThreadOccurrenceCycle)).BeginInit();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label13);
            this.groupBox1.Controls.Add(this.ntxt_DeleteThreadOccurrenceCycle);
            this.groupBox1.Controls.Add(this.label14);
            this.groupBox1.Controls.Add(this.label12);
            this.groupBox1.Controls.Add(this.ntxt_DeleteCycleForErrorFiles);
            this.groupBox1.Controls.Add(this.label11);
            this.groupBox1.Controls.Add(this.label9);
            this.groupBox1.Controls.Add(this.ntxt_MinPercent);
            this.groupBox1.Controls.Add(this.label10);
            this.groupBox1.Controls.Add(this.label8);
            this.groupBox1.Controls.Add(this.ntxt_MinFileSize);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.lbl_LocalFolder);
            this.groupBox1.Controls.Add(this.txt_LocalFolder);
            this.groupBox1.Controls.Add(this.cbox_CopyMode);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.ntxt_ThreadCount);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.txt_EqId);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(465, 226);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Transcoder 설정";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(253, 157);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(15, 12);
            this.label9.TabIndex = 14;
            this.label9.Text = "%";
            // 
            // ntxt_MinPercent
            // 
            this.ntxt_MinPercent.Location = new System.Drawing.Point(126, 148);
            this.ntxt_MinPercent.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_MinPercent.Name = "ntxt_MinPercent";
            this.ntxt_MinPercent.Size = new System.Drawing.Size(120, 21);
            this.ntxt_MinPercent.TabIndex = 13;
            this.ntxt_MinPercent.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(16, 150);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(105, 12);
            this.label10.TabIndex = 12;
            this.label10.Text = "Minimum Percent";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(252, 133);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(24, 12);
            this.label8.TabIndex = 11;
            this.label8.Text = "MB";
            // 
            // ntxt_MinFileSize
            // 
            this.ntxt_MinFileSize.Location = new System.Drawing.Point(126, 124);
            this.ntxt_MinFileSize.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_MinFileSize.Name = "ntxt_MinFileSize";
            this.ntxt_MinFileSize.Size = new System.Drawing.Size(120, 21);
            this.ntxt_MinFileSize.TabIndex = 10;
            this.ntxt_MinFileSize.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(16, 126);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(107, 12);
            this.label7.TabIndex = 9;
            this.label7.Text = "Minimum FileSize";
            // 
            // lbl_LocalFolder
            // 
            this.lbl_LocalFolder.AutoSize = true;
            this.lbl_LocalFolder.Location = new System.Drawing.Point(50, 100);
            this.lbl_LocalFolder.Name = "lbl_LocalFolder";
            this.lbl_LocalFolder.Size = new System.Drawing.Size(71, 12);
            this.lbl_LocalFolder.TabIndex = 8;
            this.lbl_LocalFolder.Text = "LocalFolder";
            // 
            // txt_LocalFolder
            // 
            this.txt_LocalFolder.Location = new System.Drawing.Point(127, 97);
            this.txt_LocalFolder.Name = "txt_LocalFolder";
            this.txt_LocalFolder.Size = new System.Drawing.Size(332, 21);
            this.txt_LocalFolder.TabIndex = 7;
            // 
            // cbox_CopyMode
            // 
            this.cbox_CopyMode.AutoSize = true;
            this.cbox_CopyMode.Location = new System.Drawing.Point(18, 75);
            this.cbox_CopyMode.Name = "cbox_CopyMode";
            this.cbox_CopyMode.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.cbox_CopyMode.Size = new System.Drawing.Size(125, 16);
            this.cbox_CopyMode.TabIndex = 6;
            this.cbox_CopyMode.Text = "       Copy && Work";
            this.cbox_CopyMode.UseVisualStyleBackColor = true;
            this.cbox_CopyMode.CheckedChanged += new System.EventHandler(this.cbox_CopyMode_CheckedChanged);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(266, 29);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(63, 12);
            this.label6.TabIndex = 5;
            this.label6.Text = "ex) TC_01";
            // 
            // ntxt_ThreadCount
            // 
            this.ntxt_ThreadCount.Location = new System.Drawing.Point(127, 48);
            this.ntxt_ThreadCount.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_ThreadCount.Name = "ntxt_ThreadCount";
            this.ntxt_ThreadCount.Size = new System.Drawing.Size(120, 21);
            this.ntxt_ThreadCount.TabIndex = 4;
            this.ntxt_ThreadCount.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(16, 50);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(82, 12);
            this.label2.TabIndex = 3;
            this.label2.Text = "Thread Count";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 23);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(48, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "EquipID";
            // 
            // txt_EqId
            // 
            this.txt_EqId.Location = new System.Drawing.Point(126, 20);
            this.txt_EqId.Name = "txt_EqId";
            this.txt_EqId.Size = new System.Drawing.Size(121, 21);
            this.txt_EqId.TabIndex = 0;
            // 
            // txt_Wsdl
            // 
            this.txt_Wsdl.Location = new System.Drawing.Point(126, 33);
            this.txt_Wsdl.Name = "txt_Wsdl";
            this.txt_Wsdl.Size = new System.Drawing.Size(333, 21);
            this.txt_Wsdl.TabIndex = 0;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(16, 36);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(98, 12);
            this.label3.TabIndex = 3;
            this.label3.Text = "WebService URL";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.ntxt_JobRequestCycle);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.txt_Wsdl);
            this.groupBox2.Location = new System.Drawing.Point(6, 244);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(465, 93);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "통신 설정";
            // 
            // ntxt_JobRequestCycle
            // 
            this.ntxt_JobRequestCycle.Location = new System.Drawing.Point(127, 61);
            this.ntxt_JobRequestCycle.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_JobRequestCycle.Name = "ntxt_JobRequestCycle";
            this.ntxt_JobRequestCycle.Size = new System.Drawing.Size(120, 21);
            this.ntxt_JobRequestCycle.TabIndex = 5;
            this.ntxt_JobRequestCycle.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("굴림", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label5.Location = new System.Drawing.Point(253, 69);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(34, 11);
            this.label5.TabIndex = 6;
            this.label5.Text = "(sec)";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(16, 63);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(108, 12);
            this.label4.TabIndex = 5;
            this.label4.Text = "JobRequest Cycle";
            // 
            // btnOk
            // 
            this.btnOk.Location = new System.Drawing.Point(315, 343);
            this.btnOk.Name = "btnOk";
            this.btnOk.Size = new System.Drawing.Size(75, 23);
            this.btnOk.TabIndex = 2;
            this.btnOk.Text = "확인";
            this.btnOk.UseVisualStyleBackColor = true;
            this.btnOk.Click += new System.EventHandler(this.btnOk_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.btnCancel.Location = new System.Drawing.Point(396, 343);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(75, 23);
            this.btnCancel.TabIndex = 3;
            this.btnCancel.Text = "취소";
            this.btnCancel.UseVisualStyleBackColor = true;
            // 
            // ntxt_DeleteCycleForErrorFiles
            // 
            this.ntxt_DeleteCycleForErrorFiles.Location = new System.Drawing.Point(209, 199);
            this.ntxt_DeleteCycleForErrorFiles.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_DeleteCycleForErrorFiles.Name = "ntxt_DeleteCycleForErrorFiles";
            this.ntxt_DeleteCycleForErrorFiles.Size = new System.Drawing.Size(120, 21);
            this.ntxt_DeleteCycleForErrorFiles.TabIndex = 16;
            this.ntxt_DeleteCycleForErrorFiles.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(11, 203);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(168, 12);
            this.label11.TabIndex = 15;
            this.label11.Text = "Delete Cycle For Errord Files";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(332, 205);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(40, 12);
            this.label12.TabIndex = 17;
            this.label12.Text = "(hour)";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(332, 181);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(36, 12);
            this.label13.TabIndex = 20;
            this.label13.Text = "(day)";
            // 
            // ntxt_DeleteThreadOccurrenceCycle
            // 
            this.ntxt_DeleteThreadOccurrenceCycle.Location = new System.Drawing.Point(209, 175);
            this.ntxt_DeleteThreadOccurrenceCycle.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.ntxt_DeleteThreadOccurrenceCycle.Name = "ntxt_DeleteThreadOccurrenceCycle";
            this.ntxt_DeleteThreadOccurrenceCycle.Size = new System.Drawing.Size(120, 21);
            this.ntxt_DeleteThreadOccurrenceCycle.TabIndex = 19;
            this.ntxt_DeleteThreadOccurrenceCycle.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(11, 179);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(191, 12);
            this.label14.TabIndex = 18;
            this.label14.Text = "Delete Thread Occurrence Cycle";
            // 
            // ConfigForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(489, 378);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnOk);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "ConfigForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Config";
            this.Shown += new System.EventHandler(this.ConfigForm_Shown);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_MinPercent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_MinFileSize)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_ThreadCount)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_JobRequestCycle)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_DeleteCycleForErrorFiles)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.ntxt_DeleteThreadOccurrenceCycle)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txt_EqId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txt_Wsdl;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnOk;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.NumericUpDown ntxt_ThreadCount;
        private System.Windows.Forms.NumericUpDown ntxt_JobRequestCycle;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.CheckBox cbox_CopyMode;
        private System.Windows.Forms.Label lbl_LocalFolder;
        private System.Windows.Forms.TextBox txt_LocalFolder;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.NumericUpDown ntxt_MinFileSize;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.NumericUpDown ntxt_MinPercent;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.NumericUpDown ntxt_DeleteCycleForErrorFiles;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.NumericUpDown ntxt_DeleteThreadOccurrenceCycle;
        private System.Windows.Forms.Label label14;
    }
}