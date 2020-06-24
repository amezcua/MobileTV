package net.byteabyte.mobiletv.data.network.retrofit

class InvalidApiResponseException(source: Exception, message: String? = null) :
    Exception(message, source)