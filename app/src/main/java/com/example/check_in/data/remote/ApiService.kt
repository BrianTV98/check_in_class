package com.example.check_in.data.remote

import com.example.check_in.until.base_url

class ApiService {
    companion object {
        fun getService(): AppReponsitory {
            return RetrofitClient.getClient(base_url)!!.create(AppReponsitory::class.java)
        }
    }
}