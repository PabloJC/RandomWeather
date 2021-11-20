package com.example.domain

sealed class DomainError
object NoDataFound : DomainError()
object NoLocation : DomainError()
