package com.vinhnt_study.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}


object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("DATE", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): Date {
        val formatter = SimpleDateFormat()
        val date = formatter.parse(decoder.decodeString())
        return date

    }

    override fun serialize(encoder: Encoder, value: Date) {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        encoder.encodeString(formatter.format(value))
    }
}