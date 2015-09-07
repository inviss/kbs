using System;

/*
 * FTP Client Class Library 1.1
 * Author: Melih Odaba?
 * Date: 10/June/2003
 * You may distribute this code freely or use it in your applications provided that 
 * you keep the credits of author.
 * You may edit this code, but if you do so please comment your changes clearly and 
 * state that those changes are not affliated with the author.
 * Disclaimer:
 * This code and all of its contents are provided as is. The author is not 
 * responsible for any loss or damage you might encounter due to using this code.
*/

namespace D2net.Common.Ftp
{
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnConnectionStateChanged"/> event.</para>
	/// </summary>
	public delegate void ConnectionStateChanged(object sender, ConnectionStateChangedEventArgs e);

	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncOpenCompleted"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnAsyncCloseCompleted"/> events.</para>
	/// </summary>
	public delegate void AsyncConnectionOpCompleted(object sender, AsyncConnectionOpCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncConnectCompleted"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnAsyncQuitCompleted"/> events.</para>
	/// </summary>
	public delegate void AsyncSessionOpCompleted(object sender, AsyncSessionOpCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncCreateDirectoryCompleted"/> event.</para>
	/// </summary>
	public delegate void AsyncCreateDirectoryCompleted(object sender, AsyncCreateDirectoryCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncDeleteFileCompleted"/> event.</para>
	/// </summary>
	public delegate void AsyncDeleteFileCompleted(object sender, AsyncDeleteFileCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncFindFirstFileCompleted"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnAsyncFindNextFileCompleted"/> events.</para>
	/// </summary>
	public delegate void AsyncFindFileCompleted(object sender, AsyncFindFileCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncGetCurrentDirectoryCompleted"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnAsyncSetCurrentDirectoryCompleted"/> events.</para>
	/// </summary>
	public delegate void AsyncCurrentDirectoryOpCompleted(object sender, AsyncCurrentDirectoryOpCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncGetFileCompleted"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnAsyncPutFileCompleted"/> events.</para>
	/// </summary>
	public delegate void AsyncFileTransferCompleted(object sender, AsyncFileTransferCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncRemoveDirectoryCompleted"/> event.</para>
	/// </summary>
	public delegate void AsyncRemoveDirectoryCompleted(object sender, AsyncRemoveDirectoryCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncRenameFileCompleted"/> event.</para>
	/// </summary>
	public delegate void AsyncRenameFileCompleted(object sender, AsyncRenameFileCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnAsyncSendCommandCompleted"/> event.</para>
	/// </summary>
	public delegate void AsyncSendCommandCompleted(object sender, AsyncSendCommandCompletedEventArgs e);
	/// <summary>
	/// <para>Delegate for <see cref="E:Melih.FTPClient.FTPClient.OnFileUploadDataSent"/> and
	///  <see cref="E:Melih.FTPClient.FTPClient.OnFileDownloadDataReceived"/> events.</para>
	/// </summary>
	public delegate void FileTransferDelegate(object sender, FileTransferEventArgs e);

}
