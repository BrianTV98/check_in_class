package com.example.check_in.ui.qrcode

import android.view.View

interface QRCodeListenner{
    fun btnScanQRcode(view:View)

    fun btnGotoCapture(view: View)
}