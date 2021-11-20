package com.example.domain

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()

    data class Right<out R>(val value: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)
}

suspend fun <L, R, T> Either<L, R>.fold(left: suspend (L) -> T, right: suspend (R) -> T): T =
        when (this) {
            is Either.Left -> left(value)
            is Either.Right -> right(value)
        }

suspend fun <L, R, T> Either<L, R>.flatMap(f:suspend (R) -> Either<L, T>):
        Either<L, T> =
        fold({ this as Either.Left }, f)

suspend fun <L, R, T> Either<L, R>.map(f: suspend (R) -> T): Either<L, T> =
        flatMap { Either.Right(f(it)) }
