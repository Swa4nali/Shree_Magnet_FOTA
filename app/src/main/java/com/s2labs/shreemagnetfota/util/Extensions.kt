package com.s2labs.shreemagnetfota.util

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun ByteArray.decrypt(password: String): ByteArray {
	val passwordBytes = password.sha256()
	val key = passwordBytes/*.substring(0, 32).toByteArray(Charsets.US_ASCII)*/.copyOf(32)
	val sKeySpec = SecretKeySpec(key, "AES")
	val ivParameterSpec =  IvParameterSpec(ByteArray(16) { 0.toByte() })
	val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
	cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec)
	return cipher.doFinal(this)
}

fun String.sha256(): ByteArray {
	val bytes = this.toByteArray()
	val md = MessageDigest.getInstance("SHA-256")
	return md.digest(bytes)/*.fold("", { str, it -> str + "%02x".format(it) })*/
}