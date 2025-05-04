package com.example.easterexplore.presentation.ui.component

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.easterexplore.R
import com.example.easterexplore.domain.model.Category

/**
 * A composable function that displays a dropdown menu for selecting a category.
 *
 * @param context The context used to retrieve string resources.
 * @param selected The currently selected category.
 * @param onSelected Callback function to handle the selection of a category.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuCategory(
    context: Context,
    selected: Category,
    onSelected: (Category) -> Unit
) {
    // A state variable to control the expanded state of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = context.getString(selected.displayNameRes),
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.dropdown_arrow)
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Category.entries.forEach { category ->
                DropdownMenuItem(
                    text = { Text(context.getString(category.displayNameRes)) },
                    onClick = {
                        expanded = false
                        onSelected(category)
                    }
                )
            }
        }
    }

}