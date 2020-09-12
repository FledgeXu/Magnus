package com.otakusaikou.magnus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MagnusApplication

fun main(args: Array<String>) {
    runApplication<MagnusApplication>(*args)
}