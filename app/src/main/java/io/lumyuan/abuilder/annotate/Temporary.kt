package io.lumyuan.abuilder.annotate

/**
 * 此注解表示该作用域用于临时测试
 */
@Target(AnnotationTarget.FILE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Temporary