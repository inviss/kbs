using System;

namespace D2net.Common.API
{
    public enum WindowMessage : int
    {
        LButtonDown   = 0x0201,
        RButtonDown   = 0x0204,
        MButtonDown   = 0x0207,

        User          = 0x0400,
    }

    public enum MenuFlags : int
    {
        Insert           = 0x00000000,
        Change           = 0x00000080,
        Append           = 0x00000100,
        Delete           = 0x00000200,
        Remvoe           = 0x00001000,
        ByCommand        = 0x00000000,
        ByPosition       = 0x00000400,
        Separator        = 0x00000800,
        Enabled          = 0x00000000,
        Grayed           = 0x00000001,
        Disabled         = 0x00000002,
        Unchecked        = 0x00000000,
        Checked          = 0x00000008,
        UseCheckBitmaps  = 0x00000200,
        String           = 0x00000000,
        Bitmap           = 0x00000004,
        OwnerDraw        = 0x00000100,
        Popup            = 0x00000010,
        MenubarBreak     = 0x00000020,
        Menubreak        = 0x00000040,
        UnHilite         = 0x00000000,
        Hilite           = 0x00000080,
        Default          = 0x00001000,
        SysMenu          = 0x00002000,
        Help             = 0x00004000,
        RightJustify     = 0x00004000,
        MouseSelect      = 0x00008000,
        End              = 0x00000080,
    }

    public enum SystemMenuCommand : int
    {
        Size         = 0xF000,
        Move         = 0xF010,
        Minimize     = 0xF020,
        Maximize     = 0xF030,
        NextWindow   = 0xF040,
        PrevWindow   = 0xF050,
        Close        = 0xF060,
        VScroll      = 0xF070,
        HScroll      = 0xF080,
        MouseMenu    = 0xF090,
        KeyMenu      = 0xF100,
        Arrange      = 0xF110,
        Restore      = 0xF120,
        TaskList     = 0xF130,
        ScreenSave   = 0xF140,
        HotKey       = 0xF150,
        Default      = 0xF160,
        MonitorPower = 0xF170,
        ContextHelp  = 0xF180,
    }
}
