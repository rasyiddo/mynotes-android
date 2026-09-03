package com.example.mynotes_android

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun FormattedText(
    text: String
) {
    Text(
        text = parseFormattedText(text)
    )
}

fun parseFormattedText(
    text: String
): AnnotatedString {

    val pattern = Regex(
        """(\*\*(.*?)\*\*)|(~~(.*?)~~)|(\*(.*?)\*)"""
    )

    return buildAnnotatedString {

        var lastIndex = 0

        pattern.findAll(text).forEach { match ->

            // Teks biasa sebelum teks yang diformat
            append(
                text.substring(
                    lastIndex,
                    match.range.first
                )
            )

            when {
                // BOLD
                match.value.startsWith("**") -> {

                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(match.groupValues[2])
                    }
                }

                // STRIKETHROUGH
                match.value.startsWith("~~") -> {

                    withStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.LineThrough
                        )
                    ) {
                        append(match.groupValues[4])
                    }
                }

                // ITALIC
                match.value.startsWith("*") -> {

                    withStyle(
                        SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append(match.groupValues[6])
                    }
                }
            }

            lastIndex = match.range.last + 1
        }

        // Menambahkan sisa teks
        append(
            text.substring(lastIndex)
        )
    }
}