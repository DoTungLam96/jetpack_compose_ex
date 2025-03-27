sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val code: Int?, val message: String?) : ApiResult<T>()

}


