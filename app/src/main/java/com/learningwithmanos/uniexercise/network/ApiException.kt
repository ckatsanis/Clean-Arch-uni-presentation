package com.learningwithmanos.uniexercise.network

data class ApiException(val statusCode: String) : Exception()

/**
 * Exception indicating that device is not connected to the internet
 */
class NoInternetException : Exception()

/**
 * Not handled unexpected exception
 */
class UnexpectedException(cause: Exception) : Exception(cause)