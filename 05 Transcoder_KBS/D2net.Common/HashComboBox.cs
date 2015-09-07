using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// "Key=Value"의 형태의 데이터를 입력하고, Key를 입력하여 값을 선택/반환할 수 있다. 
    /// 또한, ComboBox에 선택된 아이템을 이용하여 Key값을 반환 받을 수 있다.
    /// </summary>
    ///
    /// <example> ComboBox에 데이터 추가/ 선택/ 반환
    /// 데이터 추가
    /// <code>
    ///    cmbVideoDevice.Add(1, "비디오 디바이스(I1)");
    ///    cmbVideoDevice.Add(2, "비디오 디바이스(I2)");
    ///    cmbVideoDevice.Add(3, "SDTI1");
    ///    cmbVideoDevice.Add(4, "SDTI2");
    /// </code>
    /// 데이터 선택
    /// <code>
    ///    cmbVideoDevice.SelectKey(1);
    /// </code>
    /// 데이터 반환
    /// <code>
    ///    int selkey = (int)cmbCompressionType.SelectedObject;
    /// </code>
    /// </example>
	public class HashComboBox : System.Windows.Forms.UserControl
	{
        /// <summary> 
		/// 이름을 표시할 ComboBox
		/// </summary>
        protected D2net.Common.UI.ComboBoxEx cmbHashTable;

        /// <summary> 
		/// 이름에 해당하는 값을 저장할 Hashtable
		/// </summary>
        protected Hashtable _Table = new Hashtable();

		/// <summary> 
		/// 필수 디자이너 변수입니다.
		/// </summary>
		private System.ComponentModel.Container components = null;

        /// <summary>
        /// 기본 생성자
        /// </summary>
        public HashComboBox()
		{
			InitializeComponent();
		}

		/// <summary> 
		/// 사용 중인 모든 리소스를 정리합니다.
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
        /// ComboBox에서 선택된 아이템의 Key값을 설정한다.
        /// </summary>
        /// <example>
        /// 선택된 아이템 사용
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

		#region 구성 요소 디자이너에서 생성한 코드
		/// <summary> 
		/// 디자이너 지원에 필요한 메서드입니다. 
		/// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
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
        /// ComboBox에 아이템을 추가한다.
        /// </summary>
        /// <returns>추가된 아이템의 인덱스</returns>
        /// <param name="key">추가할 아이템을 검색할 Key</param>
        /// <param name="value">아이템에 추가할 값</param>
        /// <example> 아이템 추가
        /// <code>
        ///    cmbVideoDevice.Add(1, "비디오 디바이스(I1)");
        ///    cmbVideoDevice.Add(2, "비디오 디바이스(I2)");
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
        /// ComboBox에 아이템을 삭제한다.
        /// </summary>
        /// <param name="key">삭제할 아이템을 검색할 Key</param>
        /// <example> 아이템 삭제
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
        /// ComboBox에 모든 아이템을 삭제한다.
        /// </summary>
        /// <example> 아이템 모두 삭제
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
        /// ComboBox에서 key에 해당하는 value값을 반환 받는다.
        /// </summary>
        /// <returns>키에 대응되는 아이템의 값</returns>
        /// <param name="key">검색할 Key</param>
        /// <example> 아이템 검색
        /// <code>
        /// object obj = cmbVideoDevice.FindValue(1);
        /// System.Console.WriteLine("obj = " + obj.toString());
        /// 
        /// Output : obj = 비디오 디바이스(I1)
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
        /// ComboBox에서 value에 해당하는 key값을 검색한다.
        /// </summary>
        /// <returns>값에 대응되는 키</returns>
        /// <param name="val">검색할 값</param>
        /// <example> Key 검색
        /// <code>
        /// object key = cmbVideoDevice.FindKey("비디오 디바이스(I1)");
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
        /// ComboBox에서 key에 해당하는 아이템을 선택한다.
        /// </summary>
        /// <param name="key">선택할 Key</param>
        /// <example> 아이템 선택
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
