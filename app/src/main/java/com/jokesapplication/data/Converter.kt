package com.jokesapplication.data

abstract class Converter<I, O>
constructor(private val logger: Logger? = null) {

    abstract fun convert(input: I): O

    fun convertList(list: MutableList<I>, propagateError: Boolean = false): MutableList<O> {
        val out = arrayListOf<O>()
        list.forEach {
            try {
                out.add(convert(it))
            } catch (e: Exception) {
                if (propagateError) {
                    throw e
                }
            }
        }
        return out
    }


    abstract fun convertBack(input: O): I

    fun convertListBack(list: MutableList<O>, propagateError: Boolean = false): MutableList<I> {
        val out = arrayListOf<I>()
        list.forEach {
            try {
                out.add(convertBack(it))
            } catch (e: Exception) {
                logger?.onError(e)
                if (propagateError) {
                    throw e
                }
            }
        }
        return out
    }

    interface Logger {
        fun onError(e: Exception)
    }

}