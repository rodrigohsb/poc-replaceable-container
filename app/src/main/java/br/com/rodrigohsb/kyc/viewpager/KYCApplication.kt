package br.com.rodrigohsb.kyc.viewpager

import android.app.Application
import br.com.rodrigohsb.kyc.viewpager.webservice.WebServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit


/**
 * @rodrigohsb
 */
class KYCApplication: Application() {

    companion object {

        val retrofit: Retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.readTimeout(5, TimeUnit.SECONDS)

            Retrofit.Builder()
                    .baseUrl("http://private-28cf5e-kyc6.apiary-mock.com/")
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        val mWebServiceAPI: WebServiceAPI by lazy { retrofit.create(WebServiceAPI::class.java) }
    }
}