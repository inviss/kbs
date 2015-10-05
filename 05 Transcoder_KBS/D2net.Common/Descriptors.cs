// 

using System;
using System.Collections;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Reflection;


namespace D2net.Common.UI
{
    internal class GlobalizedItem
    {
        public string DisplayName;
        public string Description;

        public GlobalizedItem(string DisplayName, string Description)
        {
            this.DisplayName = DisplayName;
            this.Description = Description;
        }
    }

	#region GlobalizedPropertyDescriptor

	/// <summary>
	/// GlobalizedPropertyDescriptor enhances the base class bay obtaining the display name for a property
	/// from the resource.
	/// </summary>
	public class GlobalizedPropertyDescriptor : PropertyDescriptor
	{
		private PropertyDescriptor basePropertyDescriptor; 
        private GlobalizedObject _GlobalizedObject = null;

        public GlobalizedPropertyDescriptor(PropertyDescriptor basePropertyDescriptor, GlobalizedObject _GlobalizedObject) : base(basePropertyDescriptor)
		{
			this.basePropertyDescriptor = basePropertyDescriptor;
            this._GlobalizedObject = _GlobalizedObject;
		}

		public override bool CanResetValue(object component)
		{
			return basePropertyDescriptor.CanResetValue(component);
		}

		public override Type ComponentType
		{
			get { return basePropertyDescriptor.ComponentType; }
		}

		public override string DisplayName
		{
			get 
			{
                string tableName = "";
                string displayName = "";

                try
                {
                    // First lookup the property if GlobalizedPropertyAttribute instances are available. 
                    // If yes, then try to get resource table name and display name id from that attribute.
                    foreach( Attribute oAttrib in this.basePropertyDescriptor.Attributes )
                    {
                        if( oAttrib.GetType().Equals(typeof(GlobalizedPropertyAttribute)) )
                        {
                            displayName = ((GlobalizedPropertyAttribute)oAttrib).Name;
                            tableName = ((GlobalizedPropertyAttribute)oAttrib).Table;
                        }
                    }

                    // If no resource table specified by attribute, then build it itself by using namespace and class name.
                    if( tableName.Length == 0 )
                        tableName = basePropertyDescriptor.ComponentType.Namespace + "." + basePropertyDescriptor.ComponentType.Name;

                    // If no display name id is specified by attribute, then construct it by using default display name (usually the property name) 
                    if( displayName.Length == 0 )
                        displayName = this.basePropertyDescriptor.DisplayName;

                    /*
                    // Now use table name and display name id to access the resources.  
                    System.Resources.ResourceManager rm = 
                        new System.Resources.ResourceManager(tableName,basePropertyDescriptor.ComponentType.Assembly);

                    // Get the string from the resources. 
                    // If this fails, then use default display name (usually the property name) 
                    string s = rm.GetString(displayName);
                    this.localizedName = (s!=null)? s : this.basePropertyDescriptor.DisplayName; 
                    */
                    GlobalizedItem item = (GlobalizedItem)_GlobalizedObject._DescriptionMap[displayName];

                    if (item == null)
                        return displayName;

                    return item.DisplayName;
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine(ex.Message + "\r\n" + ex.StackTrace);
                    return displayName;
                }
			}
		}

		public override string Description
		{
            get
			{
                string tableName = "";
                string displayName = "";
                string Description = "";

                try
                {
                    // First lookup the property if there are GlobalizedPropertyAttribute instances
                    // are available. 
                    // If yes, try to get resource table name and display name id from that attribute.
                    foreach( Attribute oAttrib in this.basePropertyDescriptor.Attributes )
                    {
                        if( oAttrib.GetType().Equals(typeof(GlobalizedPropertyAttribute)) )
                        {
                            displayName = ((GlobalizedPropertyAttribute)oAttrib).Name;
                            Description = ((GlobalizedPropertyAttribute)oAttrib).Description;
                            tableName = ((GlobalizedPropertyAttribute)oAttrib).Table;
                        }
                    }

                    // If no resource table specified by attribute, then build it itself by using namespace and class name.
                    if( tableName.Length == 0 )
                        tableName = basePropertyDescriptor.ComponentType.Namespace + "." + basePropertyDescriptor.ComponentType.Name;

                    // If no display name id is specified by attribute, then construct it by using default display name (usually the property name) 
                    if( displayName.Length == 0 )
                        displayName = this.basePropertyDescriptor.DisplayName + "Description";
    				
                    /*
                    // Now use table name and display name id to access the resources.  
                    System.Resources.ResourceManager rm = new System.Resources.ResourceManager(tableName,basePropertyDescriptor.ComponentType.Assembly);

                    // Get the string from the resources. 
                    // If this fails, then use default empty string indictating 'no description' 
                    string s = rm.GetString(displayName);
                    this.localizedDescription = (s!=null)? s : ""; 
                    */

                    // return this.localizedDescription;

                    GlobalizedItem item = (GlobalizedItem)_GlobalizedObject._DescriptionMap[displayName];

                    if (item == null)
                        return Description;

                    return item.Description;
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine(ex.Message + "\r\n" + ex.StackTrace);
                    return displayName;
                }
			}
		}

		public override object GetValue(object component)
		{
			return this.basePropertyDescriptor.GetValue(component);
		}

		public override bool IsReadOnly
		{
			get { return this.basePropertyDescriptor.IsReadOnly; }
		}

		public override string Name
		{
			get { return this.basePropertyDescriptor.Name; }
		}

		public override Type PropertyType
		{
			get { return this.basePropertyDescriptor.PropertyType; }
		}

		public override void ResetValue(object component)
		{
			this.basePropertyDescriptor.ResetValue(component);
		}

		public override bool ShouldSerializeValue(object component)
		{
			return this.basePropertyDescriptor.ShouldSerializeValue(component);
		}

		public override void SetValue(object component, object value)
		{
			this.basePropertyDescriptor.SetValue(component, value);
		}
	}
	#endregion

	#region GlobalizedObject

	/// <summary>
	/// GlobalizedObject implements ICustomTypeDescriptor to enable 
	/// required functionality to describe a type (class).<br></br>
	/// The main task of this class is to instantiate our own property descriptor 
	/// of type GlobalizedPropertyDescriptor.  
	/// </summary>
	public class GlobalizedObject : ICustomTypeDescriptor
	{
        private PropertyDescriptorCollection globalizedProps;
        internal Hashtable _DescriptionMap = new Hashtable();

        protected void AddDescriptionMapItem(string PropertyName, string DisplayName, string Description)
        {
            _DescriptionMap.Add(PropertyName, new GlobalizedItem(DisplayName, Description));
        }

        protected void ClearAllDescriptionMapItems()
        {
            _DescriptionMap.Clear();
        }

		public String GetClassName()
		{
			return TypeDescriptor.GetClassName(this,true);
		}

		public AttributeCollection GetAttributes()
		{
			return TypeDescriptor.GetAttributes(this,true);
		}

		public String GetComponentName()
		{
			return TypeDescriptor.GetComponentName(this, true);
		}

		public TypeConverter GetConverter()
		{
			return TypeDescriptor.GetConverter(this, true);
		}

		public EventDescriptor GetDefaultEvent() 
		{
			return TypeDescriptor.GetDefaultEvent(this, true);
		}

		public PropertyDescriptor GetDefaultProperty() 
		{
			return TypeDescriptor.GetDefaultProperty(this, true);
		}

		public object GetEditor(Type editorBaseType) 
		{
			return TypeDescriptor.GetEditor(this, editorBaseType, true);
		}

		public EventDescriptorCollection GetEvents(Attribute[] attributes) 
		{
			return TypeDescriptor.GetEvents(this, attributes, true);
		}

		public EventDescriptorCollection GetEvents()
		{
			return TypeDescriptor.GetEvents(this, true);
		}

		/// <summary>
		/// Called to get the properties of a type.
		/// </summary>
		/// <param name="attributes"></param>
		/// <returns></returns>
		public PropertyDescriptorCollection GetProperties(Attribute[] attributes)
		{
			if ( globalizedProps == null) 
			{
				// Get the collection of properties
				PropertyDescriptorCollection baseProps = TypeDescriptor.GetProperties(this, attributes, true);

				globalizedProps = new PropertyDescriptorCollection(null);

				// For each property use a property descriptor of our own that is able to be globalized
				foreach( PropertyDescriptor oProp in baseProps )
				{
					globalizedProps.Add(new GlobalizedPropertyDescriptor(oProp, this));
				}
			}
			return globalizedProps;
		}

		public PropertyDescriptorCollection GetProperties()
		{
			// Only do once
			if ( globalizedProps == null) 
			{
				// Get the collection of properties
				PropertyDescriptorCollection baseProps = TypeDescriptor.GetProperties(this, true);
				globalizedProps = new PropertyDescriptorCollection(null);

				// For each property use a property descriptor of our own that is able to be globalized
				foreach( PropertyDescriptor oProp in baseProps )
				{
					globalizedProps.Add(new GlobalizedPropertyDescriptor(oProp, this));
				}
			}
			return globalizedProps;
		}

		public object GetPropertyOwner(PropertyDescriptor pd) 
		{
			return this;
		}
	}

	#endregion
}