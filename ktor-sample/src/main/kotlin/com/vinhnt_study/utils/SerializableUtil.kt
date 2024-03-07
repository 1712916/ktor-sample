package com.vinhnt_study.utils

import com.vinhnt_study.data.models.MoneyType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("DATE", PrimitiveKind.STRING)


    override fun deserialize(decoder: Decoder): Date {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT)
        val date = formatter.parse(decoder.decodeString())
        return date

    }

    override fun serialize(encoder: Encoder, value: Date) {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT)
        encoder.encodeString(formatter.format(value))
    }
}

object LocaleDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override val descriptor: SerialDescriptor
        get() =   PrimitiveSerialDescriptor("LOCALEDATE", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}



object MoneyTypeSerializer : KSerializer<MoneyType> {
    override val descriptor = PrimitiveSerialDescriptor("MoneyType", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): MoneyType {
        return MoneyType.entries[decoder.decodeInt()]

    }

    override fun serialize(encoder: Encoder, value: MoneyType) {

        encoder.encodeInt(value.ordinal)
    }
}