using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// "Key=Value"�� ������ �����͸� �Է��ϰ�, Key�� �Է��Ͽ� ���� ����/��ȯ�� �� �ִ�. 
    /// ����, ComboBox�� ���õ� �������� �̿��Ͽ� Key���� ��ȯ ���� �� �ִ�.
    /// </summary>
    ///
    /// <example> ComboBox�� ������ �߰�/ ����/ ��ȯ
    /// ������ �߰�
    /// <code>
    ///    cmbVideoDevice.Add(1, "���� ����̽�(I1)");
    ///    cmbVideoDevice.Add(2, "���� ����̽�(I2)");
    ///    cmbVideoDevice.Add(3, "SDTI1");
    ///    cmbVideoDevice.Add(4, "SDTI2");
    /// </code>
    /// ������ ����
    /// <code>
    ///    cmbVideoDevice.SelectKey(1);
    /// </code>
    /// ������ ��ȯ
    /// <code>
    ///    int selkey = (int)cmbCompressionType.SelectedObject;
    /// </code>
    /// </example>
	public class HashComboBox : System.Windows.Forms.UserControl
	{
        /// <summary> 
		/// �̸��� ǥ���� ComboBox
		/// </summary>
        protected D2net.Common.UI.ComboBoxEx cmbHashTable;

        /// <summary> 
		/// �̸��� �ش��ϴ� ���� ������ Hashtable
		/// </summary>
        protected Hashtable _Table = new Hashtable();

		/// <summary> 
		/// �ʼ� �����̳� �����Դϴ�.
		/// </summary>
		private System.ComponentModel.Container components = null;

        /// <summary>
        /// �⺻ ������
        /// </summary>
        public HashComboBox()
		{
			InitializeComponent();
		}

		/// <summary> 
		/// ��� ���� ��� ���ҽ��� �����մϴ�.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
            Clear();

			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

        /// <summary>
        /// ComboBox���� ���õ� �������� Key���� �����Ѵ�.
        /// </summary>
        /// <example>
        /// ���õ� ������ ���
        /// <code>
        /// object key = cmbVideoDevice.SelectedObject;
        /// </code>
        /// </example>
        public object SelectedObject
        {
            get
            {
                try
                {
                    System.Collections.IDictionaryEnumerator de = _Table.GetEnumerator();
                    int index;
                    if (_Table.Count <= 0)
                        return null;
                    while (de.MoveNext())
                    {
                        index = (int)de.Value;
                        if (index == cmbHashTable.SelectedIndex)
                            return de.Key;
                    }

                    return null;
                }
                catch (Exception ex)
                {
                    throw ex;
                }
            }
        }

		#region ���� ��� �����̳ʿ��� ������ �ڵ�
		/// <summary> 
		/// �����̳� ������ �ʿ��� �޼����Դϴ�. 
		/// �� �޼����� ������ �ڵ� ������� �������� ���ʽÿ�.
		/// </summary>
		private void InitializeComponent()
		{
            this.cmbHashTable = new D2net.Common.UI.ComboBoxEx();
            this.SuspendLayout();
            // 
            // cmbHashTable
            // 
            this.cmbHashTable.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmbHashTable.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbHashTable.Location = new System.Drawing.Point(0, 0);
            this.cmbHashTable.Name = "cmbHashTable";
            this.cmbHashTable.Size = new System.Drawing.Size(336, 20);
            this.cmbHashTable.TabIndex = 0;
            this.cmbHashTable.Text = "";
            // 
            // HashComboBox
            // 
            this.Controls.Add(this.cmbHashTable);
            this.Name = "HashComboBox";
            this.Size = new System.Drawing.Size(336, 20);
            this.ResumeLayout(false);

        }
		#endregion

        /// <summary>
        /// ComboBox�� �������� �߰��Ѵ�.
        /// </summary>
        /// <returns>�߰��� �������� �ε���</returns>
        /// <param name="key">�߰��� �������� �˻��� Key</param>
        /// <param name="value">�����ۿ� �߰��� ��</param>
        /// <example> ������ �߰�
        /// <code>
        ///    cmbVideoDevice.Add(1, "���� ����̽�(I1)");
        ///    cmbVideoDevice.Add(2, "���� ����̽�(I2)");
        ///    cmbVideoDevice.Add(3, "SDTI1");
        ///    cmbVideoDevice.Add(4, "SDTI2");
        /// </code>
        /// </example>
        public int Add(object key, object value)
        {
            try
            {
                int index = cmbHashTable.Items.Add(value);
                _Table.Add(key, index);
                return index;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// ComboBox�� �������� �����Ѵ�.
        /// </summary>
        /// <param name="key">������ �������� �˻��� Key</param>
        /// <example> ������ ����
        /// <code>
        /// cmbVideoDevice.Remove(1);
        /// </code>
        /// </example>
        public void Remove(object key)
        {
            try
            {
                object obj = _Table[key];
                if (obj == null)
                    return;
                int index = (int)obj; 
                _Table.Remove(key);
                cmbHashTable.Items.RemoveAt(index);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// ComboBox�� ��� �������� �����Ѵ�.
        /// </summary>
        /// <example> ������ ��� ����
        /// <code>
        /// cmbVideoDevice.Clear();
        /// </code>
        /// </example>
        public void Clear()
        {
            try
            {
                _Table.Clear();
                cmbHashTable.Items.Clear();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// ComboBox���� key�� �ش��ϴ� value���� ��ȯ �޴´�.
        /// </summary>
        /// <returns>Ű�� �����Ǵ� �������� ��</returns>
        /// <param name="key">�˻��� Key</param>
        /// <example> ������ �˻�
        /// <code>
        /// object obj = cmbVideoDevice.FindValue(1);
        /// System.Console.WriteLine("obj = " + obj.toString());
        /// 
        /// Output : obj = ���� ����̽�(I1)
        /// </code>
        /// </example>
        public object FindValue(object key)
        {
            try
            {
                object obj = _Table[key];
                if (obj == null)
                    return null;
                int index = (int)obj; 
                return cmbHashTable.Items[index];
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// ComboBox���� value�� �ش��ϴ� key���� �˻��Ѵ�.
        /// </summary>
        /// <returns>���� �����Ǵ� Ű</returns>
        /// <param name="val">�˻��� ��</param>
        /// <example> Key �˻�
        /// <code>
        /// object key = cmbVideoDevice.FindKey("���� ����̽�(I1)");
        /// System.Console.WriteLine("key = " + key.toString());
        /// 
        /// Output : key = 1
        /// </code>
        /// </example>
        public object FindKey (object val)
        {
            try
            {
                bool find = false;
                ComboBox.ObjectCollection oc = cmbHashTable.Items;
                int i = 0;
                for (i = 0; i < oc.Count; i++)
                {
                    if (oc[i].Equals(val) == true)
                    {
                        find = true;
                        break;
                    }
                }

                if (find != true)
                    return null;

                System.Collections.IDictionaryEnumerator de = _Table.GetEnumerator();
                int index;
                if (_Table.Count <= 0)
                    return null;
                while (de.MoveNext())
                {
                    index = (int)de.Value;
                    if (index == i)
                        return de.Key;
                }

                return null;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// ComboBox���� key�� �ش��ϴ� �������� �����Ѵ�.
        /// </summary>
        /// <param name="key">������ Key</param>
        /// <example> ������ ����
        /// <code>
        /// object obj = cmbVideoDevice.SelectKey(1);
        /// </code>
        /// </example>
        public void SelectKey(object key)
        {
            try
            {
                object obj = _Table[key];
                if (obj == null)
                    return;
                cmbHashTable.SelectedIndex = (int)obj;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
	}
}
