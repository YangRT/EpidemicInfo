package com.yang.epidemicinfo.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val NEWS_BASE_URL = "http://www.dzyong.top:3005/"
    private const val COUNTRY_BASE_URL = "https://lab.isaaclin.cn/"
    private const val KNOWLEDGE_BASE_URL = "http://49.232.173.220:3001/"

    private val httpClient = OkHttpClient.Builder()

    private val newsBuilder = Retrofit.Builder()
        .baseUrl(NEWS_BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())

    private val countryBuilder = Retrofit.Builder()
        .client(httpClient.build())
        .baseUrl(COUNTRY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val knowledgeBuilder = Retrofit.Builder()
        .baseUrl(KNOWLEDGE_BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())

    private val newsRetrofit = newsBuilder.build()

    private val countryRetrofit = countryBuilder.build()

    private val knowledgeRetrofit = knowledgeBuilder.build()

    fun<T> createAboutNews(serviceClass:Class<T>):T = newsRetrofit.create(serviceClass)

    fun <T> createAboutCountry(serviceClass: Class<T>):T = countryRetrofit.create(serviceClass)

    fun <T> createAboutKnowledge(serviceClass: Class<T>):T = knowledgeRetrofit.create(serviceClass)

}