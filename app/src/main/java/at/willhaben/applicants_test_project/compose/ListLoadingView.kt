package at.willhaben.applicants_test_project.compose

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import at.willhaben.applicants_test_project.R

@Composable
fun ListLoadingView(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.loading_view_text),
        modifier = modifier.background(Color(R.color.teal_700)),
        textAlign = TextAlign.Center
    )
}