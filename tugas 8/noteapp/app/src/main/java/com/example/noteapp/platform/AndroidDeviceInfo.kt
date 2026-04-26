package com.example.noteapp.platform

import android.os.Build

class AndroidDeviceInfo : DeviceInfo {
    override val model: String = Build.MODEL
    override val osVersion: String = Build.VERSION.RELEASE
    override val manufacturer: String = Build.MANUFACTURER
}
