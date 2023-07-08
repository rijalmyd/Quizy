package com.bangkit23.quizy.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.bangkit23.quizy.R

// Set of Material typography styles to start with
val Typography = Typography(
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_medium)
        )
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_regular)
        )
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = FontFamily(
            Font(R.font.poppins_medium)
        )
    )
)