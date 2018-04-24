package com.fc.main.beans;

import java.io.File;

import android.os.Environment;

public interface Const {

	String VIDEO_PATH = Environment.getExternalStorageDirectory()
			+ File.separator + "WORKVIDEOS";
	String USB_PATH = "/mnt/usb";
}
